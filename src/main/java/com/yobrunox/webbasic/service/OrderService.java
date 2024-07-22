package com.yobrunox.webbasic.service;

import com.yobrunox.webbasic.configuration.JwtService;
import com.yobrunox.webbasic.dto.DashboardDTO;
import com.yobrunox.webbasic.dto.DetailAddDTO;
import com.yobrunox.webbasic.dto.OrderAddDTO;
import com.yobrunox.webbasic.dto.OrderGetDTO;
import com.yobrunox.webbasic.exception.BusinessException;
import com.yobrunox.webbasic.model.ERole;
import com.yobrunox.webbasic.model.Order;
import com.yobrunox.webbasic.model.Role;
import com.yobrunox.webbasic.model.UserEntity;
import com.yobrunox.webbasic.repository.OrderRepository;
import com.yobrunox.webbasic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    private final JwtService jwtService;
    private final DetailService detailService;
    public DashboardDTO getDataDashboard(String token){
        String username = jwtService.getUsernameFromToken(token);
        UserEntity user = userRepository.findByUser(username).orElseThrow(
                () -> new BusinessException("400", HttpStatus.BAD_GATEWAY,"Token invalido")
        );

        //DashboardDTO data = orderRepository.getDahsboard().orElseThrow(
                //() -> new BusinessException("400", HttpStatus.BAD_GATEWAY,"Error al obtener los datos")

        //);
        DashboardDTO data;

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        Double day = orderRepository.getTotalDay(username,startOfDay,endOfDay).orElseThrow();


        Set<Role> roles = user.getRoles();
        System.out.println(roles);

        if(roles.stream().anyMatch(role -> role.getRole() == ERole.ADMIN)){

            Double total = orderRepository.getTotal(username).orElseThrow();
            data = DashboardDTO.builder()
                    .daySales(day)
                    .totalSales(total)
                    .build();
        }else {
            data = DashboardDTO.builder()
                    .daySales(day)
                    .build();
        }




        return data;




    }


    public List<OrderGetDTO> getOrderAll(String token){
        //Verificar si el tokenm todavia esta activo
        String username = jwtService.getUsernameFromToken(token);
        System.out.println("xddd" + username);
        if(Objects.isNull(username)){
            throw new BusinessException("403",HttpStatus.FORBIDDEN,"Token invalido vuelve a iniciar sesion");
        }

        return orderRepository.getOrderAll();
    }

    @Transactional
    public Order addOrder(String token, OrderAddDTO orderAddDTO){
        String username = jwtService.getUsernameFromToken(token);
        UserEntity user = userRepository.findByUser(username).orElseThrow(
                () -> new BusinessException("4000",HttpStatus.BAD_GATEWAY,"Token invalido")
        );



        //Crear la order luego se crea los details
        Double total = orderAddDTO.getDetails().stream().mapToDouble(DetailAddDTO::getPrice).sum();
        if(total == 0){
            throw new BusinessException("102",HttpStatus.PROCESSING,"Productos con 0??");
        }

        Order order = Order.builder()
                .total(total)
                .creationDate(LocalDateTime.now())
                .user(user)
                .build();
        Order orderSave = orderRepository.save(order);

        if(Objects.isNull(orderSave)){
            throw new BusinessException("500",HttpStatus.BAD_REQUEST,"Token invalido");
        }

        List<DetailAddDTO> details = orderAddDTO.getDetails();
        //details.forEach(d->d.setOrder(orderSave));
        for(var d : details){
            d.setOrder(order);
            detailService.addDetail(d);
        }
        return order;
    }
}
