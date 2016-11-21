package org.gpschat.core.service;

import java.util.ArrayList;
import java.util.List;

import org.gpschat.core.exceptions.InvalidUserException;
import org.gpschat.core.exceptions.InvalidValueException;
import org.gpschat.core.exceptions.UserNotFoundException;
import org.gpschat.persistance.domain.Login;
import org.gpschat.persistance.domain.UserEntity;
import org.gpschat.persistance.repositories.LoginRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.gpschat.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
	@Autowired
	LoginRepository			loginRepository;
	@Autowired
	UserEntityRepository	userEntityRepository;

	public void blockUser(String blockedId, UserEntity user)
	{
		if (blockedId.equals(user.getId()))
		{
			throw new InvalidUserException();
		}
		if (userEntityRepository.countById(blockedId) == 0)
		{
			throw new UserNotFoundException();
		}

		UserEntity blockedUser = userEntityRepository.findOne(blockedId);
		user.addBlockedUser(blockedUser);
		userEntityRepository.save(user);
	}

	public User getUser(String id)
	{
		UserEntity entity = userEntityRepository.findOne(id);
		if (entity == null)
		{
			throw new UserNotFoundException();
		}
		return convertToUser(entity);
	}

	public List<User> queryUsers(String queryString)
	{
		List<UserEntity> entities = userEntityRepository
				.findByUserNameContainingOrFullNameContainingOrEmailContaining(queryString,
						queryString, queryString);
		List<User> users = new ArrayList<>();
		for (UserEntity userEntity : entities)
		{
			users.add(convertToUser(userEntity));
		}
		return users;
	}

	public void updateUser(User modifiedUser, UserEntity user)
	{
		if (modifiedUser.getUsername() != null && !modifiedUser.getUsername().isEmpty())
		{
			user.setUserName(modifiedUser.getUsername());
		}
		if (modifiedUser.getFullName() != null && !modifiedUser.getFullName().isEmpty())
		{
			user.setFullName(modifiedUser.getFullName());
		}
		userEntityRepository.save(user);

		if (modifiedUser.getPassword() != null && !modifiedUser.getPassword().isEmpty())
		{
			Login login = loginRepository.findByEmail(user.getEmail());
			login.setPassword(modifiedUser.getPassword());
			loginRepository.save(login);
		}
	}

	public int getViewDistance(UserEntity user)
	{
		return user.getViewDistance();
	}

	public void setViewDistance(Integer distance, UserEntity user)
	{
		if (distance == null || distance <= 0)
		{
			throw new InvalidValueException();
		}

		user.setViewDistance(distance);
	}

	private User convertToUser(UserEntity entity)
	{
		User user = new User();
		user.id(entity.getId()).username(entity.getUserName()).fullName(entity.getFullName())
				.email(entity.getEmail());
		return user;
	}
}
