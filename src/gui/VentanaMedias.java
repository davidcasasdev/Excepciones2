package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import clases.Alumno;
import excepciones.AlumnoYaExisteException;
import net.miginfocom.swing.MigLayout;

public class VentanaMedias extends JFrame {

	private JPanel contentPane;
	private JTextField txtNota1;
	private JTextField txtNota2;
	private JTextField txtNota3;
	private JTextField txtNombre;
	private JLabel lblPromedio;
	private JLabel lblResultado;
	private JTable table;
	
	private ArrayList<Alumno> listaAlumnos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMedias frame = new VentanaMedias();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaMedias() {
		listaAlumnos = new ArrayList<Alumno>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow][][grow][][grow]", "[44.00][][][][][][grow]"));
		
		JLabel lblNewLabel_4 = new JLabel("CALCULAR PROMEDIO");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBackground(Color.BLACK);
		contentPane.add(lblNewLabel_4, "cell 0 0 6 1,grow");
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		contentPane.add(lblNewLabel, "cell 0 1,alignx trailing");
		
		txtNombre = new JTextField();
		contentPane.add(txtNombre, "cell 1 1 5 1,growx");
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nota 1:");
		contentPane.add(lblNewLabel_1, "cell 0 2,alignx trailing");
		
		txtNota1 = new JTextField();
		contentPane.add(txtNota1, "cell 1 2,growx");
		txtNota1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Nota 2:");
		contentPane.add(lblNewLabel_2, "flowx,cell 2 2");
		
		JLabel lblNewLabel_3 = new JLabel("Nota 3:");
		contentPane.add(lblNewLabel_3, "cell 4 2,alignx trailing");
		
		txtNota3 = new JTextField();
		contentPane.add(txtNota3, "cell 5 2,growx");
		txtNota3.setColumns(10);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 3 6 1,grow");
		panel.setLayout(new MigLayout("", "[grow][grow]", "[]"));
		
		JButton btnNewButton_1 = new JButton("Mostrar Datos");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDatos();
			}
		});
		panel.add(btnNewButton_1, "flowx,cell 0 0,alignx center");
		
		JButton btnNewButton = new JButton("Calcular e Insertar");
		panel.add(btnNewButton, "cell 1 0,alignx center");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcular();
			}
		});
		
		JLabel lblNewLabel_5 = new JLabel("Promedio:");
		contentPane.add(lblNewLabel_5, "cell 0 4");
		
		lblPromedio = new JLabel("");
		contentPane.add(lblPromedio, "cell 1 4 5 1");
		
		JLabel lblNewLabel_7 = new JLabel("Resultado:");
		contentPane.add(lblNewLabel_7, "cell 0 5");
		
		lblResultado = new JLabel("");
		contentPane.add(lblResultado, "cell 1 5 5 1");
		
		txtNota2 = new JTextField();
		contentPane.add(txtNota2, "cell 3 2,growx");
		txtNota2.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 6 6 1,grow");
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Nota 1", "Nota 2", "Nota 3", "Nota media"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Double.class, Double.class, Double.class, Double.class
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
		scrollPane.setViewportView(table);
	}

	protected void mostrarDatos() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		for (Alumno alumno : listaAlumnos) {
			Object fila [] = {
				alumno.getNombre(),alumno.getNota1(), alumno.getNota2(),
				alumno.getNota3(), alumno.calculaNotaFinal()
			};
			modelo.addRow(fila);
		}
		
	}

	protected void calcular() {
		try {
			double nota1 = Double.parseDouble(txtNota1.getText());
			double nota2 = Double.parseDouble(txtNota2.getText());
			double nota3 = Double.parseDouble(txtNota3.getText());
			
			String nombre = txtNombre.getText();
			
			Alumno a = new Alumno(nombre, nota1,nota2,nota3);
			
			this.lblPromedio.setText(""+a.calculaNotaFinal());
			
			if (a.estaAprobado()) {
				this.lblResultado.setText("Ha aprobado la asignatura");
				lblResultado.setForeground(new Color(24,175,39));
			} else {
				this.lblResultado.setText("Toca recuperar");
				lblResultado.setForeground(Color.RED);
			}
			
			insertar(a);
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(contentPane, "Debe introducir una nota valida", 
					"Error en los datos", JOptionPane.ERROR_MESSAGE);
		} catch (AlumnoYaExisteException e) {
			JOptionPane.showMessageDialog(contentPane, "Ya existe un alumno con ese nombre", 
					"Error en los datos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void insertar(Alumno a) throws AlumnoYaExisteException {
		if (listaAlumnos.contains(a)) {
			throw new AlumnoYaExisteException();
		}
		listaAlumnos.add(a);
	}

}
