package org.example.haso.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.BusinessRequestFormat;
import org.example.haso.domain.auth.dto.BusinessValidateRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


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
        try {
            // ✅ 사업자번호가 null이거나 비어 있으면 예외 발생
            if (request.getB_no() == null || request.getB_no().isBlank()) {
                throw new IllegalArgumentException("사업자등록번호(b_no)는 필수값입니다.");
            }

            // ✅ 서비스키 이중 인코딩 방지
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("serviceKey", serviceKey)  // 인코딩 없이 그대로 사용
                    .toUriString();

            // ✅ JSON 요청 데이터 생성
            BusinessRequestFormat requestBody = BusinessRequestFormat.from(request);

            // ✅ HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // ✅ HttpEntity 설정
            HttpEntity<BusinessRequestFormat> entity = new HttpEntity<>(requestBody, headers);

            // ✅ 로그 추가
            System.out.println("보낼 URL: " + url);
            System.out.println("보낼 JSON: " + objectMapper.writeValueAsString(requestBody));

            // ✅ API 호출
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // ✅ 응답 로그 추가
            System.out.println("응답 코드: " + response.getStatusCode());
            System.out.println("응답 본문: " + response.getBody());

            // ✅ 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                JsonNode dataNode = jsonNode.path("data");
                if (dataNode.isArray() && dataNode.size() > 0) {
                    JsonNode validNode = dataNode.get(0).path("valid");
                    return switch (validNode.asText("unknown")) {
                        case "01" -> "유효한 사업자등록번호입니다.";
                        case "02" -> "유효하지 않은 사업자등록번호입니다.";
                        default -> "알 수 없는 응답입니다.";
                    };
                } else {
                    return "API 응답 데이터가 없습니다.";
                }
            }
            return "API 요청 실패: " + response.getStatusCode();
        } catch (IllegalArgumentException e) {
            return "입력 오류: " + e.getMessage();
        } catch (RestClientException e) {
            return "API 호출 중 네트워크 오류 발생: " + e.getMessage();
        } catch (Exception e) {
            return "API 요청 중 오류 발생: " + e.getMessage();
        }
    }

}
