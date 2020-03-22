package com.asu.secureBankApp.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

//   @Override
//   public void addInterceptors(InterceptorRegistry registry) {
//
//      // Register admin interceptor with multiple path patterns
//      registry.addInterceptor(new RequestInterceptor())
//              .addPathPatterns(new String[] { "/api", "/api/*" });
//   }

//   @Bean
//   public InternalResourceViewResolver resolver() {
//      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//      //resolver.setPrefix("/WEB-INF/views/");
//      //resolver.setSuffix(".jsp");
//      return resolver;
//   }

	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}