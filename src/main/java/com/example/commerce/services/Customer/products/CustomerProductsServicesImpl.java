package com.example.commerce.services.Customer.products;

import com.example.commerce.dto.ProductDetailDto;
import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ProductResponseDto;
import com.example.commerce.entity.Faqs;
import com.example.commerce.entity.Products;
import com.example.commerce.entity.Review;

import com.example.commerce.repository.FaqRepository;
import com.example.commerce.repository.ProductRepository;
import com.example.commerce.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductsServicesImpl implements CustomerProductsServices{
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final FaqRepository faqRpository;
    @Override
    public List<ProductDto> getAllProducts() {
        List<Products> productsList=productRepository.findAll();
        return productsList.stream().map(Products::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProduct(String query) {
        List<Products> products=productRepository.searchProduct(query);
        System.out.println("params:"+query);
        if (products.isEmpty()){
            throw new RuntimeException("no product found with name" + query);
        }
        return products.stream().map(Products::getProductDto).collect(Collectors.toList());
    }


    @Override
    public List<ProductDto> getAllProductsByName(String name) {
        List<Products> productsList=productRepository.findAllByProductNameContaining(name);
        return productsList.stream().map(Products::getProductDto).collect(Collectors.toList());
    }
    public ProductResponseDto getAllPageableProducts(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,6);
        Page<Products> productsPage=productRepository.findAll(pageable);
        ProductResponseDto productResponseDto=new ProductResponseDto();
        productResponseDto.setTotalPages(productsPage.getTotalPages());
        productResponseDto.setPageNumber(productsPage.getPageable().getPageNumber());
        productResponseDto.setProductDtoList(productsPage.stream().map(Products::getProductDto).collect(Collectors.toList()));
        return productResponseDto;
    }
    public ProductDetailDto getProductDetailById(Long productId){
        Optional<Products> productsOptional=productRepository.findById(productId);
        if (productsOptional.isPresent()) {
            List<Faqs> faqsList=faqRpository.findAllByProductsId(productId);
            List<Review>    reviewList=reviewRepository.findAllByProductsId(productId);
            ProductDetailDto productDetailDto=new ProductDetailDto();
            productDetailDto.setProductDto(productsOptional.get().getProductDto());
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getReviewDto).collect(Collectors.toList()));
            productDetailDto.setFaqDtoList(faqsList.stream().map(Faqs::getFaqDto).collect(Collectors.toList()));
            return productDetailDto;
        }
        return null;
    }
}
