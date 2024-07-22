package com.yobrunox.webbasic.service;

import com.yobrunox.webbasic.dto.DetailAddDTO;
import com.yobrunox.webbasic.model.Detail;
import com.yobrunox.webbasic.model.Order;
import com.yobrunox.webbasic.model.Type;
import com.yobrunox.webbasic.repository.DetailRepository;
import com.yobrunox.webbasic.repository.OrderRepository;
import com.yobrunox.webbasic.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DetailService {

    private final DetailRepository detailRepository;
    private final TypeRepository typeRepository;
    public Detail addDetail(DetailAddDTO detailAddDTO){

        Type type = typeRepository.getById(detailAddDTO.getIdType());

        Detail detail = Detail.builder()
                .description(detailAddDTO.getDescription())
                .price(detailAddDTO.getPrice())
                .creationDate(LocalDateTime.now())
                .type(type)
                .order(detailAddDTO.getOrder())
                .build();
        return detailRepository.saveAndFlush(detail);
    }



}
