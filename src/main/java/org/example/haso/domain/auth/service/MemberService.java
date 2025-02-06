package org.example.haso.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.MemberException;
import org.example.haso.domain.auth.dto.RefreshMemberRequest;
import org.example.haso.domain.auth.dto.SigninMemberRequest;
import org.example.haso.domain.auth.dto.SignupMemberRequest;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.global.auth.JwtUtils;
import org.example.haso.global.auth.TokenInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final JwtUtils utils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Transactional
    public TokenInfo signupMember(SignupMemberRequest dto) {

        if (!dto.isPasswordMatch()) {
            throw MemberException.PASSWORD_NOT_MATCH.getException();
        }

        if(repository.existsByUserId(dto.userId())) {
            throw MemberException.ALREADY_EXIST.getException();
        }


        String encodedPassword = passwordEncoder.encode(dto.password());

        MemberEntity entity = MemberEntity.builder()
//                .id(null)
                .userId(dto.userId())
                .name(dto.name())
                .password(encodedPassword)
                .tel(dto.tel())
                .storeName(dto.storeName())
                .storeNo(dto.storeNo())
                .faxNo(dto.faxNo())
                .businessNo(dto.businessNo())
                .build();


        repository.save(entity);

        return utils.generate(entity);
    }

    @Transactional
    public TokenInfo signinMember(SigninMemberRequest dto) {
        MemberEntity entity = repository.findByUserId(dto.userId())
                .orElseThrow(MemberException.NOT_EXIST::getException);

        if (!passwordEncoder.matches(dto.password(), entity.getPassword())) {
            System.out.println("입력된 비밀번호: " + dto.password());
            System.out.println("DB에 저장된 암호화된 비밀번호: " + entity.getPassword());
            System.out.println("비교 결과: " + passwordEncoder.matches(dto.password(), entity.getPassword()));

            throw MemberException.PASSWORD_NOT_MATCH.getException();
        }

        return utils.generate(entity);
    }


    @Transactional
    public String refreshMember(RefreshMemberRequest dto) {
        String userId = utils.parse(dto.refreshToken()).get("userId", String.class);

        MemberEntity entity = repository.findByUserId(userId)
                .orElseThrow(MemberException.NOT_EXIST::getException);

        return utils.refreshToken(entity);
    }
}
