import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import javax.swing.JLabel;

public class panelProductos extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public panelProductos() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 575);
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 1050, 575));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLocale(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1050, 575);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel pruebaProd = new JLabel("Prueba");
		pruebaProd.setBounds(199, 94, 56, 16);
		panel.add(pruebaProd);
	}

}
