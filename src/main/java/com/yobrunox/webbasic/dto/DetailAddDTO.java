package com.yobrunox.webbasic.dto;

import com.yobrunox.webbasic.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailAddDTO {
    private String description;
    private Double price;
    private UUID idType;
    private Order order;
}
