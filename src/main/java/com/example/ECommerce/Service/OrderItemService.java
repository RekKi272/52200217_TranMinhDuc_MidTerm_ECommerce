package com.example.ECommerce.Service;

import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    void removeItem(Long cartItemId);
}
