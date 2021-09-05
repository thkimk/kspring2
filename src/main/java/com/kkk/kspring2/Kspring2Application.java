package com.kkk.kspring2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class Kspring2Application {

	public static void main(String[] args) {
		SpringApplication.run(Kspring2Application.class, args);
	}

/*
	@Bean
	public InternalResourceViewResolver setupViewResolver() {

		InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		return resolver;
	}
*/

}
