package cokothon.backend.global.oauth2;

import cokothon.backend.global.jwt.TokenProvider;
import cokothon.backend.global.oauth2.dto.CustomOAuth2User;
import cokothon.backend.member.domain.Member;
import cokothon.backend.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;

    @Value("${login.redirect.uri}")
    private String loginRedirectUri;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String kakaoUserId = oAuth2User.getAttributes().get("id").toString();
        Member member = memberRepository.findByKakaoId(kakaoUserId)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .kakaoId(kakaoUserId)
                            .name(oAuth2User.getName())
                            .build();
                    return memberRepository.save(newMember);
                });

        String accessToken = tokenProvider.generateToken(member, Duration.ofDays(30));
        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(loginRedirectUri, accessToken));
    }

    private String getRedirectUrl(String targetUrl, String token) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }
}