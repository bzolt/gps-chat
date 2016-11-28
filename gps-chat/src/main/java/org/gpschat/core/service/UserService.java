package org.gpschat.core.service;

import java.util.ArrayList;
import java.util.List;

import org.gpschat.core.constants.SecurityConstants;
import org.gpschat.core.exceptions.InvalidUserException;
import org.gpschat.core.exceptions.InvalidValueException;
import org.gpschat.core.exceptions.UserNotFoundException;
import org.gpschat.persistance.domain.Login;
import org.gpschat.persistance.domain.ProfilePicture;
import org.gpschat.persistance.domain.UserEntity;
import org.gpschat.persistance.repositories.LoginRepository;
import org.gpschat.persistance.repositories.ProfilePictureRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.gpschat.web.data.User;
import org.gpschat.web.data.ViewDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
	@Autowired
	LoginRepository				loginRepository;
	@Autowired
	ProfilePictureRepository	profilePictureRepository;
	@Autowired
	UserEntityRepository		userEntityRepository;

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

	public User getUser(String userId)
	{
		UserEntity entity = userEntityRepository.findOne(userId);
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
			user.setUserName(modifiedUser.getUsername().trim());
		}
		if (modifiedUser.getFullName() != null && !modifiedUser.getFullName().isEmpty())
		{
			user.setFullName(modifiedUser.getFullName().trim());
		}
		userEntityRepository.save(user);

		if (modifiedUser.getPassword() != null && !modifiedUser.getPassword().isEmpty())
		{
			Login login = loginRepository.findByEmail(user.getEmail());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(
					SecurityConstants.B_CRYPT_STRENGTH);
			login.setPassword(encoder.encode(modifiedUser.getPassword()));
			loginRepository.save(login);
		}
	}

	public ViewDistance getViewDistance(UserEntity user)
	{
		ViewDistance distance = new ViewDistance();
		distance.setValue(user.getViewDistance());
		return distance;
	}

	public void setViewDistance(ViewDistance distance, UserEntity user)
	{
		if (distance == null || distance.getValue() <= 0)
		{
			throw new InvalidValueException();
		}

		user.setViewDistance(distance.getValue());
		userEntityRepository.save(user);
	}

	public void setFcmToken(List<String> token, UserEntity user)
	{
		if (token.size() != 1)
		{
			throw new InvalidValueException();
		}
		user.setFcmToken(token.get(0));
		userEntityRepository.save(user);
	}

	public void logout(UserEntity user)
	{
		user.setFcmToken(null);
		userEntityRepository.save(user);
	}

	public void saveProfilePicture(List<String> picture, UserEntity user)
	{
		System.out.println("asd");
		if (picture.size() != 1)
		{
			throw new InvalidValueException();
		}
		ProfilePicture profilePicture = profilePictureRepository.findByUser(user);
		if (profilePicture == null)
		{
			profilePicture = new ProfilePicture();
			profilePicture.setUser(user);
		}
		profilePicture.setPicture(picture.get(0));
		profilePictureRepository.save(profilePicture);
	}

	public List<String> getProfilePicture(String userId)
	{
		UserEntity entity = userEntityRepository.findOne(userId);
		if (entity == null)
		{
			throw new UserNotFoundException();
		}
		ProfilePicture profilePicture = profilePictureRepository.findByUser(entity);
		List<String> picture = new ArrayList<>();
		picture.add(profilePicture.getPicture());
		return picture;
	}

	private User convertToUser(UserEntity entity)
	{
		User user = new User();
		user.id(entity.getId()).username(entity.getUserName()).fullName(entity.getFullName())
				.email(entity.getEmail());
		return user;
	}
}
