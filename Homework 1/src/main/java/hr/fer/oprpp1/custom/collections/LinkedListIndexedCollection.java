package hr.fer.oprpp1.custom.collections;
/**
 *This class is a linked list-backed collection of objects.
 *
 */
public class LinkedListIndexedCollection extends Collection {

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
	}

	/**
	 *This method gets the object at the given index.
	 *
	 *@param index Index of the object.
	 *@return Object at the index.
	 */
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
	
	/**
	 *This method inserts elements at the given index. Objects at the given
	 *index and so on are shifted forward for one place.
	 *
	 *@param value Object that will be inserted.
	 *@param position Index where the object will be inserted.
	 */
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
	}
	
	/**
	 *This method searches for the index of the given object.
	 *
	 *@param value Object which index we are searching for.
	 *@return Index of the given object, -1 otherwise.
	 */
	public int indexOf(Object value) {
		if(value == null) return -1;
		ListNode iterator = first;
		for (int i = 0; i < this.size; i++) {
			if(iterator.value == value) return i;
			iterator = iterator.next;
		}
		return -1;
	}
	
	/**
	 *This method is used to remove an object from the given index.
	 *
	 *@param index Index where we want to remove an object.
	 */
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
	public void forEach(Processor processor) {
		ListNode iterator = first;
		while(iterator != null) {
			processor.process(iterator.value);
			iterator = iterator.next;
		}
	}

	@Override
	public void clear() {
		first = last = null;
		this.size = 0;
	}
	
	
}
