package com.yobrunox.webbasic.service;

import com.yobrunox.webbasic.configuration.JwtService;
import com.yobrunox.webbasic.dto.ResponseAuth;
import com.yobrunox.webbasic.dto.UserLoginDTO;
import com.yobrunox.webbasic.dto.UserRegisterDTO;
import com.yobrunox.webbasic.exception.BusinessException;
import com.yobrunox.webbasic.model.ERole;
import com.yobrunox.webbasic.model.Role;
import com.yobrunox.webbasic.model.UserEntity;
import com.yobrunox.webbasic.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public ResponseAuth login(UserLoginDTO userLoginDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        //GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+user.getAuthorities());
        UserEntity users = userRepository.findByUser(userLoginDTO.getUsername()).orElseThrow(() ->
            new BusinessException("400-p",HttpStatus.ALREADY_REPORTED,"Usuario no existente")

        );


        UserDetails user = users;


        String token=jwtService.getToken(user);
        return ResponseAuth.builder()
                .token(token)
                .build();
    }

    public String registerForAdmin(UserRegisterDTO userDTO,String a){
        String username = jwtService.getUsernameFromToken(userDTO.getTkAdm());
        UserEntity admin = userRepository.findByUser(username).orElseThrow();

        UserEntity user = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .admin(admin)
                .roles(Set.of(Role.builder().role(ERole.valueOf(ERole.USER.name())).build()))
                .build();
        userRepository.save(user);
        if(user != null){
            return "Usuario " + user.getUsername() + " registrado correctamente";
        }else{
            return "Error, pero este error no esta capturado, no hay excepciones";
        }
    }

    public ResponseAuth registerAll(UserRegisterDTO userDTO){

        UserEntity user = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(
                        Set.of(Role.builder()
                                .role(ERole.USER)
                                .build(),
                                Role.builder()
                                        .role(ERole.ADMIN)
                                        .build()
                        )
                )
                .build();
        userRepository.save(user);
        return ResponseAuth.builder().token(jwtService.getToken(user)).build();
    }
}
