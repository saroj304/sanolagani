package com.bitflip.sanolagani.securityconfigurationfilerchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
	    		"/managecompany/edit/**","/addCompany",
	    	    "/","/raisecapital","/documents",
	    		"/refunddata","/admin/**","/resetpassword","/updatepassword","/forgotpassword",
	    		"/changepassword","/reset/reports").permitAll()
	//    .antMatchers("/changepassword").hasAnyAuthority("ROLE_USER","ROLE_COMPANY","ROLE_ADMIN")
	    //.antMatchers("/admin/**").hasRole("ADMIN")
	    .anyRequest().authenticated()
	    .and()
	    .formLogin()
	    .loginPage("/login")
	    .defaultSuccessUrl("/home", true)
	    .failureUrl("/login?error=true")
	    .usernameParameter("email")
	    .passwordParameter("password")
	    .loginProcessingUrl("/processlogin")
	    .permitAll()
	    .and()
			 .logout()
			 .logoutSuccessUrl("/logout")
	    .and()
	    .exceptionHandling();
	     
}
@Bean
public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}



@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(customuserdetailservice)
        .passwordEncoder(passwordEncoder());
    
}

@Override
public void configure(WebSecurity web) throws Exception {
	web.ignoring()
	.antMatchers("/resources/**","/static/**","/css/**","/photos/**","/documents/**","classpath:/static/documents/**");
}



}

