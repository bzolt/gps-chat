package org.gpschat.persistance.repositories;

import java.util.Date;
import java.util.List;

import org.gpschat.persistance.domain.Chat;
import org.gpschat.persistance.domain.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MessageEntityRepository extends MongoRepository<MessageEntity, String>
{
	public long countByChat(Chat chat);

	public List<MessageEntity> findFirst10ByChatAndDateTimeAfter(Chat chat, Date dateTime);

	public List<MessageEntity> findFirst10ByChatAndDateTimeBefore(Chat chat, Date dateTime);

	public List<MessageEntity> findFirst10ByChatIsNullAndDateTimeAfterAndLocationNear(Date dateTime,
			Point point, Distance max);

	public List<MessageEntity> findFirst10ByChatIsNullAndDateTimeBeforeAndLocationNear(
			Date dateTime, Point point, Distance max);

	public List<MessageEntity> findFirst10ByChatIsNullAndDateTimeBeforeAndLocationWithin(
			Date dateTime, Circle circle, Pageable pageable);

	@Query(" { 'chat': null, 'dateTime': { '$lt': ?0 }, 'location': { '$geoWithin': { '$centerSphere': [[ ?1, ?2], ?3 ]} }}")
	public List<MessageEntity> findCommonBefore(Date dateTime, double longitude, double latitude,
			double distance, Pageable pageable);

	@Query(" { 'chat': null, 'dateTime': { '$gt': ?0 }, 'location': { '$geoWithin': { '$centerSphere': [[ ?1, ?2], ?3 ]} }}")
	public List<MessageEntity> findCommonAfter(Date dateTime, double longitude, double latitude,
			double distance, Pageable pageable);
}
