package com.srchicharron.productosjwt.model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponseBean {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;
}
