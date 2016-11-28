package org.gpschat.persistance.repositories;

import org.gpschat.persistance.domain.ProfilePicture;
import org.gpschat.persistance.domain.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfilePictureRepository extends MongoRepository<ProfilePicture, String>
{
	public ProfilePicture findByUser(UserEntity user);
}
