package wiki.wisuggest;

import java.util.ArrayList;
import java.util.List;

public class HT {
	
	private Node[] table = new Node[16];
	private List<TFIDF> tfidfList = new ArrayList<TFIDF>();
	
	private final float THRESHOLD_VAL = 0.75f;
	private String link;
	private double similarity;
	private int threshold = 0;
	private int count = 0;
	private int size = 0;

	public int get(String k) {
		int h = k.hashCode();
		int i = h & (table.length - 1);

		for (Node p = table[i]; p != null; p = p.next) {
			if (k.equals(p.key))
				return p.val;

		}
		return 0;
	}

	public void addOne(String k) {
		int h = k.hashCode();
		int i = h & (table.length - 1);
		size++;
		
		for (Node p = table[i]; p != null; p = p.next) {
			if (k.equals(p.key)) {
				++p.val;
				return;
			}
		}
		
		Node a = new Node(k, 1, table[i]);
		table[i] = a;
		threshold = (int) (THRESHOLD_VAL * table.length);
		
		if (++count > threshold) {
			resize();
		}

	}

	private void resize() {
		Node[] newtable = new Node[table.length * 2];
		for (int i = 0; i < table.length; i++) {
			Node list = table[i];
			while (list != null) {
				Node next = list.next;
				int h = list.key.hashCode();
				int j = h & (newtable.length - 1);
				list.next = newtable[j];
				newtable[j] = list;
				list = next;
			}
		}
		table = newtable;
	}

	public List<TFIDF> getTfidfList() {
		return tfidfList;
	}

	public void setTfidfList(List<TFIDF> tf_idf) {
		this.tfidfList = tf_idf;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Node[] getTable() {
		return table;
	}

	public int getCount() {
		return count;
	}

	public int getSize() {
		return size;
	}

	static class Node {
		String key;
		int val;
		Node next;

		Node(String k, int v, Node n) {
			key = k;
			val = v;
			next = n;
		}
	}
}