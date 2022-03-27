package Proyecto3;

import java.util.NoSuchElementException;
import java.util.Stack;

import pilasyQueus.QueueLE;


/**
 * @author Ingrid Shaiany Chan Topete A01227694
 * @author Daniel Díaz López A01636706
 * Esta clase implementa un árbol AVL.
 * @param <E> - Tipo parametrizada
 * @version 31 de julio de 2020
 */
public class AVLtree<E extends Comparable<E>> {
	private NodoAVL<E> root;
	private int size;

	/**
	 * Constructor default que inicializa el árbol en null y size en 0.
	 */
	public AVLtree() {
		this.root = null;
		this.size = 0;
	}
	
	/**
	 * Getter de raíz.
	 * @return - Regresa la raíz
	 */
	public NodoAVL<E> getRoot(){
		return this.root;
	}

	/**
	 * Método que inserta un elemento. En caso de haber un desbalaceo
	 * hace uno de los cuatro tipos de rotaciones.
	 * @param dato - Elemento a insertar.
	 */
	public void insertar(E dato) {
		Stack<NodoAVL<E>> stack=new Stack<>();
		NodoAVL<E> nvo=new NodoAVL<>(dato);
		if(this.root==null) {
			this.root=nvo;
		}else {
			NodoAVL<E> current=this.root;
			NodoAVL<E> padre=null;
			while(current!=null) {
				padre=current;
				stack.push(current);
				if(dato.equals(current.valor)) {
					return;
				}
				if(dato.compareTo(current.valor)<0) {
					current=current.left;
				}else {
					current=current.right;
				}
			}
			//Prev apunta a quien serÃ¡ el padre de nvo y el stack esta lleno con el searchpath
			if(dato.compareTo(padre.valor)<0) {
				padre.left=nvo;
			}else {
				padre.right=nvo;
			}

			this.size++;
			//NEXT es el siguiente en el stack, mientras que PREV es el anterior.
			NodoAVL<E> prev = nvo;
			NodoAVL<E> next =stack.pop();
			if(prev.valor.compareTo(next.valor)<0) {
				next.fb-=1;
			}else
				next.fb+=1;
			while(next != root && Math.abs(next.fb)<=1 && !stack.isEmpty() && next.fb !=0) {
				prev = next;
				next = stack.pop();
				if(prev.valor.compareTo(next.valor)<0) {
					next.fb-=1;
				}else
					next.fb+=1;
			}
			//Aquí ya actualizamos fb hasta que una de las tres condiciones de paro se cumpliï¿½.
			//NEXT quedó en el pivote y PREV es el anterior en el searchpath, que hay checar.
			if(Math.abs(next.fb)>1) {
				if(next.fb>1) {
					if(prev.fb>0) {
						next = rotLeft(next, true);
					}
					else
						next =rotRightLeft(next);
				}else {
					if(prev.fb<0)
						next =rotRight(next, true);
					else
						next =rotLeftRight(next);
				}
				if(stack.isEmpty()) {
					this.root = next;
				}else {
					NodoAVL<E> nextPadre = stack.pop();
					if(next.valor.compareTo(nextPadre.valor)<0)
						nextPadre.left = next;
					else
						nextPadre.right = next;
				}
			}
		}
	}

	/**
	 * Método que realiza una rotación simple a la derecha.
	 * @param piv - Nodo pivote.
	 * @param insertar - Booleano para diferenciar entre los dos casos
	 * de la actuzalización de los factores de balanceo.
	 * @return Regresa el nuevo nodo pivote.
	 */
	private NodoAVL<E> rotRight(NodoAVL<E>  piv, Boolean insertar) {
		NodoAVL<E> A=piv;
		NodoAVL<E> B=A.left;
		A.left=B.right;
		B.right=A;
		//Actualizamos fb
		if(insertar) {
			A.fb = 0;
			B.fb = 0;
		}else {
			A.fb = -1;
			B.fb = 1;
		}
		return B;

	}

