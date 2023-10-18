package com.ceiba.biblioteca.Services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.biblioteca.Entidad.Prestamo;
import com.ceiba.biblioteca.Exceptions.BadRequestException;
import com.ceiba.biblioteca.Repository.PrestamoRepository;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    public Prestamo realizarPrestamo(Prestamo prestamo) {
         if (prestamo.getTipoUsuario() < 1 || prestamo.getTipoUsuario() > 3) {
        throw new BadRequestException("Tipo de usuario no permitido en la biblioteca");
    }

    if (prestamo.getTipoUsuario() == 3) {
        List<Prestamo> prestamosUsuario = prestamoRepository.findByIdentificacionUsuarioAndFechaMaximaDevolucionIsNull(prestamo.getIdentificacionUsuario());
        if (!prestamosUsuario.isEmpty()) {
            throw new BadRequestException("El usuario con identificación " + prestamo.getIdentificacionUsuario() + " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
        }
    }

    // Calcula la fecha de devolución
    LocalDate fechaMaximaDevolucion = LocalDate.now();
    int diasAdicionales = 0;
    switch (prestamo.getTipoUsuario()) {
        case 1:
            diasAdicionales = 10;
            break;
        case 2:
            diasAdicionales = 8;
            break;
        case 3:
            diasAdicionales = 7;
            break;
    }

    fechaMaximaDevolucion = addDaysSkippingWeekends(fechaMaximaDevolucion, diasAdicionales);

    // Formatea la fecha de devolución como una cadena
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaMaximaDevolucionString = fechaMaximaDevolucion.format(formato);

    // Establece la cadena formateada en tu entidad Prestamo
    prestamo.setFechaMaximaDevolucion(fechaMaximaDevolucionString);

    // Almacena el préstamo en la base de datos
    return prestamoRepository.save(prestamo);
    }

    public Prestamo consultarPrestamo(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return result;
    }
    
}
