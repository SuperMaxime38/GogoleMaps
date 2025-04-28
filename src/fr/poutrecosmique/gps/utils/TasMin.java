package fr.poutrecosmique.gps.utils;

import java.util.ArrayList;
import java.util.List;

public class TasMin {
	
	public List<Pair> tas;
	
	public TasMin() {
		tas = new ArrayList<Pair>();
	}
	
	public void add(Pair d) {
		tas.add(d);
		
		int index = tas.size() - 1;
		boolean isSorted = false;
		
		if(tas.size() == 1) isSorted = true;
		
		while(!isSorted) {
			if((double) (this.tas.get((index-1)/2).a) < (double) d.a) {
				double root = (double) this.tas.get((index-1)/2).a;
				double child = (double) this.tas.get(index).a;
				
				this.tas.set((index-1)/2, new Pair(child, this.tas.get(index).b));
				this.tas.set(index, new Pair(root, this.tas.get((index-1)/2).b));
				
				index = (index-1)/2;
				
				if(index <= 0) {
					break;
				}
			} else {
				isSorted = true;
			}
		}
	}
	
	public Pair pop() {
		Pair elt = this.tas.get(0);
		this.tas.set(0, this.tas.get(this.tas.size() - 1));
		this.tas.remove(this.tas.size() - 1);
		
		int index = 0;
		boolean isSorted = false;
		
		while(!isSorted) {
			if(this.tas.size() == 1) isSorted = true;
			
			if(this.tas.size() <= 2*index+1) {
					isSorted = true;
			} else if(this.tas.size() <= 2*index+2) {

				if((double) (this.tas.get(index).a) > (double) this.tas.get(2*index+1).a) {
					double root = (double) this.tas.get(index).a;
					double child = (double) this.tas.get(2*index+1).a;
					
					this.tas.set(index, new Pair(child, this.tas.get(2*index+1).b));
					this.tas.set(2*index+1, new Pair(root, this.tas.get(index).b));
					
					index = 2*index+1;
				} else {
					double root = (double) this.tas.get(index).a;
					double child = (double) this.tas.get(2*index+2).a;
					
					this.tas.set(index, new Pair(child, this.tas.get(2*index+2).b));
					this.tas.set(2*index+2, new Pair(root, this.tas.get(index).b));
					
					index = 2*index+2;
				}
			} else {
				isSorted = true;
			}
		}
		
		return elt;
	}
}
