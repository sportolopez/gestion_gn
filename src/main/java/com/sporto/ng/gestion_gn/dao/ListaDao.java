package com.sporto.ng.gestion_gn.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Lista;

public interface ListaDao extends CrudRepository<Lista, String> {

	List<Lista> findAll();
}