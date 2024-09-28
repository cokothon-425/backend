package cokothon.backend.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MemberController {
    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity<Void> kakaoLoginCallback(@RequestParam String code) {
        return ResponseEntity.ok().build();
    }
}
