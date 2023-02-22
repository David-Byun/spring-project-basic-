package login.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import login.project.domain.UserRoleEnum;
import login.project.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    /*
     * 1. 사용자의 Request Header에 토큰을 가져온다
     * 2. 해당 토큰의 유효성 검사를 실시해서 유효하면
     * 3. Authentication 인증 객체를 만들고
     * 4. ContextHolder에 저장해준다
     * 5. Filter 과정이 끝나면 시큐리티에 다음 Filter로 이동
     */
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Value("${jwt.token.key}")
    private String secretKey;

    //토큰 유효시간 설정
    private Long tokenValidTime = 240 * 60 * 1000L;

    //secretkey를 미리 인코딩 해줌
    @PostConstruct
    protected void init() {
      secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //jwt 토큰 생성
    //토큰을 만들 때 핵심적으로 사용자 id, 사용자 권한, 토큰 만료시간이 필요
    //토큰을 만드는 코드는 정해져 있기 때문에 이대로 만들면 됨
    //application.properties에 저장했던 secretkey가 여기서 사용
    public String createToken(String email, UserRoleEnum role) {

        //payload설정
        //registered claims
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("access_token") //토큰 제목
                .setIssuedAt(now) // 발행 시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)); //토큰 만료기한

        //private claims
        claims.put("email", email);
        claims.put("role", role);

        return Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    //JWT 토큰에서 인증정보 조회
    //loadUserByUsername으로 UserDetails 객체에 담아줌
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
    }

    // Request의 Header에서 token값을 가져옴 "Authorization" : "Token값"
    // 로그인 후 토큰을 발행해주면 프런트에서는 이제 모든 요청마다 헤더에 토큰을 담아서 보내게 됨
    // 그 토큰을 헤더에서 가져오는 역할을 하는 메서드
    // 참고로 프런트와 커스텀헤더의 이름을 서로 정해주면 됨. 여기서는 JWT 로 진행
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("JWT");
    }

    //토큰의 유호성 + 만료일자 확인 -> 토큰이 expire되지 않았는지 true/false 반환
    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
