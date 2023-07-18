package com.srchicharron.productosjwt.controller;

import com.srchicharron.productosjwt.model.entity.Producto;
import com.srchicharron.productosjwt.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/producto")
public class ProductoController {
    @Autowired
    private IProductoRepository productoRepository;
    @GetMapping("/all")
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto savedProducto = productoRepository.save(producto);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setPrecio(productoActualizado.getPrecio());
                    // Copia todos los campos que necesites actualizar
                    Producto updatedProducto = productoRepository.save(producto);
                    return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    productoActualizado.setId(Math.toIntExact(id));
                    Producto savedProducto = productoRepository.save(productoActualizado);
                    return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
                });
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
