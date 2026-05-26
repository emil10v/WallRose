package interfaz;

import control.Controladora;
import logica.Cliente;
import logica.OrdenCompra;
import logica.Producto;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import javax.swing.JLabel;

public class VentanaPrincipal {

	private JFrame frame;
	private JTable tableClientes;
	private JTable tableProductos;
	private JButton btnAgregarProducto;
	private JScrollPane scrollPane_1;
	private JTable tableOrdenesCompra;
	private JPanel panelOrdenesCompra;
	private JLabel labelTotalPendiente;

	/**
	 * Launch the application.
	 */

	// funciones auxiliares
	private void cargarClientes() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tableClientes.getModel();
		model.setRowCount(0);
		List<Cliente> listaClientes = control.getClientes();
		for (Cliente cliente : listaClientes) {
			Object[] fila = new Object[] { cliente.getId(), cliente.getNombre(), cliente.getEmail() };
			model.addRow(fila);
		}
	}
	private void verCliente() {
		int numFila = tableClientes.getSelectedRow();
		if (numFila == -1) {
			JOptionPane.showMessageDialog(
					frame, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel)tableClientes.getModel();
			String idCliente = (String)model.getValueAt(numFila, 0);
			DetalleCliente ventanaDetalleCliente = new DetalleCliente(idCliente);
			ventanaDetalleCliente.setVisible(true);
		}
	}
	
	private void agregarCliente() {
		    try {
		        String idCliente = JOptionPane.showInputDialog(frame,"ID del cliente:");
		        if (idCliente == null) return;
		        String nombre = JOptionPane.showInputDialog(frame,"Nombre:");
		        if (nombre == null) return;
		        String email = JOptionPane.showInputDialog(frame,"Email:");
		        if (email == null) return;
		        Controladora control = Controladora.getInstance();
		        control.crearCliente(idCliente, nombre, email);
		        cargarClientes();
		    } catch (Exception e) {
		        JOptionPane.showMessageDialog(
		        		frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
	}
	private void editarCliente() {
	    int numeroFila = tableClientes.getSelectedRow();
	    if (numeroFila == -1) {
	        JOptionPane.showMessageDialog(
	        		frame,"Debe seleccionar un cliente.","Error", JOptionPane.ERROR_MESSAGE);
	    } else {
	        try {
	            DefaultTableModel model = (DefaultTableModel) tableClientes.getModel();
	            Controladora control = Controladora.getInstance();
	            String idCliente = (String) model.getValueAt(numeroFila, 0);
	            Cliente c = control.getCliente(idCliente);
	            String nombre =JOptionPane.showInputDialog(frame, "Nombre:", c.getNombre());
	            String email = JOptionPane.showInputDialog(frame, "Email:", c.getEmail());
	            control.actualizarCliente(idCliente, nombre, email);
	            cargarClientes();
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog( frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	private void borrarCliente() {
		int numeroFila = tableClientes.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel) tableClientes.getModel();
			String idCliente = (String) model.getValueAt(numeroFila, 0);
			String nombreCliente = (String) model.getValueAt(numeroFila, 1);
			int res = JOptionPane.showConfirmDialog(frame,
					"Se eliminará la información del cliente " + nombreCliente + " y todas sus ordenes asociadas.",
					"Confirmar", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				Controladora control = Controladora.getInstance();
				try {
					control.borrarCliente(idCliente);
					cargarClientes();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Error al borrar cliente: " + e.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	// Productos ------------
	
	private void cargarProductos() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
		model.setRowCount(0);
		List<Producto> listaProductos = control.getProductos();
		for (Producto prod : listaProductos) {
			Object[] fila = new Object[] { prod.getCodigo(), prod.getNombre(), prod.getExistencias(), prod.getUnidad(), prod.getPrecio() };
			model.addRow(fila);
		}
	}
	
	private void agregarProducto() {
		VentanaProducto v = new VentanaProducto(null);
		v.setModal(true);
		v.setVisible(true);
		cargarProductos();
	}
	
	private void editarProducto() {
		int fila = tableProductos.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(
					frame,
					"Debe seleccionar un producto.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				DefaultTableModel model = (DefaultTableModel)tableProductos.getModel();
				int codigo = (int) model.getValueAt(fila, 0);
				Controladora control = Controladora.getInstance();
				Producto p = control.getProducto(codigo);
				VentanaProducto v = new VentanaProducto(p);
				v.setModal(true);
				v.setVisible(true);
				cargarProductos();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(
						frame,
						e.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void borrarProducto() {
		int numeroFila = tableProductos.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar un producto.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
			int codProducto = (int)model.getValueAt(numeroFila, 0);
			String nombreProducto = (String) model.getValueAt(numeroFila, 1);
			int res = JOptionPane.showConfirmDialog(frame,
					"Se eliminará el producto: " + nombreProducto,
					"Confirmar", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				Controladora control = Controladora.getInstance();
				try {
					control.borrarProducto(codProducto);
					cargarProductos();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "Error al borrar producto: " + e.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	// OrdenesCompra
	
	private void cargarOrdenesCompra() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tableOrdenesCompra.getModel();
		model.setRowCount(0);
		List<OrdenCompra> listaOrdenes = control.getOrdenesCompra();
		for (OrdenCompra orden : listaOrdenes) {
			Object[] fila = new Object[] {orden.getNumero(), orden.getFecha(), orden.getEstado()
			};
			model.addRow(fila);
		}
		labelTotalPendiente.setText("" + control.getMontoTotalPendiente());
	}
	
	private void agregarOrdenCompra() {
		VentanaNuevaOrden v = new VentanaNuevaOrden();
		v.setModal(true);
		v.setVisible(true);
		cargarOrdenesCompra();
	}
	
	private void detalleOrden() {
		int fila = tableOrdenesCompra.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(
					frame,
					"Debe seleccionar un producto.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				DefaultTableModel model = (DefaultTableModel)tableOrdenesCompra.getModel();
				int numOrden = (int) model.getValueAt(fila, 0);
				Controladora control = Controladora.getInstance();
				OrdenCompra orden = control.getOrdenCompra(numOrden);
				DetalleOrdenCompra d = new DetalleOrdenCompra(orden);
				d.setVisible(true);
				cargarProductos();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(
						frame,
						e.getMessage(),
						"Error",
						JOptionPane.ERROR_MESSAGE);
			}
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
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableClientes.getColumnModel().getColumn(0).setResizable(false);
		tableClientes.getColumnModel().getColumn(0).setPreferredWidth(219);
		tableClientes.getColumnModel().getColumn(1).setResizable(false);
		tableClientes.getColumnModel().getColumn(1).setPreferredWidth(262);
		tableClientes.getColumnModel().getColumn(2).setResizable(false);
		tableClientes.getColumnModel().getColumn(2).setPreferredWidth(227);
		tableClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableClientes);

		JButton btnVerCliente = new JButton("VER");
		btnVerCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verCliente();
			}
		});
		btnVerCliente.setBounds(361, 0, 143, 75);
		panelClientes.add(btnVerCliente);

		JButton btnAgregarCliente = new JButton("AGREGAR");
		btnAgregarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarCliente();
			}
		});
		btnAgregarCliente.setBounds(361, 72, 143, 75);
		panelClientes.add(btnAgregarCliente);

		JButton btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCliente();
			}
		});
		btnEditar.setBounds(361, 145, 143, 81);
		panelClientes.add(btnEditar);

		JButton btnBorrar = new JButton("BORRAR");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarCliente();
			}
		});
		btnBorrar.setBounds(361, 226, 143, 75);
		panelClientes.add(btnBorrar);
		
		JPanel panelProductos = new JPanel();
		panelProductos.setToolTipText("");
		panelProductos.setLayout(null);
		tabbedPane.addTab("Productos", null, panelProductos, null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarProductos();
			}
		});
		scrollPane_1.setBounds(0, 0, 385, 301);
		panelProductos.add(scrollPane_1);
		
		tableProductos = new JTable();
		tableProductos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo", "Nombre", "Existencias", "Unidad", "Precio"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Double.class, String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableProductos.getColumnModel().getColumn(0).setResizable(false);
		tableProductos.getColumnModel().getColumn(0).setPreferredWidth(116);
		tableProductos.getColumnModel().getColumn(1).setResizable(false);
		tableProductos.getColumnModel().getColumn(1).setPreferredWidth(170);
		tableProductos.getColumnModel().getColumn(2).setResizable(false);
		tableProductos.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableProductos.getColumnModel().getColumn(3).setResizable(false);
		tableProductos.getColumnModel().getColumn(3).setPreferredWidth(130);
		tableProductos.getColumnModel().getColumn(4).setResizable(false);
		tableProductos.getColumnModel().getColumn(4).setPreferredWidth(130);
		tableProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(tableProductos);
		
		btnAgregarProducto = new JButton("AGREGAR");
		btnAgregarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarProducto();
			}
		});
		btnAgregarProducto.setBounds(383, 0, 121, 105);
		panelProductos.add(btnAgregarProducto);
		
		JButton btnEditar_1 = new JButton("EDITAR");
		btnEditar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProducto();
			}
		});
		btnEditar_1.setBounds(383, 99, 121, 105);
		panelProductos.add(btnEditar_1);
		
		JButton btnBorrar_1 = new JButton("BORRAR");
		btnBorrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarProducto();
			}
		});
		btnBorrar_1.setBounds(383, 204, 121, 97);
		panelProductos.add(btnBorrar_1);
		
		panelOrdenesCompra = new JPanel();
		panelOrdenesCompra.setLayout(null);
		tabbedPane.addTab("Órdenes", null, panelOrdenesCompra, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarOrdenesCompra();
			}
		});
		scrollPane_2.setBounds(0, 0, 363, 235);
		panelOrdenesCompra.add(scrollPane_2);
		
		tableOrdenesCompra = new JTable();
		tableOrdenesCompra.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"N\u00DAMERO", "FECHA", "ESTADO"
			}
		) {
			Class[] columnTypes = new Class[] {
					Integer.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
		});
		tableOrdenesCompra.getColumnModel().getColumn(0).setResizable(false);
		tableOrdenesCompra.getColumnModel().getColumn(0).setPreferredWidth(235);
		tableOrdenesCompra.getColumnModel().getColumn(1).setResizable(false);
		tableOrdenesCompra.getColumnModel().getColumn(1).setPreferredWidth(235);
		tableOrdenesCompra.getColumnModel().getColumn(2).setPreferredWidth(235);
		scrollPane_2.setViewportView(tableOrdenesCompra);
		
		JButton btnNueva = new JButton("NUEVA");
		btnNueva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarOrdenCompra();
			}
		});
		btnNueva.setBounds(361, 0, 143, 80);
		panelOrdenesCompra.add(btnNueva);
		
		JButton btnDetalle = new JButton("DETALLE");
		btnDetalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detalleOrden();
			}
		});
		btnDetalle.setBounds(361, 78, 143, 80);
		panelOrdenesCompra.add(btnDetalle);
		
		JButton btnBorrar_2 = new JButton("BORRAR");
		btnBorrar_2.setBounds(361, 156, 143, 80);
		panelOrdenesCompra.add(btnBorrar_2);
		
		JLabel lblTotalPendiente = new JLabel("Total pendiente: ");
		lblTotalPendiente.setBounds(10, 246, 101, 27);
		panelOrdenesCompra.add(lblTotalPendiente);
		
		labelTotalPendiente = new JLabel("0.0");
		labelTotalPendiente.setBounds(108, 247, 128, 24);
		panelOrdenesCompra.add(labelTotalPendiente);
	}
}
