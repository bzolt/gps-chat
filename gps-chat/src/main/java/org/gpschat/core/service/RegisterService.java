package org.gpschat.core.service;

import org.gpschat.core.constants.SecurityConstants;
import org.gpschat.core.exceptions.DuplicateEmailAddressException;
import org.gpschat.persistance.domain.Login;
import org.gpschat.persistance.repositories.LoginRepository;
import org.gpschat.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService
{
	@Autowired
	LoginRepository loginRepository;

	public void registerUser(User user)
	{
		if (loginRepository.countByEmail(user.getEmail()) > 0)
		{
			throw new DuplicateEmailAddressException();
		}
		createLogin(user);
		// TODO Create UserEntity
	}

	private void createLogin(User user)
	{

		Login login = new Login();
		login.setEmail(user.getEmail());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(
				SecurityConstants.B_CRYPT_STRENGTH);
		login.setPassword(encoder.encode(user.getPassword()));
		login.setAuthorities(new String[] { "user" });
		loginRepository.save(login);
	}
}
