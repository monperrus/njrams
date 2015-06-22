package njrams;
import java.util.List;

import njrams.interfaces.IToken;

import org.eclipse.jdt.core.compiler.ITerminalSymbols;

/** identifiers are abstracted as ID$1,ID$2, etc */
public class RenameIdentifierNgram extends AbstractNgram {

  public RenameIdentifierNgram(List<IToken> data) {
    super(data);
  }

  @Override
  String computeRepresentation() {
    StringBuffer sb = new StringBuffer();
    int index = 0;
    for (IToken s : _data) {
              if (s.getType() == ITerminalSymbols.TokenNameIdentifier) {
                sb.append("ID$"+index);
              } else {
                sb.append(s.getValue());
              }
              index++;
    }
    return sb.toString();
  }

}
