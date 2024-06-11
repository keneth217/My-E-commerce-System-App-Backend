package com.example.commerce.services.Admin.Product;

import com.example.commerce.dto.ProductDto;
import com.example.commerce.dto.ProductResponseDto;
import com.example.commerce.entity.Category;
import com.example.commerce.entity.Products;
import com.example.commerce.exceptions.ProductNotFoundException;

import com.example.commerce.repository.CartItemsRepository;
import com.example.commerce.repository.CategoryRepository;
import com.example.commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {
    private  final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {
        float initialAmount = productDto.getPrice();
        int discount = productDto.getDiscount();
        float finalAmount = initialAmount - ((initialAmount * discount) / 100);

// Round off the final amount to avoid decimals
        int roundedFinalAmount = Math.round(finalAmount);



        Products products=new Products();
        products.setProductName(productDto.getProductName());
        products.setStock(productDto.getStock());
        products.setDescription(productDto.getDescription());
        products.setPrice(initialAmount);
        products.setDiscount(productDto.getDiscount());
        products.setRealAmount(roundedFinalAmount);
        products.setImage(productDto.getImage().getBytes());
        Category category=categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->
                new RuntimeException("category not found of given id"));

        products.setCategory(category);
        return productRepository.save(products).getProductDto();

    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(Products::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProduct(String query) {
        List<Products> products=productRepository.searchProduct(query);
        System.out.println("params:"+query);
        if (products.isEmpty()){
            throw new ProductNotFoundException("no product found with name" + query);
        }
        return products.stream().map(Products::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Optional<Products> optionalProducts=productRepository.findById(id);
      return optionalProducts.map(Products::getProductDto).orElseThrow(()-> new RuntimeException("product with id:"+id+ "not found"));
    }

@Transactional
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) throws IOException {
    Optional<Products> optionalProducts = productRepository.findById(id);
    Optional<Category> optionalCategory=categoryRepository.findById(productDto.getCategoryId());
    System.out.println(productDto);

    float initialAmount=productDto.getPrice();
    int discount=productDto.getDiscount();
    float finalAmount=initialAmount-((initialAmount * discount)/100);

        if (optionalProducts.isPresent() && optionalProducts.isPresent()) {
            Products  existingProduct = optionalProducts.get();

            existingProduct.setPrice(initialAmount);
            existingProduct.setDiscount(discount);
            existingProduct.setRealAmount(finalAmount);
            existingProduct.setStock(productDto.getStock());
            existingProduct.setProductName(productDto.getProductName());
            existingProduct.setDescription(productDto.getDescription());

            existingProduct.setCategory(optionalCategory.get());
            if (productDto.getImage() != null)
                existingProduct.setImage(productDto.getImage().getBytes());
            System.out.println(existingProduct);
         return   productRepository.save(existingProduct).getProductDto();


        }else{
            return null;
        }

    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Products> optionalProducts=productRepository.findById(id);
        if (optionalProducts.isEmpty()){
            throw new ProductNotFoundException("product with id:"+id+ "not found");
        }else {

            productRepository.deleteById(id);

        }

    }
    public ProductResponseDto getAllPageableProducts(int pageNumber){
        Pageable pageable= PageRequest.of(pageNumber,4);
        Page<Products> productsPage=productRepository.findAll(pageable);
        ProductResponseDto productResponseDto=new ProductResponseDto();
        productResponseDto.setTotalPages(productsPage.getTotalPages());
        productResponseDto.setPageNumber(productsPage.getPageable().getPageNumber());
        productResponseDto.setProductDtoList(productsPage.stream().map(Products::getProductDto).collect(Collectors.toList()));
        return productResponseDto;
    }
    public long getProductCount() {
        return productRepository.count();
    }
}
