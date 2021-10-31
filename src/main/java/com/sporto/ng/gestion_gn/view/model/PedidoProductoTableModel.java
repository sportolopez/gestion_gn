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

	public PedidoProductoTableModel() {
		List<String> list;

		list = Arrays.asList(new String[] { "CÓDIGO PRODUCTO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "DESCUENTO",
				"SUBTOTAL", "ELIMINAR" });

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
		if (col > (getColumnCount() - 2) || col == 1 || col == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void add(int idProducto, String descripcion, int cantidad, Double precio, String descuento) {
		double productoConDescuento = calcularSubtotal(precio, descuento, cantidad);

		addRow(new Object[] { idProducto, descripcion, cantidad, precio, descuento, productoConDescuento,
				Constants.ICONO_ELIMINAR });
	}
	
	public Collection<PedidoProducto> getPedidoProductos(Pedido pedido){
		int nRow = getRowCount();
		List<PedidoProducto> listPedido = new ArrayList<PedidoProducto>();
		for (int i = 0; i < nRow; i++) {
				
			Producto nroProducto = Producto.builder().id(Integer.parseInt(getValueAt(i, 0).toString())).build();
			int cantidad = Integer.parseInt(getValueAt(i, 2).toString());
			double precio = Double.parseDouble(getValueAt(i, 3).toString());
			String descuento = (getValueAt(i, 4).toString());
			
			PedidoProducto unPedidoProducto = PedidoProducto.builder()
				.cantidad(cantidad)
				.descuento(descuento)
				.precio(precio)
				.producto(nroProducto)
				.pedido(pedido).build();
			
			
			listPedido.add(unPedidoProducto);
			
		}
		return listPedido;
		
	}

	public double calcularSubtotal(double precio, String descuento, int cantidad) {

		int descuentoParsed = Integer.parseInt(descuento.substring(0, 1));

		double d = precio - Math.round(precio * descuentoParsed) / 100;
		return d * cantidad;
	}

}