	/**
	 * Método que realiza una rotación simple a la izquierda.
	 * @param piv - Nodo pivote.
	 * @param insertar - Booleano para diferenciar entre los dos casos
	 * de actualización de factores de balanceo.
	 * @return Regresa el nuevo nodo pivote.
	 */
	private NodoAVL<E> rotLeft(NodoAVL<E>  piv, Boolean insertar) {
		NodoAVL<E> A=piv;
		NodoAVL<E> B=A.right;
		A.right=B.left;
		B.left=A;
		//A=B;
		//Actualizamos fb
		if(insertar) {
			A.fb = 0;
			B.fb = 0;
		}else {
			A.fb = 1;
			B.fb = -1;
		}
		return B;
		
	}

	/**
	 * Método que realiza una rotación doble a la izquierda.
	 * @param piv - Nodo pivote.
	 * @return - Regresa el nuevo nodo pivote.
	 */
	private NodoAVL<E> rotRightLeft(NodoAVL<E>  piv ) {
		NodoAVL<E> B=piv;
		NodoAVL<E> A=B.right;
		NodoAVL<E> C=A.left;
		A.left=C.right;
		C.right=A;
		B.right=C.left;
		C.left=B;

		if(C.fb == 1) {
			A.fb = 0;
			B.fb = -1;
		}else if(C.fb == -1) {
			A.fb = 1;
			B.fb = 0;
		}else {
			//Caso cuando C no tiene hijos.
			A.fb = 0;
			B.fb = 0;
		}
		C.fb = 0;
		B=C;
		return B;
	}

	/**
	 * Método que realiza una rotación doble a la derecha.
	 * @param piv
	 * @return
	 */
	private NodoAVL<E> rotLeftRight(NodoAVL<E>  piv ) {
		NodoAVL<E> A=piv;
		NodoAVL<E> B=A.left;
		NodoAVL<E> C=B.right;
		B.right=C.left;
		C.left=B;
		A.left=C.right;
		C.right=A;

		if(C.fb == 1) {
			A.fb = 0;
			B.fb = -1;
		}else if(C.fb == -1) {
			A.fb = 1;
			B.fb = 0;
		}else {
			//Caso cuando C no tiene hijos.
			A.fb = 0;
			B.fb = 0;
		}
		C.fb = 0;
		A=C;
		return A;

	}


	/**
	 * Método que regresa el predecesor del nodo pasado como parámetro.
	 * @param current - Nodo
	 * @return - Nodo predecesor.
	 */
	private NodoAVL<E> predecesor(NodoAVL<E> current){
		NodoAVL<E> predecesor=current.left;
		while(predecesor.right!=null) {
			predecesor=predecesor.right;
		}
		return predecesor;
	}



	/**
	 * Método que usa un stack para guardar el search path por el que se movió hasta llegar al padre del nuevo
	 * nodo que borarremos.
	 * @param borrado - Dato a borrar.
	 * @return Regresa un stack con el searchpath recorrido.
	 */
	private Stack<NodoAVL<E>> stackPath(E borrado) {
		Stack<NodoAVL<E>> stackpath=new Stack<>();
		NodoAVL<E> parent=null,
				current=this.root;
		//Tener a parent sobre el nodo a borrar
		while(!current.valor.equals(borrado)) {
			parent = current;
			//cuando el valor no esta se arroja un nullPointerException
			stackpath.push(current);
			if(borrado.compareTo(current.valor)<0) {
				current=current.left;
			}else {
				current=current.right;
			}
		}
		return stackpath;
	}

	/**
	 * Método auxiliar que define el tipo de rotación a realizar sobre el nodo pivote.
	 * @param prev - Nodo pivote.
	 * @return - Regresa el nuevo nodo pivote.
	 */
	private NodoAVL<E> rotation(NodoAVL<E> prev) {
		if(Math.abs(prev.fb)>1) { //AGREGAR A UN METODO SOLO
			if(prev.fb>1) {
				if(prev.right.fb>=0)
					prev = rotLeft(prev, false);
				else
					prev  =rotRightLeft(prev);
			}else {
				if(prev.left.fb<=0)
					prev = rotRight(prev, false);
				else
					prev  =rotLeftRight(prev);
			}
		}
		return prev;
	}

