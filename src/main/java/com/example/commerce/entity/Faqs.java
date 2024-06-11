package com.example.commerce.entity;

import com.example.commerce.dto.FaqDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Data
@Entity
public class Faqs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;
    @Lob
    private String question;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="product_id",nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private Products products;
    public FaqDto getFaqDto(){
        FaqDto faqDto=new FaqDto();
        faqDto.setId(id);
        faqDto.setQuestion(question);
        faqDto.setAnswer(answer);
        faqDto.setProductId(products.getId());
        return  faqDto;
    }
}
