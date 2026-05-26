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
import logica.Producto;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaLineaOrden extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable tableProductos;
	private JTextField textCantidad;

	
	private void cargarProductos() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
		model.setRowCount(0);
		List<Producto> listaProductos = control.getProductos();
		for (Producto prod : listaProductos) {
			Object[] fila = new Object[] { prod.getCodigo(), prod.getNombre(),prod.getPrecio(), prod.getExistencias() };
			model.addRow(fila);
		}
	}
	private void agregarLinea(int numOrden) {
	int fila = tableProductos.getSelectedRow();
	if (fila == -1) {
		JOptionPane.showMessageDialog(this, "Debe seleccionar un Producto.", "Error", JOptionPane.ERROR_MESSAGE);
	} else {
		try {
			DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
			int codProducto = (Integer) model.getValueAt(fila, 0);
			Controladora control = Controladora.getInstance();
			double cantidad = Double.parseDouble(textCantidad.getText());
			control.agregarLineaOrdenCompra(numOrden, codProducto, cantidad);
			
			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}



	/**
	 * Create the dialog.
	 */
	public VentanaLineaOrden(int numOrden) {
		setModalityType(ModalityType.MODELESS);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 230, 436, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("AGREGAR\r\n");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						agregarLinea(numOrden);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
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
		{
			JPanel contentPanel = new JPanel();
			contentPanel.setBounds(0, 0, 436, 231);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel);
			contentPanel.setLayout(null);
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 11, 389, 178);
				contentPanel.add(scrollPane);
				{
					tableProductos = new JTable();
					scrollPane.setViewportView(tableProductos);
					tableProductos.setModel(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"C\u00F3digo Producto", "Nombre", "Precio", "Existencias"
						}
					) {
						Class[] columnTypes = new Class[] {
							Integer.class, String.class, Double.class, Double.class
						};
						public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}
					});
					tableProductos.getColumnModel().getColumn(0).setResizable(false);
					tableProductos.getColumnModel().getColumn(0).setPreferredWidth(217);
					tableProductos.getColumnModel().getColumn(1).setResizable(false);
					tableProductos.getColumnModel().getColumn(1).setPreferredWidth(170);
					tableProductos.getColumnModel().getColumn(2).setResizable(false);
					tableProductos.getColumnModel().getColumn(2).setPreferredWidth(160);
					tableProductos.getColumnModel().getColumn(3).setResizable(false);
					tableProductos.getColumnModel().getColumn(3).setPreferredWidth(153);
					tableProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane.setViewportView(tableProductos);
				}
			}
			{
				JLabel lblNewLabel = new JLabel("Cantidad:");
				lblNewLabel.setBounds(20, 200, 59, 20);
				contentPanel.add(lblNewLabel);
			}
			
			textCantidad = new JTextField();
			textCantidad.setBounds(87, 200, 96, 20);
			contentPanel.add(textCantidad);
			textCantidad.setColumns(10);
			
			cargarProductos();
		}
	}
}
