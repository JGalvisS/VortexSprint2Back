package com.example.vortex_games.service;

import com.example.vortex_games.Dto.DtoBooking;
import com.example.vortex_games.Dto.DtoFechasBusqueda;
import com.example.vortex_games.Dto.DtopProductos;
import com.example.vortex_games.entity.Booking;
import com.example.vortex_games.entity.Product;
import com.example.vortex_games.entity.User;
import com.example.vortex_games.exception.ResourceNotFoundException;
import com.example.vortex_games.repository.BookingRepository;
import com.example.vortex_games.repository.ProductRepository;
import com.example.vortex_games.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;
@Log4j2
@Service

public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
     private ProductRepository productRepository;

    //Methods Manual


    /*public Booking addBooking(Booking booking) {
        Optional<User> usuarioReserva = userRepository.findByUsername(booking.getUsuario().getUsername());
        if (usuarioReserva.isPresent()) {
            booking.setUsuario(usuarioReserva.get());
            Set<Product> productosEnReserva = new HashSet<>();
            int contador=0;
            for (Product producto : booking.getProductosReservados()) {
                boolean productoExistente = false;
                for (Booking reservaExistente : this.listaReservas()) {
                    if (reservaExistente.getProductosReservados().contains(producto)) {
                        productoExistente = true;
                        contador++;
                        break;
                    }
                }
                // Si el producto no existe en ninguna reserva existente, agregarlo
                if (!productoExistente) {
                    Optional<Product> productoEncontrado = productRepository.findByName(producto.getName());
                    productosEnReserva.add(productoEncontrado.get());
                }
            }


            if(contador>0){
                if (!fechasCoinciden(booking)) {
                    // Asignar los productos únicos a la reserva
                    booking.setProductosReservados(productosEnReserva);
                    // Guardar la reserva
                    return bookingRepository.save(booking);
                }
                else{
                    return null;
                }
            }
            else{
                booking.setProductosReservados(productosEnReserva);
                return bookingRepository.save(booking);
            }


        }
        return null;
    }*/

    public DtoBooking addBooking(Booking booking){
        Optional<User> usuarioReserva = userRepository.findByUsername(booking.getUsuario().getUsername());
        booking.setUsuario(usuarioReserva.get());
        Set<Product> productosEnReserva = new HashSet<>();
        DtoFechasBusqueda fechaReserva= new DtoFechasBusqueda(booking.getFechaInicio(),booking.getFechaFin());
        for (Product producto: booking.getProductosReservados()) {
            for (Product disponible: this.ProductosDisponibles(fechaReserva)) {
               if(producto.getName().equals(disponible.getName())){
                   productosEnReserva.add(disponible);
               }
            }
        }
        if(productosEnReserva.size()==booking.getProductosReservados().size()){
            booking.setProductosReservados(productosEnReserva);
           Booking bookingGuardado= bookingRepository.save(booking);
            return bookingADto(bookingGuardado);
        }

        return null;

    }

    public List<DtoBooking> listaReservas(){
        List<DtoBooking> bookingsDto=new ArrayList<>();
        for (Booking book: bookingRepository.findAll()) {
            bookingsDto.add(bookingADto(book));
        }
        return  bookingsDto;
    }

  /* public List<Product> ProductosDisponibles(DtoFechasBusqueda dtoFechasBusqueda){
        List<Booking> reservas = bookingRepository.findAll();
        List<Product> productosDisponibles = new ArrayList<>();
        List<Product> productosDeLaAplicacion = productRepository.findAll();

        LocalDate inicio = dtoFechasBusqueda.getInicio();
        LocalDate fin = dtoFechasBusqueda.getFin();

        if (inicio == null && fin == null) return productosDeLaAplicacion;

        // Iterar sobre todos los productos de la aplicación
        for (Product pro: productosDeLaAplicacion ) {
            boolean productoEnReserva = false;
            // Verificar si el producto está presente en alguna reserva dentro del rango especificado
            for (Booking reserva: reservas) {
                for (Product productoReservado: reserva.getProductosReservados()) {

                    boolean reservaEnRango = inicio == null || fin == null ||
                            (!inicio.isAfter(reserva.getFechaFin()) && !fin.isBefore(reserva.getFechaInicio()));
                    // Si el producto está en alguna reserva dentro del rango, marcarlo como reservado
                    if (pro.getId().equals(productoReservado.getId()) && reservaEnRango) {
                        productoEnReserva = true;
                        break;
                    }
                }
                if(productoEnReserva) {
                    break;
                }
            }
            // Si el producto no está reservado en ningún momento dentro del rango de fechas, agregarlo a la lista de productos disponibles
            if (!productoEnReserva) {
                productosDisponibles.add(pro);
            }
        }
        return productosDisponibles;
    }*/

    public List<Product> ProductosDisponibles(DtoFechasBusqueda dtoFechasBusqueda){
        List<Booking> reservas = bookingRepository.findAll();
        List<Product> productosDisponibles = new ArrayList<>();
        List<Product> productosDeLaAplicacion = productRepository.findAll();

        LocalDate inicio = dtoFechasBusqueda.getInicio();
        LocalDate fin = dtoFechasBusqueda.getFin();

        if (inicio == null && fin == null) return productosDeLaAplicacion;

        // Iterar sobre todos los productos de la aplicación
        for (Product pro: productosDeLaAplicacion ) {
            boolean productoEnReserva = false;
            // Verificar si el producto está presente en alguna reserva dentro del rango especificado
            for (Booking reserva: reservas) {
                for (Product productoReservado: reserva.getProductosReservados()) {

                    boolean reservaEnRango = inicio == null || fin == null ||
                            (!inicio.isAfter(reserva.getFechaFin()) && !fin.isBefore(reserva.getFechaInicio()));
                    // Si el producto está en alguna reserva dentro del rango, marcarlo como reservado
                    if (pro.getId().equals(productoReservado.getId()) && reservaEnRango) {
                        productoEnReserva = true;
                        break;
                    }
                }
                if(productoEnReserva) {
                    break;
                }
            }
            // Si el producto no está reservado en ningún momento dentro del rango de fechas, agregarlo a la lista de productos disponibles
            if (!productoEnReserva) {
                productosDisponibles.add(pro);
            }
        }
        return productosDisponibles;
    }

    public List<DtoFechasBusqueda> fechasNoDisponiblesXProducto(Long productId){
        Product productoBuscado = productRepository.findById(productId).get();
        List<Booking> reservas = bookingRepository.findAll();
        List<DtoFechasBusqueda> fechasReservadas = new ArrayList<>();
        for (Booking booking: reservas){
            DtoFechasBusqueda fechaInicioFin = new DtoFechasBusqueda();
            if((booking.getFechaFin().isAfter(LocalDate.now()) || booking.getFechaFin().isEqual(LocalDate.now())) && booking.getProductosReservados().contains(productoBuscado)){
                fechaInicioFin.setInicio(booking.getFechaInicio());
                fechaInicioFin.setFin(booking.getFechaFin());
                fechasReservadas.add(fechaInicioFin);
            }
        }
        return fechasReservadas;
    }

    public List<Booking> reservasFinalizadas(){
        List<Booking> reservasFinalizadas = new ArrayList<>();
        for (Booking booking: bookingRepository.findAll()){
            if(booking.getFechaFin().isBefore(LocalDate.now())) reservasFinalizadas.add(booking);
        }
        return reservasFinalizadas;
    }

    public DtoBooking bookingADto(Booking booking){
        DtoBooking dtoBooking=new DtoBooking();
        List<DtopProductos> productos=new ArrayList<>();
        dtoBooking.setId(booking.getId());
        dtoBooking.setFechaInicio(booking.getFechaInicio());
        dtoBooking.setFechaFin(booking.getFechaFin());
        dtoBooking.setUserName(booking.getUsuario().getUsername());
        for (Product pro: booking.getProductosReservados()) {
            productos.add(new DtopProductos(pro.getId(),pro.getName()));
        }
        dtoBooking.setProductos(productos);
        return dtoBooking;
    }


}
