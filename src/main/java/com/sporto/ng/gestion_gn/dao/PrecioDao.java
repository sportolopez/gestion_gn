package com.sporto.ng.gestion_gn.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.model.PrecioId;

public interface PrecioDao extends CrudRepository<Precio, PrecioId> {

	List<Precio> findByLista(Lista lista);
}