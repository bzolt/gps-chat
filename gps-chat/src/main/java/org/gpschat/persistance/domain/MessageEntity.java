package org.gpschat.persistance.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias("message")
@Document
public class MessageEntity
{
	@Id
	private String id;

	@DBRef
	private UserEntity		sender;
	@DBRef
	private Chat			chat;
	private String			text;
	private Date			dateTime;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint	location;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public UserEntity getSender()
	{
		return sender;
	}

	public void setSender(UserEntity sender)
	{
		this.sender = sender;
	}

	public Chat getChat()
	{
		return chat;
	}

	public void setChat(Chat chat)
	{
		this.chat = chat;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Date getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}

	public GeoJsonPoint getLocation()
	{
		return location;
	}

	public void setLocation(GeoJsonPoint location)
	{
		this.location = location;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chat == null) ? 0 : chat.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageEntity other = (MessageEntity) obj;
		if (chat == null)
		{
			if (other.chat != null)
				return false;
		}
		else if (!chat.equals(other.chat))
			return false;
		if (dateTime == null)
		{
			if (other.dateTime != null)
				return false;
		}
		else if (!dateTime.equals(other.dateTime))
			return false;
		if (sender == null)
		{
			if (other.sender != null)
				return false;
		}
		else if (!sender.equals(other.sender))
			return false;
		return true;
	}

}
