/**
 * 
 */
package com.home.domain.security;

/**
 * @author ADAM
 *
 */
public interface SecurityParams {
public static final String JWT_HEADER_NAME="Authorization";

public static final String SECRET="cherrate@gmail.com";

public static final long EXPIRATION=10*24*3600*1000;



public static final String HEADER_PREFIX="Bearer ";
}
