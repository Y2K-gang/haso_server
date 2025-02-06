package org.example.haso.global.config;

import lombok.RequiredArgsConstructor;
import org.example.haso.global.auth.JwtAuthenticationFilter;
import org.example.haso.global.auth.JwtExceptionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "org.example.haso.global.auth") // 해당 패키지를 명시적으로 추가
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter authFilter;
    private final JwtExceptionFilter exceptFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 해제
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/member/signup", "/member/signin", "/member/refresh", "/member/validate").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)  // JWT 인증 필터 추가
                .addFilterBefore(exceptFilter, JwtAuthenticationFilter.class);

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // CSRF 보호 해제
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/member/signup", "/member/signin", "/member/refresh", "/member/validate")
//                        .permitAll()  // 인증 없이 접근 가능한 URL
//                        .anyRequest().authenticated()  // 그 외의 요청은 인증 필요
//                )
//                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)  // JWT 인증 필터 추가
//                .addFilterBefore(exceptFilter, JwtAuthenticationFilter.class);  // JWT 예외 필터 추가
//
//        return http.build();  // 필터 체인 반환
//    }


    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:8345"));  // 클라이언트 URL
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    // PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder 빈으로 등록
    }



}

