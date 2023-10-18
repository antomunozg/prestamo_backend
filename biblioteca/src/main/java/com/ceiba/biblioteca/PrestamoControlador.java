package com.ceiba.biblioteca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.biblioteca.Entidad.Prestamo;
import com.ceiba.biblioteca.Services.PrestamoService;

@RestController
public class PrestamoControlador {

     @Autowired
    private PrestamoService prestamoService;

    @PostMapping("/prestamo")
    public ResponseEntity<Prestamo> crearPrestamo(@RequestBody Prestamo prestamo) {
    Prestamo nuevoPrestamo = prestamoService.realizarPrestamo(prestamo);
    return ResponseEntity.status(HttpStatus.OK).body(nuevoPrestamo);
}

    @GetMapping("/prestamo/{id}")
    public ResponseEntity<Prestamo> consultarPrestamo(@PathVariable Long id) {
        Prestamo prestamo = prestamoService.consultarPrestamo(id);
        if (prestamo != null) {
            return ResponseEntity.status(HttpStatus.OK).body(prestamo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}

