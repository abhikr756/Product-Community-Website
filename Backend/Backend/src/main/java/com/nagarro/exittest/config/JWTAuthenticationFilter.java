package com.nagarro.exittest.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nagarro.exittest.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsServiceImpl detailsServiceImpl;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Retrieve the JWT token from the request header
		final String requestTokenHeader = request.getHeader("Authorization");
		System.out.println(requestTokenHeader);

		String username = null;
		String jwtToken = null;

		// Check if the token is present and starts with "Bearer "
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7); // Removing "Bearer " prefix
			System.out.println(jwtToken);
			
			try {
				// Extract the username from the JWT token
				username = this.jwtUtils.extractEmail(jwtToken);
				System.out.println(username);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
				System.out.println("JWT Token expired");
			}
		} else {
			System.out.println("Invalid Token, does not start with Bearer");
		}

		// Validate security
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = this.detailsServiceImpl.loadUserByUsername(username);
			if (this.jwtUtils.validateToken(jwtToken, userDetails)) {

				// token is valid
				UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);

			}
		} else {
			System.out.println("Token is not valid");
		}

		filterChain.doFilter(request, response);

	}

}
