package com.micro.inventory_service.service;

import com.micro.inventory_service.dto.InventoryResponse;
import com.micro.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    //check xem sluong san pham  ton tai trong kho co du  hay khong (dua tren ma cua san pham , cu the la skuCode)
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode){
        //test vi du neu call cham thi phan ngat mach se xu li nhu nao , vi du la cham 10s , vuot qua cho 3s nen bi
        // bao exception , tuc la ben ordercontroller no se khong cho den tan 10s , ma chi cho 3s thoi
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");

        return  inventoryRepository.findBySkuCodeIn(skuCode).stream()
               .map(inventory ->
                   InventoryResponse.builder().skuCode(inventory.getSkuCode())
                           .isInStock(inventory.getQuantity() > 0).build() //neu con hang thi set isInStock la true , khong thi la false
               ).toList();
    }
}
