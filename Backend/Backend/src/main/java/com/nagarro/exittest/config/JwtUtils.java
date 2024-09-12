package com.nagarro.exittest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

	private String SECRET_KEY = "nagarroTest";

	/**
	 * Extracts the email from the JWT token.
	 *
	 * @param token The JWT token.
	 * @return The email extracted from the token.
	 */
	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extracts the expiration date from the JWT token.
	 *
	 * @param token The JWT token.
	 * @return The expiration date of the token.
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extracts a specific claim from the JWT token.
	 *
	 * @param token          The JWT token.
	 * @param claimsResolver The function to resolve the desired claim.
	 * @return The value of the resolved claim.
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extracts all claims from the JWT token.
	 *
	 * @param token The JWT token.
	 * @return All claims extracted from the token.
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Checks if the JWT token is expired.
	 *
	 * @param token The JWT token.
	 * @return True if the token is expired, false otherwise.
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Generates a JWT token for the provided user details.
	 *
	 * @param userDetails The user details.
	 * @return The generated JWT token.
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	/**
	 * Creates a JWT token with the specified claims and subject.
	 *
	 * @param claims  The claims to be included in the token.
	 * @param subject The subject (username) of the token.
	 * @return The created JWT token.
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	/**
	 * Validates the JWT token.
	 *
	 * @param token        The JWT token.
	 * @param userDetails The user details.
	 * @return True if the token is valid, false otherwise.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractEmail(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}

