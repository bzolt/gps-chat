package org.gpschat.web.config;

import java.util.Collection;
import java.util.List;

import org.gpschat.persistance.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails
{
	public UserEntity getEntity()
	{
		return entity;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2340811135829026848L;

	private List<? extends GrantedAuthority>	authorities;
	private String								password;
	private UserEntity							entity;

	public CustomUserDetails(UserEntity entity, String password,
			List<? extends GrantedAuthority> authorities)
	{
		this.authorities = authorities;
		this.password = password;
		this.entity = entity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return authorities;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public String getUsername()
	{
		return entity.getEmail();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}
