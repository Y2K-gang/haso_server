package org.example.haso.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.product.dto.GetProductResponse;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.product.dto.ProductResponse;
import org.example.haso.domain.product.service.ProductService;
import org.example.haso.domain.profile.dto.EditProfileRequest;
import org.example.haso.domain.profile.dto.EditProfileResponse;
import org.example.haso.domain.profile.dto.ProfileResponse;
import org.example.haso.domain.profile.entity.Profile;
import org.example.haso.domain.profile.service.ProfileService;
import org.example.haso.global.auth.GetAuthenticatedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProductService productService;
    private final ProfileService profileService;


    // 프로필 조회 (GET /profile)
    @GetMapping
    public ResponseEntity<ProfileResponse> profile(@GetAuthenticatedUser MemberEntity member) {
        ProfileResponse response = profileService.getProfile(member);
        return ResponseEntity.ok(response);
    }


    // 사용자가 등록한 게시물 조회 (GET /profile/product)
    @GetMapping("/product")
    public ResponseEntity<List<GetProductResponse>> postedProduct(@GetAuthenticatedUser MemberEntity member) {
        List<GetProductResponse> responses = productService.getUserProducts(member);
        return ResponseEntity.ok(responses);
    }

//    // 상품 삭제 (DELETE /profile/product/{id})
//    @DeleteMapping("/product/{id}")
//    public ResponseEntity<Integer> deleteProduct(@GetAuthenticatedUser MemberEntity member, @PathVariable int id) {
//        int deleteId = productService.deleteProduct(member, id);
//        return ResponseEntity.ok(deleteId);
//    }
//
//    // 상품 상세 조회 (GET /profile/product/{id})
//    @GetMapping("/product/{id}")
//    public ResponseEntity<ProductResponse> getProduct(@GetAuthenticatedUser MemberEntity member, @PathVariable int id) {
//        ProductResponse response = productService.getProduct(member, id);
//        return ResponseEntity.ok(response);
//    }
//
//    // 상품 수정 (PATCH /profile/product/{id})
//    @PatchMapping("/product/{id}")
//    public ResponseEntity<ProductResponse> editProduct(
//            @GetAuthenticatedUser MemberEntity member,
//            @PathVariable int id,
//            @RequestBody ProductRequest editrequest) {
//        ProductResponse response = productService.editProduct(member, id, editrequest);
//        return ResponseEntity.ok(response);
//    }

    // 사용자 정보 수정 (PATCH /profile/edit)
    @PatchMapping("/edit")
    public ResponseEntity<EditProfileResponse> edit(@GetAuthenticatedUser MemberEntity member, @RequestBody EditProfileRequest request) {
        EditProfileResponse response = profileService.edit(member, request);
        return ResponseEntity.ok(response);
    }

}
