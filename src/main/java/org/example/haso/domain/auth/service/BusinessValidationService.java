package org.example.haso.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.BusinessRequestFormat;
import org.example.haso.domain.auth.dto.BusinessValidateRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessValidationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.serviceKey}")
    private String serviceKey;

    @Value("${api.url}")
    private String apiUrl;

    public String validateBusinessNumber(BusinessValidateRequest request) {
        // API 요청 URL 설정
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("serviceKey", serviceKey)
                .toUriString();

        // 요청 본문: API 요구 JSON 포맷으로 변환
        BusinessRequestFormat requestBody = BusinessRequestFormat.from(request);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity 설정 (헤더와 본문)
        HttpEntity<BusinessRequestFormat> entity = new HttpEntity<>(requestBody, headers);

        try {
            // POST 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            // 응답에서 valid 값을 추출
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());

                // JSON에서 valid 값 추출 (안전한 방식)
                Optional<JsonNode> validNode = Optional.ofNullable(jsonNode.path("data"))
                        .filter(JsonNode::isArray)
                        .map(data -> data.get(0))
                        .map(node -> node.path("valid"));


                // valid 값 가져오기
                String valid = validNode.map(JsonNode::asText).orElse("unknown");

                return switch (valid) {
                    case "01" -> "유효한 사업자등록번호입니다.";
                    case "02" -> "유효하지 않은 사업자등록번호입니다.";
                    default -> "알 수 없는 응답입니다.";
                };
            }
            return "API 요청 실패: " + response.getStatusCode();
        } catch (Exception e) {
            // 예외 발생 시 로깅
            e.printStackTrace();
            return "API 요청 중 오류 발생: " + e.getMessage();
        }
    }
}

