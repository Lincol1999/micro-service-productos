package com.group.microserviceproductos.controller;

import com.group.microserviceproductos.entity.Product;
import com.group.microserviceproductos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProducController {

    @Autowired
    private ProductService productService;

    @GetMapping
    //Devuelve toda la lista que existe en nuestra DB
    public ResponseEntity<List<Product>> listProduct(){
        //consultamos la lista de los productos
        List<Product> products = productService.listAllProduct();

        if (products.isEmpty()){
            //noContent, nos da una peticion con exito que no devuelve contenido en la respuesta
            return ResponseEntity.noContent().build();
        }

        //Si nos devuelve un producto, haremos una lista con los productos.
        return ResponseEntity.ok(products);
    }
}
