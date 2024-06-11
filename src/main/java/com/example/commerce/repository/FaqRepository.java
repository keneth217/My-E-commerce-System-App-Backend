package com.example.commerce.repository;

import com.example.commerce.entity.Faqs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepository extends JpaRepository<Faqs,Long> {
    List<Faqs> findAllByProductsId(Long productId);
}
