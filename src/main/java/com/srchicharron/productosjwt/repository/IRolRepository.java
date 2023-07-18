package com.srchicharron.productosjwt.repository;

import com.srchicharron.productosjwt.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
}
