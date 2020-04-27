import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class IntefazPrincipal {
	//Establecemos conexion con la base de datos
	public static final String DRIVER_JDBC = "com.mysql.jdbc.Driver"; // "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/jardineria?serverTimezone=UTC&useSSL=false";
	public static final String USERNAME= "root";
	public static final String PASSWORD="alumnodb";
	
	private JFrame frame;
	private final JPanel panelMenu = new JPanel();
	private final JLabel agregProd = new JLabel("Agregar Producto");
	private final JLabel factura = new JLabel("Factura");
	private final JLabel historico = new JLabel("Historico");
	private final JLabel iconoAgreg = new JLabel("");
	private final JLabel iconoProd = new JLabel("");
	private final JLabel iconoFactura = new JLabel("");
	private final JLabel iconoHistorico = new JLabel("");
	private JTable tablePrinicipal;
	private JTable tableFactura;
	private JTable tableHistorico;
	private JTextField textFieldCodigo;
	private JTextField textFieldNombre;
	private JTextField textFieldDimension;
	private JTextField textFieldProveedor;
	private JTextField textFieldDescripcion;
	private JTextField textFieldStock;
	private JTextField textFieldPrecioVenta;
	private JTextField textFieldPrecioPro;
	private JTextField textFieldCodP;
	private JTextField textFieldCantidad;
	private JTextField textFieldTotalNum;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntefazPrincipal window = new IntefazPrincipal();
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
	public IntefazPrincipal() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Modelos para la JTable de Prductos
		String [] titulos = {"codigo_producto", "nombre", "cantidad_en_stock", "precio_venta"};
		DefaultTableModel model = new DefaultTableModel(null, titulos);
		
		
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 13));
		frame.setBounds(100, 100, 1050, 575);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JPanel actualizaPanel = new JPanel();
		JComboBox comboBoxGama = new JComboBox();
		comboBoxGama.setBackground(Color.WHITE);
		JPanel panelAgregar = new JPanel();
		panelAgregar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panelAgregar.setBackground(Color.WHITE);
		panelAgregar.setVisible(false);
		
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Color.WHITE);
		panelPrincipal.setBounds(0, 204, 1044, 282);
		frame.getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		tablePrinicipal = new JTable();
		tablePrinicipal.setEnabled(false);
		tablePrinicipal.setColumnSelectionAllowed(true);
		tablePrinicipal.setCellSelectionEnabled(true);
		tablePrinicipal.setGridColor(new Color(0, 0, 0));
		tablePrinicipal.setForeground(new Color(0, 0, 0));
		tablePrinicipal.setBackground(Color.WHITE);
		tablePrinicipal.setBounds(170, 43, 705, 220);
		tablePrinicipal.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		JLabel lblCodigoProd = new JLabel("Codigo Producto");
		lblCodigoProd.setForeground(new Color(255, 69, 0));
		lblCodigoProd.setBounds(170, 93, 175, 16);
		panelPrincipal.add(lblCodigoProd);
		
		JLabel lblNombreProducto = new JLabel("Nombre Producto");
		lblNombreProducto.setForeground(new Color(255, 69, 0));
		lblNombreProducto.setBounds(346, 93, 175, 16);
		panelPrincipal.add(lblNombreProducto);
		
		JLabel lblCantidadDisponible = new JLabel("Cantidad Disponible");
		lblCantidadDisponible.setForeground(new Color(255, 69, 0));
		lblCantidadDisponible.setBounds(523, 93, 175, 16);
		panelPrincipal.add(lblCantidadDisponible);
		
		JLabel lblPrecioVenta = new JLabel("Precio Venta");
		lblPrecioVenta.setForeground(new Color(255, 69, 0));
		lblPrecioVenta.setBounds(700, 93, 175, 16);
		panelPrincipal.add(lblPrecioVenta);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			//Este metodo es un experimento para poder enviar info desde la tabla principal al campo de codigo. Más adelante voy a seguir desarrollandolo 
			public void mouseClicked(MouseEvent arg0) {
				int seleccionar = tablePrinicipal.rowAtPoint(arg0.getPoint());
				textFieldCodP.setText(String.valueOf(tablePrinicipal.getValueAt(seleccionar, 0)));
				System.out.print(String.valueOf(tablePrinicipal.getValueAt(seleccionar, 0)));	
			}
		});
		scrollPane.setForeground(new Color(0, 0, 0));
		scrollPane.setBounds(170, 112, 705, 170);
		
		panelPrincipal.add(scrollPane);
		scrollPane.setViewportView(tablePrinicipal);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			//Buscador de producto en tabla principal
			public void keyReleased(KeyEvent arg0) {
				String datoB = textField.getText();
				buscarProducto(datoB);
			}
		});
		textField.setBounds(333, 24, 175, 22);
		panelPrincipal.add(textField);
		textField.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar por nombre");
		lblBuscar.setForeground(new Color(255, 69, 0));
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setBounds(181, 26, 140, 19);
		panelPrincipal.add(lblBuscar);
		panelPrincipal.setVisible(true);
		JComboBox comboBoxidCliente = new JComboBox();
		comboBoxidCliente.setBackground(Color.WHITE);
		JPanel panelFactura = new JPanel();
		panelFactura.setBackground(Color.WHITE);
		panelFactura.setBounds(0, 204, 1044, 282);
		frame.getContentPane().add(panelFactura);
		panelFactura.setLayout(null);
		panelFactura.setVisible(false);
		

		
		tableFactura = new JTable();
		tableFactura.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFactura.setBackground(Color.WHITE);
		tableFactura.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		));
		tableFactura.setBounds(182, 115, 671, 112);
		panelFactura.add(tableFactura);
		
		JLabel lblCodigoProFac = new JLabel("Codigo Producto");
		lblCodigoProFac.setForeground(new Color(255, 69, 0));
		lblCodigoProFac.setBounds(240, 13, 131, 16);
		panelFactura.add(lblCodigoProFac);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setForeground(new Color(255, 69, 0));
		lblCantidad.setBounds(383, 13, 131, 16);
		panelFactura.add(lblCantidad);
		
		textFieldCodP = new JTextField();
		textFieldCodP.setBounds(240, 42, 131, 22);
		panelFactura.add(textFieldCodP);
		textFieldCodP.setColumns(10);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setColumns(10);
		textFieldCantidad.setBounds(383, 42, 131, 22);
		panelFactura.add(textFieldCantidad);
		JButton bttnAgregarAFact = new JButton("Agregar");
		bttnAgregarAFact.setBackground(new Color(255, 255, 255));
		bttnAgregarAFact.setForeground(new Color(255, 69, 0));
		bttnAgregarAFact.addActionListener(new ActionListener() {
			
			
			//Agregar a la Jtbale factura los datos necesario. Acá se valida también los datos para no introducir negativos en cantidad
			public void actionPerformed(ActionEvent e) {
				
				Double totalCont = 0.0;
				String codP = textFieldCodP.getText().toString();
				//Variable para la row de la tabla
				String cantiF2 = textFieldCantidad.getText().toString();
				int cantiF = Integer.parseInt(textFieldCantidad.getText());
				String subTot = Double.toString( calcularSubTotal(codP, cantiF));
				
				if(validaDatos(cantiF, actualizaPanel)==true) {
					
					textFieldCantidad.setText("");
					textFieldCodP.setText("");
				}else {

				
				String [] fila = new String[4];
				fila[0] = codP;
				fila[1] = devolverNombre(codP);
				fila[2] = cantiF2;
				fila[3] = subTot;
				model.insertRow(model.getRowCount(), fila);
				tableFactura.setModel(model);
				totalCont += calcularSubTotal(codP, cantiF);
				textFieldTotalNum.setText(totalCont.toString());
				textFieldCantidad.setText("");
				textFieldCodP.setText("");
				actualizaPanel.setVisible(false);
				}
				
				
			}
		});
		bttnAgregarAFact.setBounds(526, 42, 97, 25);
		panelFactura.add(bttnAgregarAFact);
		//Metodo y boton para limpiar toda la Jtable y generar una factura nueva
			JButton btnGenerarFacturaNue = new JButton("Generar Factura Nueva");
			btnGenerarFacturaNue.setForeground(new Color(255, 69, 0));
			btnGenerarFacturaNue.setBackground(Color.WHITE);
			btnGenerarFacturaNue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					limpiarModelo(model);
				}
				
			});
			btnGenerarFacturaNue.setBounds(635, 42, 165, 25);
			panelFactura.add(btnGenerarFacturaNue);
			
			
			JLabel lblNombreProducto_1 = new JLabel("Codigo Producto");
			lblNombreProducto_1.setForeground(new Color(255, 69, 0));
			lblNombreProducto_1.setBorder(null);
			lblNombreProducto_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNombreProducto_1.setBounds(182, 97, 165, 16);
			panelFactura.add(lblNombreProducto_1);
			
			JLabel lblCantidadFact = new JLabel("Cantidad");
			lblCantidadFact.setForeground(new Color(255, 69, 0));
			lblCantidadFact.setBorder(null);
			lblCantidadFact.setHorizontalAlignment(SwingConstants.CENTER);
			lblCantidadFact.setBounds(520, 97, 167, 16);
			panelFactura.add(lblCantidadFact);
			
			JLabel lblSubtotal = new JLabel("SubTotal");
			lblSubtotal.setForeground(new Color(255, 69, 0));
			lblSubtotal.setHorizontalAlignment(SwingConstants.CENTER);
			lblSubtotal.setBounds(686, 97, 167, 16);
			panelFactura.add(lblSubtotal);
			
			JButton btnCheckOut = new JButton("Finalizar Compra");
			btnCheckOut.setBackground(Color.WHITE);
			btnCheckOut.setForeground(new Color(255, 69, 0));
			btnCheckOut.addActionListener(new ActionListener() {
				int idCliente= 0;
				
				public void actionPerformed(ActionEvent e) {
					
					//Llamamos al metodo para insertar la factura finalizada en el historico y en la BBDD
					//Obtenemos el id de cliente, el total y lo mandamos a la Jtable de Historico donde se guardan todas las 
					//facturas generadas y guardadas en la base de datos
					
					 idCliente = Integer.parseInt(comboBoxidCliente.getSelectedItem().toString());
					 double total = Double.parseDouble(textFieldTotalNum.getText());
					 agregarFacturaHis(idCliente, total);
					 //Llamamos al metodo para consultar la bbdd e imprimir en tableHistorico
					 limpiarModelo(model);
					 consultarHistor();
					 
				}
			});
			btnCheckOut.setBounds(812, 42, 167, 25);
			panelFactura.add(btnCheckOut);
			
			JLabel lblTotal = new JLabel("Total");
			lblTotal.setForeground(new Color(255, 69, 0));
			lblTotal.setBounds(475, 242, 56, 16);
			panelFactura.add(lblTotal);
			
			textFieldTotalNum = new JTextField();
			textFieldTotalNum.setBackground(Color.WHITE);
			textFieldTotalNum.setEditable(false);
			textFieldTotalNum.setBounds(628, 239, 225, 22);
			panelFactura.add(textFieldTotalNum);
			textFieldTotalNum.setColumns(10);
			comboBoxidCliente.setEditable(true);
			comboBoxidCliente.setBounds(100, 42, 118, 22);
			obtenerOpciones(comboBoxidCliente);
			panelFactura.add(comboBoxidCliente);
			
			JLabel lblCodigoCliente = new JLabel("Codigo Cliente");
			lblCodigoCliente.setForeground(new Color(255, 69, 0));
			lblCodigoCliente.setBounds(100, 13, 118, 16);
			panelFactura.add(lblCodigoCliente);
			
			JLabel lblNombreProducto_2 = new JLabel("Nombre Producto");
			lblNombreProducto_2.setForeground(new Color(255, 69, 0));
			lblNombreProducto_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNombreProducto_2.setBounds(349, 97, 165, 16);
			panelFactura.add(lblNombreProducto_2);

		
		panelAgregar.setBounds(0, 204, 1044, 282);
		frame.getContentPane().add(panelAgregar);
		panelAgregar.setLayout(null);
		panelAgregar.setVisible(false);
		
		JLabel agregarProduct = new JLabel("Agregar Producto Nuevo");
		agregarProduct.setHorizontalAlignment(SwingConstants.CENTER);
		agregarProduct.setForeground(new Color(255, 69, 0));
		agregarProduct.setFont(new Font("Tahoma", Font.BOLD, 26));
		agregarProduct.setBounds(99, 13, 822, 48);
		panelAgregar.add(agregarProduct);
		
		JButton bttnAgregar = new JButton("Agregar");
		bttnAgregar.setForeground(new Color(255, 69, 0));
		bttnAgregar.setBackground(Color.WHITE);
		//Metodo y Boton para agregar productos. Tambien validamos los datos para que no haya negativo en cantidad ni en precio
		bttnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			String cod = textFieldCodigo.getText().toString();
			String nom = textFieldNombre.getText().toString();
			String gam = comboBoxGama.getSelectedItem().toString(); 
			String dim = textFieldDimension.getText().toString();
			String proveed = textFieldProveedor.getText().toString();
			String descr = textFieldDescripcion.getText().toString();
			double precioP = Double.parseDouble(textFieldPrecioPro.getText().toString());
			//Estos datos son convertidos
			int stock = Integer.parseInt( textFieldStock.getText().toString());
			double precioV = Double.parseDouble(textFieldPrecioVenta.getText().toString());
			if(validaDatos(stock, actualizaPanel)==true||validaDatos(precioV,actualizaPanel)==true||validaDatos(precioP,actualizaPanel)==true) {
			textFieldCodigo.setText("");
			textFieldNombre.setText("");
			textFieldDimension.setText("");
			textFieldProveedor.setText("");
			textFieldDescripcion.setText("");
			textFieldStock.setText("");
			textFieldPrecioPro.setText("");
			textFieldPrecioVenta.setText("");
			
			}else {
				//Una vez validados los datos los pasamos al metodo para agregar a la base de datos.
				agregarProducto(cod, nom, gam, proveed,descr, dim, stock, precioV, precioP);
				textFieldCodigo.setText("");
				textFieldNombre.setText("");
				textFieldDimension.setText("");
				textFieldProveedor.setText("");
				textFieldDescripcion.setText("");
				textFieldStock.setText("");
				textFieldPrecioPro.setText("");
				textFieldPrecioVenta.setText("");
			}
			
			
			
			
			
			}
			
		});
		bttnAgregar.setBounds(363, 197, 113, 25);
		panelAgregar.add(bttnAgregar);
		
		textFieldCodigo = new JTextField();
		textFieldCodigo.setColumns(10);
		textFieldCodigo.setBounds(57, 124, 77, 22);
		panelAgregar.add(textFieldCodigo);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(146, 124, 77, 22);
		panelAgregar.add(textFieldNombre);
		
		textFieldDimension = new JTextField();
		textFieldDimension.setColumns(10);
		textFieldDimension.setBounds(363, 124, 77, 22);
		panelAgregar.add(textFieldDimension);
		
		textFieldProveedor = new JTextField();
		textFieldProveedor.setColumns(10);
		textFieldProveedor.setBounds(452, 124, 77, 22);
		panelAgregar.add(textFieldProveedor);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setColumns(10);
		textFieldDescripcion.setBounds(541, 124, 77, 22);
		panelAgregar.add(textFieldDescripcion);
		
		textFieldStock = new JTextField();
		textFieldStock.setColumns(10);
		textFieldStock.setBounds(630, 124, 77, 22);
		panelAgregar.add(textFieldStock);
		
		textFieldPrecioVenta = new JTextField();
		textFieldPrecioVenta.setColumns(10);
		textFieldPrecioVenta.setBounds(719, 124, 77, 22);
		panelAgregar.add(textFieldPrecioVenta);
		
		textFieldPrecioPro = new JTextField();
		textFieldPrecioPro.setColumns(10);
		textFieldPrecioPro.setBounds(808, 124, 113, 22);
		panelAgregar.add(textFieldPrecioPro);
		
		JButton btnLimpiarCampos = new JButton("Limpiar Campos");
		btnLimpiarCampos.setForeground(new Color(255, 69, 0));
		btnLimpiarCampos.setBackground(Color.WHITE);
		btnLimpiarCampos.addActionListener(new ActionListener() {
			//Limpiar campos del panel Agregar
			public void actionPerformed(ActionEvent e) {
				textFieldCodigo.setText("");
				textFieldNombre.setText("");
				textFieldDimension.setText("");
				textFieldProveedor.setText("");
				textFieldDescripcion.setText("");
				textFieldStock.setText("");
				textFieldPrecioPro.setText("");
				textFieldPrecioVenta.setText("");
			}
		});
		btnLimpiarCampos.setBounds(505, 197, 113, 25);
		panelAgregar.add(btnLimpiarCampos);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setForeground(new Color(255, 69, 0));
		lblCodigo.setBounds(57, 95, 77, 22);
		panelAgregar.add(lblCodigo);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setForeground(new Color(255, 69, 0));
		lblNombre.setBounds(146, 95, 77, 22);
		panelAgregar.add(lblNombre);
		
		JLabel lblGama = new JLabel("Gama");
		lblGama.setForeground(new Color(255, 69, 0));
		lblGama.setBounds(277, 95, 77, 22);
		panelAgregar.add(lblGama);
		
		JLabel lblDimensiones = new JLabel("Dimensiones");
		lblDimensiones.setForeground(new Color(255, 69, 0));
		lblDimensiones.setBounds(363, 95, 77, 22);
		panelAgregar.add(lblDimensiones);
		
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setForeground(new Color(255, 69, 0));
		lblProveedor.setBounds(452, 95, 77, 22);
		panelAgregar.add(lblProveedor);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setForeground(new Color(255, 69, 0));
		lblDescripcion.setBounds(541, 95, 77, 22);
		panelAgregar.add(lblDescripcion);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setForeground(new Color(255, 69, 0));
		lblStock.setBounds(630, 95, 77, 22);
		panelAgregar.add(lblStock);
		
		JLabel lblPrecioVenta_1 = new JLabel("Precio Venta");
		lblPrecioVenta_1.setForeground(new Color(255, 69, 0));
		lblPrecioVenta_1.setBounds(719, 95, 77, 22);
		panelAgregar.add(lblPrecioVenta_1);
		
		JLabel lblPrecioProovedor = new JLabel("Precio Proovedor");
		lblPrecioProovedor.setForeground(new Color(255, 69, 0));
		lblPrecioProovedor.setBounds(808, 95, 113, 22);
		panelAgregar.add(lblPrecioProovedor);
		
		
		comboBoxGama.setBounds(235, 124, 119, 22);
		obtenerOpcionesG(comboBoxGama);
		//Obtenemos con las opciones de Gama que hay en la base de datos. Esto permite que esten siempre actualizadas
		panelAgregar.add(comboBoxGama);
		
		
		
		JPanel panelHistorico = new JPanel();
		panelHistorico.setBackground(Color.WHITE);
		panelHistorico.setBounds(0, 204, 1044, 282);
		frame.getContentPane().add(panelHistorico);
		panelHistorico.setLayout(null);
		panelHistorico.setVisible(false);
		tableHistorico = new JTable();
		tableHistorico.setBackground(Color.WHITE);
		tableHistorico.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		tableHistorico.setBounds(156, 24, 628, 238);
		JScrollPane scrollPaneHistorico = new JScrollPane();
		scrollPaneHistorico.setForeground(Color.BLACK);
		scrollPaneHistorico.setBackground(Color.WHITE);
		scrollPaneHistorico.setEnabled(false);
		scrollPaneHistorico.setBounds(156, 24, 628, 238);
		panelHistorico.add(scrollPaneHistorico);
		scrollPaneHistorico.setViewportView(tableHistorico);
		
		JLabel lblIdfactura = new JLabel("idFactura");
		lblIdfactura.setForeground(new Color(255, 69, 0));
		lblIdfactura.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdfactura.setBounds(156, 0, 209, 29);
		panelHistorico.add(lblIdfactura);
		
		JLabel labelIdCliente = new JLabel("idCliente");
		labelIdCliente.setForeground(new Color(255, 69, 0));
		labelIdCliente.setHorizontalAlignment(SwingConstants.CENTER);
		labelIdCliente.setBounds(364, 0, 209, 29);
		panelHistorico.add(labelIdCliente);
		
		JLabel lblTotal_1 = new JLabel("Total");
		lblTotal_1.setForeground(new Color(255, 69, 0));
		lblTotal_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal_1.setBounds(575, 0, 209, 29);
		panelHistorico.add(lblTotal_1);
		
		
		actualizaPanel.setBounds(0, 519, 1044, 56);
		frame.getContentPane().add(actualizaPanel);
		actualizaPanel.setVisible(false);
		actualizaPanel.setBackground(new Color(220, 20, 60));
		actualizaPanel.setLayout(null);
		//Comenzamos mostrando todos los productos que haya en la ventana principal
		buscarProducto();
		panelMenu.setBackground(Color.WHITE);
		panelMenu.setBounds(0, 0, 1050, 203);
		frame.getContentPane().add(panelMenu);
		panelMenu.setLayout(null);
		
		JLabel label = new JLabel("");
		//Boton superior derecho para poder salir
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		label.setIcon(new ImageIcon(IntefazPrincipal.class.getResource("/icons8-close-window-26.png")));
		label.setBounds(1012, 13, 26, 32);
		panelMenu.add(label);
		
		

		
		
		JLabel productos = new JLabel("Productos");
		productos.setHorizontalAlignment(SwingConstants.CENTER);
		productos.setForeground(new Color(255, 69, 0));
		productos.setBackground(new Color(238, 112, 82));
		productos.setBounds(184, 158, 147, 32);
		panelMenu.add(productos);
		factura.setHorizontalAlignment(SwingConstants.CENTER);
		factura.setForeground(new Color(255, 69, 0));
		factura.setBackground(new Color(238, 112, 82));
		factura.setBounds(357, 158, 147, 32);
		
		panelMenu.add(factura);
		historico.setHorizontalAlignment(SwingConstants.CENTER);
		historico.setForeground(new Color(255, 69, 0));
		historico.setBackground(new Color(238, 112, 82));
		historico.setBounds(527, 158, 147, 32);
		panelMenu.add(historico);
		//Boton para desplazarse entre ventanas. 
		iconoProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelAgregar.setVisible(false);
				panelPrincipal.setVisible(true);
				panelFactura.setVisible(false);
				panelHistorico.setVisible(false);
				
				}
			
		});
		iconoProd.setIcon(new ImageIcon(IntefazPrincipal.class.getResource("/icons8-new-product-100.png")));
		iconoProd.setBounds(207, 35, 113, 114);
		
		panelMenu.add(iconoProd);
		//Boton para desplazarse entre ventanas. 
		iconoFactura.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelAgregar.setVisible(false);
				panelPrincipal.setVisible(false);
				panelFactura.setVisible(true);
				panelHistorico.setVisible(false);
			}
		});
		iconoFactura.setBackground(new Color(255, 140, 0));
		iconoFactura.setForeground(new Color(255, 140, 0));
		iconoFactura.setIcon(new ImageIcon(IntefazPrincipal.class.getResource("/icons8-cash-register-100.png")));
		iconoFactura.setBounds(375, 35, 107, 120);
		
		panelMenu.add(iconoFactura);
		//Boton para desplazarse entre ventanas. 
		iconoHistorico.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelAgregar.setVisible(false);
				panelPrincipal.setVisible(false);
				panelFactura.setVisible(false);
				panelHistorico.setVisible(true);
				consultarHistor();
			}
		});
		iconoHistorico.setIcon(new ImageIcon(IntefazPrincipal.class.getResource("/icons8-time-machine-100.png")));
		iconoHistorico.setBounds(550, 50, 100, 105);
		
		panelMenu.add(iconoHistorico);
		iconoAgreg.setBounds(718, 35, 100, 120);
		panelMenu.add(iconoAgreg);
		//Boton para desplazarse entre ventanas. 
		iconoAgreg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				panelPrincipal.setVisible(false);
				panelAgregar.setVisible(true);
				panelFactura.setVisible(false);
				panelHistorico.setVisible(false);
				
			}
		});
		iconoAgreg.setIcon(new ImageIcon(IntefazPrincipal.class.getResource("/icons8-add-100.png")));
		agregProd.setBounds(686, 158, 179, 32);
		panelMenu.add(agregProd);
		agregProd.setHorizontalAlignment(SwingConstants.CENTER);
		agregProd.setForeground(new Color(255, 69, 0));
		agregProd.setBackground(new Color(238, 112, 82));
		
		
		
	
		JLabel lblError = new JLabel("Error Datos Ingresados. Intente nuevamente");
		lblError.setForeground(new Color(0, 0, 0));
		lblError.setBorder(new LineBorder(new Color(255, 0, 0)));
		lblError.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 38));
		lblError.setBackground(new Color(255, 0, 0));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(0, 0, 1044, 56);
		actualizaPanel.add(lblError);
		
		
		
	}
	
	
	/*
	 * Metodos implementados 
	 */
	public void buscarProducto() {
		try {
			Class.forName(DRIVER_JDBC);
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String [] titulos = {"codigo_producto", "nombre", "cantidad_en_stock", "precio_venta"};
			String sql = "select codigo_producto, nombre, cantidad_en_stock, precio_venta from producto";
			DefaultTableModel model = new DefaultTableModel(null, titulos);
			ResultSet rs = st.executeQuery(sql);
			String [] fila = new String[4];
			while(rs.next()) {
				fila[0] = rs.getString("codigo_producto");
				fila[1] = rs.getString("nombre");
				fila[2] = rs.getString("cantidad_en_stock");
				fila[3] = rs.getString("precio_venta");
				model.addRow(fila);
				
			}
			tablePrinicipal.setModel(model);
			rs.close();
		
		} catch (Exception e) {
			// TODO: handle exception
			System.err.print(""+e.getMessage());
		}	
		}
	public void buscarProducto(String dato) {
		try {
			Class.forName(DRIVER_JDBC);
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String [] titulos = {"codigo_producto", "nombre", "cantidad_en_stock", "precio_venta"};
			String sql = "select codigo_producto, nombre, cantidad_en_stock, precio_venta from producto where nombre like '%_" + dato +"_%' ";
			DefaultTableModel model = new DefaultTableModel(null, titulos);
			ResultSet rs = st.executeQuery(sql);
			String [] fila = new String[4];
			while(rs.next()) {
				fila[0] = rs.getString("codigo_producto");
				fila[1] = rs.getString("nombre");
				fila[2] = rs.getString("cantidad_en_stock");
				fila[3] = rs.getString("precio_venta");
				model.addRow(fila);
				
			}
			tablePrinicipal.setModel(model);
			rs.close();
		
		} catch (Exception e) {
			// TODO: handle exception
			System.err.print(""+e.getMessage());
		}	
		}
	
	
	public void agregarProducto(String codigo, String nombre, String gama, String proveedor, String descripcion, String dimensiones, int cantidad, double precioV, double precioP) {
		try {
			Class.forName(DRIVER_JDBC);
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String profSql = "Insert into `producto` (codigo_producto, nombre, gama, proveedor, descripcion, cantidad_en_stock, precio_venta, precio_proveedor ) values ('"+ codigo + "', '"+ nombre + "','"+ gama +"','" + proveedor + "','"
					+ dimensiones + "','"+ cantidad + "','" + precioV + "','" + precioP + "');";
			st.executeUpdate(profSql);
			st.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
	}
	public void agregarFacturaHis(int idCliente, double total) {
		try {
			Class.forName(DRIVER_JDBC);
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String sql = "Insert into `factura`(idCliente, total) values ('"+ idCliente +"','" + total +"');";
			st.executeUpdate(sql);
			st.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void consultarHistor() {
		try {
			Class.forName(DRIVER_JDBC);
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String [] titulos = {"idFactura", "idCliente", "total"};
			String sql = "select idFactura, idCliente, total from factura";
			DefaultTableModel modelH = new DefaultTableModel(null, titulos);
			ResultSet rs = st.executeQuery(sql);
			String [] fila = new String[3];
			while(rs.next()) {
				fila[0] = rs.getString("idFactura");
				fila[1] = rs.getString("idCliente");
				fila[2] = rs.getString("total");
				modelH.addRow(fila);}
			tableHistorico.setModel(modelH);
			rs.close();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//generar Modelo nuevo
	public void limpiarModelo(DefaultTableModel model) {

		 int a = model.getRowCount()-1;
	        for (int i = a; i >= 0; i--) {          
	        model.removeRow(model.getRowCount()-1);
	        }
		
		textFieldCantidad.setText("");
		textFieldCodP.setText("");
		textFieldTotalNum.setText("");
		 
	}
	 
	
	//Calculo de Subtotal para JTabtleFactura
	public double calcularSubTotal(String codigo, int cantidad) {
		Double precio = 0.0;
		try {
		Class.forName(DRIVER_JDBC);
		Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement st = con.createStatement();
		String sql = "select precio_venta from producto where codigo_producto like '" + codigo +"'";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
		 precio = rs.getDouble("precio_venta");
				 
			
			}
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		double SubT = precio*cantidad;
		return SubT;
		}
	
	
	public boolean validaDatos(double dato, JPanel panel) {
		if(dato<0) {
			
			panel.setVisible(true);
			return true;
		}
		return false;
	}
	public boolean validaDatos(int dato, JPanel panel) {
		if(dato<0) {
			panel.setVisible(true);
			return true;
		}
		return false;
	}
	
	
	public void obtenerOpciones(JComboBox combo) {
		try {
		Class.forName(DRIVER_JDBC);
		Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement st2 = con2.createStatement();
		String sql2 = "SELECT codigo_cliente FROM cliente ORDER BY codigo_cliente";
		ResultSet rs2 = st2.executeQuery(sql2);
		
		while(rs2.next()) {
			 
			combo.addItem(rs2.getInt("codigo_cliente"));
				}
		rs2.close();

	} catch (Exception e) {
		// TODO: handle exception
		System.err.print(""+e.getMessage());
	}
	
	}
	
	
	
	public void obtenerOpcionesG(JComboBox combo) {
		try {
		Class.forName(DRIVER_JDBC);
		Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		Statement st2 = con2.createStatement();
		String sql2 = "SELECT gama FROM gama_producto";
		ResultSet rs2 = st2.executeQuery(sql2);
		
		while(rs2.next()) {
			 
			combo.addItem(rs2.getString("gama"));
				}
		rs2.close();

	} catch (Exception e) {
		// TODO: handle exception
		System.err.print(""+e.getMessage());
	}
	
	}
	
	public String devolverNombre(String dato) {
		String result = "";
		try {
			Class.forName(DRIVER_JDBC);
			Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String sql = "select nombre from producto where codigo_producto like '" + dato +"'";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
			 result = rs.getString("nombre");
				
				}
			
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			
			return result ;
		
	}
	}
	
	//Fin del codigo
//Autor Mauri Speroni


	
	
	


