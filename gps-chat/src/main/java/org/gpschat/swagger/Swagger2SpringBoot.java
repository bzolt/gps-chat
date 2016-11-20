package org.gpschat.swagger;

import org.gpschat.persistance.repositories.LoginRepository;
import org.gpschat.persistance.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableSwagger2
@ComponentScan(basePackages = "org.gpschat")
@EnableMongoRepositories(basePackages = "org.gpschat")
public class Swagger2SpringBoot implements CommandLineRunner
{
	@Autowired
	private LoginRepository			loginRepository;

	@Autowired
	private UserEntityRepository	userEntityRepository;

	@Override
	public void run(String... arg0) throws Exception
	{
		if (arg0.length > 0 && arg0[0].equals("exitcode"))
		{
			throw new ExitException();
		}
		//
		// loginRepository.deleteAll();
		//
		// // save a couple of customers
		// loginRepository.save(new Login("Alice", "Smith"));
		// UserEntity asd = new UserEntity();
		// asd.setEmail("asd");
		// asd.setFullName("asd");
		// asd.setUserName("asd");
		// userEntityRepository.save(asd);
		// Login fgh = new Login("Bob", "Smith");
		// fgh.setAsd(asd);
		// loginRepository.save(fgh);
		//
		// // fetch all customers
		// System.out.println("Customers found with findAll():");
		// System.out.println("-------------------------------");
		// for (Login Login : loginRepository.findAll()) {
		// System.out.println(Login);
		// }
		// System.out.println();
		//
		// // fetch an individual Login
		// System.out.println("Login found with findByFirstName('Alice'):");
		// System.out.println("--------------------------------");
		// System.out.println(loginRepository.findByEmail("Alice"));
	}

	public static void main(String[] args) throws Exception
	{
		new SpringApplication(Swagger2SpringBoot.class).run(args);
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator
	{
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode()
		{
			return 10;
		}

	}
}
