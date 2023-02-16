package login.project.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import login.project.service.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig   {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

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
        //csfr 사용안함
        http.csrf().disable();

        //URL 인증여부 설정
        http.authorizeRequests().antMatchers("/users/sighup", "/", "/user/login", "/css/**").permitAll().anyRequest().authenticated();

        //로그인 관련 설정
        http.formLogin().loginPage("/user/login").loginProcessingUrl("/user/login") //Post요청
                .defaultSuccessUrl("/").failureUrl("/user/login?error").permitAll();

        //로그아웃 설정
        http.logout().logoutUrl("/user/logout").logoutSuccessUrl("/");

        //비 인가자 요청시 보낼 api url
        http.exceptionHandling().accessDeniedPage("/forbidden");

        return http.build();
    }
}
