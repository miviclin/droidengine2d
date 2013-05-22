package com.miviclin.droidengine2d.util;

import java.util.Arrays;

/**
 * Array es una estructura de datos cuya funcionalidad es similar a la funcionalidad basica del ArrayList
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <T>
 */
public class Array<T> {
	
	private Object[] objects;
	private int size;
	private int capacityIncrement;
	
	/**
	 * Crea un Array
	 */
	public Array() {
		this(16);
	}
	
	/**
	 * Crea un Array
	 * 
	 * @param initialCapacity capacidad inicial del Array
	 */
	public Array(int initialCapacity) {
		this(initialCapacity, 16);
	}
	
	/**
	 * Crea un Array
	 * 
	 * @param initialCapacity capacidad inicial del Array
	 * @param capacityIncrement Cuando el Array se llene, se incrementara su capacidad en el numero de elementos especificado en este
	 *            parametro
	 */
	public Array(int initialCapacity, int capacityIncrement) {
		this.objects = new Object[initialCapacity];
		this.size = 0;
		this.capacityIncrement = capacityIncrement;
	}
	
	/**
	 * Agrega un objeto al final del Array. Si el Array esta lleno, se redimensionara automaticamente.
	 * 
	 * @param object Objeto que se va a agregar al Array
	 */
	public void add(T object) {
		if (size == objects.length) {
			ensureCapacity(objects.length + capacityIncrement);
		}
		objects[size++] = object;
	}
	
	/**
	 * Agrega un objeto en la posicion especificada del Array. El elemento se inserta justo delante del elemento que hubiera inicialmente en
	 * la posicion especificada. Si el Array esta lleno, se redimensionara automaticamente.
	 * 
	 * @param index Indice en el que se insertara el objeto
	 * @param object Objeto que se va a agregar al Array
	 */
	public void add(int index, T object) {
		if (index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		if (size < objects.length) {
			System.arraycopy(objects, index, objects, index + 1, size - index);
		} else {
			Object[] aux = new Object[objects.length + capacityIncrement];
			System.arraycopy(objects, 0, aux, 0, index);
			System.arraycopy(objects, index, aux, index + 1, size - index);
			objects = aux;
		}
		objects[index] = object;
		size++;
	}
	
	/**
	 * Asegura que el Array tenga la capacidad suficiente como para almacenar el numero de elementos especificado
	 * 
	 * @param capacity Capacidad que se quiere garantizar que tenga el Array
	 */
	public void ensureCapacity(int capacity) {
		if (objects.length < capacity) {
			Object[] aux = new Object[capacity];
			System.arraycopy(objects, 0, aux, 0, size);
			objects = aux;
		}
	}
	
	/**
	 * Devuelve el objeto que se encuentra en la posicion especficada del Array.
	 * 
	 * @param index Posicion del Array cuyo elemento se quiere obtener
	 * @return Objeto almacenado en la posicion especificada o null si no habia ninguno
	 */
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		@SuppressWarnings("unchecked")
		T object = (T) objects[index];
		return object;
	}
	
	/**
	 * Elimina todos los elementos del Array, dejandolo vacio.
	 */
	public void clear() {
		if (size != 0) {
			Arrays.fill(objects, 0, size, null);
			size = 0;
		}
	}
	
	/**
	 * Comprueba si el objeto especificado se encuentra en el Array (utilizando el metodo equals(Object))
	 * 
	 * @param object Objeto que se quiere buscar
	 * @return true si el Array contiene al objeto especificado, false en caso contrario
	 */
	public boolean contains(Object object) {
		for (int i = 0; i < size; i++) {
			if (object.equals(objects[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Devuelve el indice en el que se encuentra el objeto especificado
	 * 
	 * @param object Objeto que se quiere buscar
	 * @return indice en el que se encuentra, o -1 si no se encuentra
	 */
	public int indexOf(Object object) {
		for (int i = 0; i < size; i++) {
			if (object.equals(objects[i])) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Elimina el elemento situado en el indice especificado del Array y lo devuelve
	 * 
	 * @param index Indice cuyo elemento se quiere eliminar
	 * @return Elemento eliminado
	 */
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		@SuppressWarnings("unchecked")
		T removedObject = (T) objects[index];
		System.arraycopy(objects, index + 1, objects, index, --size - index);
		objects[size] = null;
		return removedObject;
	}
	
	/**
	 * Elimina el objeto especificado del Array
	 * 
	 * @param object Objeto que se quiere eliminar
	 * @return true si se ha eliminado un objeto, false en caso contrario
	 */
	public boolean remove(Object object) {
		for (int i = 0; i < size; i++) {
			if (object.equals(objects[i])) {
				System.arraycopy(objects, i + 1, objects, i, --size - i);
				objects[size] = null;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Inserta el elemento especificado en la posicion especificada del Array. Sustituye el elemento que hubiera previamente en dicha
	 * posicion.
	 * 
	 * @param index Indice en el que se quiere insertar el objeto
	 * @param object Objeto que se va a insertar
	 * @return Objeto que hubiera en la posicion especificada del Array
	 */
	public T set(int index, T object) {
		if (index < 0 || index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		@SuppressWarnings("unchecked")
		T removedObject = (T) objects[index];
		objects[size] = object;
		return removedObject;
	}
	
	/**
	 * Devuelve el numero de elementos del Array
	 * 
	 * @return numero de elementos que contiene el Array
	 */
	public int size() {
		return size;
	}
	
}
