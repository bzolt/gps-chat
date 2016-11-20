package org.gpschat.persistance.repositories;

import org.gpschat.persistance.domain.MongoPersistentRememberMeToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPersistentRememberMeTokenRepository
		extends MongoRepository<MongoPersistentRememberMeToken, String>
{
	public MongoPersistentRememberMeToken findBySeries(String series);

	public long countBySeries(String series);

	public void removeByUsername(String username);
}
