package Proyecto3;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author Ingrid Shaiany Chan Topete A01227694
 * @author Daniel Díaz López A01636706
 * Clase que representa el JFrame principal en donde se añaden los paneles.
 * @param <E> - Tipo de dato parametrizado.
 */
public class VentanaAVL<E extends Comparable<E>> extends JFrame{
	private AVLtree<Integer> arbol;
	private Lienzo<Integer> panel;
	
	/**
	 * Constructor que añade todos los componentes necesarios.
	 */
	public VentanaAVL() {
		super("AVL Tree - Ingrid y Daniel");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.arbol = new AVLtree<Integer>();
		this.panel = new Lienzo<Integer>(this.arbol);
		this.add(panel);
		PanelControles<E> pc = new PanelControles<>(this);
		this.add(pc, BorderLayout.SOUTH);
		this.setSize(new Dimension(1000, 600));
		this.setVisible(true);
	}

	/**
	 * Getter del panel o lienzo de dibujo.
	 * @return - Regresa el panel de dibujo.
	 */
	public Lienzo<Integer> getPanel(){
		return this.panel;
	}
	
	/**
	 * Getter de árbol.
	 * @return -Regresa el árbol AVL.
	 */
	public AVLtree<Integer> getArbol(){
		return this.arbol;
	}
	
	
	public static void main(String[] args) {
		VentanaAVL<Integer> nva = new VentanaAVL<Integer>();
	}

}
