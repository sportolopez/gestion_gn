package com.sporto.ng.gestion_gn.dao;
import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.CierreCaja;

public interface CierreCajaDao extends CrudRepository<CierreCaja, LocalDate> {

}