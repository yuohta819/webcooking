package com.example.demo.Config;

import com.example.demo.Repository.DataRepositoryAccount;
import com.example.demo.Repository.UserSessionRepository;
import com.example.demo.model.DBAccount;
import com.example.demo.model.DBUserSession;
import com.example.demo.until.JwtFilter;
import com.example.demo.until.JwtUntil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${frontend.local:http://localhost:5173}")
    private String localFrontend;

    @Value("${frontend.deploy:https://webcooking.onrender.com}")
    private String deployedFrontend;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private DataRepositoryAccount account;
    @Autowired
    private JwtUntil jwtService;
    @Autowired
    private UserSessionRepository session;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ✅ Bật CORS sử dụng cấu hình custom bên dưới
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // ❌ Tắt CSRF vì API dùng JWT
                .csrf(csrf -> csrf.disable())

                // 🔐 Cho phép tất cả các request (tùy chỉnh sau nếu cần)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .oauth2Login(oauth -> oauth
                        .successHandler((request, response, authentication) -> {
                            OAuth2User user = (OAuth2User) authentication.getPrincipal();

                            String email = user.getAttribute("email");
                            String name = user.getAttribute("name");

                            DBAccount acc = account.findId(email);
                            if (acc == null) {
                                acc = new DBAccount();
                                acc.setUsername(email);
                                acc.setName(name);
                                acc.setPassword("GOOGLE");
                                account.save(acc);
                            }

                            String jwt = jwtService.generateTokenGoogle(email, acc.getAccountid(), name);
                            DBUserSession se = new DBUserSession();
                            DBAccount infor = account.findId(email);
                            se.setAccountid(infor.getAccountid());
                            se.setToken(jwt);
                            session.save(se);
                            response.sendRedirect("https://webcooking.onrender.com/login/success?token=" + jwt
                                    + "&email=" + email
                                    + "&name=" + URLEncoder.encode(name, StandardCharsets.UTF_8));
                        }))

                // 🚫 Stateless: không dùng session
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🧩 Thêm JWT Filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Cấu hình CORS đúng chuẩn
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(localFrontend, deployedFrontend));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization", "Content-Disposition"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
