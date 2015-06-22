package njrams;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import njrams.interfaces.INgram;
import njrams.interfaces.INgramSet;

/** naive implementation of INgramSet using a HashMap.
 * Uses the toString representations of Ngrams.
 **/
public class NgramSetListImpl implements INgramSet {

	final Map<INgram, Integer> differentTokens = new HashMap<INgram, Integer>();

	public NgramSetListImpl() {
	}

	@Override
	public void add(INgram token) {
		Integer nb = differentTokens.get(token);
		int val = 0;// default value
		if (nb != null) {
			val = nb.intValue();
		}
		val = val + 1;
		differentTokens.put(token, val);
	}

	@Override
	public boolean contains(INgram ng) {
		return differentTokens.containsKey(ng);
	}

	@Override
	public int getNbOccurrenceOf(INgram ng) {
		return differentTokens.get(ng);
	}

	@Override
	public int size() {
		return differentTokens.size();
	}

	@Override
	public Iterator<INgram> iterator() {
		return differentTokens.keySet().iterator();
	}

}
