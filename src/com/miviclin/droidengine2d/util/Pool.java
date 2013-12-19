package com.miviclin.droidengine2d.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Object Pool.
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <T>
 */
public class Pool<T> {

	private ArrayList<T> elements;

	/**
	 * Creates a new Pool with ititial capacity for 16 elements.
	 */
	public Pool() {
		this(16);
	}

	/**
	 * Creates a new Pool.
	 * 
	 * @param initialCapacity Initial capacity.
	 */
	public Pool(int initialCapacity) {
		this.elements = new ArrayList<T>(initialCapacity);
	}

	/**
	 * Retuns an element from this Pool or null if the Pool was empty.<br>
	 * The returned element is removed from this Pool.
	 * 
	 * @return an element from this Pool or null if the Pool was empty
	 */
	public T get() {
		if (elements.size() > 0) {
			return elements.remove(elements.size() - 1);
		}
		return null;
	}

	/**
	 * Adds the specified element to this Pool.
	 * 
	 * @param element Element to be added.
	 */
	public void put(T element) {
		if (element instanceof Poolable) {
			((Poolable) element).reset();
		}
		elements.add(element);
	}

	/**
	 * Adds the specified elements to this Pool.
	 * 
	 * @param elements Elements to be added.
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
	 * Removes all elements from this Pool, leaving it empty.
	 */
	public void clear() {
		elements.clear();
	}

	/**
	 * Returns the number of elements in this Pool.
	 * 
	 * @return Number of elements in this Pool
	 */
	public int size() {
		return elements.size();
	}

}
