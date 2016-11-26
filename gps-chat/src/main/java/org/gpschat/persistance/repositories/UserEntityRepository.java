package org.gpschat.persistance.repositories;

import java.util.List;

import org.gpschat.persistance.domain.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntityRepository extends MongoRepository<UserEntity, String>
{
	public UserEntity findByEmail(String email);

	public UserEntity findByFcmToken(String fcmToken);

	public List<UserEntity> findByUserNameContainingOrFullNameContainingOrEmailContaining(
			String username, String fullName, String email);

	public long countById(String id);
}
