package com.example.dawasyu.common.annotation;


import com.example.dawasyu.domain.order.dto.response.OrderStatusResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OrderAop {

        @AfterReturning(
                pointcut = "execution(* com.example.dawasyu.domain.order.service.*.changedStatus(..))",
                returning = "response"
        )
        public void afterChangedStatus(JoinPoint joinPoint, Object response) {

            OrderStatusResponseDto dto = (OrderStatusResponseDto) response;

            log.info("변경 후 상태: " + dto.getOrderStatus());
            log.info("주문 ID : "+dto.getOrderId());
            log.info("가게 ID : "+dto.getStoreId());
            log.info("변경시각 : "+dto.getUpdatedAt());
        }

}
