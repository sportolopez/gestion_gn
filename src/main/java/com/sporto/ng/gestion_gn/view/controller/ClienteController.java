package com.sporto.ng.gestion_gn.view.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.dao.CierreCajaDao;
import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.GastoCajaDao;
import com.sporto.ng.gestion_gn.dao.ListaDao;
import com.sporto.ng.gestion_gn.dao.MovimientoCajaDao;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PedidoProductoDao;
import com.sporto.ng.gestion_gn.dao.PedidoServicioDao;
import com.sporto.ng.gestion_gn.dao.PrecioDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.CierreCaja;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.GastoCaja;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.MedioPago;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.Precio;
import com.sporto.ng.gestion_gn.utils.ArqueoCajaExporter;
import com.sporto.ng.gestion_gn.utils.ExcelUtils;
import com.sporto.ng.gestion_gn.utils.PrecioExcelExporter;
import com.sporto.ng.gestion_gn.view.ArqueoCajaDialog;
import com.sporto.ng.gestion_gn.view.ClienteDetalle;
import com.sporto.ng.gestion_gn.view.ClienteDialog;
import com.sporto.ng.gestion_gn.view.ClientePanel;
import com.sporto.ng.gestion_gn.view.EgresoDineroDialog;
import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.LiberarPedidoDialog;
import com.sporto.ng.gestion_gn.view.PedidoDialog;
import com.sporto.ng.gestion_gn.view.model.ClienteTableModel;

@Component
public class ClienteController {

	ClienteDao clienteDao;
	ClientePanel clientePanel;
	ClienteDialog clienteDialog;
	ClienteDetalle clienteDetalle;
	PrecioDao precioDao;
	PedidoDao pedidoDao;
	private PedidoDialog pedidoDialog;
	private LiberarPedidoDialog liberarPedidoDialog;
	private HomeForm homeForm;
	private GastoCajaDao gastoCajaDao;
	private MovimientoCajaDao movimientoCajaDao;
	private CierreCajaDao cierreCajaDao;

