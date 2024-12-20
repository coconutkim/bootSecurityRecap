package me.shinsunyoung.blog.service;

import lombok.RequiredArgsConstructor;
import me.shinsunyoung.blog.domain.RefreshToken;
import me.shinsunyoung.blog.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private  final RefreshTokenRepository refreshTokenRepository;

    // 전달받은 리프레시 토큰으로 db에서 리프레시 토큰을 검색
    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
