package org.example.haso.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.MemberException;
import org.example.haso.domain.auth.MemberType;
import org.example.haso.domain.auth.dto.RefreshMemberRequest;
import org.example.haso.domain.auth.dto.SigninMemberRequest;
import org.example.haso.domain.auth.dto.SignupMemberRequest;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.auth.repository.MemberRepository;
import org.example.haso.domain.profile.entity.Edit;
import org.example.haso.domain.profile.entity.Profile;
import org.example.haso.domain.profile.repository.EditRepository;
import org.example.haso.domain.profile.repository.ProfileRepository;
import org.example.haso.global.auth.JwtUtils;
import org.example.haso.global.auth.TokenInfo;
//import org.example.haso.global.util.RedisUtil;
//import org.example.haso.global.util.SmsUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final EditRepository editRepository;
    private final ProfileRepository profileRepository;
    private final JwtUtils utils;
//    private final SmsUtil smsUtil;
//    private final RedisUtil redisUtil;
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
                .role(MemberType.ROLE_USER)
                .build();


        repository.save(entity);

        // Profile 엔티티 생성
        Profile profile = Profile.builder()
                .userId(dto.userId())
                .build();

        profileRepository.save(profile);  // Profile 저장

        Edit edit = Edit.builder()
                .userId(dto.userId())
                .name(dto.name())
                .password(dto.password())
                .tel(dto.tel())
                .storeName(dto.storeName())
                .storeNo(dto.storeNo())
                .faxNo(dto.faxNo())
                .businessNo(dto.businessNo())
                .build();

        editRepository.save(edit);

        return utils.generate(entity);
    }


//    // 회원가입 (1단계) - 기본 정보 받고 인증 코드 전송
//    @Transactional
//    public String sendVerificationCode(SignupMemberRequest dto) {
//
//        if (!dto.isPasswordMatch()) {
//            throw MemberException.PASSWORD_NOT_MATCH.getException();
//        }
//
//        if(repository.existsByUserId(dto.userId())) {
//            throw MemberException.ALREADY_EXIST.getException();
//        }
//
//        // 비밀번호 암호화
//        String encodedPassword = passwordEncoder.encode(dto.password());
//
//        // 6자리 인증 코드 생성
//        String verificationCode = generateVerificationCode();
//
//        // Redis에 임시 저장 (5분 후 자동 삭제)
//        redisUtil.setDataExpire(dto.tel(),
//                dto.userId() + "|" + dto.name() + "|" + encodedPassword + "|" + dto.tel(),
//                60 * 5L);
//
//        // 인증 코드 전송
//        smsUtil.sendOne(dto.tel(), verificationCode);
//
//        return "인증 코드가 전송되었습니다.";
//    }

//    // 전화번호 인증 (2단계) - 인증 후 추가 정보 입력받아 최종 저장
//    @Transactional
//    public TokenInfo verifyPhoneNumber(String tel, String inputCode, SignupMemberRequest dto) {
//        String storedCode = redisUtil.getData(tel);
//        if (storedCode == null || !storedCode.equals(inputCode)) {
//            throw new IllegalArgumentException("인증 코드가 유효하지 않습니다.");
//        }
//
//        // 인증 성공 시 Redis에서 저장된 회원 정보 가져오기
//        String tempData = redisUtil.getData(tel);
//        if (tempData == null) {
//            throw new IllegalArgumentException("회원 정보가 만료되었습니다. 다시 시도해주세요.");
//        }
//
//        // Redis에서 가져온 데이터를 분리
//        String[] dataParts = tempData.split("\\|");
//        String userId = dataParts[0];
//        String name = dataParts[1];
//        String encodedPassword = dataParts[2];
//        String phoneNumber = dataParts[3];
//
//        // 인증 완료 후 Redis에서 삭제
//        redisUtil.deleteData(tel);
//
//        // 최종적으로 DB에 저장할 Entity 생성
//        MemberEntity entity = MemberEntity.builder()
//                .userId(userId)
//                .name(name)
//                .password(encodedPassword)
//                .tel(phoneNumber)
//                .storeName(dto.storeName())
//                .storeNo(dto.storeNo())
//                .faxNo(dto.faxNo())
//                .businessNo(dto.businessNo())
//                .role(MemberType.ROLE_USER)
//                .build();
//
//        repository.save(entity);
//
//        return utils.generate(entity);
//    }
//
//    // 6자리 인증 코드 생성
//    private String generateVerificationCode() {
//        return String.valueOf((int) (Math.random() * 900000) + 100000);
//    }


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
