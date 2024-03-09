package com.example.vortex_games;

import com.example.vortex_games.entity.Category;
import com.example.vortex_games.entity.Characteristic;
import com.example.vortex_games.entity.Image;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.repository.CategoryRepository;
import com.example.vortex_games.repository.CharacteristicRepository;
import com.example.vortex_games.repository.ImageRepository;
import com.example.vortex_games.repository.ProductRepository;
import com.example.vortex_games.user.UserRepository;
import com.example.vortex_games.user.User;
import com.example.vortex_games.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CharacteristicRepository characteristicRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    //Constructor
    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, CharacteristicRepository characteristicRepository, CategoryRepository categoryRepository, ImageRepository imageRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.characteristicRepository = characteristicRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        if(userRepository.findByUsername("admin").isEmpty()) {
            String password = passwordEncoder.encode("admin");
            userRepository.save(new User(1,"admin", password, "admin", "admin", "adminDir", Role.ADMIN));
        }

        if(userRepository.findByUsername("user").isEmpty()) {
            String password2 = passwordEncoder.encode("user");
            userRepository.save(new User(2,"user", password2, "user", "user", "userDir", Role.USER));
        }
        /*
        if (characteristicRepository.findAll().isEmpty()) {
            //Cargo características
            //Esto lo hago para que solo lo corra la primera vez que se corre la aplicación
            characteristicRepository.save(new Characteristic("Online", "Juegos online"));
            characteristicRepository.save(new Characteristic("Multi Plataforma", "Juegos Multiplataforma"));
            characteristicRepository.save(new Characteristic("Primera Persona", "Juegos primera persona"));
            characteristicRepository.save(new Characteristic("Tercera Persona", "Juegos tercera persona"));
            characteristicRepository.save(new Characteristic("Modo Historia", "Juegos con modo historia"));
            characteristicRepository.save(new Characteristic("Cooperativo", "Juegos coperativo"));
            characteristicRepository.save(new Characteristic("PEGI 18", "Mayores de 18"));
            characteristicRepository.save(new Characteristic("PEGI 16", "Mayores de 16"));
            characteristicRepository.save(new Characteristic("PEGI 14", "Mayores de 14"));
            characteristicRepository.save(new Characteristic("PEGI 12", "Mayores de 12"));

        }

        if(categoryRepository.findAll().isEmpty()) {
            //Se cargan las categorías con sus respectivas imgágenes

            categoryRepository.save(new Category("Accion", "Juegos de accion",
                    new Image("https://static.wikia.nocookie.net/videojuego/images/7/7c/Tomb_Raider_acci%C3%B3n.jpg/revision/latest?cb=20180616142353")));
            categoryRepository.save(new Category("Aventura", "Juegos de Aventura",
                    new Image("https://www2.claro.com.co/portal/recursos/co/cpp/promociones/imagenes/1637160450806-6-03-Tipos-de-videojuegos.jpg")));
            categoryRepository.save(new Category("Arcade", "Juegos de Arcade",
                    new Image("https://www2.claro.com.co/portal/recursos/co/cpp/promociones/imagenes/1637160465907-6-04-Tipos-de-videojuegos.jpg")));
            categoryRepository.save(new Category("Deportivo", "Juegos Deportivos",
                    new Image("https://www2.claro.com.co/portal/recursos/co/cpp/promociones/imagenes/1637160484886-6-05-Tipos-de-videojuegos.jpg")));
            categoryRepository.save(new Category("Estrategia", "Juegos de Estrategia",
                    new Image("https://www2.claro.com.co/portal/recursos/co/cpp/promociones/imagenes/1637160499765-6-06-Tipos-de-videojuegos.jpg")));
            categoryRepository.save(new Category("Simulación", "Juegos de Simulación",
                    new Image("https://i.blogs.es/e72264/filght/1366_2000.jpg")));
            categoryRepository.save(new Category("Musicales", "Juegos Musicales",
                    new Image("https://www.mundodeportivo.com/alfabeta/hero/2021/05/guitar-hero-1.jpg?width=768&aspect_ratio=16:9&format=nowebp")));
            categoryRepository.save(new Category("Guerra", "Juegos de Guerra",
                    new Image("https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/media/image/2018/04/juegos-guerra-pc.jpg")));

        }
*/
    }
}