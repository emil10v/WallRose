package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import logica.Cliente;
import logica.Linea;
import logica.OrdenCompra;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetalleOrdenCompra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel labelidCliente;
	private JLabel labelNombreCliente;
	private JLabel labelCosto;
	private JLabel labelImpuesto;
	private JLabel labelTotal;
	private JLabel labelFecha;
	private JLabel labelidEstado;
	private JLabel labelNumeroOrden;
	private JButton btnTerminar;
	private JButton btnPendiente;
	private JTable tableLineas;
	private JButton btnEditar;
	private JButton btnAgregar_1;
	private OrdenCompra orden;
	private Cliente cliente;
	
	
	private void cargarLineas() {
		DefaultTableModel model = (DefaultTableModel) tableLineas.getModel();
		model.setRowCount(0);
		for (Linea l : orden.getLineas()) {
			Object[] fila = new Object[] {l.getProducto().getCodigo(), l.getProducto().getNombre(), l.getCantidad(), l.getCosto()
			};
			model.addRow(fila);
		};
		
	}
	
	private void agregarLinea(int numOrden) {
		try {
			VentanaLineaOrden dialog = new VentanaLineaOrden(numOrden);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			labelCosto.setText("" + orden.getMontoTotal());
			labelImpuesto.setText("" + orden.getImpuesto());
			labelTotal.setText("" + orden.getMontoTotalIVAI());
			cargarLineas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public DetalleOrdenCompra(OrdenCompra ordenCompra) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.orden = ordenCompra;
		this.cliente = orden.getCliente();
		setBounds(100, 100, 561, 318);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 65, 392, 108);
		getContentPane().add(scrollPane);
		
		tableLineas = new JTable();
		tableLineas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"C\u00F3digo producto", "Nombre producto", "Cantidad", "Costo"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableLineas.getColumnModel().getColumn(0).setResizable(false);
		tableLineas.getColumnModel().getColumn(0).setPreferredWidth(188);
		tableLineas.getColumnModel().getColumn(1).setResizable(false);
		tableLineas.getColumnModel().getColumn(1).setPreferredWidth(223);
		tableLineas.getColumnModel().getColumn(2).setResizable(false);
		tableLineas.getColumnModel().getColumn(2).setPreferredWidth(141);
		tableLineas.getColumnModel().getColumn(3).setResizable(false);
		tableLineas.getColumnModel().getColumn(3).setPreferredWidth(155);
			tableLineas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(tableLineas);
			
			JLabel lblCliente = new JLabel("Cliente: ");
			lblCliente.setBounds(10, 11, 57, 19);
			getContentPane().add(lblCliente);
			
			JLabel lblidCliente = new JLabel("ID: ");
			lblidCliente.setBounds(191, 13, 25, 14);
			getContentPane().add(lblidCliente);
			
			labelNombreCliente = new JLabel(cliente.getNombre());
			labelNombreCliente.setBounds(61, 11, 128, 19);
			getContentPane().add(labelNombreCliente);
			
			labelidCliente = new JLabel(cliente.getId());
			labelidCliente.setBounds(213, 13, 115, 14);
			getContentPane().add(labelidCliente);
			
			JLabel lblFecha = new JLabel("Fecha: ");
			lblFecha.setBounds(342, 13, 48, 14);
			getContentPane().add(lblFecha);
			
			JLabel lblEstado = new JLabel("Estado: ");
			lblEstado.setBounds(191, 40, 48, 14);
			getContentPane().add(lblEstado);
			
			labelFecha = new JLabel(orden.getFecha());
			labelFecha.setBounds(394, 13, 105, 14);
			getContentPane().add(labelFecha);
			
			lblEstado = new JLabel(orden.getEstado());
			labelidEstado.setBounds(245, 40, 115, 14);
			getContentPane().add(lblEstado);
			
			JLabel lblNumeroOrden = new JLabel("N\u00FAmero orden:");
			lblNumeroOrden.setBounds(10, 40, 98, 19);
			getContentPane().add(lblNumeroOrden);
			
			labelNumeroOrden = new JLabel("" + orden.getNumero());
			labelNumeroOrden.setBounds(110, 41, 57, 14);
			getContentPane().add(labelNumeroOrden);
			
			JLabel lblCosto = new JLabel("Costo:");
			lblCosto.setBounds(280, 176, 48, 14);
			getContentPane().add(lblCosto);
			
			JLabel lblImpuesto = new JLabel("Impuesto:");
			lblImpuesto.setBounds(277, 192, 72, 14);
			getContentPane().add(lblImpuesto);
			
			JLabel lblTotal = new JLabel("Total:");
			lblTotal.setBounds(280, 208, 39, 14);
			getContentPane().add(lblTotal);
			
			labelCosto = new JLabel("" + orden.getMontoTotal());
			labelCosto.setBounds(354, 176, 72, 14);
			getContentPane().add(labelCosto);
			
			labelImpuesto = new JLabel("" + orden.getImpuesto());
			labelImpuesto.setBounds(354, 192, 48, 14);
			getContentPane().add(labelImpuesto);
			
			labelTotal = new JLabel("" + orden.getMontoTotal());
			labelTotal.setBounds(354, 208, 50, 14);
			getContentPane().add(labelTotal);
			
			JButton btnAgregar = new JButton("AGREGAR");
			btnAgregar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					agregarLinea(orden.getNumero());
					cargarLineas();
				}
			});
			btnAgregar.setBounds(412, 68, 115, 22);
			getContentPane().add(btnAgregar);
			
			btnEditar = new JButton("EDITAR");
			btnEditar.setBounds(412, 101, 115, 22);
			getContentPane().add(btnEditar);
			
			btnAgregar_1 = new JButton("AGREGAR");
			btnAgregar_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnAgregar_1.setBounds(412, 134, 115, 22);
			getContentPane().add(btnAgregar_1);
			
			btnPendiente = new JButton("PENDIENTE");
			btnPendiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnPendiente.setBounds(10, 184, 128, 34);
			getContentPane().add(btnPendiente);
			
			btnTerminar = new JButton("TERMINAR\r\n");
			btnTerminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnTerminar.setBounds(142, 184, 128, 34);
			getContentPane().add(btnTerminar);
		
			cargarLineas();
	}
}
