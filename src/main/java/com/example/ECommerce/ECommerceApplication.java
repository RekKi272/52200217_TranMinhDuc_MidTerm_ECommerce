package com.example.ECommerce;

import com.example.ECommerce.Service.UserService;
import com.example.ECommerce.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public 
class ECommerceApplication{
	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
}
