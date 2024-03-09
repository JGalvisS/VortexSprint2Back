package com.example.vortex_games.service;


import com.example.vortex_games.entity.Category;
import com.example.vortex_games.entity.Characteristic;
import com.example.vortex_games.entity.Image;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.repository.CategoryRepository;
import com.example.vortex_games.repository.CharacteristicRepository;
import com.example.vortex_games.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CharacteristicRepository characteristicRepository;


    //Manual Methods

    public Product addProduct(Product producto){
        log.info(producto);
        if(producto.getCategory().getId()==null){
            Image imagenSinCategoria = new Image("https://cdns-images.dzcdn.net/images/cover/c40b8889fd4153ade9bdf3808650273d/264x264.jpg");
            Category categoriaVacia=new Category("Sin categoria","Producto sin categoria", imagenSinCategoria);
            Optional<Category> categoriaEncontrada=categoryRepository.findByTitle(categoriaVacia.getTitle());
            if(categoriaEncontrada.isEmpty()){
                categoriaVacia=categoryRepository.save(categoriaVacia);
                producto.setCategory(categoriaVacia);
            }
            else{
                producto.setCategory(categoriaEncontrada.get());
            }

        }

        producto.getImages().forEach(image -> image.setProduct(producto));
        return productRepository.save(producto);
    }
    public List<Product> searchByCategory(String category) {
        Category categoriaEncontrada=categoryRepository.findByTitle(category).get();
         return productRepository.findByCategory(categoriaEncontrada);
    }

    public List<Product> searchByCharacteristic(String characteristic){
        Characteristic characteristicEncontrada = characteristicRepository.findByName(characteristic).get();
        List<Product> productos = new ArrayList<>();
        for(Product product: productRepository.findAll()){
            for(Characteristic characteristic1: product.getCharacteristics()){
                if(characteristic1.equals(characteristicEncontrada)){
                    productos.add(product);
                }
            }
        }
        return productos;
    }

    public List<Product> searchByConsole(String console) {
        System.out.println("se esta buscando Play Station 4");
        System.out.println(productRepository.findByConsole("Play Station 4"));
        return productRepository.findByConsole(console);}
    public Optional<Product> searchById(Long id){
        return productRepository.findById(id);
    }
    public Optional<Product> searchByName(String name){
        return productRepository.findByName(name);
    }
    public List<Product> listProducts(){
        return productRepository.findAll();
    }


    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void updateProduct(Product product){

        Optional<Product> productoEncontrado = productRepository.findById(product.getId());
        if (productoEncontrado.isPresent()) {
            productRepository.save(product);
        }

    }

}