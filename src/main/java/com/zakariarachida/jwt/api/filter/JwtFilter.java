package com.zakariarachida.jwt.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zakariarachida.jwt.api.repository.UserRepository;
import com.zakariarachida.jwt.api.service.CustomUserDetailService;
import com.zakariarachida.jwt.api.util.JwtUtil;
@Component
public class JwtFilter extends OncePerRequestFilter   {
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	UserRepository repository ;
	@Autowired
	private CustomUserDetailService customUserDetailService ;




	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		if (authorizationHeader!=null ){
			token = authorizationHeader;
			userName = jwtUtil.extractUsername(token);

		}
		if (userName != null){

			UserDetails user =  customUserDetailService.loadUserByUsername(userName);
			if (jwtUtil.validateToken(token,user)){
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						user,null,user.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request,response);
	
	}



}
