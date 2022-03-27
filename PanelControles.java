package Proyecto3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Clase que representa un panel con botones.
 * @author Ingrid Shaiany Chan Topete A01227694
 * @author Daniel Díaz López A01636706
 * @param <E> - Tipo parametrizado.
 */
public class PanelControles<E extends Comparable<E>> extends JPanel {
	private JTextField insertField,
	removeField;
	private JButton insertButton,
	removeButton,
	flushButton;
	private VentanaAVL<E> ventana;

	/**
	 * Constructor que crea el panel y los botones necesarios.
	 * @param ventana - Objeto JFrame para poder accesar a sus métodos. 
	 */
	public PanelControles(VentanaAVL<E> ventana) {
		super();
		this.ventana = ventana;
		this.setPreferredSize(new Dimension(1000,100));
		this.setBackground(Color.GRAY);

		//Text field insertar
		this.insertField = new JTextField();
		this.insertField.setPreferredSize( new Dimension( 120, 25 ));
		this.insertField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		this.add(this.insertField);

		//Botón insertar
		this.insertButton = new JButton("Insertar");
		this.insertButton.setPreferredSize( new Dimension( 120, 25 ));
		this.insertButton.setFont(new Font("Arial", Font.PLAIN,18));
		this.insertButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		this.add(this.insertButton);


		//Text field remover
		this.removeField = new JTextField();
		this.removeField.setPreferredSize( new Dimension( 120, 25 ));
		this.removeField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});
		this.add(this.removeField);

		//Botón remover
		this.removeButton = new JButton("Remover");
		this.removeButton.setPreferredSize( new Dimension( 120, 25 ));
		this.removeButton.setFont(new Font("Arial", Font.PLAIN,18));
		this.removeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});
		this.add(this.removeButton);

		//Botón FLUSH
		this.flushButton = new JButton("Eliminar todo");
		this.flushButton.setPreferredSize( new Dimension( 170, 25 ));
		this.flushButton.setFont(new Font("Arial", Font.PLAIN,18));
		this.flushButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.getArbol().flush();
				ventana.getPanel().refresh();


			}
		});
		this.add(this.flushButton);

	}
	
	/**
	 * Método auxiliar que llama al método remover de la clase AVLtree y que actualiza el dibujo del árbol.
	 */
	private void remove() {
		try {
			ventana.getArbol().remove(Integer.parseInt(removeField.getText()));
			ventana.getPanel().refresh();
			removeField.setText("");
		}catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "No has ingresado un valor a borrar o la entrada es inválida.", "Excepción", 2);
			removeField.setText("");
		}catch(NoSuchElementException ex) {
			JOptionPane.showMessageDialog(null, "No se puede borrar de un árbol vacío.", "Excepción", 2);
			removeField.setText("");
		}catch(NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "No se puede borrar un elemento que no esta en el arbol.", "Excepción", 2);
			removeField.setText("");
		}
	}
	
	/**
	 * Método auxiliar que llama al método insertar de la clase AVLtree y actualiza el dibujo del árbol.
	 */
	private void insert() {
		try {
			ventana.getArbol().insertar(Integer.parseInt(insertField.getText()));
			ventana.getPanel().refresh();
			insertField.setText("");
		}catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Valor inválido.", "Excepción", 2);
		}
	}

}
