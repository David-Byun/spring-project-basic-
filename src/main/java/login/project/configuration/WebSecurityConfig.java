package login.project.configuration;

import login.project.error.CustomAccessDeniedHandler;
import login.project.error.CustomAuthenticationEntryPoint;
import login.project.security.JwtAuthenticationFilter;
import login.project.security.JwtTokenProvider;
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
public class WebSecurityConfig   {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encoderPassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected WebSecurityCustomizer configure() throws Exception {
        return (web) -> web.ignoring().antMatchers("/mysql/**");
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //csfr 사용안함, JWT방식을 제대로 쓰려고 하면 Restful한 API 형태
        http.csrf().disable();

        //프런트엔드가 별도로 존재하여 rest API로 구성한다고 가정(스프링 시큐리티에서 만들어주는 로그인 페이지 안쓰기 위해)
        http.httpBasic().disable();

        //JWT는 stateless이기 때문에 세션 사용 안함
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //URL 인증여부 설정
        http.authorizeRequests().antMatchers("/users/signup", "/", "/user/login", "/css/**", "/exception/**", "/favicon.ico").permitAll().anyRequest().authenticated();

        //JwtFilter 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        //JwtAuthentication exception handling
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        //access Denial handler
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

        return http.build();
    }
}
