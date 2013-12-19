package com.miviclin.droidengine2d.util;

import java.util.Arrays;

/**
 * Array is a data structure similar to {@link java.util.ArrayList}.<br>
 * The main difference is that it is possible to specify the way it is resized.
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
	 * Creates a new Array.
	 */
	public Array() {
		this(16);
	}

	/**
	 * Creates a new Array.
	 * 
	 * @param initialCapacity Initial capacity.
	 */
	public Array(int initialCapacity) {
		this(initialCapacity, 16);
	}

	/**
	 * Creates a new Array.
	 * 
	 * @param initialCapacity Initial capacity.
	 * @param capacityIncrement When the Array is full, it will resized incrementing its capacity using this value.
	 */
	public Array(int initialCapacity, int capacityIncrement) {
		this.objects = new Object[initialCapacity];
		this.size = 0;
		this.capacityIncrement = capacityIncrement;
	}

	/**
	 * Appends the specified object to the end of this Array.
	 * 
	 * @param object Object to be added.
	 */
	public void add(T object) {
		if (size == objects.length) {
			ensureCapacity(objects.length + capacityIncrement);
		}
		objects[size++] = object;
	}

	/**
	 * Inserts the specified object at the specified position in this Array. Shifts the element currently at that
	 * position (if any) and any subsequent elements to the right (adds one to their indices).
	 * 
	 * @param index Position.
	 * @param object Object to be added.
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
	 * Increases the capacity of this Array instance, if necessary, to ensure that it can hold at least the number of
	 * elements specified by the capacity argument.
	 * 
	 * @param capacity Minimum capacity.
	 */
	public void ensureCapacity(int capacity) {
		if (objects.length < capacity) {
			Object[] aux = new Object[capacity];
			System.arraycopy(objects, 0, aux, 0, size);
			objects = aux;
		}
	}

	/**
	 * Returns the object at the specified position of this Array
	 * 
	 * @param index Position.
	 * @return Object at the specified position or null.
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
	 * Removes all of the elements from this Array. The Array will be empty after this call returns.
	 */
	public void clear() {
		if (size != 0) {
			Arrays.fill(objects, 0, size, null);
			size = 0;
		}
	}

	/**
	 * Returns true if this list contains the specified object.
	 * 
	 * @param object Object to be compared with the objects in the Array.
	 * @return true if this Array contains the specified object, false otherwise.
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
	 * Returns the index of the first occurrence of the specified objact in this Array.
	 * 
	 * @param object Object.
	 * @return index or -1 if not found
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
	 * Removes the object at the specified position in this Array.
	 * 
	 * @param index Index of the object to be removed.
	 * @return Removed element or null
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
	 * Removes the first occurrence of the specified object from this Array.
	 * 
	 * @param object Object to remove.
	 * @return true if the objact was removed, false otherwise
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
	 * Replaces the object at the specified position in this Array with the specified object.
	 * 
	 * @param index Index of the object to replace.
	 * @param object Object to be stored at the specified position.
	 * @return object previously at the specified position
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
	 * Returns the number of objects in this Array
	 * 
	 * @return number of objects in this Array
	 */
	public int size() {
		return size;
	}

}