	/**
	 *Método que remueve un elemento del árbol.
	 * @param valor - Elemento  borrar.
	 * @return - Regresa el valor del nodo eliminado.
	 * @throws NoSuchElementException cuando se intenta borrar de un árbol vacío.
	 * @throws NullPointerException cuando el elemento a borrar no se encuentra en el árbol.
	 */
	public E remove(E valor) {
		try {
			if(this.root==null) {
				//No necesita actualizar fb
				throw new NoSuchElementException("No se puede borrar de un arbol vacio");
			}else if(this.root.valor.equals(valor)){
				NodoAVL<E> current=this.root;
				if(root.left==null && root.right==null) {
					//No necesita actualizar fb
					this.root=null;
				}
				//un hijo derecho
				//No necesita actualizar fb
				else if(current.left==null) {
					this.root=current.right;
					//un hijo izquierdo
					//No necesita actualizar fb
				}else if(current.right==null) {
					this.root=current.left;
				}
				//dos hijos
				//Actualiza desde padre del predecesor, todo el camino que hace para obtenerlo
				else {
					E tmpPredecesor=predecesor(this.root).valor;
					Stack<NodoAVL<E>> stack1=stackPath(tmpPredecesor);
					current.valor=remove(tmpPredecesor);
					NodoAVL<E> tmp=null;
					NodoAVL<E> prev=stack1.pop();
					
					while(!stack1.isEmpty() && prev.fb !=-1 && prev.fb !=1) {
						tmp = prev;
						prev=stack1.pop();
						if(Math.abs(prev.fb)<= 1) {
							prev = rotation(prev);
							this.size++;
						}
					}
						
				}
				this.size--;
				return current.valor;
			}else {
				NodoAVL<E> parent=null,
						current=this.root;
				//Tener a parent sobre el nodo a borrar
				while(!current.valor.equals(valor)) {
					//cuando el valor no esta se arroja un nullPointerException
					parent=current;
					if(valor.compareTo(current.valor)<0) {
						current=current.left;
					}else {
						current=current.right;
					}
					//CASO NODO HOJA
				}E res=current.valor;
				if(current.left==null && current.right==null) {
					Stack<NodoAVL<E>> stack2=stackPath(current.valor);
					if(parent.left==current) {
						parent.left=null;
					}else {
						parent.right=null;
					}
					NodoAVL<E> tmp=null;
					NodoAVL<E> prev=stack2.pop();
					if(current.valor.compareTo(prev.valor)<0) {
						prev.fb+=1;
					}else {
						prev.fb-=1;
					}
					while(!stack2.isEmpty() && prev.fb !=-1 && prev.fb !=1) {
						tmp = prev;
						if(Math.abs(prev.fb)>1) {
							prev = rotation(prev);
							if(prev.valor.compareTo(stack2.peek().valor)<0)
								stack2.peek().left = prev;
							else
								stack2.peek().right = prev;
						}	
						prev=stack2.pop();
						if(tmp.valor.compareTo(prev.valor)<0) {
							prev.fb+=1;
						}else
							prev.fb-=1;
					}
					if(Math.abs(prev.fb)>1)
						prev = rotation(prev);
					
					if(stack2.isEmpty()) {
						this.root = prev;
					}else {
						NodoAVL<E> nextPadre = stack2.pop();
						if(prev.valor.compareTo(nextPadre.valor)<0)
							nextPadre.left = prev;
						else
							nextPadre.right = prev;
					}	
				}

				//NODO HIJO DERECHO 
				else if(current.left==null) {
					Stack<NodoAVL<E>> stack3=stackPath(current.valor);
					if(parent.left==current) {
						parent.left=current.right;
					}else {
						parent.right=current.right;
					}

					NodoAVL<E> tmp=null;
					NodoAVL<E> prev=stack3.pop();
					if(current.valor.compareTo(prev.valor)<0) {
						prev.fb+=1;
					}else {
						prev.fb-=1;
					}
					while(!stack3.isEmpty() && prev.fb !=-1 && prev.fb !=1) {
						tmp = prev;
						if(Math.abs(prev.fb)>1) {
							prev = rotation(prev);
							if(prev.valor.compareTo(stack3.peek().valor)<0)
								stack3.peek().left = prev;
							else
								stack3.peek().right = prev;
						}	
						prev=stack3.pop();
						if(tmp.valor.compareTo(prev.valor)<0) {
							prev.fb+=1;
						}else
							prev.fb-=1;
					}
					if(Math.abs(prev.fb)>1)
						prev = rotation(prev);
					
					if(stack3.isEmpty()) {
						this.root = prev;
					}else {
						NodoAVL<E> nextPadre = stack3.pop();
						if(prev.valor.compareTo(nextPadre.valor)<0)
							nextPadre.left = prev;
						else
							nextPadre.right = prev;
					}	
					//CASO HIJO IZQUIERDO
				}else if(current.right==null) {
					//al menos tiene un hijo, no tiene der
					//Tiene que comprobar al padre
					Stack<NodoAVL<E>> stack4=stackPath(current.valor);
					if(parent.left==current) {
						parent.left=current.left;
					}else {
						parent.right=current.left;
					}

					NodoAVL<E> tmp=null;
					NodoAVL<E> prev=stack4.pop();

					if(current.valor.compareTo(prev.valor)<0) {
						prev.fb+=1;
					}else {
						prev.fb-=1;
					}
					while(!stack4.isEmpty() && prev.fb !=-1 && prev.fb !=1) {
						tmp = prev;
						if(Math.abs(prev.fb)>1) {
							prev = rotation(prev);
							if(prev.valor.compareTo(stack4.peek().valor)<0)
								stack4.peek().left = prev;
							else
								stack4.peek().right = prev;
						}	
						prev=stack4.pop();
						if(tmp.valor.compareTo(prev.valor)<0) {
							prev.fb+=1;
						}else
							prev.fb-=1;
					}
					if(Math.abs(prev.fb)>1)
						prev = rotation(prev);
					
					if(stack4.isEmpty()) {
						this.root = prev;
					}else {
						NodoAVL<E> nextPadre = stack4.pop();
						if(prev.valor.compareTo(nextPadre.valor)<0)
							nextPadre.left = prev;
						else
							nextPadre.right = prev;
					}	
				}

				//caso cuando el nodo a borrar(current) tiene dos hijos
				else {
					//Técnica del predecesor
					E tmpPredecesor=predecesor(current).valor;
					Stack<NodoAVL<E>> stack5=stackPath(tmpPredecesor);
					current.valor=remove(tmpPredecesor);
					//para que no haya doble decremento
					this.size++;	
					NodoAVL<E> tmp=null;
					NodoAVL<E> prev=stack5.pop();
					

					while(!stack5.isEmpty() && prev.fb !=-1 && prev.fb !=1) {
						tmp = prev;
						prev=stack5.pop();
						if(Math.abs(prev.fb)<= 1) {
							prev = rotation(prev);
							this.size++;
						}	
					}
					
					if(stack5.isEmpty()) {
						this.root = prev;
					}else {
						NodoAVL<E> nextPadre = stack5.pop();
						if(prev.valor.compareTo(nextPadre.valor)<0)
							nextPadre.left = prev;
						else
							nextPadre.right = prev;
					}
				}
				this.size--;
				return res;
			}
		}catch(NullPointerException ex) {
			throw new NullPointerException();
		}
	}

