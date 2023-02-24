package login.project.service.user;

import login.project.repository.user.OAuthDto;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService= new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        //현재 로그인이 진행되는 서비스가 어디 id인지 : 어떤 서비스인지 구분하는 코드. google / naver / kakao
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //userNameAttributeName : OAuth2 로그인 진행시 키가 되는 필드값
        //일종의 Primary Key. 다만 구글만 지원하는 점이 있다.
        //OAuth 여러 서비스로 로그인할 수 있도록 구현하기 때문에 registrationID Dto 생성
        OAuthDto oAuthDto = OAuthDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        log.info("{}", oAuthDto);

        var memberAttribute = oAuthDto.convertToMap();

        //return 후 OAuth2LoginAuthenticationProvider 117번째 줄의 OAuth2User 객체로 정의됨
        //OAuth2LoginAuthenticationFilter를 거치면서 Authentication에 저장
        //그래서 handler에서 꺼내올 수 있음
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), memberAttribute, "email"
        );
    }
}
