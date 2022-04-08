package com.group.microserviceproductos.service;

import com.group.microserviceproductos.entity.Category;
import com.group.microserviceproductos.entity.Product;
import com.group.microserviceproductos.repository.ProductoRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor //De esta forma se hace una inyeccion de dependencias pero por constructor
//Esto quiere decir, se va inyectar el ProductoRespository en el constructor ProductServiceImpl
public class ProductServiceImpl implements  ProductService{


    private final  ProductoRespository productoRespository;

    @Override
    public List<Product> listAllProduct() {
        return productoRespository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productoRespository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productoRespository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if(productDB == null){
            return null;
        }

        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());

        return productoRespository.save(productDB);

    }

    @Override
    public Product deleteProduct(Long id) {

        Product productDB = getProduct(id);
        if(productDB == null){
            return null;
        }
        productDB.setStatus("DELETE");
        return productoRespository.save(productDB);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productoRespository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if(productDB == null){
            return null;
        }

        Double stock = productDB.getStock() + quantity;
        productDB.setStock(stock);
        return productoRespository.save(productDB);
    }
}
