package com.likelion.plantication.auth.application;

import com.likelion.plantication.auth.domain.UserInfo;
import com.likelion.plantication.auth.domain.GoogleToken;
import com.likelion.plantication.auth.api.dto.response.GoogleResDto;
import com.likelion.plantication.global.exception.CustomException;
import com.likelion.plantication.global.exception.code.ErrorCode;
import com.likelion.plantication.global.jwt.TokenProvider;
import com.likelion.plantication.user.domain.Role;
import com.likelion.plantication.user.domain.User;
import com.likelion.plantication.user.domain.repository.UserRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {
    @Value("${client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    // Google
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/api/v1/auth/code/google";

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

//    public ResponseEntity<String> getGoogleToken(String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, String> params = Map.of(
//                "code", code,
//                "scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
//                "client_id", GOOGLE_CLIENT_ID,
//                "client_secret", GOOGLE_CLIENT_SECRET,
//                "redirect_uri", GOOGLE_REDIRECT_URI,
//                "grant_type", "authorization_code"
//        );
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, request, String.class);
//
//        if (responseEntity.getStatusCode().is2xxSuccessful()) {
//            String json = responseEntity.getBody();
//            Gson gson = new Gson();
//
//            String accessToken = gson.fromJson(json, GoogleToken.class).accessToken();
//
//            return ResponseEntity.ok(accessToken);
//        }
//
//        throw new CustomException(ErrorCode.FAIL_GET_OAUTH_TOKEN, ErrorCode.FAIL_GET_OAUTH_TOKEN.getMessage());
//    }

    public ResponseEntity<String> getGoogleToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("code", code);
        requestBody.add("scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
        requestBody.add("client_id", GOOGLE_CLIENT_ID);
        requestBody.add("client_secret", GOOGLE_CLIENT_SECRET);
        requestBody.add("redirect_uri", GOOGLE_REDIRECT_URI);
        requestBody.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, request, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            GoogleToken googleToken = gson.fromJson(json, GoogleToken.class);
            String accessToken = googleToken.accessToken();

            return ResponseEntity.ok(accessToken);
        } else {
            throw new CustomException(ErrorCode.FAIL_GET_OAUTH_TOKEN, ErrorCode.FAIL_GET_OAUTH_TOKEN.getMessage());
        }
    }

    public ResponseEntity<GoogleResDto> googleOAuth(String googleToken) {
        UserInfo userInfo = getUserInfo(googleToken);

        User user = userRepository.findByEmail(userInfo.email()).orElseGet(() ->
                userRepository.save(User.builder()
                        .email(userInfo.email())
                        .name(userInfo.name())
                        .password(null)
                        .nickname(userInfo.name())
                        .role(Role.USER)
                        .build())
        );

        GoogleResDto googleResDto = GoogleResDto.builder()
                .accessToken(tokenProvider.createAccessToken(user))
                .build();

        return ResponseEntity.ok(googleResDto);
    }

    public UserInfo getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            return gson.fromJson(json, UserInfo.class);
        }

        throw new CustomException(ErrorCode.USER_NOT_FOUND_EXCEPTION, ErrorCode.USER_NOT_FOUND_EXCEPTION.getMessage());
    }
}
