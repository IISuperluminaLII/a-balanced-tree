package src.edu.iastate.cs228.hw5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Shashank Murthy
 */
public class ABTreeMap<K extends Comparable<? super K>, V> {

	
	private ABTreeSet<Entry> entrySet;
	private boolean isSelfBalancing;
	
	
	
	final class Entry implements Comparable<Entry>, Map.Entry<K, V> {
		private final K key; // immutable
		private V value; // mutable

		Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int compareTo(Entry o) {
			
			K key = o.getKey();
			V value = o.getValue();
			
			if(key.compareTo(this.key) < 0 && value.hashCode() < this.value.hashCode()){
				return -1;
			}
			else if(key.compareTo(this.key) > 0 && value.hashCode() > this.value.hashCode()){
				return 1;
			}
			return 0;
			
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V newValue) {
			// TODO

			this.value = newValue;
			return value;
		}

	}



	/**
	 * Default constructor. Builds a map that uses a non-self-balancing tree.
	 */
	public ABTreeMap() {
		// TODO
		
		entrySet = new ABTreeSet<Entry>();

	}

	/**
	 * If isSelfBalancing is true, <br>
	 * builds a map that uses self-balancing tree with alpha = 2/3. <br>
	 * If isSelfBalancing is false, <br>
	 * builds a map that uses a non-self-balancing tree.
	 * 
	 * @param isSelfBalancing
	 */
	public ABTreeMap(boolean isSelfBalancing) {
		// TODO
		int top = 2;
		int bottom = 3;
		
		entrySet = new ABTreeSet<Entry>(isSelfBalancing,top,bottom);


	}

	/**
	 * If isSelfBalancing is true, <br>
	 * builds a map that uses a self-balancing tree with alpha = top/bottom.
	 * <br>
	 * If isSelfBalancing is false, <br>
	 * builds a map that uses a non-self-balancing tree.
	 * 
	 * @param isSelfBalancing
	 * @param top
	 * @param bottom
	 * @throws IllegalArgumentException
	 *             if (1/2 < alpha < 1) is false
	 */
	public ABTreeMap(boolean isSelfBalancing, int top, int bottom) {
		// TODO
		
		
		double alpha = top/bottom;
		
		if(!(1/2 < alpha && alpha < 1)){
			throw new IllegalArgumentException("1/2 < alpha < 1 is false");
		}
		
		entrySet = new ABTreeSet<Entry>(isSelfBalancing,top,bottom);
		
		

	}

	/**
	 * Returns true if this map contains a mapping for key; <br>
	 * otherwise, it returns false.
	 * 
	 * @param key
	 * @return true if this map contains a mapping for key; <br>
	 *         otherwise, it returns false.
	 */
	public boolean containsKey(K key) {
		// TODO
		
		
		Iterator<Entry> keyItr = this.entrySet.iterator();
		while(keyItr.hasNext()){
			Entry baseEntry = keyItr.next();
			if(key.compareTo(baseEntry.key) == 0){
				return true;
			}
			
		}
		
		
		
		

		return false;
	}

	/**
	 * Returns the value to which key is mapped, <br>
	 * or null if this map contains no mapping for key.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		// TODO
		
		
		Iterator<Entry> keyItr = this.entrySet.iterator();
		while(keyItr.hasNext()){
			Entry baseEntry = keyItr.next();
			if(key.compareTo(baseEntry.key) == 0){
				return baseEntry.value;
			}
			
		}
		return null;
	}

	/**
	 * Returns a ABTreeSet storing the keys (not the values)
	 * 
	 * Example. Suppose this map consists of the following (key, value) pairs:
	 * (10, Carol), (21, Bill), (45, Carol), (81, Alice), (95, Bill). <br>
	 * Then, the ABTreeSet returned should consist of 10, 21, 45, 81, 91.
	 * <p>
	 * The keySet should have the same tree structure as entrySet.
	 * 
	 * @return a ABTreeSet storing the keys (not the values)
	 */
	public ABTreeSet<K> keySet() {
		// TODO
		ABTreeSet<K> toRet = new ABTreeSet<K>();
		
		Iterator<Entry> itr = this.entrySet.iterator();
		
		
		while(itr.hasNext()){
			K nextKey = itr.next().key;
			toRet.add(nextKey);
			
		}
		
		return toRet;
		
		
	}

	/**
	 * Associates value with key in this map. <br>
	 * Returns the previous value associated with key, <br>
	 * or null if there was no mapping for key.
	 * 
	 * @param key
	 * @param value
	 * @return the previous value associated with key, <br>
	 *         or null if there was no mapping for key.
	 * @throws NullPointerException
	 *             if key or value is null.
	 */
	public V put(K key, V value) throws NullPointerException {
		// TODO
		
		if(key == null || value == null){
			throw new NullPointerException("Key Value Pair null in Put:ABTreeMap");	
		}
		Iterator<Entry> keyFinder = this.entrySet.iterator();
		while(keyFinder.hasNext()){
			Entry nodeData = keyFinder.next();
			if(nodeData.key.compareTo(key) == 0){	
				entrySet.remove(nodeData);
				entrySet.add(new Entry(key, value));
				return nodeData.value;
			}
		}
		return null;
		
		
	}

	/**
	 * Removes the mapping for key from this map if it is present. <br>
	 * Returns the previous value associated with key, <br>
	 * or null if there was no mapping for key.
	 * 
	 * @param key
	 * @return the previous value associated with key, <br>
	 *         or null if there was no mapping for key.
	 */
	public V remove(K key) throws NoSuchElementException {
		// TODO
		
		Iterator<Entry> itr = this.entrySet.iterator();
		
		
		while(itr.hasNext()){
			Entry nextVal = itr.next();
			if(nextVal.key.compareTo(key) == 0){
				V ValroRet = nextVal.value;
				itr.remove();
				return ValroRet;
			}
			
		}
		throw new NoSuchElementException("Remove->ABTree MAP: No Such Value to Remove: "+key.toString());
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 * 
	 * @return the number of key-value mappings in this map.
	 */
	public int size() {
		// TODO
		return this.entrySet.BSTsize;
	}

	@Override
	public String toString() {
		return entrySet.toString();
	}

	/**
	 * Returns an ArrayList storing the values contained in this map. <br>
	 * Note that there may be duplicate values. <br>
	 * The ArrayList should contain the values in ascending order of keys.
	 * <p>
	 * Example. <br>
	 * Suppose this map consists of the following (key, value) pairs: <br>
	 * (10, Carol), (21, Bill), (45, Carol), (81, Alice), (95, Bill). <br>
	 * Then, the ArrayList returned should consist of the strings <br>
	 * Carol, Bill, Carol, Alice, Bill, in that order.
	 * 
	 * @return
	 */
	public ArrayList<V> values() {
		// TODO
		ArrayList<V> toRetVal = new ArrayList<V>();
		Iterator<Entry> itr = this.entrySet.iterator();
		
		while(itr.hasNext()){
			Entry Value = itr.next();
			toRetVal.add(Value.value);
		}
	
		return toRetVal;
	}
}
