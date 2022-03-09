package gestion_ng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sporto.ng.gestion_gn.GestionNG;
import com.sporto.ng.gestion_gn.dao.ClienteDao;
import com.sporto.ng.gestion_gn.dao.MovimientoCajaDao;
import com.sporto.ng.gestion_gn.dao.PedidoDao;
import com.sporto.ng.gestion_gn.dao.PedidoProductoDao;
import com.sporto.ng.gestion_gn.dao.ProductoDao;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.EstadoPedido;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.MedioPago;
import com.sporto.ng.gestion_gn.model.MovimientoCaja;
import com.sporto.ng.gestion_gn.model.Pedido;
import com.sporto.ng.gestion_gn.model.PedidoProducto;
import com.sporto.ng.gestion_gn.model.Producto;
import com.sporto.ng.gestion_gn.model.TipoMovimiento;
import com.sporto.ng.gestion_gn.pruebas.Impresora;

@SpringBootTest(classes = GestionNG.class)
class PedidoTest {

	@Autowired
	private ProductoDao productoDao;

	@Autowired
	private PedidoDao pedidoDao;
	
	@Autowired
	private PedidoProductoDao pedidoProductoDao;

	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private MovimientoCajaDao movimientoCajaDao;

	@Test
	void test() {

		Cliente build2 = Cliente.builder().cuit("2323232").razonSocial("Seba")
				.listaPrecio(Lista.builder().nombre("VIP").build()).build();
		clienteDao.save(build2);

		Cliente cliente = clienteDao.findAll().get(0);

		Collection<PedidoProducto> productos = new ArrayList<PedidoProducto>();
		Producto producto = productoDao.findAll().get(0);
		Pedido build = Pedido.builder().cliente(cliente).estado(EstadoPedido.EMITIDO).fecha(new Date())
			//.productos(productos)
				.build();
		Pedido save = pedidoDao.save(build);

		PedidoProducto pedidoproducto = PedidoProducto.builder().cantidad(1).pedido(save).descuento("1 %").precio(23).producto(producto).build();
		pedidoProductoDao.save(pedidoproducto);
		
		System.out.println(save.getId());
	}

	@Test
	void testGet() {
		Optional<Pedido> findById = pedidoDao.findById(67);
		System.out.println(findById.get().getMonto());
	}
	
	@Test
	void testRegistrarMovimiento() {
		Cliente cliente = clienteDao.findById(2).get();
		
		System.out.println(cliente);
		MovimientoCaja unMovimiento = MovimientoCaja.builder().cliente(cliente)
					.comentario("Prueba")
					.fecha(new Date())
					.medioPago(MedioPago.DEPOSITO)
					.monto((double) 100)
					.tipoMovimiento(TipoMovimiento.INGRESO).build();
		//movimientoCajaDao.save(unMovimiento);
		
		
		
	}
	

}
