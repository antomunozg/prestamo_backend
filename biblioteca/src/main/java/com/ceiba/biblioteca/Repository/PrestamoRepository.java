package com.ceiba.biblioteca.Repository;

import org.springframework.stereotype.Repository;

import com.ceiba.biblioteca.Entidad.Prestamo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByIdentificacionUsuarioAndFechaMaximaDevolucionIsNull(String identificacionUsuario);
    
}
