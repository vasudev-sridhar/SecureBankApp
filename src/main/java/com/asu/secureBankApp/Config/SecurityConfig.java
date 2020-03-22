package com.asu.secureBankApp.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.security.CustomWebAuthenticationDetailsSource;
import com.asu.secureBankApp.security.MyUserDetailsService;
 
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
    private MyUserDetailsService userDetailsService;

//    @Autowired
//    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
//
//    @Autowired
//    private LogoutSuccessHandler myLogoutSuccessHandler;
//
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    private UserRepository userRepository;
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
    	http.httpBasic().disable();
//        http
//        .authorizeRequests()
//         
//         .antMatchers("/").permitAll()
//			.antMatchers("/login").permitAll()
//         .antMatchers("/login**").permitAll()
//         .anyRequest().authenticated()
//        .and()
//        .csrf().disable()
//        .formLogin()
//        .loginPage("/login")
//        .defaultSuccessUrl("/index.html")
//        .failureUrl("/login?error=true")
//        .usernameParameter("username")
//		.passwordParameter("password")
////        .successHandler(myAuthenticationSuccessHandler)
////        .failureHandler(authenticationFailureHandler)
//        .authenticationDetailsSource(authenticationDetailsSource);
//        .and()
//		.logout()
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//		.logoutSuccessUrl("/").and()
		//.exceptionHandling()
		//.accessDeniedPage("/access-denied");
        ;
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
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
    
//    @Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").antMatchers(HttpMethod.POST, "/login");;
//	}
}