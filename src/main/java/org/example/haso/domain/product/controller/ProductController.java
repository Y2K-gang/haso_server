package org.example.haso.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.product.dto.GetProductResponse;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.product.dto.ProductResponse;
import org.example.haso.domain.product.entity.Category;
import org.example.haso.domain.product.service.ProductService;
import org.example.haso.global.auth.GetAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @GetAuthenticatedUser MemberEntity member,
            @RequestParam("title") String title,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("category") Category category,
            @RequestParam("picture") MultipartFile picture) {
        ProductResponse response = productService.createProduct(member, title, quantity, price, description, location, category, picture);
        return ResponseEntity.status(201).body(response);
    }



    // 상품 삭제 (DELETE /product/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteProduct(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable int id) {
        int deleteId = productService.deleteProduct(member, id);
        return ResponseEntity.ok(deleteId);
    }



    // 상품 상세 조회 (GET /product/{id})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable int id) {
        ProductResponse response = productService.getProduct(member, id);
        return ResponseEntity.ok(response);
    }

    // 상품 전체 조회 (GET /product)
    @GetMapping
    public ResponseEntity<List<GetProductResponse>> getAllProduct(
            @GetAuthenticatedUser MemberEntity member) {
        List<GetProductResponse> responses = productService.getAllProduct(member);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> editProduct(
            @GetAuthenticatedUser MemberEntity member,
            @PathVariable int id,
            @RequestParam("title") String title,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("category") Category category,
            @RequestParam(value = "picture", required = false) MultipartFile picture) {
        ProductResponse response = productService.editProduct(member, id, title, quantity, price, description, location, category, picture);
        return ResponseEntity.ok(response);
    }
}
