package org.gpschat.persistance.repositories;

import java.util.List;

import org.gpschat.persistance.domain.Chat;
import org.gpschat.persistance.domain.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String>
{
	public List<Chat> findByMembersContaining(UserEntity user);
}
