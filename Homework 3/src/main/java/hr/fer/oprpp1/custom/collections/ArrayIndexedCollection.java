package hr.fer.oprpp1.custom.collections;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 *This class is used as a resizable array-backed collection of objects.
 *
 */
public class ArrayIndexedCollection<T> implements List<T>{

	private int size;
	private Object[] elements;
	private long modificationCount = 0;
	
	/**
	 *Constructor with no argument with default capacity.
	 */
	public ArrayIndexedCollection() {
		//default capacity
		this.elements = new Object[16];
	}
	
	/**
	 *Constructor that initializes collection with given capacity.
	 *
	 *@param initialCapacity Given capacity.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) throw new IllegalArgumentException();
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 *Constructor that initializes collection with the size of the 
	 *collection and add all objects of the given collection.
	 *
	 *@param other Collection which will initialize this collection and 
	 *which elements will be added.
	 */
	public ArrayIndexedCollection(Collection<T> other) {
		this.elements = new Object[other.size()];
		addAll(other);
	}
	
	/**
	 *Constructor that initializes collection with given capacity and 
	 *adds all elements of given collection.
	 *
	 *@param initialCapacity Given capacity.
     *@param other Collection which objects will be added.
	 */
	public ArrayIndexedCollection(int initialCapacity, Collection<T> other) {
		if(other.isEmpty()) throw new NullPointerException();
		if(initialCapacity < 1) throw new IllegalArgumentException();
		
		if(initialCapacity < other.size()) this.elements = new Object[other.size()];
		else this.elements = new Object[initialCapacity];
		addAll(other);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void add(T value) {
		if(value == null) throw new NullPointerException();

		if(this.size == elements.length) 
			elements = Arrays.copyOf(elements, elements.length * 2);
		elements[this.size++] = value;
		modificationCount++;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();
		return (T) elements[index];
	}

	public void insert(T value, int position) {
		if(value == null) throw new NullPointerException();
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		
		if(this.size == elements.length) 
			elements = Arrays.copyOf(elements, elements.length * 2);
	
		for (int i = this.size -1 ; i > position; i--) elements[i] = elements[i-1];
		elements[position] = value;
		this.size++;
		modificationCount++;
	}

	public int indexOf(Object value) {
		if(value == null) return -1;	
		for (int i = 0; i < this.size; i++) {
			if(elements[i] ==  value) {
				return i;
			}
		}
		return -1;
	}
	
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();
		
		for (int i = index; i < this.size; i++) {
			elements[i] = elements[i+1];
		}
		
		elements[this.size - 1] = null;
		this.size--;
		modificationCount++;
	}
	
	@Override
	public boolean contains(Object value) {
		for(Object elem : elements) {
			if(elem.equals(value)) return true;
		}
		
		return false;
	}

	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < elements.length; i++) {
			if(elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] toArray() {
		return elements;
	}

	@Override
	public void clear() {	
		for (int i = 0; i < this.size; i++) elements[i] = null;
		this.size = 0;
		modificationCount++;
	}
	
	/**
	 *This private static class is used to as an elements getter.
	 */
	private static class AIC<T> implements ElementsGetter<T> {
		int index = 0;
		ArrayIndexedCollection<T> collection;
		long savedModificationCount;
		
		/**
		 *Constructor that has the reference to the collection and the modification count
		 *which is used to be sure that the collection has no new elements or less.
		 *
		 *@param collection Reference to the collection.
	     *@param other modificationCount modification number.
		 */
		public AIC(ArrayIndexedCollection<T> collection, long modificationCount) {
			this.collection = collection;
			this.savedModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
			if(collection.size > index) return true;
			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T getNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
			if(!hasNextElement()) throw new NoSuchElementException();
			return (T) collection.elements[index++];
		}
		
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new AIC<T>(this, modificationCount);
	}
	
}
