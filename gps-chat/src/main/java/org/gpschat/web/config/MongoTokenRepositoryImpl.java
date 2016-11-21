package org.gpschat.web.config;

import java.util.Date;

import org.gpschat.persistance.domain.MongoPersistentRememberMeToken;
import org.gpschat.persistance.repositories.MongoPersistentRememberMeTokenRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class MongoTokenRepositoryImpl implements PersistentTokenRepository
{
	private MongoPersistentRememberMeTokenRepository repository;

	public synchronized void createNewToken(PersistentRememberMeToken token)
	{
		if (repository.countBySeries(token.getSeries()) > 0)
		{
			throw new DataIntegrityViolationException(
					"Series Id '" + token.getSeries() + "' already exists!");
		}

		repository.insert(new MongoPersistentRememberMeToken(token));
	}

	public synchronized void updateToken(String series, String tokenValue, Date lastUsed)
	{
		MongoPersistentRememberMeToken token = getTokenForSeries(series);

		MongoPersistentRememberMeToken newToken = new MongoPersistentRememberMeToken(
				token.getUsername(), series, tokenValue, new Date());

		repository.delete(token);

		repository.insert(new MongoPersistentRememberMeToken(newToken));
	}

	public synchronized MongoPersistentRememberMeToken getTokenForSeries(String seriesId)
	{
		return repository.findBySeries(seriesId);
	}

	public synchronized void removeUserTokens(String username)
	{
		repository.removeByUsername(username);
	}

	public void setRepository(MongoPersistentRememberMeTokenRepository repository)
	{
		this.repository = repository;
	}
}