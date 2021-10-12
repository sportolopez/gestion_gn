package com.sporto.ng.gestion_gn.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sporto.ng.gestion_gn.model.Cliente;

public interface ClienteDao extends CrudRepository<Cliente, Integer> {

	List<Cliente> findAll();
}