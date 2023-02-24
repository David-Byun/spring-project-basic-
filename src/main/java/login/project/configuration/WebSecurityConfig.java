package login.project.configuration;

import login.project.error.CustomAccessDeniedHandler;
import login.project.error.CustomAuthenticationEntryPoint;
import login.project.security.JwtAuthenticationFilter;
import login.project.security.JwtTokenProvider;
import login.project.security.OAuth2SuccessHandler;
import login.project.service.user.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig  {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encoderPassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected WebSecurityCustomizer configure() throws Exception {
        return (web) -> web.ignoring().antMatchers("/mysql/**");
    }


    //HttpSecurity 설정 : 각종 설정(리소스 접근, login/logout 페이지 인증완료 후 인증 실패시 이동, 커스텀필터, csrf, 강제 https 호출등
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //csfr 사용안함, JWT방식을 제대로 쓰려고 하면 Restful한 API 형태
        http.csrf().disable();

        //프런트엔드가 별도로 존재하여 rest API로 구성한다고 가정(스프링 시큐리티에서 만들어주는 로그인 페이지 안쓰기 위해)
        http.httpBasic().disable();

        //JWT는 stateless이기 때문에 세션 사용 안함
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //URL 인증여부 설정(Oauth시 /login/oauth2/code/google, /user/oauth/password/** 추가)
        http.authorizeRequests().antMatchers("/user/signup", "items/**", "/", "/user/login", "/css/**", "/exception/**", "/favicon.ico", "/items/**", "/login/oauth2/code/google", "/user/oauth/password/**").permitAll().anyRequest().authenticated();

        //JwtFilter 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        //JwtAuthentication exception handling
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        //access Denial handler
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

        //Oauth2 설정

        //구글로그인 성공 후, 구글에서 사용자 정보를 보내오는데 이 때 추가로 진행할 Service 클래스 구현하여 명시
        http.oauth2Login().userInfoEndpoint().userService(new OAuth2UserServiceImpl());

        //인증 성공시 처리를 진행할 successHandler 구현
        http.oauth2Login().successHandler(oAuth2SuccessHandler);

        return http.build();
    }
}





































