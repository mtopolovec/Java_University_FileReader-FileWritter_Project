package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;
/**
 * Predstavlja suèelje vozilo koji je definiran naslovom, opisom i cijenom.
 * 
 * @author Matija Topolovec
 *
 */
public interface Vozilo {
	
	////////////////////////////////
	/*
	* Konstante
	*/
	////////////////////////////////
	
	static final BigDecimal FaktorPreracunaIzKonja_u_Kilovate = new BigDecimal("0.73549875");
	static final BigDecimal FaktorPreracunaIzKilovata_u_Konje = new BigDecimal("1.359621617");
	static final BigDecimal FaktorZaOsiguranje = new BigDecimal("1000");
	/**
	 * Izraèuna kilovate snage ovisno o konjskoj snazi samog vozila.
	 * 
	 * @param konjskeSnage BigDecimal ulazni podatak konjske snage vozila.
	 * @return Vraæa BigDecimal izraèunatog iznosa kilovata snage.
	 *
	 */
	default public BigDecimal izracunajKw(BigDecimal konjskeSnage) {
		BigDecimal kw = konjskeSnage.multiply(FaktorPreracunaIzKonja_u_Kilovate);
		return kw;
	}
	/**
	 * Izraèuna grupu osiguranja ovisno o konjskoj snazi vozila.
	 * <p>
	 * Ukoliko se ne može odrediti grupa osiguranja proslijeðuje iznimku dalje.
	 *
	 *@throws NemoguceOdreditiGrupuOsiguranjaException proslijeðuje iznimku dalje.
	 *@return Vraæa izraèunatu grupu osiguranja.
	 */
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException;
	/**
	 * Izraèuna cijenu osiguranja ovisno o grupi osiguranja.
	 * <p>
	 * Ukoliko se ne može odrediti cijena osiguranja proslijeðuje iznimku dalje.
	 *
	 *@throws NemoguceOdreditiGrupuOsiguranjaException proslijeðuje iznimku dalje.
	 *@return Vraæa cijenu osiguranja.
	 */
	default public BigDecimal izracunajCijenuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		
		BigDecimal izlaz = null;
		
		BigDecimal grupa = izracunajGrupuOsiguranja();
		
		if(grupa.intValue() > 0 && grupa.intValue() <= 5) {
			
			switch(grupa.intValue()) {
			case 1:
				izlaz = grupa.multiply(FaktorZaOsiguranje);
				break;
			case 2:
				izlaz = grupa.multiply(FaktorZaOsiguranje);
				break;
			case 3:
				izlaz = grupa.multiply(FaktorZaOsiguranje);
				break;
			case 4:
				izlaz = grupa.multiply(FaktorZaOsiguranje);
				break;
			case 5:
				izlaz = grupa.multiply(FaktorZaOsiguranje);
				break;
			default:
				throw new IllegalArgumentException();
			}
		} 
		return izlaz;
	}
	
}
