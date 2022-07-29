package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 *This class is a Dictionary which stores pairs.
 *@param <K> K key.
 *@param <V> V value.
 *@param <T> T Collection type.
 */
public class Dictionary<K, V> {

	/**
	 *This is a private class used to create pairs.
	 *@param <K> K key.
	 *@param <V> V value.
	 */
	@SuppressWarnings("hiding")
	private class Pair<K, V> {
		K key;
		V value;
		 
		/**
		 *This constructor is used to initialize the key and value.
		 *@param key The key of the pair.
		 *@param value The value of the pair.
		 */
		private Pair(K key, V value) {
			if(key == null) throw new NullPointerException();
			this.key = key;
			this.value = value;
		}


		@Override
		public int hashCode() {
			return Objects.hash(key);
		}


		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if(this == obj) return true;
			if(obj == null || obj.getClass() != this.getClass()) return false;
			Pair<K, V> other = (Pair<K, V>) obj;
			return Objects.equals(key, other.key);
		}
		
		
	}
	
	private ArrayIndexedCollection<K> collection;
	
	/**
	 *This constructor is used to create a Dictionary storage.
	 */
	public Dictionary() {
		this.collection = new ArrayIndexedCollection<K>();
	}
	
	/**
	 *This method is used to check if Dictionary has pairs or not.
	 *@return True is Dictionary has elements, otherwise false.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 *This method is used to find out the number of pairs in Dictionary.
	 *@return Number of pairs.
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 *This method is used to remove all pairs in the Dictionary.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 *This method is used to insert pairs in the Dictionary.
	 *@param key Key of the pair.
	 *@param value Value of the pair.
	 *@return The replaced value.
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if(key == null) throw new NullPointerException();
		V val = (V) get(key);
		if(val == null) {
			collection.add((K) new Pair<K, V>(key, value));
		} else {
			collection.remove(new Pair<K, V>(key, val));
			collection.add((K) new Pair<K, V>(key, value));
		}
		return val;
	}
	
	/**
	 *This method is used to get the value of the key.
	 *@param key Key of the pair.
	 *@return The value of the key, otherwise null.
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		if(key == null) throw new NullPointerException();
		Object[] col =  collection.toArray();
		for(Object pair : col) {
			if(pair == null) return null;
			if(((Pair<K, V>) pair).key.equals(key)) return (V) ((Pair<K, V>) pair).value;
		}
		return null;
	}
	
	/**
	 *This method is used to remove a pair by the key.
	 *@param key Key of the pair.
	 *@return Removed value of the key, otherwise null.
	 */
	public V remove(K key) {
		if(key == null) throw new NullPointerException();
		V value = (V) get(key);
		if(collection.remove(new Pair<K, V>(key, value))) return value;
		return null;
	}
}
