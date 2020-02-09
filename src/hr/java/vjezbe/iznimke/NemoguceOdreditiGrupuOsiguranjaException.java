package hr.java.vjezbe.iznimke;
/**
 * Predstavlja oznaèenu iznimku nemoguæe odrediti grupu osiguranja koji je definiran naslovom, opisom i cijenom.
 * 
 * @author Matija Topolovec
 *
 */
public class NemoguceOdreditiGrupuOsiguranjaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2291535264076831445L;
	/**
	 * Ruèno napisana poruka iznimke koja se proslijeðuje konstruktoru nadklase.
	 * 
	 *
	 */
	public NemoguceOdreditiGrupuOsiguranjaException() {
		super("Previše kw, ne mogu odrediti grupu osiguranja.");
	}
	/**
	 * Poruka iznimke koja se proslijeðuje konstruktoru nadklase.
	 * 
	 * @param message String poruka.
	 *
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String message) {
		super(message);
	}
	/**
	 * Poruka i uzrok iznimke koji se proslijeðuju konstruktoru nadklase.
	 * 
	 * @param message String poruka.
	 * @param cause uzrok iznimke.
	 *
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Uzrok iznimke koji se proslijeðuje konstruktoru nadklase.
	 * @param cause uzrok iznimke.
	 *
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(Throwable cause) {
		super(cause);
	}
}
