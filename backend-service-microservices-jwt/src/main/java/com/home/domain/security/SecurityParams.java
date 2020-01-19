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
//Expirer jusqu'à 10 jours
public static final long EXPIRATION=10*24*3600;
//Préfix
public static final String HEADER_PREFIX="Bearer ";
}
