package com.sporto.ng.gestion_gn.view.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.ListaDao;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PedidoProductoDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.utils.ExcelUtils;
import com.sporto.ng.gestion_gn.utils.PrecioExcelExporter;
import com.sporto.ng.gestion_gn.view.ClienteDialog;
import com.sporto.ng.gestion_gn.view.ClientePanel;
import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.PedidoDialog;
import com.sporto.ng.gestion_gn.view.model.ClienteTableModel;

@Component
public class ClienteController {

	ClienteDao clienteDao;
	ClientePanel clientePanel;
	ClienteDialog clienteDialog;
	PrecioDao precioDao;
	PedidoDao pedidoDao;
	private PedidoDialog dialog;

	@Autowired
	public ClienteController(ListaDao listaDao, ClienteDao clienteDao, HomeForm homeForm, PrecioDao precioDao, PedidoDao pedidoDao, ProductoDao productoDao, PedidoProductoDao pedidoProductoDao) {
		this.clienteDao = clienteDao;
		this.precioDao = precioDao;
		this.pedidoDao = pedidoDao;
		this.clientePanel = homeForm.getPanelClientes();
		List<Lista> listaPrecios = listaDao.findAll();
		dialog = new PedidoDialog(productoDao,pedidoDao,homeForm,precioDao,pedidoProductoDao);
		clienteDialog = new ClienteDialog(listaPrecios.toArray(new Lista[listaPrecios.size()]));
		clienteDialog.getBtnGuardar().addActionListener(l -> guardarCliente(clienteDialog));
		clientePanel.getBtnExportar().addActionListener(i -> exportarClientes());
		clientePanel.getBtnImportarPrecios().addActionListener(i -> importarExcel());
		clientePanel.getBtnNuevoCliente().addActionListener(i -> nuevoCliente());
		cargarListaInicial();

		clientePanel.getTableClientes().addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = clientePanel.getTableClientes().rowAtPoint(evt.getPoint());
				int idCliente = Integer.valueOf(clientePanel.getTableClientes().getValueAt(row, 0).toString());
				int col = clientePanel.getTableClientes().columnAtPoint(evt.getPoint());
				if (col == ClienteTableModel.COLUMN_PEDIDO)
					nuevoPedido(idCliente);
				if (col == ClienteTableModel.COLUMN_EDITAR)
					editarCliente(listaPrecios, idCliente);
				if (col == ClienteTableModel.COLUMN_EXPORTAR)
					exportarPrecios(clientePanel.getTableClientes().getValueAt(row, ClienteTableModel.COLUMN_LISTA).toString());

			}


		});
		
		Constants.setListas(listaDao.findAll());
	}
	
	private void nuevoPedido(int idCliente) {
		Cliente cliente = clienteDao.findById(idCliente).get();
		dialog.nuevoPedido(cliente);
		
		
	}
	protected void exportarPrecios(String lista) {

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Guardar como..");
		jfc.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xlsx")) {
				file = new File(file.toString() + ".xlsx");
			}
			String heading = "Clientes";
			List<Precio> findByLista = precioDao.findByLista(Lista.builder().nombre(lista).build());
			System.out.println("Lista precios"+ findByLista);
			PrecioExcelExporter precioExcelExporter = new PrecioExcelExporter(findByLista);
			try {
				precioExcelExporter.export(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void nuevoCliente() {
		clienteDialog.limpiarCampos();
		clienteDialog.setVisible(true);
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
		clientePanel.cargarLista(clienteDao.findAll());
	}

	public void importarExcel() {
		JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.setDialogTitle("Importar precios");
		fileChooser.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
		int option = fileChooser.showOpenDialog(clientePanel);
		if (option == JFileChooser.APPROVE_OPTION) {
			List<Precio> conError = new ArrayList<Precio>();
			List<Precio> procesarExcel = ExcelUtils.procesarExcelPrecios(fileChooser.getSelectedFile());
			for (Precio precio : procesarExcel) {
				try {
					precioDao.save(precio);
				} catch (Exception e) {
					conError.add(precio);
				}
			}
			JOptionPane.showMessageDialog(clientePanel, "Se procesaron " + (procesarExcel.size() - conError.size())
					+ " registros correctamente. Con error: " + conError.size());
			cargarListaInicial();
		}
	}

}
