package com.micro.inventory_service.service;

import com.micro.inventory_service.dto.InventoryResponse;
import com.micro.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    //check xem sluong san pham  ton tai trong kho co du  hay khong (dua tren ma cua san pham , cu the la skuCode)
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
       return  inventoryRepository.findBySkuCodeIn(skuCode).stream()
               .map(inventory ->
                   InventoryResponse.builder().skuCode(inventory.getSkuCode())
                           .isInStock(inventory.getQuantity() > 0).build() //neu con hang thi set isInStock la true , khong thi la false
               ).toList();
    }
}
