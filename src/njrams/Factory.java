package njrams;

import java.util.List;

import njrams.interfaces.INgram;
import njrams.interfaces.INgramSet;
import njrams.interfaces.IToken;

public class Factory {

	private Factory() {
	}

	// returns an INgram based on a given implementation
	static INgram createNgram(List<IToken> data,
			Class<? extends INgram> ngramImpl) {
		try {
			return ngramImpl.getConstructor(new Class[] { List.class })
					.newInstance(new Object[] { data });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static IToken createToken(int type, String value, Class<? extends IToken> tokenImpl) {
		try {
			return tokenImpl.getConstructor(
					new Class[] { int.class, String.class }).newInstance(
					new Object[] { type, value });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
