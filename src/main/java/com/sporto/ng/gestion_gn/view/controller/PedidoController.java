package com.sporto.ng.gestion_gn.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.PedidoDialog;
import com.sporto.ng.gestion_gn.view.PedidoPanel;

@Component
public class PedidoController {

	ClienteDao clienteDao;
	PedidoPanel panel;
	PedidoDialog dialog;
	PrecioDao precioDao;
	PedidoDao pedidoDao;
	
	@Autowired
	public PedidoController(ClienteDao clienteDao, HomeForm homeForm, PedidoDao pedidoDao, ProductoDao productoDao,PrecioDao precioDao) {
		this.clienteDao = clienteDao;
		this.pedidoDao = pedidoDao;
		this.panel = homeForm.getPanelPedidos();
		
	
		//dialog = new PedidoDialog(productoDao.findAll(),clienteDao.findAll(),pedidoDao,homeForm,precioDao);
		
		panel.getBtnNuevoPedido().addActionListener(l -> nuevoPedido());
	}
	
	private void nuevoPedido() {
		dialog.limpiarCampos();
		dialog.setVisible(true);
		
	}
	
}
