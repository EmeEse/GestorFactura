

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Buscar extends JDialog {
	
	public static final String DRIVER_JDBC = "com.mysql.jdbc.Driver"; // "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/jardineria?serverTimezone=UTC&useSSL=false";
	public static final String USERNAME= "root";
	public static final String PASSWORD="alumnodb";
	private final JPanel contentPanel = new JPanel();
	private JTextField Consulta;
	private JTextField resultado;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the dialog.
	 */
	public Buscar() {
		
		
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Introduce el producto que quieras ac\u00E1");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 1;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			Consulta = new JTextField();
			GridBagConstraints gbc_Consulta = new GridBagConstraints();
			gbc_Consulta.gridwidth = 5;
			gbc_Consulta.insets = new Insets(0, 0, 5, 0);
			gbc_Consulta.fill = GridBagConstraints.HORIZONTAL;
			gbc_Consulta.gridx = 2;
			gbc_Consulta.gridy = 1;
			contentPanel.add(Consulta, gbc_Consulta);
			Consulta.setColumns(10);
		}
			
			{
			/*{
				resultado = new JTextField();
				resultado.setEditable(false);
				GridBagConstraints gbc_resultado = new GridBagConstraints();
				gbc_resultado.insets = new Insets(0, 0, 5, 5);
				gbc_resultado.fill = GridBagConstraints.HORIZONTAL;
				gbc_resultado.gridx = 1;
				gbc_resultado.gridy = 4;
				contentPanel.add(resultado, gbc_resultado);
				resultado.setColumns(10);
			}*/
			
					{
						JLabel lblNewLabel_1 = new JLabel("Resultado");
						GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
						gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
						gbc_lblNewLabel_1.gridwidth = 6;
						gbc_lblNewLabel_1.gridx = 1;
						gbc_lblNewLabel_1.gridy = 2;
						contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
					}
		}
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String consultaO = Consulta.getText();
				ArrayList<String> result = new ArrayList<String>();
				result = mostrarProducto(consultaO);
				for(int i = 0;i<result.size();i++) {
					resultado = new JTextField();
					resultado.setEditable(false);
					GridBagConstraints gbc_resultado = new GridBagConstraints();
					gbc_resultado.insets = new Insets(0, 0, 5, 5);
					gbc_resultado.fill = GridBagConstraints.HORIZONTAL;
					gbc_resultado.gridx = 1;
					gbc_resultado.gridy = 4+i;
					resultado.setText(result.get(i).toString());
					contentPanel.add(resultado, gbc_resultado);
					resultado.setColumns(10);
				}
				
				
				
			}
		});
		
		
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 0, 5);
		gbc_btnBuscar.gridx = 1;
		gbc_btnBuscar.gridy = 7;
		contentPanel.add(btnBuscar, gbc_btnBuscar);
		{
			JButton btnCancelar = new JButton("Cancelar");
			GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
			gbc_btnCancelar.gridwidth = 5;
			gbc_btnCancelar.gridx = 4;
			gbc_btnCancelar.gridy = 7;
			contentPanel.add(btnCancelar, gbc_btnCancelar);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			
		}
	}
	
	private static ArrayList<String> mostrarProducto(String consulta) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		ArrayList<String> result = new ArrayList<String>();
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL,"root", "alumnodb");
			Statement stmt = conn.createStatement();
			String prodJard = "Select nombre "+ "From producto where nombre like '%"+ consulta+ "%'";
			ResultSet rs =stmt.executeQuery(prodJard);
			while(rs.next()) {
				//sb.append(rs.getString("nombre"));
				result.add(rs.getString("nombre"));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}		
}
