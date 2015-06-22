package njrams;

import java.io.File;

import njrams.interfaces.INgram;

public class Main1 {
	static void usage() {
		System.err.println(Main1.class.getCanonicalName() +" outputs the n-grams of a given size and type");
		System.err.println("java " + Main1.class.getCanonicalName()
				+ " <source dir> <ngram size> <tokenizer type>");
		System.err.print("<tokenizer type>: ");
		for (Class klass : Experiment.knownNgramsKinds) {
			System.err.print(klass.getCanonicalName()+" ");
		}
		System.err.println();
		System.err.println("Example: ");
		System.err.println("java " + Main1.class.getCanonicalName()
				+ " src 3 "+FullNgram.class.getCanonicalName());
		System.err.println();
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			usage();
			System.exit(-1);
		}

		Class<? extends INgram> ngramImpl = (Class<? extends INgram>) Class
				.forName(args[2]);
		int ngramSize = Integer.parseInt(args[1]);
		TokenRecIterator tri = new TokenRecIterator(new File(args[0]),
				ngramSize, ngramImpl);
		while (tri.hasNext()) {
			INgram ngram = tri.next();
			if (ngram.getSize() == ngramSize) {
				System.out.println(ngram.toString());
			}
		}
	}

}
