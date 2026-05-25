package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class VentanaProducto extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel labelCodigo;
	private JLabel labelNombre;
	private JLabel labelExistencias;
	private JLabel labelUnidad;
	private JLabel labelPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaProducto dialog = new VentanaProducto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentanaProducto() {
		setBounds(100, 100, 278, 259);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			labelCodigo = new JLabel("C\u00F3digo: ");
			labelCodigo.setBounds(21, 22, 41, 14);
			contentPanel.add(labelCodigo);
		}
		{
			labelNombre = new JLabel("Nombre: ");
			labelNombre.setBounds(21, 47, 49, 14);
			contentPanel.add(labelNombre);
		}
		{
			labelExistencias = new JLabel("Existencias: ");
			labelExistencias.setBounds(21, 72, 71, 14);
			contentPanel.add(labelExistencias);
		}
		{
			labelUnidad = new JLabel("Unidad: ");
			labelUnidad.setBounds(21, 97, 48, 14);
			contentPanel.add(labelUnidad);
		}
		{
			labelPrecio = new JLabel("Precio: ");
			labelPrecio.setBounds(21, 122, 48, 14);
			contentPanel.add(labelPrecio);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnGuardar = new JButton("Guardar");
				btnGuardar.setActionCommand("OK");
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}

}
