package com.miviclin.droidengine2d.util;

import java.util.Arrays;

public class Array<T> {
	
	private Object[] objects;
	private int size;
	private int capacityIncrement;
	
	public Array() {
		this(16);
	}
	
	public Array(int initialCapacity) {
		this(initialCapacity, 16);
	}
	
	public Array(int initialCapacity, int capacityIncrement) {
		this.objects = new Object[initialCapacity];
		this.size = 0;
		this.capacityIncrement = capacityIncrement;
	}
	
	public void add(T object) {
		if (size == objects.length) {
			ensureCapacity(objects.length + capacityIncrement);
		}
		objects[size++] = object;
	}
	
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
	
	public void ensureCapacity(int capacity) {
		if (objects.length < capacity) {
			Object[] aux = new Object[capacity];
			System.arraycopy(objects, 0, aux, 0, size);
			objects = aux;
		}
	}
	
	public T get(int index) {
		if (index < 0 || index >= size) {
        	throw new ArrayIndexOutOfBoundsException(index);
        }
		@SuppressWarnings("unchecked")
		T object = (T) objects[index];
		return object;
	}
	
	public void clear() {
		if (size != 0) {
			Arrays.fill(objects, 0, size, null);
			size = 0;
		}
	}
	
	public boolean contains(Object object) {
		for (int i = 0; i < size; i++) {
			if (object.equals(objects[i])) {
				return true;
			}
		}
		return false;
	}
	
	public int indexOf(Object object) {
		for (int i = 0; i < size; i++) {
			if (object.equals(objects[i])) {
				return i;
			}
		}
		return -1;
	}
	
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
	
	public T set(int index, T object) {
		if (index < 0 || index >= size) {
        	throw new ArrayIndexOutOfBoundsException(index);
        }
		@SuppressWarnings("unchecked")
		T removedObject = (T) objects[index];
		objects[size] = object;
        return removedObject;
	}
	
	public int size() {
		return size;
	}
	
	
	public int getCapacityIncrement() {
		return capacityIncrement;
	}
	
	
	public void setCapacityIncrement(int capacityIncrement) {
		this.capacityIncrement = capacityIncrement;
	}
	
}
