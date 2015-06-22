package njrams;

import java.io.File;

import njrams.interfaces.INgram;

public class Main2 {
	static void usage() {
		System.err
				.println(Main2.class.getCanonicalName() + " outputs the main statistics of n-grams of a given size for all known n-gram types");
		System.err.println("java " + Main2.class.getCanonicalName()
				+ " <source dir> <ngram size>");
		System.err.println("Example: ");
		System.err.println("java " + Main2.class.getCanonicalName() + " src 5");
		System.err.println();
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			usage();
			System.exit(-1);
		}

		int ngramSize = Integer.parseInt(args[1]);
		new Experiment().computeResult(args[0], ngramSize,
				System.out);
	}

}
