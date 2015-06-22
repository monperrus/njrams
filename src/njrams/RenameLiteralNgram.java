package njrams;
import java.util.List;

import njrams.interfaces.IToken;

/** literals  are abstracted as LIT$1,LIT$2, etc */
public class RenameLiteralNgram extends AbstractNgram {

  public RenameLiteralNgram(List<IToken> data) {
    super(data);
  }

  @Override
  String computeRepresentation() {
    StringBuffer sb = new StringBuffer();
    int index = 0;
    for (IToken s : _data) {
      if (s.isLiteral()) { 
        sb.append("LIT$" + index);
      } else {
        sb.append(s.getValue());
      }
      index++;
    }
    return sb.toString();
  }

}
