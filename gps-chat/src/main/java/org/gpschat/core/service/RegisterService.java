package org.gpschat.core.service;

import org.gpschat.core.constants.ChatConstants;
import org.gpschat.core.constants.SecurityConstants;
import org.gpschat.core.exceptions.DuplicateEmailAddressException;
import org.gpschat.persistance.domain.Login;
import org.gpschat.persistance.domain.UserEntity;
import org.gpschat.persistance.repositories.LoginRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.gpschat.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
	@Autowired
	LoginRepository			loginRepository;
	@Autowired
	UserEntityRepository	userEntityRepository;

	public void registerUser(User user)
	{
		if (loginRepository.countByEmail(user.getEmail()) > 0)
		{
			throw new DuplicateEmailAddressException();
		}
		createLogin(user);
		createUserEntity(user);
	}

	private void createLogin(User user)
	{

		Login login = new Login();
		login.setEmail(user.getEmail().trim());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(
				SecurityConstants.B_CRYPT_STRENGTH);
		login.setPassword(encoder.encode(user.getPassword()));
		login.setAuthorities(new String[] { "user" });
		loginRepository.save(login);
	}

	private void createUserEntity(User user)
	{
		UserEntity entity = new UserEntity();
		entity.setUserName(user.getUsername().trim());
		entity.setFullName(user.getFullName().trim());
		entity.setEmail(user.getEmail().trim());
		entity.setViewDistance(ChatConstants.DEFAULT_VIEW_DISTANCE);
		userEntityRepository.save(entity);
	}
}
