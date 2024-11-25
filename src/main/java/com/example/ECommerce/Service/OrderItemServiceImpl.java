package com.example.ECommerce.Service;

import com.example.ECommerce.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void removeItem(Long cartItemId){
        orderItemRepository.deleteById(cartItemId);
    }
}
