package njrams.interfaces;

public interface IResult {
	/** the number of tokens in this data */
	int ngramSize();

	/** the number of ngrams in this data */
	long nbNgrams();

	/** for which data this result comes from */
	String forData();

	/** the number of unique ngrams */
	long nbUniqueNgrams();
	
	/** outputs a line-based vector for being given as input to e.g. Scilab */
	String toNumericalVector();

	/** the name of the tokenizer */
	String forTokenizer();

	/** the number of redundant ngrams */
	long nbRedundantNgrams();

	/** the number of redundant ngram occurences (sum of all occurences of redundant ngrams */
	long nbRedundantNgramOccurrences();

}
