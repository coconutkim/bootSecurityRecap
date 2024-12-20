package me.shinsunyoung.blog.config;


import lombok.RequiredArgsConstructor;
import me.shinsunyoung.blog.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 시큐리티 비활성화
    // 인증, 인가 서비스를 모든 곳에 적용하지 않는다
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console()) // mysql로 데이터베이스를 변경할 때 주석 처리하기
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    // TODO authorizeRequests deprecated
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/login"),
                        // 특정 요청과 일치하는 url에 대한 엑세스
                        new AntPathRequestMatcher("/signup"),
                        new AntPathRequestMatcher("/user"),
                        new AntPathRequestMatcher("/articles") // 로그인 없이도 목록 페이지로
                        )
//                        ,new AntPathRequestMatcher("/api/token")) //
                .permitAll()
                .anyRequest().authenticated()) // 별도의 인가는 필요하지 않지만 인증은 해야 한다
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles", true))
                .logout(logout -> logout
                        .logoutSuccessUrl("/articles")
                        .invalidateHttpSession(true)) // 로그아웃 이후에 세션을 전체 삭제할지 결정
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화. 테스트시에 번거러우니까능
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailService userDetailService) throws Exception{
                                                    // 사용자 정보를 가져오는 서비스
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
















}


