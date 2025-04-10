package com.micro.inventory_service.controller;

import com.micro.inventory_service.dto.InventoryResponse;
import com.micro.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    //http://localhost:8082/api/inventory?sku-code=iphone-16&sku-code=iphone-16red
    //1 don hang thi co nhieu san pham , nen phai truyen vao  1 list cac skucode chu khong phai 1 cai
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    //check xem san phảm còn hàng hay khong
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.isInStock(skuCode);
    }
}
