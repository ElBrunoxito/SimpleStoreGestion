package com.yobrunox.webbasic.dto;

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
public class OrderGetDTO {
    private UUID id;
    private Double total;
    private LocalDateTime creationDate;
    private Long countDetails;
    private String username;

}
