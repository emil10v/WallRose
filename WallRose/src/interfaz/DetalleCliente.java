package interfaz;

import logica.OrdenCompra;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Cliente;
import logica.OrdenCompra;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetalleCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableOrdenes;
	private String idCliente;
	private JLabel labelTotalPendiente;
	private JLabel labelIDCliente;
	private JLabel labelNombreCliente;
	private JLabel labelEmailCliente;

	/**
	 * Launch the application.
	 */
	private void cargarDatosCliente() {
		Controladora control = Controladora.getInstance();
		try {
			Cliente c = control.getCliente(idCliente);
			labelIDCliente.setText(c.getId());
			labelNombreCliente.setText(c.getNombre());
			labelEmailCliente.setText(c.getEmail());
			labelTotalPendiente.setText(c.getMontoPendiente().toString());
			cargarOrdenesCliente();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					this, 
					"Error al cargar datos del cliente: " + e.toString(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	private void cargarOrdenesCliente() {
		Controladora control = Controladora.getInstance();
		try {
			DefaultTableModel model = (DefaultTableModel) tableOrdenes.getModel();
			model.setRowCount(0);
			List<OrdenCompra> listaOrdenes = control.getOrdenesCliente(idCliente);
			for (OrdenCompra orden : listaOrdenes) {
				Object[] fila = new Object[] {orden.getNumero(), orden.getFecha(), orden.getEstado()};
				model.addRow(fila);			
			}			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this,  "Error al cargar las ordenes del cliente: " + e.toString(), "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cargarOrdenesPorEstado(String estado) {
		Controladora control = Controladora.getInstance();
		try {
			DefaultTableModel model = (DefaultTableModel) tableOrdenes.getModel();
			model.setRowCount(0);
			List<OrdenCompra> listaOrdenes = control.getOrdenesPorEstadoCliente(idCliente, estado);
			for (OrdenCompra orden : listaOrdenes) {
				Object[] fila = new Object[] {orden.getNumero(), orden.getFecha(), orden.getEstado()};
				model.addRow(fila);			
			}			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this,  "Error al cargar las ordenes del cliente: " + e.toString(), "Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * Create the dialog.
	 */
	public DetalleCliente(String idCliente) {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		this.idCliente = idCliente;
		setTitle("Cliente");
		setBounds(100, 100, 410, 394);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarDatosCliente();
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPanel.add(lblNewLabel);
		
		labelIDCliente = new JLabel("");
		labelIDCliente.setBounds(67, 11, 301, 14);
		contentPanel.add(labelIDCliente);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(10, 36, 59, 14);
		contentPanel.add(lblNewLabel_1);
		
		labelNombreCliente = new JLabel("");
		labelNombreCliente.setBounds(67, 36, 301, 14);
		contentPanel.add(labelNombreCliente);
		
		JLabel lblNewLabel_2 = new JLabel("Email:");
		lblNewLabel_2.setBounds(10, 61, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		labelEmailCliente = new JLabel("");
		labelEmailCliente.setBounds(67, 61, 301, 14);
		contentPanel.add(labelEmailCliente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 109, 259, 139);
		contentPanel.add(scrollPane);
		
		tableOrdenes = new JTable();
		tableOrdenes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00FAmero", "Fecha", "Estado"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableOrdenes.getColumnModel().getColumn(1).setPreferredWidth(94);
		tableOrdenes.getColumnModel().getColumn(2).setPreferredWidth(91);
		scrollPane.setViewportView(tableOrdenes);
		
		JLabel lblNewLabel_3 = new JLabel("Ordenes del cliente:");
		lblNewLabel_3.setBounds(10, 86, 160, 14);
		contentPanel.add(lblNewLabel_3);
		
		JButton btnVerTodas = new JButton("Todas");
		btnVerTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarOrdenesCliente();
			}
		});
		btnVerTodas.setBounds(264, 109, 122, 35);
		contentPanel.add(btnVerTodas);
		
		JButton btnVerIniciadas = new JButton("Iniciadas");
		btnVerIniciadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarOrdenesPorEstado("Iniciada");
			}
		});
		btnVerIniciadas.setBounds(264, 144, 122, 35);
		contentPanel.add(btnVerIniciadas);
		
		JButton btnVerPendientes = new JButton("Pendientes");
		btnVerPendientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarOrdenesPorEstado("Pendiente");
			}
		});
		btnVerPendientes.setBounds(264, 179, 122, 35);
		contentPanel.add(btnVerPendientes);
		
		JButton btnVerTerminadas = new JButton("Terminadas");
		btnVerTerminadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarOrdenesPorEstado("Terminada");
			}
		});
		btnVerTerminadas.setBounds(264, 214, 122, 35);
		contentPanel.add(btnVerTerminadas);
		
		JLabel lblNewLabel_4 = new JLabel("Total pendiente:");
		lblNewLabel_4.setBounds(10, 256, 117, 14);
		contentPanel.add(lblNewLabel_4);
		
		labelTotalPendiente = new JLabel("");
		labelTotalPendiente.setBounds(113, 256, 133, 14);
		contentPanel.add(labelTotalPendiente);
		cargarDatosCliente();
	}
}
