package com.example.commerce.services.Admin.faq;

import com.example.commerce.dto.FaqDto;
import com.example.commerce.entity.Faqs;
import com.example.commerce.entity.Products;

import com.example.commerce.repository.FaqRepository;
import com.example.commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FAqServiceImpl implements FaqService{
    private final ProductRepository productRepository;
    private final FaqRepository faqRepository;
    public FaqDto postFAq(Long productId,FaqDto faqDto){
        Optional<Products> optionalProducts =productRepository.findById(productId);
        if (optionalProducts.isPresent()){
            Faqs faqs=new Faqs();
            faqs.setAnswer(faqDto.getAnswer());
            faqs.setQuestion(faqDto.getQuestion());
            faqs.setProducts(optionalProducts.get());
            return faqRepository.save(faqs).getFaqDto();
        }
        return null;
    }

}
