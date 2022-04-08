package com.group.microserviceproductos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.microserviceproductos.entity.Category;
import com.group.microserviceproductos.entity.Product;
import com.group.microserviceproductos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProducController {

    @Autowired
    private ProductService productService;

    @GetMapping
    //Devuelve toda la lista que existe en nuestra DB
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> products = new ArrayList<>();
        if (categoryId == null){
            //consultamos la lista de los productos
            products = productService.listAllProduct();

            if (products.isEmpty()){
                //noContent, nos da una peticion con exito que no devuelve contenido en la respuesta
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());

            if (products.isEmpty()){
                //noContent, nos da una peticion con exito que no devuelve contenido en la respuesta
                return ResponseEntity.notFound().build();  //No existen productos para esta categoria
            }
        }
        //Si nos devuelve un producto, haremos una lista con los productos.
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product>getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);

        if (product == null){
            return ResponseEntity.notFound().build();  //No existen productos para esta categoria
        }else{
            return ResponseEntity.ok(product);
        }
    }

    //Creamos un producto
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){

        if (result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreate = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    //Metodo para actualizar producto
    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,@RequestBody Product product){

        product.setId(id);

        //Actualizamos el producto
        Product productDB = productService.updateProduct(product);

        if (productDB == null){
            return ResponseEntity.notFound().build();
        }

        //Si se actualiza, lo retornamos
        return ResponseEntity.ok(productDB);
    }

    //Eliminar un producto
    @DeleteMapping(value = "/{id}")
    public  ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){

        Product productDelete = productService.deleteProduct(id);

        if (productDelete == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productDelete);
    }

    //Actulizar el stock del product
    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id,@RequestParam(name = "quantity", required = true) Double quantity){ //Enviamos el id, y la cantidad de producto que vamos actualizar

        Product product = productService.updateStock(id, quantity);
        if (product == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);

    }


    //Metodo para formatear el mensaje
    private String formatMessage(BindingResult result){  //Recibimos un obj de BindingResult

        List<Map<String, String>> errors = result.getFieldErrors().stream() //obtenemos todos los campos de errores con getFieldErrors, Usamos stream para generar un flujo.
                .map(err -> { //Usamos map para capturar cada elemento del flujo
                    //Formateamos nuestro mensaje
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage()); //err.getField() -> cambiando el nombre del campo y err.getDefaultMessage() -> mostramos el mensaje del error
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .message(errors).build();

        ObjectMapper mapper = new ObjectMapper();

        String jsonString="";

        try {
            jsonString = mapper.writeValueAsString(errorMessage); //Cogemos el obj errorMessage y transformalo en un jsonString
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return jsonString;
    }
}
