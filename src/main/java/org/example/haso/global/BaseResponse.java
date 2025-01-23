package org.example.haso.global;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
// Jackson 라이브러리에서 사용됨, JSON 직렬화 시 포함할 필드의 조건을 설정
// 특정 조건에 따라 필드를 JSON에 포함하거나 제외
// JsonInclude.Include.NON_NULL -> 값이 null이거나 빈 컬렉션/문자열은 제외.
public record BaseResponse<T> (
        //BaseResponse<T> : 어떤 타입의 data도 받을 수 있음(String, List, User 등)
        int status,
        String message,
        T data
){}
