package org.gpschat.web.config;

import org.gpschat.core.constants.SecurityConstants;
import org.gpschat.persistance.repositories.LoginRepository;
import org.gpschat.persistance.repositories.MongoPersistentRememberMeTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	private static final String					TOKEN_KEY	= "asdasfjhakjia";

	@Autowired
	LoginRepository								loginReporitory;

	@Autowired
	MongoPersistentRememberMeTokenRepository	mongoPersistentRememberMeTokenRepository;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(mongoUserDetailsService())
				.passwordEncoder(new BCryptPasswordEncoder(SecurityConstants.B_CRYPT_STRENGTH));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeRequests().antMatchers("/register").permitAll().anyRequest()
				.authenticated().and().rememberMe().key(TOKEN_KEY)
				.rememberMeServices(mongoTokenBasedRememberMeService()).and().httpBasic().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
	}

	@Bean
	public MongoUserDetailsService mongoUserDetailsService()
	{
		MongoUserDetailsService service = new MongoUserDetailsService();
		service.setLoginReporitory(loginReporitory);
		return service;
	}

	@Bean
	public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception
	{
		return new RememberMeAuthenticationFilter(authenticationManager(),
				mongoTokenBasedRememberMeService());
	}

	@Bean
	public MongoTokenRepositoryImpl mongoTokenRepositoryImpl()
	{
		MongoTokenRepositoryImpl repository = new MongoTokenRepositoryImpl();
		repository.setRepository(mongoPersistentRememberMeTokenRepository);
		return repository;
	}

	@Bean
	public MongoTokenBasedRememberMeServices mongoTokenBasedRememberMeService()
	{
		MongoTokenBasedRememberMeServices service = new MongoTokenBasedRememberMeServices(TOKEN_KEY,
				mongoUserDetailsService(), mongoTokenRepositoryImpl());
		service.setAlwaysRemember(true);
		return service;
	}

}
