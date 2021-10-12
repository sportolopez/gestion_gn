package com.sporto.ng.gestion_gn.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.ListaDao;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.view.ClienteDialog;
import com.sporto.ng.gestion_gn.view.ClientePanel;
import com.sporto.ng.gestion_gn.view.HomeForm;

@Component
public class ClienteController {

	ClienteDao clienteDao;
	ClientePanel panelClientes;
	ClienteDialog clienteDialog;

	@Autowired
	public ClienteController(ListaDao listaDao, ClienteDao clienteDao, HomeForm homeForm) {
		this.clienteDao = clienteDao;
		this.panelClientes = homeForm.getPanelClientes();
		List<Lista> listaPrecios = listaDao.findAll();

		clienteDialog = new ClienteDialog(listaPrecios.toArray(new Lista[listaPrecios.size()]));
		clienteDialog.getBtnGuardar().addActionListener(l -> guardarCliente(clienteDialog));
		panelClientes.getBtnExportar().addActionListener(i -> exportarClientes());
		panelClientes.getBtnNuevoCliente().addActionListener(i -> nuevoCliente());
		cargarListaInicial();

		panelClientes.getTableClientes().addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = panelClientes.getTableClientes().rowAtPoint(evt.getPoint());
				int idCliente = Integer.valueOf(panelClientes.getTableClientes().getValueAt(row, 0).toString());
				int col = panelClientes.getTableClientes().columnAtPoint(evt.getPoint());
				editarCliente(listaPrecios, idCliente);
			}
		});
	}

	private void nuevoCliente() {
		clienteDialog.setVisible(true);
		clienteDialog.limpiarCampos();
	}

	private void editarCliente(List<Lista> listas, int idCliente) {
		clienteDialog.cargarCampos(clienteDao.findById(idCliente).get());
		clienteDialog.setVisible(true);
	}

	private void exportarClientes() {

	}

	private void guardarCliente(ClienteDialog dialog) {
		if (dialog.validar()) {
			clienteDao.save(dialog.getCliente());
			dialog.setVisible(false);
			cargarListaInicial();
		}
	}

	public void cargarListaInicial() {
		panelClientes.cargarLista(clienteDao.findAll());
	}

}
