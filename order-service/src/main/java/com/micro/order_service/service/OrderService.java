package com.micro.order_service.service;

import com.micro.order_service.dto.InventoryResponse;
import com.micro.order_service.dto.OrderLineItemsDto;
import com.micro.order_service.dto.OrderRequest;
import com.micro.order_service.model.Order;
import com.micro.order_service.model.OrderLineItems;
import com.micro.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@Transactional

public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderLineItems> orderLineItemsList =
            orderRequest.getOrderLineItemsDtoList().stream()
                    .map(this::mapToDto).toList();
            order.setOrderLineItemsList(orderLineItemsList);

            //lay danh sach ma code
            List<String> skuCodes =order.getOrderLineItemsList().stream().map(
                    orderLineItems -> orderLineItems.getSkuCode()
            ).toList();
            //cách giao tiếp giữa các service với nhau , kiểu 1 (đồng bộ)
            //gọi đến inventory service , nếu sản phẩm trong kho còn thì đặt được hàng
            //vì bên inventory controller đang có api check trong kho là kiểu GET nên webClient dùng phương thức get
         InventoryResponse[] inventoryResponsesArray =   webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                    .retrieve().bodyToMono(InventoryResponse[].class) //để có thể đọc dữ liệu từ phản hồi , bạn
                 // cần dùng mono và tryển voà đó kiểu dữ liệu trả về , vi du nhu response cuar api tren la 1 list , thi de la []
                 .block();

        for (InventoryResponse response : inventoryResponsesArray) {
            if(response.isInStock() ==  true) {
                orderRepository.save(order);
            }else{
                throw new IllegalArgumentException("Product is not in stock, plese try again later");
            }
        }


    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
