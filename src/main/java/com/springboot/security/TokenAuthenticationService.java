package com.springboot.security;

import com.springboot.controller.RestApiController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {

	static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse res, String username) {
		
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	static Authentication getAuthentication(HttpServletRequest request) throws AuthenticationServiceException{
		String token = request.getHeader(HEADER_STRING);
		if (token != null && !token.isEmpty()) {
			// parse the token.
			logger.info("try with token: {}", token );
			String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject();

			Date expirationDate = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody().getExpiration();
			if(StringUtils.isEmpty(user)){
				return null;
			}
			if(isExpirate(expirationDate)){
				throw new AuthenticationServiceException("Token has expirated");
			}

			return new UsernamePasswordAuthenticationToken(user, null, emptyList());
		}
		return null;
	}
	private static boolean isExpirate(Date expirationDate){
		return expirationDate.before(new Date(System.currentTimeMillis()) );
	}
}
// // http://www.svlada.com/jwt-token-authentication-with-spring-boot/
