package Proyecto3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Ingrid Shaiany Chan Topete A01227694
 * @author Daniel Díaz López A01636706
 * Clase que representa el JPanel o lienzo de dibujo.
 * @param <E> - Tipo parametrizado.
 */
public class Lienzo<E extends Comparable<E>> extends JPanel{
	private AVLtree<E> arbol;
	public static final int diametro = 30;
	public static final int radio = 15;
	public static final int ancho = 50;
	
	/**
	 * Constructor de lienzo que recibe un árbol como parámetro.
	 * @param arbol - Árbol recibido como parámetro.
	 */
	public Lienzo(AVLtree<E> arbol) {
		super();
		this.arbol = arbol;
	}
	
	/**
	 * Método que hace un repaint del dibujo.
	 */
	public void refresh() {
		repaint();
	}
	
	/**
	 *Método principal de dibujo.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		pintar(g, getWidth()/2, 20, arbol.getRoot());
	}

	/**
	 * Método recursivo auxiliar encargado de dibujar el árbol: sus nodos y líneas.
	 * @param g - Recibe el componente graphics g.
	 * @param x - Coordenada en x.
	 * @param y - Coordenada en y.
	 * @param nodo - Nodo a pintar.
	 */
	private void pintar(Graphics g, int x, int y, NodoAVL<E> nodo) {
		if(nodo != null) {
			int anchoExtra = this.arbol.nodosCompletos(nodo)*(this.ancho/2);
			g.setColor(Color.GREEN);
			g.fillOval(x, y, this.diametro, this.diametro);
			g.setColor(Color.BLACK);
			g.setFont(new Font( "Arial", Font.BOLD, 14 ) );
			g.drawString(nodo.valor.toString(), x+8, y+18);
			g.setColor(Color.GREEN);
			if(nodo.left != null)
				g.drawLine(x+radio, y+radio, x-ancho-anchoExtra+this.radio, y+ancho+this.radio);
			if(nodo.right != null)
				g.drawLine(x+radio, y+radio, x+ancho+anchoExtra+this.radio, y+ancho+this.radio);
			pintar(g, x-this.ancho-anchoExtra, y+this.ancho, nodo.left);
			pintar(g, x+this.ancho+anchoExtra, y+this.ancho, nodo.right);
		}
	}
}
