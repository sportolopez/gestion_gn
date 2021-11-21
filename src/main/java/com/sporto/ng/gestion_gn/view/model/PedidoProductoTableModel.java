package com.sporto.ng.gestion_gn.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoProducto;
import com.sporto.ng.gestion_gn.model.Producto;

public class PedidoProductoTableModel extends DefaultTableModel {

	public static final int COLUMNA_CODIGO = 0;
	public static final int COLUMNA_DESCRIPCION = 1;
	public static final int COLUMNA_CANTIDAD = 2;
	public static final int COLUMNA_SUBTOTAL = 5;
	public final static int COLUMNA_ELIMINAR = 6;

	public PedidoProductoTableModel() {
		List<String> list;

		list = Arrays.asList(
				new String[] { "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "DESCUENTO", "SUBTOTAL", "ELIMINAR" });

		for (String columnName : list) {
			addColumn(columnName);
		}

	}

	@Override
	public Class getColumnClass(int column) {
		if (column == getColumnCount() - 1)
			return ImageIcon.class;
		return Object.class;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void add(int idProducto, String descripcion, int cantidad, Double precio, String descuento) {
		double productoConDescuento = calcularSubtotal(precio, descuento, cantidad);

		addRow(new Object[] { idProducto, descripcion, cantidad, precio, descuento, productoConDescuento,
				Constants.ICONO_ELIMINAR });
	}

	public Collection<PedidoProducto> getPedidoProductos(Pedido pedido) {
		int nRow = getRowCount();
		List<PedidoProducto> listPedido = new ArrayList<PedidoProducto>();
		for (int i = 0; i < nRow; i++) {

			Producto nroProducto = Producto.builder().id(Integer.parseInt(getValueAt(i, 0).toString()))
					.descripcion(getValueAt(i, 1).toString()).build();
			int cantidad = Integer.parseInt(getValueAt(i, 2).toString());
			double precio = Double.parseDouble(getValueAt(i, 3).toString());
			String descuento = (getValueAt(i, 4).toString());

			PedidoProducto unPedidoProducto = PedidoProducto.builder().cantidad(cantidad).descuento(descuento)
					.precio(precio).producto(nroProducto).pedido(pedido).build();

			listPedido.add(unPedidoProducto);

		}
		return listPedido;

	}

	public double calcularSubtotal(double precio, String descuento, int cantidad) {

		double descuentoParsed = 1 - (double) Integer.parseInt(descuento.substring(0, 1)) / 100;

		double d = precio * descuentoParsed;
		return d * cantidad;
	}

	public void addAll(Collection<PedidoProducto> listaProductos) {
		
		for (PedidoProducto pedidoProducto : listaProductos) {
			addRow(new Object[] { pedidoProducto.getProducto().getId(),pedidoProducto.getProducto().getDescripcion(),pedidoProducto.getCantidad(),pedidoProducto.getPrecio(),pedidoProducto.getDescuento(),pedidoProducto.calcularSubtotal()});
		}
	}

}