package com.micro.order_service.controller;

import com.micro.order_service.dto.OrderRequest;
import com.micro.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    //dat hang
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory" , fallbackMethod = "fallbackMethod") //logic ngat mach se ap dung cho api nay ,fallbackMethod la logic du phong neu api bi ngat mach
    @TimeLimiter(name = "inventory") //vi de anotation nay nen phai tra ve kieu nay CompletableFuture
    @Retry(name = "inventory") //chay thu lai
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
//        return "Order Placed Successfully";
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() -> "Oops ,! Something went wrong , please order after some time!");
    }



}
