package org.example.haso.domain.business.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.auth.service.MemberService;
import org.example.haso.domain.business.dto.business.BusinessRequest;
import org.example.haso.domain.business.dto.business.BusinessResponse;
import org.example.haso.domain.business.dto.business.GetBusinessResponse;
import org.example.haso.domain.business.model.Business;
import org.example.haso.domain.business.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    // 거래처 검색
    @Transactional
    public BusinessResponse search(MemberEntity member, BusinessRequest request) {

//        Business business = businessRepository.findByUserId(request.getUserId())
//                .orElseThrow(() -> new RuntimeException("Business entity not found for the given user ID"));

        if (request.getUserId() == null) {
            throw new IllegalArgumentException("ID는 null일 수 없어요");
        }

        MemberEntity memberEntity = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("입력하신 userId로 user를 찾을 수 없어요"));

        Business business = Business.fromMemberEntity(memberEntity, member);

        return new BusinessResponse(business);
    }

    // 거래처 추가
    @Transactional
    public GetBusinessResponse createBusiness(MemberEntity member, BusinessRequest request) {
//        Business business = new Business(request);
        MemberEntity memberEntity = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("입력하신 userId로 user를 찾을 수 없어요"));

        Business business = Business.fromMemberEntity(memberEntity, member);

        businessRepository.save(business);
        return GetBusinessResponse.from(business);
    }

    // 거래처 삭제
    @Transactional
    public String deleteBusiness(MemberEntity member, String userId) {

//        Business business = businessRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("Business entity not found for the given user ID"));

        MemberEntity memberEntity = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("입력하신 userId로 user를 찾을 수 없어요"));

        Business business = businessRepository.findByUserId(memberEntity.getUserId());

        if (!business.getUser().equals(member.getUserId())) {
            throw new IllegalArgumentException("게시물 삭제할 권한 없음");
        }

        businessRepository.delete(business);
        return userId;
    }

}
