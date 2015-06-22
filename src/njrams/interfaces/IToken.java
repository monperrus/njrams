package njrams.interfaces;

public interface IToken {
	/** the value of the token, e.g. "{" */
	public String getValue();

	/** the type the token, from JDT  */
	public int getType();

	/** returns true if the token is a literal or an identifier (e.g. a class name */
	public boolean isText();

	/** returns true if the token is a literal */
	public boolean isLiteral();
}
