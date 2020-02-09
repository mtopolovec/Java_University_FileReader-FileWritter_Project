package hr.java.vjezbe.iznimke;
/**
 * Predstavlja neoznaèenu iznimku cijena je preniska koji je definiran naslovom, opisom i cijenom.
 * 
 * @author Matija Topolovec
 *
 */
public class CijenaJePreniskaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2555341954833609776L;
	/**
	 * Ruèno napisana poruka iznimke koja se proslijeðuje konstruktoru nadklase.
	 * 
	 *
	 */
	public CijenaJePreniskaException() {
		super("Cijena ne smije biti manja od 10000 kn");
	}
	/**
	 * Poruka iznimke koja se proslijeðuje konstruktoru nadklase.
	 * 
	 * @param message String poruka.
	 *
	 */
	public CijenaJePreniskaException(String message) {
		super(message);
	}
	/**
	 * Poruka i uzrok iznimke koji se proslijeðuju konstruktoru nadklase.
	 * 
	 * @param message String poruka.
	 * @param cause uzrok iznimke.
	 *
	 */
	public CijenaJePreniskaException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Uzrok iznimke koji se proslijeðuje konstruktoru nadklase.
	 * @param cause uzrok iznimke.
	 *
	 */
	public CijenaJePreniskaException(Throwable cause) {
		super(cause);
	}
}
