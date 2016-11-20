package org.gpschat.web.config;

import java.util.ArrayList;
import java.util.List;

import org.gpschat.persistance.domain.Login;
import org.gpschat.persistance.repositories.LoginRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MongoUserDetailsService implements UserDetailsService
{
	LoginRepository loginReporitory;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		Login login = loginReporitory.findByEmail(username);

		if (login == null)
		{
			throw new UsernameNotFoundException("Email not found!");
		}

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (String role : login.getAuthorities())
		{
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return new User(login.getEmail(), login.getPassword(), authorities);
	}

	public void setLoginReporitory(LoginRepository loginReporitory)
	{
		this.loginReporitory = loginReporitory;
	}

}
