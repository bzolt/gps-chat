package org.gpschat.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class MongoTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices
{
	private static final String TOKEN_HEADER_NAME = "Auth-Token";

	public MongoTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService,
			PersistentTokenRepository tokenRepository)
	{
		super(key, userDetailsService, tokenRepository);
	}

	@Override
	protected void cancelCookie(HttpServletRequest request, HttpServletResponse response)
	{
		response.setHeader(TOKEN_HEADER_NAME, "");
	}

	@Override
	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request,
			HttpServletResponse response)
	{
		response.setHeader(TOKEN_HEADER_NAME, encodeCookie(tokens));
	}

	@Override
	protected String extractRememberMeCookie(HttpServletRequest request)
	{
		String token = request.getHeader(TOKEN_HEADER_NAME);

		if ((token == null) || (token.length() == 0))
		{
			return null;
		}

		return token;
	}
}
