package njrams;

import java.io.File;

import njrams.interfaces.INgram;

public class Main3 {
	static void usage() {
		System.err
				.println(Main3.class.getCanonicalName() + " generates a scilab file that itself creates a figure");
		System.err.println("The file summarizes the number of ngrams per size and type ");
		System.err.println("java " + Main3.class.getCanonicalName()
				+ " <source dir>");
		System.err.println("Example: ");
		System.err.println("java " + Main3.class.getCanonicalName() + " src");
		System.err.println();
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			usage();
			System.exit(-1);
		}

		new Experiment().generateScilabFile(args[0]);
	}

}
