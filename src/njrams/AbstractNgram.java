package njrams;

import java.util.LinkedList;
import java.util.List;

import njrams.interfaces.INgram;
import njrams.interfaces.IToken;

public abstract class AbstractNgram implements INgram {

	final List<IToken> _data;
	final String repr;

	public AbstractNgram(List<IToken> data) {
		_data = new LinkedList<IToken>(data);
		repr = computeRepresentation();
		// System.err.println(repr);
	}

	abstract String computeRepresentation();

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return toString().equals(((INgram) obj).toString());
	}

	@Override
	public String toString() {
		return repr;
	}

	@Override
	public int getSize() {
		return _data.size();
	}

}
