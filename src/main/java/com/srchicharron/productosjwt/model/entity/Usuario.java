package com.srchicharron.productosjwt.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    public Usuario(){

    }

    @Id
    @Column(unique = true, length = 50)
    @NotEmpty
    private String username;

    @NotEmpty
    @Column(length = 255)
    @Size(min = 8, max = 255)
    private String password;
    private Boolean enabled;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Rol> authorities;
}

