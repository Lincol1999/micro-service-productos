package com.group.microserviceproductos;


import com.group.microserviceproductos.entity.Category;
import com.group.microserviceproductos.entity.Product;
import com.group.microserviceproductos.repository.ProductoRespository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductoRespository productoRespository;

    @Test //Con este test haremos la prueba
    public void whenFindByCategory_thenReturnListProduct(){

        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1295.25"))
                .status("Created")
                .createAt(new Date()).build(); //Para generar la instancia ponemos build

        //Guardamos nuestro producto de prueba
        productoRespository.save(product01);

        //Realizamos la busqueda
        List<Product> founds = productoRespository.findByCategory(product01.getCategory());

        //Insertamos un nuevo valor
        Assertions.assertThat(founds.size()).isEqualTo(3);

    }
}
