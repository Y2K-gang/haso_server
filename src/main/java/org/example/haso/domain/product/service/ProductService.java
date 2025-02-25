package org.example.haso.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.product.dto.GetProductResponse;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.product.dto.ProductResponse;
import org.example.haso.domain.product.entity.Product;
import org.example.haso.domain.product.repository.ProductRepository;
import org.example.haso.domain.s3.controller.S3Controller;
import org.example.haso.global.auth.GetAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private S3Controller s3Controller;


    // 상품 생성
    @Transactional
    public ProductResponse createProduct(MemberEntity member, ProductRequest request) {

//        String pictureUrl = null;
//        if (request.getPicture() != null) {
//            pictureUrl = s3Controller.s3Upload(request.getPicture());
//        }

        Product product = Product.builder()
                .userId(member)
                .title(request.getTitle())
//                .picture(pictureUrl)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .description(request.getDescription())
                .location(request.getLocation())
                .category(request.getCategory())
                .createdDate(LocalDateTime.now())
                .build();

        product = productRepository.save(product);
        return ProductResponse.from(product);

    }


    // 상품 삭제
    @Transactional
    public int deleteProduct(MemberEntity member, int id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 본인만 삭제 가능하도록
        if (!product.getUserId().getUserId().equals(member.getUserId())) {
            throw new RuntimeException("no permission to delete this product");
        }

//        s3Controller.s3Delete(product.getPicture());
        productRepository.deleteById(id);
        return id;
    }

    // 상품 상세 조회
    @Transactional
    public ProductResponse getProduct(MemberEntity member, int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 현재 로그인한 사용자가 상품의 주인인지 검증
        if (!product.getUserId().getUserId().equals(member.getUserId())) {
            throw new RuntimeException("no permission to delete this product");
        }

        return ProductResponse.from(product);
    }

    // 상품 전체 조회
    @Transactional
    public List<GetProductResponse> getAllProduct(MemberEntity member) {
        return productRepository.findAll()
                .stream()
                .map(GetProductResponse::from)
                .collect(Collectors.toList());
    }

    // 상품 수정
    @Transactional
    public ProductResponse editProduct(MemberEntity member, int id, ProductRequest editRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 현재 로그인한 사용자가 상품의 주인인지 검증
        if (!product.getUserId().getUserId().equals(member.getUserId())) {
            throw new RuntimeException("no permission to delete this product");
        }

        Optional.ofNullable(editRequest.getTitle()).ifPresent(product::setTitle);
        Optional.ofNullable(editRequest.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(editRequest.getLocation()).ifPresent(product::setLocation);
        Optional.ofNullable(editRequest.getCategory()).ifPresent(product::setCategory);

        if (editRequest.getQuantity() > 0) {
            product.setQuantity(editRequest.getQuantity());
        }

        if (editRequest.getPrice() > 0) {
            product.setPrice(editRequest.getPrice());
        }

//        if (editRequest.getPicture() != null) {
//            s3Controller.s3Delete(product.getPicture());
//            s3Controller.s3Upload(editRequest.getPicture());
//        }

        return ProductResponse.from(productRepository.save(product));
    }


    @Transactional
    // 사용자가 등록한 게시물 조회
    public List<GetProductResponse> getUserProducts(MemberEntity member) {
        return productRepository.findByUserId(member.getUserId())
                .stream()
                .map(GetProductResponse::from)
                .collect(Collectors.toList());
    }

}