	/**
	 * Método que imprime un AVL ordenado de arriba-abajo izquierda-derecha
	 */
	public void nivel() {
		QueueLE<NodoAVL<E>> fila=new QueueLE<> ();
		fila.enqueue(this.root);
		while(!fila.isEmpty()) {
			NodoAVL<E> current=fila.dequeue();
			if(current!=null) {
				System.out.print(current+",");
				if(current.left!=null) {
					fila.enqueue(current.left);
				}
				if(current.right!=null) {
					fila.enqueue(current.right);
				}
			}
		}
		System.out.println();
	}

	/**
	 * Método que elimina el árbol.
	 */
	public void flush() {
		this.root = null;
	}
	
	
	/**
	 * Método recursivo que calcula cuántos nodos tienen dos hijos.
	 * @param Nodo.
	 * @return - Entero.
	 */
	public int nodosCompletos(NodoAVL<E> nodo) {
		if(nodo == null)
			return 0;
		else if(nodo.left != null && nodo.right != null) {
			return nodosCompletos(nodo.left) + nodosCompletos(nodo.right) + 1;
		}
		return nodosCompletos(nodo.left) + nodosCompletos(nodo.right);
	}
	

	public static void main(String[] args) {
		AVLtree<Integer> hope = new AVLtree<>();

		hope.insertar(34);
		hope.insertar(40);
		hope.insertar(17);
		hope.insertar(15);
		hope.insertar(8);
		hope.insertar(41);
		hope.insertar(80);
		hope.insertar(18);
		hope.insertar(7);
		hope.insertar(16);
		hope.insertar(20);
		hope.insertar(39);
		hope.insertar(90);
		hope.insertar(83);
		hope.remove(34);
		hope.remove(41);
		hope.remove(40);
		//hope.remove(17);
		//hope.remove(15);
		/*hope.insertar(16);
		hope.nivel();
		hope.insertar(100);
		hope.nivel();
		System.out.println(hope.remove(29));
		hope.nivel();
		System.out.println(hope.remove(16));
		hope.nivel();
		/*System.out.println(hope.remove(16));
		hope.nivel();
		System.out.println(hope.remove(12));
		hope.nivel();
		System.out.println(hope.remove(29));
		hope.nivel();*/

	}

}

