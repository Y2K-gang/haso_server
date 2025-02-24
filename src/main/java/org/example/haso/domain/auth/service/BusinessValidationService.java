package org.example.haso.domain.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.BusinessDetail;
import org.example.haso.domain.auth.BusinessRequestFormat;
import org.example.haso.domain.auth.dto.BusinessValidateRequest;
import org.example.haso.domain.auth.entity.BusinessValidation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BusinessValidationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.serviceKey}")
    private String serviceKey;

    @Value("${api.url}")
    private String apiUrl;

    public String validateBusinessNumber(BusinessValidateRequest request) throws Exception {
        if (request.getBusinesses() == null || request.getBusinesses().isEmpty()) {
            throw new IllegalArgumentException("사업체 정보(businesses)는 필수값입니다.");
        }

        // 첫 번째 사업체의 정보를 가져옵니다.
        BusinessDetail business = request.getBusinesses().get(0);
        if (business.getB_no() == null || business.getB_no().trim().isEmpty()) {
            throw new IllegalArgumentException("사업자등록번호(b_no)는 필수값입니다.");
        }

        BusinessRequestFormat requestFormat = BusinessRequestFormat.from(request, serviceKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + serviceKey);  // 만약 API가 Bearer 토큰을 요구한다면

        String requestBody = objectMapper.writeValueAsString(requestFormat);
        HttpEntity<String> httpRequest = new HttpEntity<>(requestBody, headers);

        // API 요청을 보냅니다.
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpRequest, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return processApiResponse(response.getBody());
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new Exception("인증 실패: 잘못된 API 키 또는 인증 정보.");
        } else {
            throw new Exception("API 요청 실패: " + response.getStatusCode());
        }
    }


    private String processApiResponse(String responseBody) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String statusCode = jsonNode.path("status_code").asText();

        switch (statusCode) {
            case "OK":
                return handleValidResponse(jsonNode);
            case "BAD_JSON_REQUEST":
                return "잘못된 JSON 포맷입니다.";
            case "REQUEST_DATA_MALFORMED":
                return "필수 요청 파라미터 누락";
            case "TOO_LARGE_REQUEST":
                return "요청 사업자번호 또는 정보가 100개를 초과했습니다.";
            case "INTERNAL_ERROR":
                return "서버 내부 오류가 발생했습니다.";
            case "HTTP_ERROR":
                return "HTTP 오류가 발생했습니다.";
            default:
                return "알 수 없는 오류가 발생했습니다.";
        }
    }

    private String handleValidResponse(JsonNode jsonNode) {
        JsonNode dataNode = jsonNode.path("data");
        if (dataNode.isArray() && dataNode.size() > 0) {
            JsonNode validNode = dataNode.get(0).path("valid");
            if ("01".equals(validNode.asText())) {
                return "유효한 사업자등록번호입니다.";
            } else if ("02".equals(validNode.asText())) {
                return "유효하지 않은 사업자등록번호입니다.";
            } else {
                return "알 수 없는 응답입니다.";
            }
        }
        return "API 응답 데이터가 없습니다.";
    }
}
