package njrams;

import njrams.interfaces.IToken;

/** encapsulates a JDT token */
public class TokenImpl implements IToken {
  final int type;
  final String value;

  public TokenImpl(int type, String value) {
    this.type = type;
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public int getType() {
    return type;
  }

  @Override
  public boolean isText() {
   return SingleFileTokenIterator.isText(type);
  }

  @Override
  public boolean isLiteral() {
    return SingleFileTokenIterator.isLiteral(type);
  }

}
