package com.example.vortex_games.controller;

import com.example.vortex_games.entity.Category;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.exception.BadRequestException;
import com.example.vortex_games.exception.ExistingProductException;
import com.example.vortex_games.exception.ResourceNotFoundException;
import com.example.vortex_games.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-categoria")
    public ResponseEntity<Category> addCategoria(@RequestBody Category category) throws ExistingProductException {
        Optional<Category> searchedCategoria=categoryService.searchByName(category.getTitle());
        if(searchedCategoria.isPresent()){
            throw new ExistingProductException("The title is already in use");
        }
        else if(category !=null){
            return ResponseEntity.ok(categoryService.addCategory(category));
        }
        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Category> categoriBuscada=categoryService.searchById(id);
        if(categoriBuscada.isPresent()) {
            Category category = categoriBuscada.get();
            categoryService.deleteCategory(category);
            return ResponseEntity.ok("The category has ben delete");
        }
        else{
            throw  new ResourceNotFoundException("The category is not exist");
        }
    }

    @GetMapping("/search-categoryById/{id}")
    public ResponseEntity<Category> buscarCategoriaPorId(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Category> categoriaBuscada=categoryService.searchById(id);
        if(categoriaBuscada.isPresent()){
            return ResponseEntity.ok(categoriaBuscada.get());
        }
        else{
            throw  new ResourceNotFoundException("Categoria no existe");
        }
    }

    @GetMapping("/search-categoryByTitle/{title}")
    public ResponseEntity<Category> buscarCategoriaPorTitle(@PathVariable String title) throws ResourceNotFoundException{
        Optional<Category> categoriaBuscada=categoryService.searchByName(title);
        if(categoriaBuscada.isPresent()){
            return ResponseEntity.ok(categoriaBuscada.get());
        }
        else{
            throw  new ResourceNotFoundException("Categoria no existe");
        }
    }

    @PutMapping("/update-category")
    public ResponseEntity<String> actualizarCategoria(@RequestBody Category category) throws BadRequestException{

        try {
            categoryService.UpdateCategory(category);
            return ResponseEntity.ok("La categoria se actualizo");
        }catch (Exception e){
            throw new BadRequestException("Falla al actualizar");
        }

    }


    @GetMapping("/listar-categorias")
    public ResponseEntity<List<Category>> listarCategorias() throws ResourceNotFoundException{
        List<Category> arrayEncontrado=categoryService.listCategory();
        if(arrayEncontrado.size()>0){
            return  ResponseEntity.ok(arrayEncontrado);
        }
        else {
            throw new ResourceNotFoundException("NO hay categorias");
        }
    }



    }







