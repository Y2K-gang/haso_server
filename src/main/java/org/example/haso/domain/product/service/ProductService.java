package org.example.haso.domain.product.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.haso.domain.auth.entity.MemberEntity;
import org.example.haso.domain.product.dto.GetProductResponse;
import org.example.haso.domain.product.dto.ProductRequest;
import org.example.haso.domain.product.dto.ProductResponse;
import org.example.haso.domain.product.entity.Category;
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



    public ProductResponse createProduct(MemberEntity member, String title, int quantity, int price, String description, String location, Category category, MultipartFile picture) {
        String pictureUrl = null;

        // 사진이 비어있거나 null일 때 처리
        if (picture != null && !picture.isEmpty()) {
            try {
                pictureUrl = s3Controller.s3Upload(picture);
            } catch (Exception e) {
                throw new RuntimeException("파일 업로드 중 오류가 발생했습니다. (사진파일이 null이거나 비어있습니다.)", e);
            }
        } else {
            // picture가 비어 있으면 기본값으로 처리하거나, 파일 없이 진행
            pictureUrl = "default-image-url"; // 기본 이미지 URL (이 부분을 필요에 맞게 변경)
        }

        Product product = Product.builder()
                .userId(member)
                .title(title)
                .picture(pictureUrl)
                .quantity(quantity)
                .price(price)
                .description(description)
                .location(location)
                .category(category)
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

        if (!product.getUserId().getUserId().equals(member.getUserId())) {
            throw new RuntimeException("no permission to delete this product");
        }

        s3Controller.s3Delete(product.getPicture());
        productRepository.deleteById(id);
        return id;
    }


    // 상품 상세 조회
    @Transactional
    public ProductResponse getProduct(MemberEntity member, int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

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

    @Transactional
    public ProductResponse editProduct(MemberEntity member, int id, String title, int quantity, int price, String description, String location, Category category, MultipartFile picture) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getUserId().getUserId().equals(member.getUserId())) {
            throw new RuntimeException("No permission to edit this product");
        }

        product.setTitle(title);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setDescription(description);
        product.setLocation(location);
        product.setCategory(category);

        if (picture != null && !picture.isEmpty()) {
            s3Controller.s3Delete(product.getPicture());
            product.setPicture(s3Controller.s3Upload(picture));
        }

        return ProductResponse.from(productRepository.save(product));
    }

    // 사용자가 등록한 게시물 조회
    @Transactional
    public List<GetProductResponse> getUserProducts(MemberEntity member) {

        List<Product> products = productRepository.findByUserIdOrderByCreatedDateDesc(member.getUserId());
        return products.stream().map(GetProductResponse::from).toList();
    }

}