package njrams;

import java.util.Iterator;

import javax.naming.OperationNotSupportedException;

import njrams.interfaces.INgram;
import njrams.interfaces.INgramSet;

/** optimized version of INgramSet based on the hash-code of INgram */
public class NgramSetHashImpl implements INgramSet {
	final static int SIZE = 2000000;// 100000 is far too small too many
									// collisions
	final int[] data = new int[SIZE];
	int size = 0;

	@Override
	public void add(INgram ng) {
		if (!contains(ng)) {
			size++;
		}
		data[indexOf(ng)]++;
	}

	public int indexOf(INgram ng) {
		return Math.abs(ng.hashCode()) % SIZE;
	}

	@Override
	public boolean contains(INgram ng) {
		return getNbOccurrenceOf(ng) != 0;
	}

	@Override
	public int getNbOccurrenceOf(INgram ng) {
		return data[indexOf(ng)];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<INgram> iterator() {
		// this version doesn't store the ngrams
		throw new UnsupportedOperationException();
	}



}
