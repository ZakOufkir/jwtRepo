package com.zakariarachida.jwt.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.zakariarachida.jwt.api.filter.JwtFilter;
import com.zakariarachida.jwt.api.repository.UserRepository;
import com.zakariarachida.jwt.api.service.CustomUserDetailService;
import com.zakariarachida.jwt.api.util.JwtUtil;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.zakariarachida.api")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtFilter jwtFilter;


	@Autowired
	CustomUserDetailService customUserDetailService;
	@Autowired
	UserRepository repository;
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception    {
		authenticationManagerBuilder.userDetailsService(customUserDetailService);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception  {
		return super.authenticationManagerBean();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{


		http.csrf().disable().authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest().authenticated()
		.and().exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}

