package engine;

import java.util.ArrayList;

import model.world.Champion;

public class PriorityQueue {

	@SuppressWarnings("rawtypes")
	private Comparable[] elements;
	private int nItems;
	private int maxSize;

	public PriorityQueue(int size) {
		maxSize = size;
		elements = new Comparable[maxSize];
		nItems = 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insert(Comparable item) {

		int i;
		for (i = nItems - 1; i >= 0 && item.compareTo(elements[i]) > 0; i--)
			elements[i + 1] = elements[i];

		elements[i + 1] = item;
		nItems++;
	}

	@SuppressWarnings("rawtypes")
	public Comparable remove() {
		nItems--;
		return elements[nItems];
	}

	public boolean isEmpty() {
		return (nItems == 0);
	}

	public boolean isFull() {
		return (nItems == maxSize);
	}

	@SuppressWarnings("rawtypes")
	public Comparable peekMin() {
		return elements[nItems - 1];
	}

	public int size() {
		return nItems;
	}
	public PriorityQueue getvirsual() {
		PriorityQueue virsualPriorityQueue=new PriorityQueue(6);
		PriorityQueue temp=new PriorityQueue(6);
		while(!this.isEmpty()) {
		   virsualPriorityQueue.insert(((Champion)this.peekMin()).getvirtualChampion());
			temp.insert(this.remove());
		}
		while(!temp.isEmpty())
			this.insert(temp.remove());
		return virsualPriorityQueue;
	}
	  public Champion getcrosChampion(String s,Player firstPlayer,Player secondPlayer) {
	    	for(Champion c:firstPlayer.getTeam())
	    		if(c.getName().equals(s))
	    			return c;
	    	for(Champion c:secondPlayer.getTeam())
	    		if(c.getName().equals(s))
	    			return c;
	    	return null;
	    }
	public PriorityQueue getvirsual(Player firstPlayer, Player secondPlayer) {
		PriorityQueue virsualPriorityQueue=new PriorityQueue(6);
		PriorityQueue temp=new PriorityQueue(6);
		while(!this.isEmpty()) {
		   virsualPriorityQueue.insert(getcrosChampion(((Champion)this.peekMin()).getName(),firstPlayer,secondPlayer));
			temp.insert(this.remove());
		}
		while(!temp.isEmpty())
			this.insert(temp.remove());
		return virsualPriorityQueue;
	}
	public ArrayList<String> allqueue(){
		ArrayList<String>allArrayList=new ArrayList<String>();
		
		for(Comparable c:elements)
			if(c!=null)
		        allArrayList.add(((Champion)c).getName());
		return allArrayList;
	} 
	public String toString() {
		String string="";
		ArrayList<String> arrayList=this.allqueue();
		for(String ss:arrayList)
			string+="   "+ss;
		return string;
	}
}
