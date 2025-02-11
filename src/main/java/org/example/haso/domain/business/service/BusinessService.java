package org.example.haso.domain.business.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.business.dto.business.BusinessRequest;
import org.example.haso.domain.business.dto.business.BusinessResponse;
import org.example.haso.domain.business.dto.business.GetBusinessResponse;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.repository.BusinessRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private BusinessRepository businessRepository;

    // 거래처 검색
    @Transactional
    public BusinessResponse search(MemberEntity member, BusinessRequest request) {
        Business business = businessRepository.findByUserId(request.getUserId());

        if (!business.getUserId().equals(member.getUserId())) {
            throw new RuntimeException("no permission to delete this 거래처");
        }
        return new BusinessResponse(business);
    }

    // 거래처 추가
    public GetBusinessResponse createBusiness(MemberEntity member, BusinessRequest request) {
        Business business = new Business(request);
        businessRepository.save(business);
        return GetBusinessResponse.from(business);
    }

    // 거래처 삭제
    @Transactional
    public String deleteBusiness(MemberEntity member, String userId) {

        Business business = businessRepository.findByUserId(userId);

        if (!business.getUserId().equals(member.getUserId())) {
            throw new RuntimeException("no permission to delete this 거래처");
        }

        businessRepository.deleteByUserId(userId);
        return userId;
    }

}
