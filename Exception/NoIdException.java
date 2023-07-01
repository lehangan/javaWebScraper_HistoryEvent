package Exception;

public class NoIdException extends Exception{
    private static final long serialVersionUID = 7811173100l;		// is decimal of "NoId"
	
	public NoIdException() {
		super("Not found ID property while constructing the entity");
	}	// close 
}
