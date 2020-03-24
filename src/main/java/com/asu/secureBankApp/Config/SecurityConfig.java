package com.asu.secureBankApp.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.security.CustomWebAuthenticationDetailsSource;
import com.asu.secureBankApp.security.MyUserDetailsService;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
//	@Autowired
//	
//    private MyUserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
//
//    @Autowired
//    private LogoutSuccessHandler myLogoutSuccessHandler;
//
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
//
//    @Autowired
//    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;
//
//    @Autowired
//    private UserRepository userRepository;
//    
    
	@Autowired
	private CustomLoginSuccessHandler successHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private CustomWebAuthenticationDetailsSource authenticationDetailsSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
		http.formLogin().authenticationDetailsSource(authenticationDetailsSource);
		http.authorizeRequests()
	        .antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
	        .antMatchers("/login**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .csrf()
	        .and()
	        .formLogin().loginPage("/login")
	        .failureUrl("/login?error=true")
	        .successHandler(successHandler)
	        .usernameParameter("username")
			.passwordParameter("password")
			.and()
			.logout()
	//        .failureHandler(authenticationFailureHandler)
//	        .authenticationDetailsSource(authenticationDetailsSource)
//	        .and()
//			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").and()
			.exceptionHandling()
			.accessDeniedPage("/access-denied");
		
		 http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.maximumSessions(1);
	        
	        }
  
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) 
//            throws Exception 
//    {
//        auth.inMemoryAuthentication()
//            .withUser("admin")
//            .password("{noop}password")
//            .roles("USER");
//    }
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//    	System.out.println("In AuthenticationManagerBuilder");
//        auth.authenticationProvider(authProvider());
//    }
    
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//    	System.out.println("In DaoAuthenticationProvider");
//        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        // authProvider.setPasswordEncoder(encoder());
//        return authProvider;
//    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
    
	@Bean
	public DaoAuthenticationProvider authProvider() {
		CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
    
//    @Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").antMatchers(HttpMethod.POST, "/login");;
//	}
}