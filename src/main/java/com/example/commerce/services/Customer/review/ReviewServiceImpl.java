package com.example.commerce.services.Customer.review;

import com.example.commerce.dto.OrderedProductsResponseDto;
import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ReviewDto;
import com.example.commerce.entity.*;
import com.example.commerce.repository.OrderRepository;
import com.example.commerce.repository.ProductRepository;
import com.example.commerce.repository.ReviewRepository;
import com.example.commerce.repository.UserRepository;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderedProductsResponseDto getOrderedProductsByOrderId(Long orderId) {
        return null;
    }

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Products> optionalProducts=productRepository.findById(reviewDto.getId());
        Optional<User> optionalUser=userRepository.findById(reviewDto.getUserId());
        if (optionalUser.isPresent() && optionalUser.isPresent()) {
            Review review=new Review();
            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setUser(optionalUser.get());
            review.setProducts(optionalProducts.get());
            review.setImage(reviewDto.getImage().getBytes());
            return reviewRepository.save(review).getReviewDto();

        }





        return  null;


    }
}
