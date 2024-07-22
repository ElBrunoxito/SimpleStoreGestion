package com.yobrunox.webbasic.controller;

import com.yobrunox.webbasic.dto.OrderAddDTO;
import com.yobrunox.webbasic.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("getAll")
    //Recupera todo, por que no hay una compañia
    public ResponseEntity<?> getOrderAll(@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer ","");
        return  new ResponseEntity<>(orderService.getOrderAll(token),HttpStatus.OK);
    }


    @PostMapping("add")
    public ResponseEntity<?> addOrder(@RequestHeader("Authorization") String authHeader,
                                      @RequestBody OrderAddDTO orderAddDTO){
        String token = authHeader.replace("Bearer ","");
        return  new ResponseEntity<>(orderService.addOrder(token,orderAddDTO),HttpStatus.OK);
    }


}
