package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *This class is a which represents a Hash Map which stores elements with address scattering.
 *@param <K> Key.
 *@param <V> Value.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	
	/**
	 *This public static class is storing key value pairs in our {@link SimpleHashtable}
	 *where every pair has a reference to his next.

	 *@param <K> Key.
	 *@param <V> Value.
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		TableEntry<K, V> next;
		
		/**
		 *This constructor is initializing the key, and his next one of the pair.
		 *@param key Key of our pair.
		 *@param value value of our pair.
		 *@param next Next element after pair.
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if(key == null) throw new NullPointerException();
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 *This method is a getter for the key.
		 *@return Key.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 *This method is a getter for the value.
		 *@return Value.
		 */
		public V getValue() {
			return value;
		}

		/**
		 *This method is a getter for the value.
		 *@param value New value.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
	}
	
	private TableEntry<K, V>[] table;
	private int size;
	private int modificationCount;
	
	/**
	 *Default constructor.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		this.table = (TableEntry<K, V>[]) new TableEntry[16];
	}
	
	/**
	 *This constructor initializes the HashTable with the given capacity with the 
	 *potency of 2.
	 *@param capacity Capacity to initialize the HashTable.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) throw new IllegalArgumentException();
		
		capacity = (int) Math.pow(2, Math.ceil(Math.log(capacity)/Math.log(2)));
		this.table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}
	
	/**
	 *This method is used to put pairs in the HashTable by the key, if the key
	 *already exists it replaces the value.
	 *@param key Key.
	 *@param value Value.
	 *@return Replaced value, otherwise null.
	 */
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException();
		
		if(this.size >= 0.75 * table.length) doubleSize();
		
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> tbEntry = this.table[slot];
		
		if(tbEntry == null) {
			this.table[slot] = new TableEntry<>(key, value, null);
			this.size++;
			this.modificationCount++;
			return null;
		}
		
		while(tbEntry.next != null) {
			if(tbEntry.key.equals(key)) {
				V prethodna = tbEntry.value;
				tbEntry.value = value;
				return prethodna;
			}
			tbEntry = tbEntry.next;
		}
		//inace dodaj na kraj
		
		if(tbEntry.key.equals(key)) {
			V prethodna = tbEntry.value;
			tbEntry.value = value;
			return prethodna;
		}
		tbEntry.next = new TableEntry<>(key, value, null);
		this.modificationCount++;
		this.size++;
		return null;
	}
	
	/**
	 *This private method is used to double the size of the HashTable if 
	 *space is too small.
	 */
	@SuppressWarnings("unchecked")
	private void doubleSize() {
		TableEntry<K, V>[] tableInfo = table;
		table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		this.size = 0;
		
		for(int i = 0; i < tableInfo.length;i++) {
			TableEntry<K, V> elem = tableInfo[i];
			while(elem != null) {
				put(elem.key, elem.value);
				elem = elem.next;
			}
		}
			
	}
	
	/**
	 *This method is used to get the pair by the key and return its value.
	 *@param key Key.
	 *@return Value of the key, otherwise null.
	 */
	public V get(Object key) {
		if(key == null) return null;
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> tbEntry = this.table[slot];
		
		while(tbEntry != null) {
			if(tbEntry.key.equals(key)) return tbEntry.value;
			tbEntry = tbEntry.next;
		}
		return null;
	}
	
	/**
	 *This method is used to return the size of the HashTable.
	 *@return Size of the HashTable.
	 */
	public int size() {
		return size;
	}
	
	/**
	 *This method is used to check if the HashTable contains the given key.
	 *@param key Key.
	 *@return True if table contains key, otherwise false.
	 */
	public boolean containsKey(Object key) {
		if(key == null) return false;
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> tbEntry = this.table[slot];
		
		while(tbEntry != null) {
			if(tbEntry.key.equals(key)) return true;
			tbEntry = tbEntry.next;
		}
		return false;
	}
	
	/**
	 *This method is used to check if the HashTable contains the given value.
	 *@param value Value.
	 *@return True if table contains Value, otherwise false.
	 */
	public boolean containsValue(Object value) {
		for(TableEntry<K, V> tbEntry : table) {
			while(tbEntry != null) {
				if(tbEntry.value.equals(value)) return true;
				tbEntry = tbEntry.next;
			}
		}
		return false;
	}
	
	/**
	 *This method is used to remove the pair from the HashTable by the given key.
	 * @param key key.
	 * @return Removed value, otherwise null.
	 */
	public V remove(Object key) {
		if(key == null) return null;
		int slot = Math.abs(key.hashCode()) % table.length;
		TableEntry<K, V> tbEntry = this.table[slot];
		TableEntry<K, V> pomocna = null;
		
		while(tbEntry != null) {
			if(tbEntry.key.equals(key)) {
				this.size--;
				this.modificationCount++;
				if (pomocna != null) {
					pomocna.next = tbEntry.next;
                } else {
                    table[slot] = tbEntry.next;
                }
				return tbEntry.value;
			}
			pomocna = tbEntry;
			tbEntry = tbEntry.next;
		}
		return null;
	}

	/**
	 *This method is used to check if HashTable has elements or not.
	 *@return True is HashTable has elements, otherwise false.
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(this.size == 0) return "[null]";
		sb.append("[");
		for(TableEntry<K, V> tbEntry : table) {
			while(tbEntry != null) {
				sb.append(tbEntry.key);
				sb.append("=");
				sb.append(tbEntry.value);
				if(tbEntry != null) sb.append(", ");
				tbEntry = tbEntry.next;
			}
		}
		//makni zarez
		if (sb.length() > 1)  sb.setLength(sb.length() - 2);
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 *This method is used to transform the HashTable to an Array.
	 *@return TableEntry<K,V> array of all HashTable elements.
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K,V>[] polje = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, this.size);
		int i = 0;
		for(TableEntry<K, V> tbEntry : table) {
			while(tbEntry != null) {
				polje[i++] = tbEntry; 
				tbEntry = tbEntry.next;
			}
		}
		return polje;
		
	}
	
	/**
	 *This method is used to clear all elements from the HashTable.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		this.size = 0;
		table = (TableEntry<K, V>[]) new TableEntry[table.length];
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 *This private class is an Iterator for the HashTable and he is used
	 *itterate through all elements in the HashTable.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		private int savedModificationCount = SimpleHashtable.this.modificationCount;
		private int index = 0;
		private TableEntry<K, V> currentElement = null;
		private boolean flag = false;
		private int indexInTable = 0;
		
		public boolean hasNext() {
			if(savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			if(size > index) return true;
			return false;
		}
		
		public TableEntry<K, V> next() { 
			if(savedModificationCount != modificationCount) throw new ConcurrentModificationException();
			if(!hasNext()) throw new NoSuchElementException();
			
			flag = false;
			//1st or removed
			if(currentElement == null) {
				//provjeri svaki dio 
				for (int i = indexInTable; i < table.length; i++, indexInTable++) {
					currentElement = table[indexInTable];
					if(currentElement != null) break;
				}
				index++;
				return currentElement;
			}
			//iduci poziv
			if(currentElement.next != null) {
				currentElement = currentElement.next;
				index++;
				return currentElement;
			} else {
				// provjeravaj po slotovima
				currentElement = table[++indexInTable];
				if(currentElement == null) {
					for (int i = (indexInTable+1); i < table.length; i++, indexInTable++) {
						currentElement = table[indexInTable];
						if(currentElement != null) break;
					}
				}
				index++;
				return currentElement;
			}
		}
		
		public void remove() { 
			if(flag) throw new IllegalStateException();
			
			SimpleHashtable.this.remove(currentElement.key);
			currentElement = null;
			index--;
			savedModificationCount++;
			flag = true;
		}
	}
}
