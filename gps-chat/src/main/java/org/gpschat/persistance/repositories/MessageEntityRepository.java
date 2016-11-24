package org.gpschat.persistance.repositories;

import java.util.Date;
import java.util.List;

import org.gpschat.persistance.domain.Chat;
import org.gpschat.persistance.domain.MessageEntity;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageEntityRepository extends MongoRepository<MessageEntity, String>
{
	public List<MessageEntity> findFirstByChatAndDateTimeAfter(int limit, Chat chat, Date dateTime);

	public List<MessageEntity> findFirstByChatAndDateTimeBefore(int limit, Chat chat,
			Date dateTime);

	public List<MessageEntity> findFirstByChatIsNullAndDateTimeAfterAndLocationNear(int limit,
			Date dateTime, Point point, Distance max);

	public List<MessageEntity> findFirstByChatIsNullAndDateTimeBeforeAndLocationNear(int limit,
			Date dateTime, Point point, Distance max);
}
