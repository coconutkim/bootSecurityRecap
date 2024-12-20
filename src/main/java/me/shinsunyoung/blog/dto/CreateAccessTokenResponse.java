package me.shinsunyoung.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAccessTokenResponse {
    private String accessToken;
}
// 유효성 검사가 끝나면 액세스 토큰을 다시 발급해주니까