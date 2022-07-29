package hr.fer.oprpp1.custom.collections;
import java.util.Arrays;

/**
 *This class is used as a resizable array-backed collection of objects.
 *
 */
public class ArrayIndexedCollection extends Collection{

	private int size;
	private Object[] elements;
	
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
	public ArrayIndexedCollection(Collection other) {
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
	public ArrayIndexedCollection(int initialCapacity, Collection other) {
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
	public void add(Object value) {
		if(value == null) throw new NullPointerException();

		if(this.size == elements.length) 
			elements = Arrays.copyOf(elements, elements.length * 2);
		elements[this.size++] = value;
	}
	
	/**
	 *This method gets the object at the given index.
	 *
	 *@param index Index of the object.
	 *@return Object at the index.
	 */
	public Object get(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();
		return elements[index];
	}
	
	/**
	 *This method inserts objects at the given index. Objects at the given
	 *index and so on are shifted forward for one place.
	 *
	 *@param value Object that will be inserted.
	 *@param position Index where the object will be inserted.
	 */
	public void insert(Object value, int position) {
		if(value == null) throw new NullPointerException();
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		
		if(this.size == elements.length) 
			elements = Arrays.copyOf(elements, elements.length * 2);
	
		for (int i = this.size -1 ; i > position; i--) elements[i] = elements[i-1];
		elements[position] = value;
		this.size++;
	}

	/**
	 *This method searches for the index of the given object.
	 *
	 *@param value Object which index we are searching for.
	 *@return Index of the given object, -1 otherwise.
	 */
	public int indexOf(Object value) {
		if(value == null) return -1;	
		for (int i = 0; i < this.size; i++) {
			if(elements[i] == value) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 *This method is used to remove an object from the given index.
	 *
	 *@param index Index where we want to remove an object.
	 */
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException();
		
		for (int i = index; i < this.size; i++) {
			elements[i] = elements[i+1];
		}
		
		elements[this.size - 1] = null;
		this.size--;
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	@Override
	public boolean remove(Object value) {
		if(indexOf(value) == -1) {
			return false;
		} else {
			remove(indexOf(value));
			return true;
		}
	}

	@Override
	public Object[] toArray() {
		return elements;
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < this.size; i++) processor.process(elements[i]);
		
	}

	@Override
	public void clear() {	
		for (int i = 0; i < this.size; i++) elements[i] = null;
		this.size = 0;
	}
	
	
	
}
