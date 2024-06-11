package com.example.commerce.services.Admin.faq;

import com.example.commerce.dto.FaqDto;

public interface FaqService {
    FaqDto postFAq(Long productId, FaqDto faqDto);
}
