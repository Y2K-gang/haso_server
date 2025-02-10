package org.example.haso.domain.profile.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.profile.dto.EditProfileRequest;
import org.example.haso.domain.profile.dto.EditProfileResponse;
import org.example.haso.domain.profile.entity.Edit;
import org.example.haso.domain.profile.entity.Profile;
import org.example.haso.domain.profile.repository.EditRepository;
import org.example.haso.domain.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
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
        return profileRepository.findByUserId(member.getUserId());
    }
}
