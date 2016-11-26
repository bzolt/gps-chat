package org.gpschat.persistance.repositories;

import java.util.List;

import org.gpschat.persistance.domain.UserEntity;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodb.client.model.geojson.Point;

public interface UserEntityRepository extends MongoRepository<UserEntity, String>
{
	public UserEntity findByEmail(String email);

	public List<UserEntity> findByUserNameContainingOrFullNameContainingOrEmailContaining(
			String username, String fullName, String email);

	// @Query("{ }")
	public List<UserEntity> findByLocationNear(Point point, Distance max);

	public long countById(String id);
}
