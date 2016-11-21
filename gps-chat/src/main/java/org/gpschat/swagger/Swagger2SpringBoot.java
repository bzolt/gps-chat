package org.gpschat.swagger;

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
	@Override
	public void run(String... arg0) throws Exception
	{
		if (arg0.length > 0 && arg0[0].equals("exitcode"))
		{
			throw new ExitException();
		}
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
