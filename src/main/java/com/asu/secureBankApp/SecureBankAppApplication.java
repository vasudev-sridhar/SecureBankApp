package com.asu.secureBankApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asu.secureBankApp.Config.RequestInterceptor;

@SpringBootApplication
@RestController
public class SecureBankAppApplication extends SpringBootServletInitializer {

	@Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(SecureBankAppApplication.class);
   }
	
	@Bean
    public FilterRegistrationBean httpServletRequestReplacedRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestInterceptor());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("RequestInterceptor");
        registration.setOrder(1);
        return registration;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SecureBankAppApplication.class, args);
	}
	
	@RequestMapping(value = "/")
	   public String hello() {
	      return "Hello World from Tomcat";
	   }
}
