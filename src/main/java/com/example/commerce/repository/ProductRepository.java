package com.example.commerce.repository;

import com.example.commerce.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {


    @Query("SELECT p FROM Products p WHERE " +
            "p.productName LIKE CONCAT('%', :query, '%')")
    List<Products> searchProduct(@Param("query") String query);

    List<Products> findAllByProductNameContaining(String name);
}
