package com.srchicharron.productosjwt.controller;

import com.srchicharron.productosjwt.jwt.JwtUtils;
import com.srchicharron.productosjwt.model.bean.AuthRequestBean;
import com.srchicharron.productosjwt.model.bean.JwtResponseBean;
import com.srchicharron.productosjwt.model.bean.MessageResponseBean;
import com.srchicharron.productosjwt.model.entity.Usuario;
import com.srchicharron.productosjwt.repository.IUsuarioRepository;
import com.srchicharron.productosjwt.service.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequestBean authRequestBean) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestBean.getUsername(), authRequestBean.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseBean(jwt,"Bearer",
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequestBean authRequestBean) {
        if (usuarioRepository.existsByUsername(authRequestBean.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponseBean("Error: Username is already taken!"));
        }

        Usuario usuario = new Usuario(authRequestBean.getUsername(),encoder.encode(authRequestBean.getPassword()),
                authRequestBean.getEnabled(), authRequestBean.getAuthorities());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new MessageResponseBean("User registered successfully!"));
    }
}
