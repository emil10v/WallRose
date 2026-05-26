package interfaz;

import control.Controladora;
import logica.Cliente;
import logica.Producto;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class VentanaProducto extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel labelCodigo;
	private JLabel labelNombre;
	private JLabel labelExistencias;
	private JLabel labelUnidad;
	private JLabel labelPrecio;
	private Producto producto;
	private JTextField textNombre;
	private JTextField textExistencias;
	private JTextField textUnidad;
	private JTextField textPrecio;

	
	private void guardar() {
		try {
			String nombre = textNombre.getText();
			String unidad = textUnidad.getText();
			double existencias = Double.parseDouble(textExistencias.getText());
			double precio = Double.parseDouble(textPrecio.getText());
			Controladora control = Controladora.getInstance();
			if (producto == null) 
				control.crearProducto(nombre, existencias, unidad, precio);
			else 
				control.actualizarProducto(producto.getCodigo(), nombre,existencias, unidad,precio);
			dispose();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(
					this,
					"Existencias y precio deben ser números reales.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					this,
					e.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * Create the dialog.
	 */
	public VentanaProducto(Producto p) {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 436, 309);
		this.producto = p;
		setTitle("Producto");

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		labelCodigo = new JLabel("C\u00F3digo: ");
		labelCodigo.setBounds(21, 11, 70, 17);
		contentPanel.add(labelCodigo);
		
		JLabel lblCodigo = new JLabel("    (Se asigna autom\u00E1ticamente)");
		lblCodigo.setBounds(114, 11, 245, 17);
		contentPanel.add(lblCodigo);

		labelNombre = new JLabel("Nombre: ");
		labelNombre.setBounds(21, 36, 96, 17);
		contentPanel.add(labelNombre);
		
		textNombre = new JTextField();
		textNombre.setColumns(10);
		textNombre.setBounds(114, 34, 168, 20);
		contentPanel.add(textNombre);

		labelExistencias = new JLabel("Existencias: ");
		labelExistencias.setBounds(20, 73, 111, 17);
		contentPanel.add(labelExistencias);
		
		textExistencias = new JTextField();
		textExistencias.setColumns(10);
		textExistencias.setBounds(114, 71, 167, 20);
		contentPanel.add(textExistencias);

		labelUnidad = new JLabel("Unidad: ");
		labelUnidad.setBounds(21, 113, 96, 17);
		contentPanel.add(labelUnidad);
		
		textUnidad = new JTextField();
		textUnidad.setColumns(10);
		textUnidad.setBounds(114, 110, 168, 20);
		contentPanel.add(textUnidad);

		labelPrecio = new JLabel("Precio: ");
		labelPrecio.setBounds(21, 152, 96, 17);
		contentPanel.add(labelPrecio);
		
		textPrecio = new JTextField();
		textPrecio.setColumns(10);
		textPrecio.setBounds(114, 149, 168, 20);
		contentPanel.add(textPrecio);
		
		if (producto != null) {
			lblCodigo.setText("" + p.getCodigo());
			textNombre.setText(producto.getNombre());
			textExistencias.setText("" + producto.getExistencias());
			textUnidad.setText(producto.getUnidad());
			textPrecio.setText("" + producto.getPrecio());
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				guardar();
			}
		});
		btnGuardar.setActionCommand("OK");
		buttonPane.add(btnGuardar);
		getRootPane().setDefaultButton(btnGuardar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setActionCommand("Cancel");
		buttonPane.add(btnCancelar);
			
		}
	}

