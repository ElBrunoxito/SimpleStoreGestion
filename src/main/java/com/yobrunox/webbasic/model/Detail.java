package com.yobrunox.webbasic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private Double price;
    private LocalDateTime creationDate = LocalDateTime.now();


    @ManyToOne
    @JoinColumn(name = "typeId")
    private Type type;


    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

}
