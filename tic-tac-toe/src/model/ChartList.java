package model;

import java.io.Serializable;
import java.util.Arrays;


@SuppressWarnings("serial")
public class ChartList<T extends Comparable<T>> implements Serializable {
	private Object[] data;
	private int size;
	
	public ChartList(int capacity) {
		this.data = new Object[capacity];
		this.size = 0;
	}

	private void checkAccessBounds(int i) {
		if (i < 0 || i > this.capacity())
			throw new ArrayIndexOutOfBoundsException(i + " is not a valid index");
	}
	
	public void add(T item) {
		for (int i = 0; i < this.capacity(); i++) {
			T current = this.get(i);
			int newSize=0;
			if (current == null) {
				this.data[i] = item;
				size++;
				return;			
			} else {
				if (item.compareTo(current) < 0) { 
					// item should be placed here...
					
					// starting from the end, move each element one position down
					for (int j=this.capacity()-1; j>i;j--) {
						data[j]=data[j-1];						
						if (data[j]!=null && newSize==0) { // update newSize if it was not updated....
							newSize=j+1;
						}
					}
					//put the item in the i position;
					data[i]=item;
					size= newSize;
					return;
				}
			}		
		}
		// System.out.println("Item not added");
	}	
	
	@SuppressWarnings("unchecked")
	public T get(int i) {
		checkAccessBounds(i);
		return (T) data[i];
	}

	public int size() {
		return size;
	}
	
	public int capacity() {
		return data.length;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}

	public void clear() {
		this.data = new Object[data.length];
	}
	
	public T remove(int i) {
		checkAccessBounds(i);
		T dead = this.get(i);
		for (int j = i; j < this.size; j++) {
			if (j+1 < this.capacity()) {
				data[j]= data[j+1];
			}else {
				data[j]=null;
			}	
		}
		size--;
		return dead;
	}
	
	public void addAll(T[] chart) {			
		for (int i = 0; i < chart.length; i++) {
			if (chart[i]!=null)
			this.add(chart[i]);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T[] toArray(T[] type) {
		return (T[]) Arrays.copyOf(this.data, size, type.getClass());
	}
}