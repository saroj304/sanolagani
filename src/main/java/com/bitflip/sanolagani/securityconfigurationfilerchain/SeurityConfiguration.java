package com.bitflip.sanolagani.securityconfigurationfilerchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bitflip.sanolagani.service.CustomUserDetailService;
@Configuration
@EnableWebSecurity
public class SeurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	CustomUserDetailService customuserdetailservice;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	 http
	    .authorizeRequests()
<<<<<<< HEAD
<<<<<<< HEAD
	    .antMatchers("/login","/register","/otpverify").permitAll()
=======
	    .antMatchers("/login","/register").permitAll()
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
=======
	    .antMatchers("/login","/register","/otpverify").permitAll()
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9
	    .anyRequest()
	    .authenticated()
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
	    .invalidateHttpSession(true)
	    .deleteCookies("JSESSIONID")
	    .and()
	    .exceptionHandling()
	    .and()
	    .csrf()
	    .disable()
	    .httpBasic();
}
@Bean
public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

@Bean
public DaoAuthenticationProvider daoAuthenticationProvider() {
	DaoAuthenticationProvider daoauth=new DaoAuthenticationProvider();
	daoauth.setUserDetailsService(customuserdetailservice);
	daoauth.setPasswordEncoder(passwordEncoder());
	return daoauth;
}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
auth.authenticationProvider(daoAuthenticationProvider());
}
@Override
public void configure(WebSecurity web) throws Exception {
	web.ignoring()
	.antMatchers("/resources/**");
}

}
