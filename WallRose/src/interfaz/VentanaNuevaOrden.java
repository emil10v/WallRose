package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Cliente;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaNuevaOrden extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tableClientes;
	private JButton btnCrear;
	
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
	
	private void crearOrden() {
		int fila = tableClientes.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				DefaultTableModel model = (DefaultTableModel) tableClientes.getModel();
				String idCliente = (String) model.getValueAt(fila, 0);
				Controladora control = Controladora.getInstance();
				control.crearOrdenCompraVacia(idCliente);
				dispose();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaNuevaOrden dialog = new VentanaNuevaOrden();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaNuevaOrden() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 41, 389, 178);
		contentPanel.add(scrollPane);
		
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
		
		JLabel lblSeleccionarCliente = new JLabel("SELECCIONE UN CLIENTE:");
		lblSeleccionarCliente.setBounds(21, 15, 182, 27);
		contentPanel.add(lblSeleccionarCliente);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCrear = new JButton("CREAR ORDEN");
				btnCrear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						crearOrden();
					}
				});
				btnCrear.setActionCommand("OK");
				buttonPane.add(btnCrear);
				getRootPane().setDefaultButton(btnCrear);
			}
			{
				JButton cancelButton = new JButton("CANCELAR\r\n");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		cargarClientes();
	}
}
