package com.yobrunox.webbasic.controller;

import com.yobrunox.webbasic.dto.DashboardDTO;
import com.yobrunox.webbasic.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class DashboardController {

    private final OrderService orderService;

    @GetMapping("/admin/dashboard/{token}")
    public ResponseEntity<?> getDashboardAdmin(@PathVariable String token){
        DashboardDTO data = orderService.getDataDashboard(token);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
