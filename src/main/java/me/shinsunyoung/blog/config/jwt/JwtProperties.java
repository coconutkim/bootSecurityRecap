package me.shinsunyoung.blog.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티값을 가져와서 사용하는 애너테이션
//yml 파일에 있는 jwt로 시작하는 값을 자동으로 바인딩한다
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
