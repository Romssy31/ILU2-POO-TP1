package villagegaulois;

public class VillageSansChefException extends Exception{
	
	//Faut il mettre autre chose que Exception ?
	
	public VillageSansChefException() {
	}
	public VillageSansChefException(String message){
		super(message);
	}
	public VillageSansChefException(Throwable cause) {
		super(cause);
	}
	public VillageSansChefException(String message, Throwable cause) {
		super(message,cause);
	}
}
