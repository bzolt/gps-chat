package org.gpschat.persistance.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias("chat")
@Document
public class Chat
{
	@Id
	private String id;

	private boolean				group;
	@DBRef
	private List<UserEntity>	members	= new ArrayList<>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isGroup()
	{
		return group;
	}

	public void setGroup(boolean group)
	{
		this.group = group;
	}

	public List<UserEntity> getMembers()
	{
		return members;
	}

	public void setMembers(List<UserEntity> members)
	{
		this.members = members;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Chat other = (Chat) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

}
