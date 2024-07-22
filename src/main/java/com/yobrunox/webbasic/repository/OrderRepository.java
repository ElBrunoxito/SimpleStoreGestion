package com.yobrunox.webbasic.repository;

import com.yobrunox.webbasic.dto.DashboardDTO;
import com.yobrunox.webbasic.dto.OrderGetDTO;
import com.yobrunox.webbasic.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT COALESCE(SUM(D.price),0) FROM Detail D WHERE D.order.user.username = :username")

    Optional<Double> getTotal(@Param("username") String username);

    @Query("SELECT COALESCE(SUM(D.price),0) FROM Detail D WHERE D.order.user.username = :username AND D.creationDate BETWEEN :start AND :end")

    Optional<Double> getTotalDay(@Param("username") String username,
                                       @Param("start")LocalDateTime start,
                                       @Param("end") LocalDateTime end);


    @Query("SELECT new com.yobrunox.webbasic.dto.OrderGetDTO(O.id,O.total,O.creationDate,COUNT(D.id),O.user.username)" +
            "FROM Order O " +
            "LEFT JOIN Detail D ON D.order.id = O.id WHERE D.order.id = O.id GROUP BY O.id,O.total,O.creationDate,O.user.username" )
    List<OrderGetDTO> getOrderAll();
}
