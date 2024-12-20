package me.shinsunyoung.blog.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequest {
    private String refreshToken;
}
// 리프레시 토큰으로 유효성 검사를 하기에
