package com.group.microserviceproductos;


import com.group.microserviceproductos.entity.Category;
import com.group.microserviceproductos.entity.Product;
import com.group.microserviceproductos.repository.ProductoRespository;
import com.group.microserviceproductos.service.ProductService;
import com.group.microserviceproductos.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock //Esta parte de los acceso de dato seran moqueados.
    private ProductoRespository productoRespository;

    private ProductService productService;

    @BeforeEach //Indica que se tiene que ejecutar antes de realizar nuestro test
    public void setup(){
        //Iniciamos la prueba mock con mockito
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productoRespository);

        Product computer = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("25.5"))
                .stock(Double.parseDouble("5"))
                .build(); //Para generar la instancia ponemos build


        //Creamos el Mock

        //cuando se busque un producto con el id 1, retornamos nuestro mok compute que hemos mockeado arriba.
        Mockito.when(productoRespository.findById(1L))
                .thenReturn(Optional.of(computer));

        //Actualizamos cuando el valor cambie y guardamos
        Mockito.when(productoRespository.save(computer)).thenReturn(computer);
    }

    @Test
    //Este metodo realiza una busqueda del producto que hemos mockeado
    public void whenValidGetId_ThenReturnProduct(){
        //Cuando usemos el productService.getProduct(1L), lo que nos devolvera sera el producto, linea 33
        Product found = productService.getProduct(1L);

        Assertions.assertThat(found.getName()).isEqualTo("computer");




    }

    //Test Final
    @Test
    //Vereficiamos la logica del Stock si funciona conrrectamente,
    public  void whenValidUpdateStock_ThenReturnNewStock(){
        //Validamos si agregamos un nuevo valor a nuestro Stock, este debe actualizarse.
        //Si a nuestro producto Stock le agregamos 8 unidades, el stock actual sera de 13 por que iniciamos con 5, linea 38
        Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
        //Validamos
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }

}
