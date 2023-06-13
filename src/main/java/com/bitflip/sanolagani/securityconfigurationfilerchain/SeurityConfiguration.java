package com.bitflip.sanolagani.securityconfigurationfilerchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.bitflip.sanolagani.serviceimpl.CustomUserDetailService;
@Configuration
@EnableWebSecurity
public class SeurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	CustomUserDetailService customuserdetailservice;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	 http
	    .authorizeRequests()
	    .antMatchers("/login","/register","/otpverify","/companyregister","/companyverify",
	    		"/tables","/addCompany","/tables/edit/**").permitAll()
	    .antMatchers("/admin/**").hasRole("ADMIN")
	    .anyRequest().authenticated()

	    .and()
	    .formLogin()
	    .loginPage("/login")
	    .defaultSuccessUrl("/home")
	    .failureUrl("/login?error=true")
	    .usernameParameter("email")
	    .passwordParameter("password")
	    .loginProcessingUrl("/processlogin")
	    .permitAll()
	    .and()
	    .logout()
	    .logoutUrl("/logout")
	    .and()
	    .exceptionHandling();
	     
}
@Bean
public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

//@Bean
//public DaoAuthenticationProvider daoAuthenticationProvider() {
//	DaoAuthenticationProvider daoauth=new DaoAuthenticationProvider();
//	daoauth.setUserDetailsService(customuserdetailservice);
//	daoauth.setPasswordEncoder(passwordEncoder());
//	return daoauth;
//}
//
//@Override
//protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//auth.authenticationProvider(daoAuthenticationProvider());
//
//}


@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customuserdetailservice)
        .passwordEncoder(passwordEncoder());
    
   
    
}

@Override
public void configure(WebSecurity web) throws Exception {
	web.ignoring()
	.antMatchers("/resources/**","/static/**","/css/**","/photos/**");
}

//private AuthenticationSuccessHandler successHandler() {
//    return (request, response, authentication) -> {
//        // Redirect the user to the originally requested page
//       response.sendRedirect(request.getRequestURI());
//   };
//}
}

