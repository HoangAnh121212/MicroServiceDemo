package com.noti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
    //trinh lang nghe cua kafka
    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlaceEvent orderPlacedEvent){
        //send email notification hay la logic gi cung dc ...., o day toi dang in ra log de check
        log.info("Received Notification for Order -{}",orderPlacedEvent.getOrderNumber());
//        System.out.println("Received Notification for Order - " + orderPlacedEvent.getOrderNumber());
    }


}