package com.miviclin.droidengine2d.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Pool de objetos.<br>
 * Permite reutilizar objetos previamente creados.
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <T>
 */
public class Pool<T> {
	
	private ArrayList<T> elements;
	
	/**
	 * Crea un nuevo Pool con una capacidad inicial por defecto de 16 elementos
	 */
	public Pool() {
		this(16);
	}
	
	/**
	 * Crea un nuevo Pool
	 * 
	 * @param initialCapacity Capacidad inicial
	 */
	public Pool(int initialCapacity) {
		this.elements = new ArrayList<T>(initialCapacity);
	}
	
	/**
	 * Devuelve un elemento almacenado o null en caso de que no hubiera elementos almacenados
	 * 
	 * @return Elemento almacenado o null si no hay elementos en el Pool
	 */
	public T get() {
		if (elements.size() > 0) {
			return elements.remove(elements.size() - 1);
		}
		return null;
	}
	
	/**
	 * Inserta un elemento en el Pool
	 * 
	 * @param element Elemento a insertar
	 */
	public void put(T element) {
		if (element instanceof Poolable) {
			((Poolable) element).reset();
		}
		elements.add(element);
	}
	
	/**
	 * Inserta una lista de elementos en el Pool
	 * 
	 * @param elements Elementos a insertar
	 */
	public void put(List<T> elements) {
		T element;
		for (int i = 0; i < elements.size(); i++) {
			element = elements.get(i);
			if (element instanceof Poolable) {
				((Poolable) element).reset();
			}
			this.elements.add(element);
		}
	}
	
	/**
	 * Elimina todos los elementos del ObjectPool
	 */
	public void clear() {
		elements.clear();
	}
	
	/**
	 * Devuelve el numero de elementos almacenados
	 * 
	 * @return Numero de elementos almacenados
	 */
	public int size() {
		return elements.size();
	}
	
}
