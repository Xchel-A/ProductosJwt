package com.srchicharron.productosjwt.model.bean;

import com.srchicharron.productosjwt.model.entity.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthRequestBean {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Boolean enabled;

    private List<Rol> authorities;
}
