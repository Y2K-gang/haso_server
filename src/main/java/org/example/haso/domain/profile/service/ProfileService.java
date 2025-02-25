package org.example.haso.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.MemberException;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.product.entity.Product;
import org.example.haso.domain.product.repository.ProductRepository;
import org.example.haso.domain.profile.dto.EditProfileRequest;
import org.example.haso.domain.profile.dto.EditProfileResponse;
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
        // Edit 엔티티 수정
        Edit edit = editRepository.findByUserId(member.getUserId());
        edit.edit(request); // Edit 엔티티 수정 메소드 호출

        editRepository.save(edit); // 수정된 Edit 객체 저장

        // 수정된 정보를 기반으로 응답 반환
        return EditProfileResponse.builder()
                .userId(edit.getUserId())
                .password(edit.getPassword())
                .name(edit.getName())
                .tel(edit.getTel())
                .business_no(edit.getBusinessNo())
                .fax_no(edit.getFaxNo())
                .store_no(edit.getStoreNo())
                .store_name(edit.getStoreName())
                .handlingProduct(edit.getHandlingProduct())
                .build();
    }

    public Profile getProfile(MemberEntity member) {
        MemberEntity entity = memberRepository.findByUserId(member.getUserId())
                .orElseThrow(MemberException.NOT_EXIST::getException);

        List<Product> products = productRepository.findByUserId(member.getUserId());

        Edit edit = editRepository.findByUserId(member.getUserId());

        Profile.builder()
                .userId(entity.getUserId())
                .handlingProduct(edit.getHandlingProduct());
//                .product(products);
        return profileRepository.findByUserId(member.getUserId());
    }
}
