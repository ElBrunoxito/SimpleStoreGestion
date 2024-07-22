package com.yobrunox.webbasic.controller;


import com.yobrunox.webbasic.dto.ResponseAuth;
import com.yobrunox.webbasic.dto.UserLoginDTO;
import com.yobrunox.webbasic.dto.UserRegisterDTO;
import com.yobrunox.webbasic.exception.BusinessException;
import com.yobrunox.webbasic.exception.RequestException;
import com.yobrunox.webbasic.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("all/login")
    public ResponseEntity<ResponseAuth> login(@RequestBody UserLoginDTO userLoginDTO){
        //Regla de negocio //
        if(userLoginDTO.getUsername().isEmpty()){
            throw new BusinessException("40",HttpStatus.BAD_REQUEST,"username required");
            //throw new RequestException(HttpStatus.BAD_REQUEST,"username required");
        }
        if(userLoginDTO.getUsername().isEmpty() || userLoginDTO.getPassword().equals("")){
            throw new RequestException(HttpStatus.BAD_REQUEST,"username and password required");
        }


        return new ResponseEntity(userService.login(userLoginDTO), HttpStatus.OK);

    }
    @PostMapping("all/register")
    public ResponseEntity<ResponseAuth> registe(@RequestBody UserRegisterDTO userRegisterDTO){
        return new ResponseEntity<>(userService.registerAll(userRegisterDTO), HttpStatus.OK);

    }

    @PostMapping("admin/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO userRegisterDTO){
        return new ResponseEntity<>(userService.registerForAdmin(userRegisterDTO,"ds"), HttpStatus.OK);

    }
    @GetMapping("admin/get")
    public ResponseEntity<String> get(){
        return new ResponseEntity<>("Ok admin", HttpStatus.OK);

    }


}
