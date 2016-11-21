package org.gpschat.persistance.repositories;

import org.gpschat.persistance.domain.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageEntityRepository extends MongoRepository<MessageEntity, String>
{

}
