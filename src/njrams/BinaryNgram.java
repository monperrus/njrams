package njrams;
import java.util.List;

import njrams.interfaces.IToken;

/** a suite of IDs or Java keywords */ 
public class BinaryNgram extends AbstractNgram {

  public BinaryNgram(List<IToken> data) {
    super(data);
  }

  @Override
  String computeRepresentation() {
    StringBuffer sb = new StringBuffer();
    int index = 0;
    for (IToken s : _data) {
      if (s.isText()) { 
        sb.append("ID$");
      } else {
        sb.append("JAVA$");
      }
      index++;
    }
    return sb.toString();
  }

}
