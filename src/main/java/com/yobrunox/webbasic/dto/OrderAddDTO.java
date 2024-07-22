package com.yobrunox.webbasic.dto;

import com.yobrunox.webbasic.model.Detail;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddDTO {
    private List<DetailAddDTO> details;
}
