package cokothon.backend.page.controller;

import cokothon.backend.global.oauth2.dto.CustomOAuth2User;
import cokothon.backend.page.dto.HomeResponseDTO;
import cokothon.backend.page.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @Operation(summary = "홈 화면을 위한 API")
    @GetMapping
    public ResponseEntity<HomeResponseDTO> getHomePageData(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) {
        return ResponseEntity.ok(homeService.getHomePageData(customOAuth2User.getMemberId()));
    }
}
