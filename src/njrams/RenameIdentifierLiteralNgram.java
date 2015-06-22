package njrams;
import java.util.List;

import njrams.interfaces.IToken;

/** identifiers and literals are abstracted as ID$1,ID$2, etc */
public class RenameIdentifierLiteralNgram extends AbstractNgram {

  public RenameIdentifierLiteralNgram(List<IToken> data) {
    super(data);
  }

  @Override
  String computeRepresentation() {
    StringBuffer sb = new StringBuffer();
    int index = 0;
    for (IToken s : _data) {
      if (s.isText()) {  
        sb.append("ID$" + index);
      } else {
        sb.append(s.getValue());
      }
      index++;
    }
    return sb.toString();
  }

}
