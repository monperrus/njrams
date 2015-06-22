package njrams;
import java.util.List;

import njrams.interfaces.IToken;

/** the unmodified list of tokens */
public class FullNgram extends AbstractNgram {

  public FullNgram(List<IToken> data) {
    super(data);
  }

  @Override
  String computeRepresentation() {
    StringBuffer sb = new StringBuffer();
    for (IToken s : _data) {
      sb.append(s.getValue());
    }
    return sb.toString();
  }
  
  @Override
  public String toString() {
    return computeRepresentation();
  }

}
