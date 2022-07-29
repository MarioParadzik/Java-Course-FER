package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 *This class is a linked list-backed collection of objects.
 *
 */
public class LinkedListIndexedCollection implements List {

	/**
	 *This class is a list node.
	 *
	 */
	private static class ListNode {
		
		ListNode previous;
		ListNode next;
		Object value;
		
		/**
		 *Constructor that initializes the value of the node.
		 *
		 *@param value Value of the node.
		 */
		public ListNode(Object value) {
			this.value = value;
		}
		
		
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	private long modificationCount = 0;
	
	/**
     *Constructor with no argument.
     */
	public LinkedListIndexedCollection() {}
	
	/**
	 *Constructor that initializes collection and add all objects
	 *of the given collection.
	 *
	 *@param other Collection that will initialize this collection and 
	 *which elements will be added.
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void add(Object value) {
		if(value == null) throw new NullPointerException();
		
		if(first == null) {
			first = new ListNode(value);
			last = first;
		} else {
			last.next = new ListNode(value);
			last.next.previous = last;
			last = last.next;
		}
		this.size++;
		modificationCount++;
	}

	public Object get(int index) {
		if(index < 0 || index > this.size -1) throw new IndexOutOfBoundsException();
		// n/2+1 complexity
		if(index <= (this.size / 2)) {
			ListNode iterator = first;
			for (int i = 0; i < this.size; i++) {
				if(index == i) return iterator.value;
				iterator = first.next;
			}
		} else {
			ListNode iterator = last;
			for (int i = this.size -1; i >= 0 ; i--) {
				if(index == i) {
					return iterator.value;
				} else {
					iterator = last.previous;
				}
			}
		}
		//inace graska prilikom izvodjenja
		throw new RuntimeException();
	}
	
	public void insert(Object value, int position) {
		if(value == null)throw new NullPointerException();
		if(position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		
		if(first == null) {
			first = new ListNode(value);
			last = first;
		} 
		if (last == first){
			last = new ListNode(value);
			first.next = last;
			last.previous = first;
		} else {
			ListNode iterator = first;
			for (int i = 0; i < this.size; i++) {
				if(position == i) {
					ListNode insertNode = new ListNode(value);
					insertNode.previous = iterator.previous;
					
					if(insertNode.previous == null) {
						insertNode.next = iterator;
						insertNode.next.previous = insertNode;
					} else {
						insertNode.previous.next = insertNode;
					}
				}
				iterator = iterator.next;
			}
		}
		this.size++;
		modificationCount++;
	}
	
	public int indexOf(Object value) {
		if(value == null) return -1;
		ListNode iterator = first;
		for (int i = 0; i < this.size; i++) {
			if(iterator.value == value) return i;
			iterator = iterator.next;
		}
		return -1;
	}
	
	public void remove(int index) {
		if(index < 0 || index > this.size -1) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode iterator = last;
		if(index == this.size -1) {
			last = iterator.previous;
			iterator.previous.next = null;
		}
		
		iterator = first;
		if(index == 0) {
			first = iterator.next;
			iterator.next.previous = null;
		}
		
		for (int i = 1; i < this.size -1; i++) {
			if(index == i) {
				iterator.next.previous = iterator.previous;
				iterator.previous.next = iterator.next;
			}
			iterator = iterator.next;
		}
		this.size--;
		modificationCount++;
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
		Object[] polje = new Object[this.size];
		int i = 0;
		for (ListNode pom = first; pom != null; pom = pom.next) {
			polje[i++] = pom.value;
		}
		return polje;
	}

	@Override
	public void clear() {
		first = last = null;
		this.size = 0;
		modificationCount++;
	}

	/**
	 *This private static class is used to as an elements getter.
	 */
	private static class LLIC implements ElementsGetter {
		LinkedListIndexedCollection collection;
		ListNode iterator;
		long savedModificationCount;
		
		/**
		 *Constructor that has the reference to the collection and the modification count
		 *which is used to be sure that the collection has no new elements or less.
		 *
		 *@param collection Reference to the collection.
	     *@param other modificationCount modification number.
		 */
		public LLIC(LinkedListIndexedCollection collection, long modificationCount) {
			this.collection = collection;
			iterator = collection.first;
			this.savedModificationCount = modificationCount;
		}
	
		@Override
		public boolean hasNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
			if(iterator == null) return false;
			return true;
		}

		@Override
		public Object getNextElement() {
			if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
			if(!hasNextElement()) throw new NoSuchElementException();
			Object value = iterator.value;
			iterator = iterator.next;
			return value;
		}
		
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new LLIC(this, modificationCount);
	}
	
	
}
