package njrams;
import java.util.List;

import njrams.interfaces.IToken;

/** a list of token types, ect PAREN, ID, OPERATOR */
public class TokenTypeNgram extends AbstractNgram {

  public TokenTypeNgram(List<IToken> data) {
    super(data);
  }

  @Override
  String computeRepresentation() {
    StringBuffer sb = new StringBuffer();
    int index = 0;
    for (IToken s : _data) {
      sb.append(s.getType());
    }
    return sb.toString();
  }

}
