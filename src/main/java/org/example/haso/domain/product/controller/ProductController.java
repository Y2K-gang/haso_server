package org.example.haso.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.product.dto.GetProductResponse;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.product.dto.ProductResponse;
import org.example.haso.domain.product.service.ProductService;
import org.example.haso.global.auth.GetAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 생성 (POST /product)
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @GetAuthenticatedUser MemberEntity member,
            @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(member, request);
        return ResponseEntity.status(201).body(response);
    }

    // 상품 삭제 (DELETE /product/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteProduct(@GetAuthenticatedUser MemberEntity member, @PathVariable int id) {
        int deleteId = productService.deleteProduct(member, id);
        return ResponseEntity.ok(deleteId);
    }

    // 상품 상세 조회 (GET /product/{id})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@GetAuthenticatedUser MemberEntity member, @PathVariable int id) {
        ProductResponse response = productService.getProduct(member, id);
        return ResponseEntity.ok(response);
    }

    // 상품 전체 조회 (GET /product)
    @GetMapping
    public ResponseEntity<List<GetProductResponse>> getAllProduct(@GetAuthenticatedUser MemberEntity member) {
        List<GetProductResponse> responses = productService.getAllProduct(member);
        return ResponseEntity.ok(responses);
    }

    // 상품 수정 (PATCH /product/{id})
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> editProduct(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable int id,
            @RequestBody ProductRequest editrequest) {
        ProductResponse response = productService.editProduct(member, id, editrequest);
        return ResponseEntity.ok(response);
    }
}
