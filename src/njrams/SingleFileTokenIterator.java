package njrams;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import njrams.interfaces.INgram;
import njrams.interfaces.IToken;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.IScanner;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.core.compiler.InvalidInputException;

/** iterates over the tokens of size n in a given file */
public class SingleFileTokenIterator implements Iterator<INgram> {
	String fText;
	IScanner scanner;
	File f;
	int currentTokenType;
	int nextTokenType;
	int ngrams;
	Class<? extends INgram> ngramImpl;

	public SingleFileTokenIterator(File f, int n,
			Class<? extends INgram> ngramImpl) throws Exception {
		this.f = f;

		if (f.isDirectory()) {
			throw new RuntimeException("oops,should be a regular file");
		}
		fText = FileUtils.readFileToString(f);
		scanner = ToolFactory.createScanner(false, false, false, false);
		scanner.setSource(fText.toCharArray());
		ngrams = n;
		this.ngramImpl = ngramImpl;
		getNextLowLevelToken();
	}

	@Override
	public boolean hasNext() {
		return nextTokenType != ITerminalSymbols.TokenNameEOF;
	}

	Stack<IToken> queue = new Stack<IToken>();

	static boolean isString(int tokenType) {
		return tokenType == ITerminalSymbols.TokenNameStringLiteral;
	}

	static boolean isLiteral(int tokenType) {
		return tokenType == ITerminalSymbols.TokenNameStringLiteral
				|| tokenType == ITerminalSymbols.TokenNameIntegerLiteral
				|| tokenType == ITerminalSymbols.TokenNameLongLiteral
				|| tokenType == ITerminalSymbols.TokenNameFloatingPointLiteral
				|| tokenType == ITerminalSymbols.TokenNameDoubleLiteral
				|| tokenType == ITerminalSymbols.TokenNameCharacterLiteral;
	}

	static boolean isText(int tokenType) {
		return isLiteral(tokenType)
				|| (tokenType == ITerminalSymbols.TokenNameIdentifier);
	}

	@Override
	public INgram next() {
		if (!hasNext()) {
			throw new RuntimeException();
		}

		currentTokenType = nextTokenType;
		int start = scanner.getCurrentTokenStartPosition();
		int end = scanner.getCurrentTokenEndPosition() + 1;
		// scanner.get

		// todo if this is a new line
		// reset the stack

		if (currentTokenType != ITerminalSymbols.TokenNameEOF) {
			String s;
			s = fText.substring(start, end);
			queue.add(Factory.createToken(currentTokenType, "#" + s,
					TokenImpl.class));
		}

		if (queue.size() > ngrams) { // e.G. if 2>1
			queue.remove(0);
		}

		getNextLowLevelToken();

		if (queue.size() == 0) {
			throw new RuntimeException();
		}

		return Factory.createNgram(new LinkedList<IToken>(queue), ngramImpl);

	}

	public void getNextLowLevelToken() {
		try {
			nextTokenType = scanner.getNextToken();
			while (!hasNext() && nextTokenType != ITerminalSymbols.TokenNameEOF) {
				nextTokenType = scanner.getNextToken();
			}
		} catch (InvalidInputException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
