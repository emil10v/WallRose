package interfaz;

import control.Controladora;
import logica.Cliente;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal {

	private JFrame frame;
	private JTable tableClientes;

	/**
	 * Launch the application.
	 */
	
	//funciones auxiliares 
	private void cargarClientes() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tableClientes.getModel();
		model.setRowCount(0);
		List<Cliente> listaClientes = control.getClientes();
		for (Cliente cliente : listaClientes) {
			Object[] fila = new Object[] {cliente.getId(), cliente.getNombre(), cliente.getEmail()};
			model.addRow(fila);
		}
	}
	
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 377);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 509, 340);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panelClientes = new JPanel();
		tabbedPane.addTab("Clientes", null, panelClientes, null);
		panelClientes.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarClientes();
			}
		});
		scrollPane.setBounds(0, 0, 363, 301);
		panelClientes.add(scrollPane);
		
		tableClientes = new JTable();
		tableClientes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NOMBRE", "EMAIL"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableClientes.getColumnModel().getColumn(0).setPreferredWidth(219);
		tableClientes.getColumnModel().getColumn(1).setPreferredWidth(262);
		tableClientes.getColumnModel().getColumn(2).setPreferredWidth(227);
		scrollPane.setViewportView(tableClientes);
		
		JButton btnVerCliente = new JButton("VER");
		btnVerCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVerCliente.setBounds(361, 0, 143, 75);
		panelClientes.add(btnVerCliente);
		
		JButton btnAgregarCliente = new JButton("AGREGAR");
		btnAgregarCliente.setBounds(361, 72, 143, 75);
		panelClientes.add(btnAgregarCliente);
		
		JButton btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditar.setBounds(361, 145, 143, 81);
		panelClientes.add(btnEditar);
		
		JButton btnBorrar = new JButton("BORRAR");
		btnBorrar.setBounds(361, 226, 143, 75);
		panelClientes.add(btnBorrar);
		
		JPanel panelProductos = new JPanel();
		tabbedPane.addTab("Productos", null, panelProductos, null);
		
		JPanel panelOrdenesCompra = new JPanel();
		tabbedPane.addTab("Ordenes de Compra", null, panelOrdenesCompra, null);
	}
}
