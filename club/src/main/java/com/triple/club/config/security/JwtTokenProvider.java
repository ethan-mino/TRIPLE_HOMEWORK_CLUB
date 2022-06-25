package com.triple.club.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    private final SecurityProperties securityProperties;

    public JwtTokenProvider(UserDetailsService userDetailsService,
                            SecurityProperties securityProperties) {
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
    }

    // Jwt 토큰 생성
    public String createToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now)   // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + securityProperties.getJwt().getEXPIRATION_TIME())) // 만료 기간
                .signWith(SignatureAlgorithm.HS512, securityProperties.getJwt().getSecret()) // 암호화 알고리즘, secret 값
                .compact(); // Token 생성
    }

    // 인증 성공시 SecurityContextHolder에 저장할 Authentication 객체 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt Token에서 User PK 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(securityProperties.getJwt().getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String token = req.getHeader(securityProperties.getJwt().getHEADER_STRING());
        if(token != null) token = token.replace(securityProperties.getJwt().getTOKEN_PREFIX(), "");
        return token;
    }

    // Jwt Token의 유효성 및 만료 기간 검사
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(securityProperties.getJwt().getSecret())
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}