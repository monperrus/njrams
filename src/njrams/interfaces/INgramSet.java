package njrams.interfaces;

public interface INgramSet extends Iterable<INgram> {
	/** adds a ngram in the set */
	void add(INgram ng);

	/** returns true iff the ngram has already been seen */
	boolean contains(INgram ng);

	/** returns the total number of occurrences of this ngram */
	int getNbOccurrenceOf(INgram ng);

	/** returns the number if ngrams */
	int size();
}