	@Autowired
	public ClienteController(ListaDao listaDao, ClienteDao clienteDao, HomeForm homeForm, PrecioDao precioDao,
			PedidoDao pedidoDao, ProductoDao productoDao, PedidoProductoDao pedidoProductoDao,PedidoServicioDao pedidoServicioDao,
			GastoCajaDao gastoCajaDao, MovimientoCajaDao movimientoCajaDao, CierreCajaDao cierreCajaDao) {
		this.clienteDao = clienteDao;
		this.homeForm = homeForm;
		this.precioDao = precioDao;
		this.pedidoDao = pedidoDao;
		this.gastoCajaDao = gastoCajaDao;
		this.movimientoCajaDao = movimientoCajaDao;
		this.cierreCajaDao = cierreCajaDao;
		this.clientePanel = homeForm.getPanelClientes();
		List<Lista> listaPrecios = listaDao.findAll();
		pedidoDialog = new PedidoDialog(productoDao, pedidoDao, homeForm, precioDao, pedidoProductoDao, pedidoServicioDao);
		liberarPedidoDialog = new LiberarPedidoDialog(pedidoDao,movimientoCajaDao, homeForm);
		clienteDialog = new ClienteDialog(listaPrecios.toArray(new Lista[listaPrecios.size()]));
		clienteDialog.getBtnGuardar().addActionListener(l -> guardarCliente(clienteDialog));
		clientePanel.getBtnExportar().addActionListener(i -> exportarClientes());
		clientePanel.getBtnImportarPrecios().addActionListener(i -> importarExcel());
		clientePanel.getBtnEgreso().addActionListener(i -> egresoDinero());
		clientePanel.getBtnArqueoDelDia().addActionListener(i -> abrirArqueoCaja());
		
		//clientePanel.getBtnArqueoDelDia().addActionListener(i -> exportarArqueo());
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
				if (col == ClienteTableModel.COLUMN_LIBERAR) {
					liberarPedidos(idCliente);
					cargarListaInicial();
				}
				if (col == ClienteTableModel.COLUMN_EDITAR)
					editarCliente(listaPrecios, idCliente);
				if (col == ClienteTableModel.COLUMN_EXPORTAR)
					exportarPrecios(
							clientePanel.getTableClientes().getValueAt(row, ClienteTableModel.COLUMN_LISTA).toString());
				if (col == ClienteTableModel.COLUMN_DETALLE)
					detalleCliente(listaPrecios, idCliente);

			}

		});


		
		Constants.setListas(listaDao.findAll());
	}
	
	private Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	}
	
	public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}

	private void abrirArqueoCaja() {
		ArqueoCajaExporter arqueoHoy = completarArqueo();
		
		
		Date yesterday = movimientoCajaDao.selectFechaUltimoCierre();
		if(yesterday == null)
			yesterday = yesterday();
		
		double monto = arqueoHoy.getMontoUltimoCierre()+arqueoHoy.getSaldoEfectivo();
		CierreCaja build = CierreCaja.builder()
				.fecha(convertToLocalDateViaSqlDate(new Date()))
				.monto(monto).build();
		cierreCajaDao.save(build);
		
		
		ArqueoCajaDialog arqueoCajaDialog = new ArqueoCajaDialog(arqueoHoy,homeForm,movimientoCajaDao);
		arqueoCajaDialog.getBtnArqueoDelDia().addActionListener(i -> exportarArqueo());
		arqueoCajaDialog.setVisible(true);
		
		

		
	}

	private void egresoDinero() {
		EgresoDineroDialog egresoDineroDialog = new EgresoDineroDialog(gastoCajaDao, homeForm);
		egresoDineroDialog.setVisible(true);
	}

	protected void liberarPedidos(int idCliente) {
		Cliente cliente = clienteDao.findById(idCliente).get();
		liberarPedidoDialog.liberarPedidos(cliente);

	}

	private void nuevoPedido(int idCliente) {
		Cliente cliente = clienteDao.findById(idCliente).get();
		pedidoDialog.nuevoPedido(cliente);
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
			System.out.println("Lista precios" + findByLista);
			PrecioExcelExporter precioExcelExporter = new PrecioExcelExporter(findByLista);
			try {
				precioExcelExporter.export(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	protected void exportarArqueo() {
		
		ArqueoCajaExporter completarArqueo = completarArqueo();
		
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Guardar como..");
		jfc.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xlsx")) {
				file = new File(file.toString() + ".xlsx");
			}
			
			
			try {
				completarArqueo.export(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private ArqueoCajaExporter completarArqueo(Date paraFecha) {
		ArqueoCajaExporter arqueoCajaExporter = new ArqueoCajaExporter();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(paraFecha);
		List<MovimientoCaja> movimientos = movimientoCajaDao.findByFecha(
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.MONTH) + 1, 
				calendar.get(Calendar.YEAR));

		for (MovimientoCaja movimientoCaja : movimientos) {
			if (movimientoCaja.getMedioPago().equals(MedioPago.EFECTIVO))
				arqueoCajaExporter.addEfectivo(movimientoCaja.getMonto(), movimientoCaja.getDenominacion());
			if (movimientoCaja.getMedioPago().equals(MedioPago.TRANSFERENCIA))
				arqueoCajaExporter.addTransferencia(movimientoCaja.getMonto(), movimientoCaja.getComentario());
			if (movimientoCaja.getMedioPago().equals(MedioPago.DEPOSITO))
				arqueoCajaExporter.addTransferencia(movimientoCaja.getMonto(), movimientoCaja.getComentario());
		}

		List<GastoCaja> gastos = gastoCajaDao.findByFecha(
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.MONTH) + 1, 
				calendar.get(Calendar.YEAR));
		
		for (GastoCaja gastosCaja : gastos) {
			arqueoCajaExporter.addGasto(gastosCaja.getMonto(), gastosCaja.getComentario() + " - " + gastosCaja.getDetalle());
		}
		
		
		
		List<Cliente> clientes = clienteDao.findAll();
		for (Cliente cliente : clientes) {
			if(cliente.getSaldo()<0)
				arqueoCajaExporter.addClienteConDeuda(cliente.getSaldo(),cliente.getRazonSocial());
		}
		
		Double selectMontoUltimoCierre = movimientoCajaDao.selectMontoUltimoCierre();
		if(selectMontoUltimoCierre == null)
			selectMontoUltimoCierre =Double.parseDouble("0");
		arqueoCajaExporter.setMontoUltimoCierre(selectMontoUltimoCierre);
		//
		
		arqueoCajaExporter.setCierreCajaHoy(movimientoCajaDao.selectCierreCaja(new Date()));
		return arqueoCajaExporter;
	}
	
	private ArqueoCajaExporter completarArqueo() {
		return this.completarArqueo(new Date());
	}

	private void nuevoCliente() {
		clienteDialog.limpiarCampos();
		clienteDialog.setVisible(true);
	}

	private void editarCliente(List<Lista> listas, int idCliente) {
		clienteDialog.cargarCampos(clienteDao.findById(idCliente).get());
		clienteDialog.setVisible(true);
	}

	private void detalleCliente(List<Lista> listas, int idCliente) {
		Cliente cliente = clienteDao.findById(idCliente).get();
		List<MovimientoCaja> findGroupByLiberado = movimientoCajaDao.findPagosCliente(cliente.getId());
		List<Pedido> pedidosDelCliente = pedidoDao.findByClienteAndEstadoIn(cliente, new EstadoPedido[]{EstadoPedido.LIBERADO,EstadoPedido.RETIRADO});
		clienteDetalle = new ClienteDetalle(homeForm,cliente,findGroupByLiberado, pedidosDelCliente, movimientoCajaDao);
		clienteDetalle.setVisible(true);
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