/**
 * @author Ingrid Shaiany Chan Topete A01227694
 * @author Daniel Díaz López A01636706 
 * Esta clase representa un nodo como elemento básico para un árbol AVL.
 * @param <E> - Tipo parametrizado.
 */
class NodoAVL<E>{
	int fb;
	NodoAVL<E> left,
	right;
	E valor;

	/**
	 * Constructor de un nodo sin hijos
	 * @param Valor que tendría el nodo
	 */
	public NodoAVL(E valor) {
		//llama al constructor 2
		this(valor, null, null);
	}

	/**
	 * Constructor de un elemento con hijos
	 * @param valor que tendría el nodo
	 * @param left es el nodo hijo menor a valor
	 * @param right es el nodo hijo mayor a valor
	 */
	public NodoAVL(E valor, NodoAVL<E> left, NodoAVL<E> right) {
		this.valor=valor;
		this.left=left;
		this.right=right;
		this.fb=0;
	}

	/**
	 * Getter del nodo hijo izquierdo
	 * @return nodo hijo izquierdo
	 */
	public NodoAVL<E> getLeft() {
		return left;
	}

	/**
	 * Setter del nodo hijo izquierdo
	 * @param left, el valor que se le asignarï¿½ al nodo hijo izquierdo
	 */
	public void setLeft(NodoAVL<E> left) {
		this.left = left;
	}
	/**
	 * Getter del nodo hijo derecho
	 * @return nodo hijo derecho
	 */
	public NodoAVL<E> getright() {
		return right;
	}
	/**
	 * Setter del nodo hijo derecho
	 * @param left, el valor que se le asignarï¿½ al nodo hijo derecho
	 */
	public void setright(NodoAVL<E> right) {
		this.right = right;
	}
	/**
	 * Getter el valor del nodo
	 * @return el valor del nodo
	 */
	public E getValor() {
		return valor;
	}
	/**
	 * Setter del valor del nodo
	 * @param valor, es el nuevo valor del nodo
	 */
	public void setValor(E valor) {
		this.valor = valor;
	}
	/**
	 * Método toString
	 */
	public String toString() {
		return this.valor.toString();
	}

}