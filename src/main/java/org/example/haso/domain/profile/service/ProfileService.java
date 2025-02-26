package org.example.haso.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.MemberException;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.product.dto.GetProductResponse;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.product.entity.Product;
import org.example.haso.domain.product.repository.ProductRepository;
import org.example.haso.domain.profile.dto.EditProfileRequest;
import org.example.haso.domain.profile.dto.EditProfileResponse;
import org.example.haso.domain.profile.dto.ProfileResponse;
import org.example.haso.domain.profile.entity.Edit;
import org.example.haso.domain.profile.entity.Profile;
import org.example.haso.domain.profile.repository.EditRepository;
import org.example.haso.domain.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final EditRepository editRepository;

    @Transactional
    public EditProfileResponse edit(MemberEntity member, EditProfileRequest request) {
        // 로그로 요청값 확인
        System.out.println("Received business_no: " + request.getBusinessNo());

        Edit edit = editRepository.findByUserId(member.getUserId());

//        if (memberRepository.existsByUserId(request.getUserId())) {
//            throw new RuntimeException("이미 존재하는 사용자 아이디입니다.");
//        }

        edit.edit(request); // Edit 엔티티 수정 메소드 호출

        editRepository.save(edit);

        Profile profile = profileRepository.findById(member.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 프로필이 존재하지 않습니다."));

        profile.setHandlingProduct(edit.getHandlingProduct());

        profileRepository.save(profile);

        member.setPassword(request.getPassword());
        member.setName(request.getName());
        member.setTel(request.getTel());
        member.setBusinessNo(request.getBusinessNo());
        member.setFaxNo(request.getFaxNo());
        member.setStoreNo(request.getStoreNo());
        member.setStoreName(request.getStoreName());

        memberRepository.save(member);


        return EditProfileResponse.builder()
                .userId(edit.getUserId())
                .password(edit.getPassword())
                .name(edit.getName())
                .tel(edit.getTel())
                .businessNo(edit.getBusinessNo())
                .faxNo(edit.getFaxNo())
                .storeNo(edit.getStoreNo())
                .storeName(edit.getStoreName())
                .handlingProduct(edit.getHandlingProduct())
                .build();
    }

    public ProfileResponse getProfile(MemberEntity member) {
        MemberEntity entity = memberRepository.findByUserId(member.getUserId())
                .orElseThrow(MemberException.NOT_EXIST::getException);

        Profile profile = profileRepository.findByUserId(member.getUserId());

        if (profile == null) {
            throw new RuntimeException("프로필이 존재하지 않습니다.");
        }

        List<Product> products = productRepository.findByUserIdOrderByCreatedDateDesc(member.getUserId());
        List<GetProductResponse> productResponses = products.stream()
                .map(GetProductResponse::from)
                .toList();
//        Edit edit = editRepository.findByUserId(entity.getUserId());

//        Profile.builder()
//                .userId(edit.getUserId())
//                .handlingProduct(edit.getHandlingProduct());
////                .product(products);
//        return profileRepository.findByUserId(member.getUserId());

        return new ProfileResponse(profile, productResponses);

    }
}
