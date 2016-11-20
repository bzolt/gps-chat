package org.gpschat.persistance.repositories;

import org.gpschat.persistance.domain.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntityRepository extends MongoRepository<UserEntity, String>
{

}
