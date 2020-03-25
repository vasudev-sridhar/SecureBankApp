package com.asu.secureBankApp.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.asu.secureBankApp.Repository.UserRepository;
import com.asu.secureBankApp.security.CustomAuthenticationProvider;
import com.asu.secureBankApp.security.CustomWebAuthenticationDetailsSource;
import com.asu.secureBankApp.security.MyUserDetailsService;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
    private MyUserDetailsService userDetailsService;

	final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
	
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
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
    
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
    	//http.httpBasic().disable();
        http
        .authorizeRequests()
         
         .antMatchers("/").permitAll()
			.antMatchers("/index.html").permitAll()
         .antMatchers("/api/login").permitAll()
         .antMatchers("/api/**").hasAnyAuthority("ADMIN", "TIER1", "TIER2", "USER", "MERCHANT","VIEW_CUSTOMER_ACCOUNT")
         .anyRequest().authenticated()
        .and()
        .csrf().disable()
//        .authenticationProvider(authProvider)
//        .formLogin()
//        .loginPage("/index.html")
//        .defaultSuccessUrl("/index.html")
//        .failureUrl("/login?error=true")
//        .usernameParameter("username")
//		.passwordParameter("password")
//       .successHandler(myAuthenticationSuccessHandler)
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
////        auth.inMemoryAuthentication()
////            .withUser("admin")
////            .password("{noop}password")
////            .roles("USER");
//    	auth.userDetailsService(userDetailsService);
//    }
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("In AuthenticationManagerBuilder");
        auth.authenticationProvider(authProvider());
    }
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
    	System.out.println("In DaoAuthenticationProvider");
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
    
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/modules/**","/resources/**", "/scripts/**", "/css/**", "/js/**", "/images/**").antMatchers(HttpMethod.POST, "/login");;
	}
}