package com.quangdat.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quangdat.entities.PersistentLogin;

@Transactional
@Repository("persistentTokenRepository")
public class PersistentTokenDao extends AbstractDaoImpl<String, PersistentLogin> implements PersistentTokenRepository{

	private static final Logger logger = LoggerFactory.getLogger(PersistentTokenDao.class);
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		logger.info("Create new token : {} ", token.getUsername());
		PersistentLogin persistentLogin = new PersistentLogin(token.getSeries(), token.getUsername(), token.getTokenValue(), token.getDate());
		add(persistentLogin);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		logger.info("Get token for series : {} ", series);
		try {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.eq("series", series));
			PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();
			return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(), persistentLogin.getToken(), persistentLogin.getLast_used());
		} catch (Exception e) {
			logger.info("Token not found :{}");
			return null;
		}
	}

	@Override
	public void removeUserTokens(String username) {
		logger.info("Remove token : {} ", username);
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("username", username));
		PersistentLogin persistentLogin = (PersistentLogin) criteria.uniqueResult();
		
		if(persistentLogin != null)			
			delete(persistentLogin);
	}

	@Override
	public void updateToken(String series, String token, Date last_used) {
		PersistentLogin persistentLogin = get(series);
		persistentLogin.setToken(token);
		persistentLogin.setLast_used(last_used);
		update(persistentLogin);
	}
	
}
