package njrams;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import njrams.interfaces.INgram;
import njrams.interfaces.INgramSet;
import njrams.interfaces.IResult;

import org.apache.commons.lang.StringUtils;

public class Experiment {

	public static final Class[] knownNgramsKinds = new Class[] { FullNgram.class,
			RenameIdentifierNgram.class, RenameLiteralNgram.class,
			RenameIdentifierLiteralNgram.class, TokenTypeNgram.class,
			BinaryNgram.class };

	/** collects all n-grams and returns them */
	public INgramSet collectNgrams(File source, int n,
			Class<? extends INgram> ngramImpl) throws Exception {
		INgramSet result = new NgramSetListImpl();
		TokenRecIterator tri = new TokenRecIterator(source, n, ngramImpl);
		while (tri.hasNext()) {
			INgram ngram = tri.next();
			if (ngram.getSize() == n) {
				// System.out.println(ngram.toString());
				result.add(ngram);
			}
		}
		return result;
	}

	public IResult fullPass(File source, int n,
			Class<? extends INgram> ngramImpl) throws Exception {
		INgramSet set = collectNgrams(source, n, ngramImpl);
		return secondpass(source, n, set, ngramImpl);
	}

	/** computes statistics on Ngrams */
	public IResult secondpass(final File source, final int n,
			final INgramSet data, final Class<? extends INgram> ngramImpl)
			throws Exception {

		int nbTokens = 0;
		int nbAlreadySeen = 0;
		int nbRedundant = 0;

		for (INgram s : data) {
			int nbOccurrence = data.getNbOccurrenceOf(s);
			nbTokens += nbOccurrence;
			if (nbOccurrence > 1) {
				nbRedundant += nbOccurrence;
				nbAlreadySeen += 1;			}
		}
		final int _nbTokens = nbTokens;
		final int _nbExists1 = nbAlreadySeen;
		final int _nbExists2 = nbRedundant;
		return new IResult() {

			@Override
			public String forTokenizer() {
				return ngramImpl.getName();
			}

			@Override
			public String forData() {
				try {
					return source.getCanonicalPath();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public long nbNgrams() {
				return _nbTokens;
			}

			@Override
			public long nbUniqueNgrams() {
				return data.size();
			}

			@Override
			public long nbRedundantNgrams() {
				return _nbExists1;
			}

			@Override
			public long nbRedundantNgramOccurrences() {
				return _nbExists2;
			}

			@Override
			public String toString() {
				return "{\n" + "\"where\": " + "\"" + forData() + "\",\n"
						+ "\"forTokenizer\": " + "\"" + forTokenizer()
						+ "\",\n" + "\"nbNgrams\": " + "" + nbNgrams() + ",\n"
						+ "\"nbUniqueNgrams\": " + "" + nbUniqueNgrams()
						+ ",\n" + "\"nbRedundantNgrams\": " + "" + nbRedundantNgrams()
						+ ",\n" + "\"nbRedundantNgramOccurrences\": " + "" + nbRedundantNgramOccurrences()
						+ "\n" + "}";
			}

			@Override
			public String toNumericalVector() {
				return ngramSize()
						+ " "

						+ nbNgrams()
						+ " "
						+ nbUniqueNgrams()
						+ " "
						+ nbRedundantNgrams()
						+ " "
						+ nbRedundantNgramOccurrences()
						+ " "
						+ String.format("%2.2f ", (double)nbUniqueNgrams() / nbNgrams())
						+ " "
						+ String.format("%2.2f ", (double)nbRedundantNgramOccurrences()
								/ nbNgrams());

			}

			@Override
			public int ngramSize() {
				return n;
			}

		};
	}

	public void computeResult(String directoryToAnalyze, int ngramSize,
			PrintStream output) throws Exception {

		output.println("[");
		for (int i = 0; i < knownNgramsKinds.length; i++) {
			output.println(fullPass(new File(directoryToAnalyze), ngramSize,
					knownNgramsKinds[i]));
			if (i < knownNgramsKinds.length - 1) {
				output.println(",\n");
			}
		}
		output.println("]");

	}

	public void generateScilabFile(String directoryToAnalyze) throws Exception {
		String expName = new File(directoryToAnalyze.replace('.', '_'))
				.getName();
		// stats(new File(fName));
		int[] data = { 1, 3, 4, 6, 8, 10, 20, 30, 40, 50 };

		String scilabName = expName + ".sci";
		System.err.println("writing to " + scilabName);

		PrintStream scilab = new PrintStream(new File(scilabName));

		scilab.println("// this is generated Scilab code");
		scilab.println("function " + expName + "(col,thetitle)");
		scilab.println("x=" + Arrays.toString(data).toString() + ";");
		Class[] ngramsClass = { FullNgram.class, RenameIdentifierNgram.class,
				RenameLiteralNgram.class, RenameIdentifierLiteralNgram.class,
				TokenTypeNgram.class, BinaryNgram.class };
		for (Class klass : ngramsClass) {
			scilab.println("y" + klass.getSimpleName() + " = [");

			for (int i = 0; i < data.length; i++) {
				System.err.println(klass.getSimpleName() + " " + i);

				scilab.println(fullPass(new File(directoryToAnalyze), data[i],
						klass).toNumericalVector());
			}
			scilab.println("];");
		}

		scilab.println("clf();");
		scilab.print("plot(x,[");
		for (Class klass : ngramsClass) {
			scilab.print("y" + klass.getSimpleName() + "(:,col) ");
		}
		scilab.println("]);");

		List<String> ss = new ArrayList<String>();
		for (Class klass : ngramsClass) {
			ss.add("'" + klass.getSimpleName() + "'");
		}
		// axis=get("current_axes");
		// axis.x_label.text='Tokens (#)';
		// axis.y_label.text='Redundancy (%)';

		scilab.println("axis=get('current_axes');");
		scilab.println("axis.x_label.text='Tokens (#)';");
		scilab.println("axis.y_label.text=thetitle;");

		scilab.println("//lSize = length(axis.children(1).links);");
		scilab.println("//for yy=1:lSize");
		scilab.println("//axis.children(2).children(yy).mark_mode=\"on\";");
		scilab.println("//axis.children(2).children(yy).mark_style=yy;");
		scilab.println("//end");

		scilab.println("legend(" + StringUtils.join(ss, ",") + ");");
		scilab.println("xtitle('" + directoryToAnalyze + "');");

		scilab.println("xs2pdf(0,'uniqueness-" + expName + ".pdf');");
		scilab.println("endfunction // end function " + expName + "");

		scilab.println("scf(3)");
		scilab.println(expName + "(3,'N grams')");
		scilab.println("scf(5)");
		scilab.println(expName + "(5,'N redundant')");
		scilab.println("scf(7)");
		scilab.println(expName + "(7,'% redundance')");

		// 9000 literals
		// some of them are redundant
		// 2063 unique literals (-100 java tokens)
		// 721 of them are unique (oops)
	}
}
