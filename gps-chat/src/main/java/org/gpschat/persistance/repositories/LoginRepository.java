package org.gpschat.persistance.repositories;

import org.gpschat.persistance.domain.Login;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<Login, String>
{
	public Login findByEmail(String email);

	public long countByEmail(String email);
}
