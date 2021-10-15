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

import com.sporto.ng.gestion_gn.model.Cliente;
import com.sporto.ng.gestion_gn.model.Cliente.ClienteBuilder;
import com.sporto.ng.gestion_gn.model.Lista;
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
	}

	private void initComponents() {
		setTitle("Cliente");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 502, 261);
		JPanel panelProductos = new JPanel();
		panelProductos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelProductos);
		panelProductos.setLayout(null);

		JLabel lblCodigoLabel = new JLabel("RAZÓN SOCIAL");
		lblCodigoLabel.setBounds(69, 23, 96, 14);
		lblCodigoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblCodigoLabel);

		textRazonSocial = new JTextField();
		textRazonSocial.setBounds(175, 20, 116, 20);
		textRazonSocial.setHorizontalAlignment(SwingConstants.RIGHT);
		textRazonSocial.setColumns(10);
		panelProductos.add(textRazonSocial);

		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(69, 51, 96, 14);
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblEmail);

		textEmail = new JTextField();
		textEmail.setText("");
		textEmail.setBounds(175, 48, 116, 20);
		textEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		textEmail.setToolTipText("");
		panelProductos.add(textEmail);
		textEmail.setColumns(10);

		JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setBounds(288, 51, 70, 14);
		lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblTelefono);

		textTelefono = new JTextField();
		textTelefono.setBounds(368, 48, 109, 20);
		textTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		textTelefono.setColumns(10);
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
		lblDescripcion.setBounds(69, 76, 96, 14);
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		panelProductos.add(lblDescripcion);

		textDomicilio = new JTextField();
		textDomicilio.setBounds(175, 73, 302, 19);
		textDomicilio.setColumns(10);
		panelProductos.add(textDomicilio);

		JLabel lblCuit = new JLabel("CUIT");
		lblCuit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuit.setBounds(288, 22, 70, 17);
		panelProductos.add(lblCuit);

		textCUIT = new JTextField();
		textCUIT.setColumns(10);
		textCUIT.setBounds(368, 20, 109, 20);
		panelProductos.add(textCUIT);

		textLimite = new JTextField();
		textLimite.setHorizontalAlignment(SwingConstants.RIGHT);
		textLimite.setColumns(10);
		textLimite.setBounds(175, 101, 116, 20);
		textLimite.setText("0");
		panelProductos.add(textLimite);

		JLabel lblCosto = new JLabel("LÍMITE DESCUBIERTO");
		lblCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCosto.setBounds(23, 104, 142, 14);
		panelProductos.add(lblCosto);

		JLabel lblListaDePrecio = new JLabel("LISTA");
		lblListaDePrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblListaDePrecio.setBounds(299, 103, 56, 14);
		panelProductos.add(lblListaDePrecio);

		comboBoxLista = new JComboBox<Lista>();
		comboBoxLista.setBounds(368, 100, 109, 22);
		panelProductos.add(comboBoxLista);

	}

	private void configValidations() {
		textRazonSocial.setInputVerifier(new TextoVerifier("Razon Social", camposInvalidos));
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
		textLimite.setText(String.valueOf(unCliente.getLimiteDeuda()));
	}

	public Cliente getCliente() {
		ClienteBuilder cliente = Cliente.builder().cuit(textCUIT.getText()).domicilio(textDomicilio.getText())
				.razonSocial(textRazonSocial.getText()).email(textEmail.getText()).telefono(textTelefono.getText())
				.listaPrecio(Lista.builder().nombre(comboBoxLista.getSelectedItem().toString()).build())
				.limiteDeuda(Double.valueOf(textLimite.getText()));
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
		textLimite.setText("");
		idClienteEditando = null;
	}

	public boolean validar() {

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
