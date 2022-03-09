package com.sporto.ng.gestion_gn.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.Cliente.ClienteBuilder;
import com.sporto.ng.gestion_gn.model.Lista;
import com.sporto.ng.gestion_gn.model.TipoCuenta;
import com.sporto.ng.gestion_gn.view.validations.DoubleVerifier;
import com.sporto.ng.gestion_gn.view.validations.TextoVerifier;

public class ClienteDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textRazonSocial;
	private Integer idClienteEditando;
	private JTextField textEmail;
	private JTextField textTelefono;
	private JButton btnGuardar;

	private JTextField textDomicilio;
	private java.util.Set<String> camposInvalidos = new HashSet<String>();
	private JTextField textCUIT;
	private JTextField textLimite;
	private JComboBox<Lista> comboBoxLista;
	private JComboBox<TipoCuenta> comboBoxTipoCuenta;

	/**
	 * Create the frame.
	 * 
	 * @param abstractAction
	 */

	public ClienteDialog(Lista[] listaPrecios) {
		URL resource = getClass().getClassLoader().getResource("icono.ico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(resource));
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		initComponents();
		comboBoxLista.setModel(new DefaultComboBoxModel<Lista>(listaPrecios));

		configValidations();
		setResizable(false);
	}

	private void initComponents() {
		setTitle("Cliente");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 261);
		JPanel panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
		panelProductos.setLayout(null);

		JLabel lblCodigoLabel = new JLabel("RAZÓN SOCIAL");
		lblCodigoLabel.setBounds(47, 25, 96, 14);
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblCodigoLabel);

		textRazonSocial = new JTextField();
		textRazonSocial.setBounds(153, 22, 200, 20);
		textRazonSocial.setHorizontalAlignment(SwingConstants.RIGHT);
		textRazonSocial.setColumns(20);
		panelProductos.add(textRazonSocial);

		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(47, 53, 96, 14);
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setText("");
		textEmail.setBounds(153, 50, 200, 20);
		textEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		textEmail.setToolTipText("");
		panelProductos.add(textEmail);
		textEmail.setColumns(20);

		JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setBounds(381, 53, 70, 14);
		lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblTelefono);

		textTelefono = new JTextField();
		textTelefono.setBounds(461, 50, 109, 20);
		textTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		textTelefono.setColumns(20);
		panelProductos.add(textTelefono);

		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setBounds(353, 160, 127, 41);
		panelProductos.add(btnGuardar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(216, 160, 127, 41);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelProductos.add(btnCancelar);

		JLabel lblDescripcion = new JLabel("DIRECCIÓN");
		lblDescripcion.setBounds(10, 78, 133, 14);
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblDescripcion);

		textDomicilio = new JTextField();
		textDomicilio.setBounds(153, 75, 200, 19);
		textDomicilio.setColumns(20);
		textDomicilio.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(textDomicilio);

		JLabel lblCuit = new JLabel("CUIT");
		lblCuit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuit.setBounds(381, 24, 70, 17);
		panelProductos.add(lblCuit);

		textCUIT = new JTextField();
		textCUIT.setColumns(20);
		textCUIT.setHorizontalAlignment(SwingConstants.RIGHT);
		textCUIT.setBounds(461, 22, 109, 20);
		panelProductos.add(textCUIT);

		textLimite = new JTextField();
		textLimite.setHorizontalAlignment(SwingConstants.RIGHT);
		textLimite.setColumns(20);
		textLimite.setBounds(461, 107, 109, 20);
		textLimite.setText("0");
		panelProductos.add(textLimite);

		JLabel lblCosto = new JLabel("DESCUBIERTO");
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosto.setBounds(363, 110, 88, 14);
		panelProductos.add(lblCosto);

		JLabel lblListaDePrecio = new JLabel("LISTA");
		lblListaDePrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblListaDePrecio.setBounds(391, 81, 56, 14);
		panelProductos.add(lblListaDePrecio);

		comboBoxLista = new JComboBox<Lista>();
		comboBoxLista.setBounds(460, 78, 109, 22);
		panelProductos.add(comboBoxLista);
		
		comboBoxTipoCuenta = new JComboBox<TipoCuenta>();
		comboBoxTipoCuenta.setModel(new DefaultComboBoxModel<TipoCuenta>(TipoCuenta.values()));
		comboBoxTipoCuenta.setBounds(153, 105, 116, 22);
		comboBoxTipoCuenta.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	TipoCuenta tipoCuentaSeleccionado = (TipoCuenta) comboBoxTipoCuenta.getSelectedItem();
		    	if(TipoCuenta.EFECTIVO.equals(tipoCuentaSeleccionado)) {
		    		textLimite.setEditable(false);
		    		textLimite.setText("0");
		    	}else {
		    		textLimite.setEditable(true);
		    	}
		    			
		    }
		});
		panelProductos.add(comboBoxTipoCuenta);
		
		JLabel lblTipoCuenta = new JLabel("TIPO CUENTA");
		lblTipoCuenta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoCuenta.setBounds(47, 108, 96, 14);
		panelProductos.add(lblTipoCuenta);

	}

	private void configValidations() {
		textRazonSocial.setInputVerifier(new TextoVerifier("Razon Social", camposInvalidos));
		textTelefono.setInputVerifier(new TextoVerifier("Teléfono", camposInvalidos));
		textDomicilio.setInputVerifier(new TextoVerifier("Domicilio", camposInvalidos));
		textLimite.setInputVerifier(new DoubleVerifier("Límite Descubierto", camposInvalidos));
	}

	public void cargarCampos(Cliente unCliente) {
		idClienteEditando = unCliente.getId();
		textRazonSocial.setText(unCliente.getRazonSocial());
		textTelefono.setText(unCliente.getTelefono());
		textDomicilio.setText(unCliente.getDomicilio());
		textCUIT.setText(unCliente.getCuit());
		textEmail.setText(unCliente.getEmail());
		comboBoxLista.setSelectedItem(unCliente.getListaPrecio());
		textLimite.setText(Constants.outDouble(unCliente.getLimiteDeuda()));
		comboBoxTipoCuenta.setSelectedItem(unCliente.getTipoCuenta());
	}

	public Cliente getCliente() {
		ClienteBuilder cliente = Cliente.builder().cuit(textCUIT.getText()).domicilio(textDomicilio.getText())
				.razonSocial(textRazonSocial.getText()).email(textEmail.getText()).telefono(textTelefono.getText())
				.listaPrecio(Lista.builder().nombre(comboBoxLista.getSelectedItem().toString()).build())
				.limiteDeuda(Constants.parseDouble(textLimite.getText())).tipoCuenta((TipoCuenta) comboBoxTipoCuenta.getSelectedItem());
		if (idClienteEditando != null)
			cliente.id(idClienteEditando);
		return cliente.build();
	}

	public void limpiarCampos() {
		textEmail.setText("");
		textDomicilio.setText("");
		textRazonSocial.setText("");
		textTelefono.setText("");
		textCUIT.setText("");
		textLimite.setText("0");
		comboBoxTipoCuenta.setSelectedItem(TipoCuenta.EFECTIVO);
		idClienteEditando = null;
	}

	public boolean validar() {
		textRazonSocial.requestFocus();
		textTelefono.requestFocus();
		textDomicilio.requestFocus();
		textLimite.requestFocus();
		btnGuardar.requestFocus();
		
		if (camposInvalidos.size() > 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Campos invalidos:" + camposInvalidos, "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;

	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}
}
