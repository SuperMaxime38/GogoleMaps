package gps.utils;

public class Pair {
	
	Object key;
	Object value;
	public Pair(Object key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public Pair(Double key, Node value) {
		this.key = key;
		this.value = value;
	}

	public Object getKey() {
		return key;
	}
	
	public Object getValue() {
		return key;
	}
	
	public void clear() {
		this.key = null;
		this.value = null;
		System.gc();
	}
	
}
