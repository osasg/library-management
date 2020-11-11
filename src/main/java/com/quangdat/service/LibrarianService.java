package com.quangdat.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.quangdat.dao.ApplicationDao;
import com.quangdat.dao.LibraryDao;
import com.quangdat.dao.RoleDao;
import com.quangdat.dao.UserDao;
import com.quangdat.entities.Application;
import com.quangdat.entities.Book;
import com.quangdat.entities.Library;
import com.quangdat.entities.Role;
import com.quangdat.entities.RoleName;
import com.quangdat.entities.Shelf;
import com.quangdat.entities.User;
import com.quangdat.model.ApplicationModel;
import com.quangdat.util.SecurityUtil;

@Transactional(rollbackFor = Exception.class)
@Service
public class LibrarianService {

	private static final String UPLOADED_FOLDER = "/home/quangdatpham/Pictures/libsmanager/";
	
	@Autowired
	private LibraryDao libraryDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	public Library getLibrary(Long id) {
		Library library = libraryDao.get(id);
		if(!check(library))
			return null;
		return library;
	}
	
	public void createLibrary(Library library) {
		User user = userDao.get(SecurityUtil.getPricipal());
		Role role_LIBRARIAN = roleDao.getRoleByName(RoleName.LIBRARIAN);
		if(! user.getRoles().contains(role_LIBRARIAN)) {//not contain role LIBRARIAN
			user.addRole(role_LIBRARIAN);//set
				//reload authentication
			reloadAuthentication(user);
		}
		library.addLibrarian(user);
		user.addOwnedLibrary(library);
	}
	
	public Boolean updateLibrary(Library newLibrary) {
		Library library = libraryDao.get(newLibrary.getId());
		if(! check(library))
			return Boolean.FALSE;
		library.setName(newLibrary.getName());
		return Boolean.TRUE;
	}
	
	public void changeImageLibrary(MultipartFile avatarFile, MultipartFile coverImageFile, Long library_id) {
		Library library = libraryDao.get(library_id);
		try {
			if(avatarFile.getSize() != 0) {			//avatar
				if(library.getAvatar() == null) {
					library.setAvatar(saveImage(avatarFile, "NEW"));
				} else {
					library.setAvatar(saveImage(avatarFile, library.getAvatar()));
				}
			}
			if(coverImageFile.getSize() != 0) {		//coverImage
				if(library.getCoverImage() == null) {
					library.setCoverImage(saveImage(coverImageFile, "NEW"));
				} else {
					library.setCoverImage(saveImage(coverImageFile, library.getCoverImage()));
				}
			}
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Autowired
	EmployeeService employeeService;
	public Boolean deleteLibrary(Long library_id) {
		File fileImage = new File("");
		Library library = libraryDao.get(library_id);
		if(! check(library))
			return Boolean.FALSE;
		User user = userDao.get(SecurityUtil.getPricipal());
		library.deleteAllLibrarian(roleDao.getRoleByName(RoleName.LIBRARIAN));
		library.deleteAllEmployee(roleDao.getRoleByName(RoleName.EMPLOYEE));
		
		for(Shelf s: new ArrayList<Shelf>(library.getShelves())) {
			employeeService.deleteShelf(s.getId());
		}
		
		fileImage = new File(UPLOADED_FOLDER + library.getAvatar());
		fileImage.delete();		// delete avatar
		fileImage = new File(UPLOADED_FOLDER + library.getCoverImage());
		fileImage.delete();		// delete coverImage
		libraryDao.delete(library);//delete library
		
		// this librarian own how many library // delete role_LIBRARIAN if == 0
		if(user.getOwnedLibraries().size() == 0) {
			Role role_LIBRARIAN = roleDao.getRoleByName(RoleName.LIBRARIAN);
			user.deleteRole(role_LIBRARIAN);
			//reload authentication
			reloadAuthentication(user);
		}
		return Boolean.TRUE;
	}

	public List<User> getEmployeesForLibrary(Long library_id){
		Library library = libraryDao.get(library_id);
		if(! check(library))
			return null;
		library.getEmployees().size();
		return library.getEmployees();
	}
	
	public List<Book> getBooksForLibrary(Long library_id) {
		List<Book> books = new ArrayList<>();
		Library library = libraryDao.get(library_id);
		if(! check(library))
			return null;
		for(Shelf s:library.getShelves()) {
			books.addAll(s.getBooks());
		}
		return books;
	}
	
	public List<Library> getOwnedLibraries() {
		List<Library> libraries =  userDao.get(SecurityUtil.getPricipal()).getOwnedLibraries();
		libraries.size();
		return libraries;
	}
	
	public List<ApplicationModel> getApplicationModels(){
		List<ApplicationModel> applicationModels = new ArrayList<>();
		for(Library lib:getOwnedLibraries()) {
			for(Application a:lib.getApplications()) {
				applicationModels.add(new ApplicationModel(a.getId(), a.getUsername(), lib.getId(), lib.getName()));
			}
		}
		return applicationModels;
	}
	
	public List<ApplicationModel> getApplicationsByLibrary(Long library_id) {
		if(check(libraryDao.get(library_id)))
			return null;
		List<ApplicationModel> applications = new ArrayList<>();
		Library lib = libraryDao.get(library_id);
		for(Application a:lib.getApplications()) {
			applications.add(new ApplicationModel(a.getId(), a.getUsername(), lib.getId(), lib.getName()));
		}
		return applications;
	}
	
	public Boolean applyEmployee(Long application_id) {
		Application application = applicationDao.get(application_id);
		try {
			if(application == null)
				throw new Exception("Application not found");
			User employee = userDao.get(application.getUsername());
			Library library = application.getLibrary();
			Role role_EMPLOYEE = roleDao.getRoleByName(RoleName.EMPLOYEE);
			if(! library.getEmployees().contains(employee) && check(library)) {
				if(! employee.getRoles().contains(role_EMPLOYEE))
					employee.addRole(role_EMPLOYEE);//set role EMPLOYEE if not own
				employee.addServedLibrary(library);
				library.addEmployee(employee);//add employee
				applicationDao.delete(application);//delete application
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Boolean.FALSE;
	}
	
	public void rejectEmployee(String username, Long library_id) {
		User employee = userDao.get(username);
		Library library = libraryDao.get(library_id);
		employee.deleteServedLibrary(library);
		library.deleteEmployee(employee);
	}
	
	public List<Shelf> getShelves(Long library_id) {
		Library library = libraryDao.get(library_id);
		if(check(library))
			return null;
		return library.getShelves();
	}
	
	//check if this library is owned by that user
	private Boolean check(Library library) {
		return getOwnedLibraries().contains(library);
	}
	
	private void reloadAuthentication(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role role:user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		}
		org.springframework.security.core.userdetails.User newUser = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
		Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, newUser.getPassword(), authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private String saveImage(MultipartFile file, String saveMode) throws IOException {
		String fileName = "";
		if(file.getSize() != 0) {
			fileName = new Date().getTime() + file.getOriginalFilename();
			if(saveMode.equalsIgnoreCase("NEW")) {//new
				FileCopyUtils.copy(file.getBytes(), new File(UPLOADED_FOLDER + fileName));
			} else {//update mode = oldFileName
				File oldFile = new File(UPLOADED_FOLDER + saveMode);
				if(oldFile.delete()) {
					FileCopyUtils.copy(file.getBytes(), new File(UPLOADED_FOLDER + fileName));
				}
			}
		}
		return fileName;
	}
}
