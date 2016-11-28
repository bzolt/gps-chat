package org.gpschat.persistance.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias("user")
@Document
public class UserEntity
{
	@Id
	private String id;

	private String				userName;
	private String				fullName;
	private String				email;
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
	private GeoJsonPoint		location;
	private int					viewDistance;
	private String				fcmToken;
	@DBRef(lazy = true)
	private List<UserEntity>	blockedUsers	= new ArrayList<>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public GeoJsonPoint getLocation()
	{
		return location;
	}

	public void setLocation(GeoJsonPoint location)
	{
		this.location = location;
	}

	public int getViewDistance()
	{
		return viewDistance;
	}

	public void setViewDistance(int viewDistance)
	{
		this.viewDistance = viewDistance;
	}

	public String getFcmToken()
	{
		return fcmToken;
	}

	public void setFcmToken(String fcmToken)
	{
		this.fcmToken = fcmToken;
	}

	public List<UserEntity> getBlockedUsers()
	{
		return blockedUsers;
	}

	public void addBlockedUser(UserEntity blockedUser)
	{
		this.blockedUsers.add(blockedUser);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		UserEntity other = (UserEntity) obj;
		if (email == null)
		{
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		return true;
	}
}
