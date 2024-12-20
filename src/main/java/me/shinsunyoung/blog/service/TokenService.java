package me.shinsunyoung.blog.service;

import lombok.RequiredArgsConstructor;
import me.shinsunyoung.blog.config.jwt.TokenProvider;
import me.shinsunyoung.blog.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) throws IllegalAccessException {
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalAccessException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(2));// 2시간짜리 토큰
    }
}

//액세스 토큰 재발급 로직
//        1. 전달받은 리프레시 토큰으로 유효성 검사 진행
//        2. 유효하다고 판단되면 이걸로 사용자 아이디 찾기
//        3. generatetoken 메서드를 호출해서 새로운 액세스 토큰을 발급
