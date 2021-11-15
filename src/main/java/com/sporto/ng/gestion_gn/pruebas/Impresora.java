package com.sporto.ng.gestion_gn.pruebas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.common.io.Resources;
import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.MedioPago;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoProducto;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;

public class Impresora extends JDialog implements ActionListener, WindowListener {

	private JEditorPane jEditorPane;

	private JDialog _self;
	public Impresora() {
		
		super(new JDialog(),true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		createUI();
		setResizable(false);
		setSize(820, 650);
		setLocationRelativeTo(null);
		_self = this;
	}


	private void createUI() {
		JPanel panel = new JPanel();
		LayoutManager layout = new FlowLayout();
		panel.setLayout(layout);

		jEditorPane = new JEditorPane();

		jEditorPane.setEditable(false);
		jEditorPane.setContentType("text/html");

		JScrollPane jScrollPane = new JScrollPane(jEditorPane);
		jScrollPane.setPreferredSize(new Dimension(800, 600));

		panel.add(jScrollPane);
		getContentPane().add(panel, BorderLayout.CENTER);
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("IMPRIMIR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					PrinterJob job = PrinterJob.getPrinterJob();

					job.setPrintable(jEditorPane.getPrintable(null, null));
					PageFormat pf = job.defaultPage();
					Paper paper = pf.getPaper();
					double margin = 10.;
					paper.setImageableArea(margin, 10, paper.getWidth() - 2 * margin, paper.getImageableHeight());
					pf.setPaper(paper);
					boolean printAccepted = job.printDialog();
					if (printAccepted) {
						job.print();
					}
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnCerrar = new JButton("CERRAR");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_self.dispose();
			}
		});
		panel_1.add(btnCerrar);
		panel_1.add(btnNewButton);
	}

	public void imprimirPedido(Pedido unPedido, Collection<PedidoProducto> listaProductos) {
		//setSize(700, 650);
		URL url = Impresora.class.getResource("/pedido.htm");

		try {
			String text = Resources.toString(url, StandardCharsets.UTF_8);
			text = text.replace("_NROPEDIDO_", String.valueOf(unPedido.getId()));
			text = text.replace("_CLIENTE_NOMBRE_", String.valueOf(unPedido.getCliente().getRazonSocial()));
			String domicilio = unPedido.getCliente().getDomicilio();
			text = text.replace("_CLIENTE_DIRECCION_", Objects.toString(domicilio, ""));
			text = text.replace("_CLIENTE_CUIT_", Objects.toString(unPedido.getCliente().getCuit(), ""));
			text = text.replace("_FECHA_", Constants.FORMATO_FECHA.format(new Date()));

			StringBuilder sb = new StringBuilder();
			int index = 0;
			double total = 0;
			for (PedidoProducto pedidoProducto : listaProductos) {

				if((index++ % 2) == 0)
					sb.append("<tr class='impar'>");
				else
					sb.append("<tr >");
				sb.append("    <td class='desc'>" + pedidoProducto.getProducto().getId() + " "
						+ pedidoProducto.getProducto().getDescripcion() + "</td>");
				sb.append("    <td class='unit'>$ " + pedidoProducto.getPrecio() + "</td>");
				sb.append("	   <td class='qty'>" + pedidoProducto.getCantidad() + "</td>");
				sb.append("    <td class='qty'>" + pedidoProducto.getDescuento() + "</td>");
				double calcularSubtotal = pedidoProducto.calcularSubtotal();
				total +=calcularSubtotal;
				sb.append("    <td class='total'>$ " + calcularSubtotal + "</td>");
				sb.append("</tr>");

			}
			text = text.replace("_LISTA_PRODUCTOS_", sb.toString());
			text = text.replace("_TOTAL_", String.valueOf(total));

			jEditorPane.setText(text);
		} catch (IOException e) {
			e.printStackTrace();
			jEditorPane.setContentType("text/html");
			jEditorPane.setText("<html>Page not found.</html>");
		}
		
	}
	public void imprimirPagos(Cliente unCliente, Collection<MovimientoCaja> listaPagos) {
		URL url = Impresora.class.getResource("/pagos.htm");
		
		try {
			String text = Resources.toString(url, StandardCharsets.UTF_8);

			text = text.replace("_CLIENTE_NOMBRE_", String.valueOf(unCliente.getRazonSocial()));
			String domicilio = unCliente.getDomicilio();
			text = text.replace("_CLIENTE_DIRECCION_", Objects.toString(domicilio, ""));
			text = text.replace("_CLIENTE_CUIT_", Objects.toString(unCliente.getCuit(), ""));
			text = text.replace("_FECHA_", Constants.FORMATO_FECHA.format(new Date()));
			
			StringBuilder sb = new StringBuilder();
			int index = 0;
			double total = 0;
			for (MovimientoCaja unMovimiento : listaPagos) {
				
				if((index++ % 2) == 0)
					sb.append("<tr class='impar'>");
				else
					sb.append("<tr >");
				sb.append("    <td class='desc'>" + unMovimiento.getMedioPago() +  "</td>");
				sb.append("	   <td class='total'>" + unMovimiento.getComentario()+ "</td>");
				sb.append("    <td class='unit'>$ " + unMovimiento.getMonto() + "</td>");
				
				double calcularSubtotal = unMovimiento.getMonto();
				total +=calcularSubtotal;
				sb.append("</tr>");
				
			}
			text = text.replace("_LISTA_PAGOS_", sb.toString());
			text = text.replace("_TOTAL_", String.valueOf(total));
			text = text.replace("_DEUDA_", String.valueOf(unCliente.getSaldo()));
			
			jEditorPane.setText(text);
		} catch (IOException e) {
			e.printStackTrace();
			jEditorPane.setContentType("text/html");
			jEditorPane.setText("<html>Page not found.</html>");
		}
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {

		Cliente cliente = Cliente.builder().id(334).cuit("2323232").razonSocial("Seba").domicilio("Un domiciio")
				.listaPrecio(Lista.builder().nombre("VIP").build()).build();

		Pedido unpedido = Pedido.builder().id(334).cliente(cliente).estado(EstadoPedido.EMITIDO).fecha(new Date())
				.build();

		Producto nroProducto = Producto.builder().id(111).descripcion("Mayonesa").build();

		Impresora impresora = new Impresora();
//		List<PedidoProducto> productos = new ArrayList<PedidoProducto>();
//		productos.add(PedidoProducto.builder().cantidad(1).pedido(unpedido).descuento("1 %").precio(23)
//				.producto(nroProducto).build());
//		productos.add(PedidoProducto.builder().cantidad(1).pedido(unpedido).descuento("1 %").precio(23)
//				.producto(nroProducto).build());
//		productos.add(PedidoProducto.builder().cantidad(1).pedido(unpedido).descuento("1 %").precio(23)
//				.producto(nroProducto).build());
		System.out.println(cliente);
		MovimientoCaja unMovimiento = MovimientoCaja.builder().cliente(cliente)
					.comentario("Prueba")
					.fecha(new Date())
					.medioPago(MedioPago.DEPOSITO)
					.monto((double) 100)
					.tipoMovimiento(TipoMovimiento.INGRESO).build();
		
		List<MovimientoCaja> pagos = new ArrayList<MovimientoCaja>();
		
		pagos.add(unMovimiento);
		pagos.add(unMovimiento);
		pagos.add(unMovimiento);
		impresora.imprimirPagos(cliente, pagos);
		impresora.setVisible(true);

	}
}