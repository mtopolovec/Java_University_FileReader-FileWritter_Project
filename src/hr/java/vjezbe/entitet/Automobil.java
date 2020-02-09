package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Predstavlja entitet automobila koji je definiran naslovom, opisom, cijenom i snagom(KS).
 * 
 * @author Matija Topolovec
 *
 */
public class Automobil extends Artikl implements Vozilo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2827591070587310672L;

	private static final Logger logger = LoggerFactory.getLogger(Automobil.class);

	private BigDecimal snagaKs;
	/**
	 * Inicijalizira podatak o naslovu, opisu, cijeni i snazi.
	 * 
	 * @param naslov	podatak o naslovu.
	 * @param opis 		podatak o opisu.
	 * @param cijena 	podatak o cijeni.
	 * @param snagaKs 	podatak o snazi izražen u mjernoj jedinici "konjska snaga".
	 * @param stanje 	podatak o stanju automobila.
	 *
	 */
	public Automobil(Long id, String naslov, String opis, BigDecimal cijena, BigDecimal snagaKs, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		this.snagaKs = snagaKs;
	}
	/**
	 * Dohvaæa parametar BigDecimal snagaKs iz Automobil objekta
	 * 
	 * @return Vraæa BigDecimal vrijednost snagaKs iz Automobil objekta.
	 * 
	 */
	public BigDecimal getSnagaKS() {
		return snagaKs;
	}
	/**
	 * Postavlja parametar BigDecimal snagaKs iz Automobil objekta
	 * 
	 * @param snagaKS BigDecimal podatak o cijeni.
	 * 
	 */
	public void setSnagaKS(BigDecimal snagaKS) {
		this.snagaKs = snagaKS;
	}
	/**
	 * Izraèunava grupu osiguranja prema snazi automobila ako ne može odrediti grupu baca iznimku.
	 * 
	 * @return Vraæa BigDecimal broj grupe od 1-5 ovisno o snazi automobila ili iznimku.
	 * 
	 */
	@Override
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		
		BigDecimal izlaz = null;

		if(snagaKs.compareTo(BigDecimal.valueOf(0)) > 0 && snagaKs.compareTo(BigDecimal.valueOf(85)) <=0) {
			izlaz = BigDecimal.valueOf(1);
		} else if(snagaKs.compareTo(BigDecimal.valueOf(85)) > 0 && snagaKs.compareTo(BigDecimal.valueOf(110)) <=0) {
				izlaz = BigDecimal.valueOf(2);
		} else if(snagaKs.compareTo(BigDecimal.valueOf(110)) > 0 && snagaKs.compareTo(BigDecimal.valueOf(150)) <=0) {
				izlaz = BigDecimal.valueOf(3);
		} else if(snagaKs.compareTo(BigDecimal.valueOf(150)) > 0 && snagaKs.compareTo(BigDecimal.valueOf(175)) <=0) {
				izlaz =BigDecimal.valueOf(4);
		} else if(snagaKs.compareTo(BigDecimal.valueOf(175)) > 0 && snagaKs.compareTo(BigDecimal.valueOf(240)) <=0) {
				izlaz = BigDecimal.valueOf(5);
		} else {
			throw new NemoguceOdreditiGrupuOsiguranjaException();
		}
		
		return izlaz;
	}
	/**
	 * Dohvaæa formatirani tekst za ispis teksta oglasa.
	 * <p>
	 * Ukoliko se ne može odrediti grupa osiguranja a s time niti cijena osiguranja.
	 * Ovdje se ispisuje korisniku poruka i u log cijeli zapis gdje je nastao problem sa odreðivanjem grupe osiguranja.
	 * 
	 * @return Vraæa String formatiranog teksta za ispis oglasa artikla. 
	 * 
	 *
	 */
	@Override
	public String tekstOglasa() {
		BigDecimal izracunOsiguranja = null;
		String tekst = null;
		try {
			izracunOsiguranja = izracunajCijenuOsiguranja();
			tekst = "Naslov automobila: " + super.getNaslov() + "\nOpis automobila: " + super.getOpis() + "\nSnaga automobila: " + izracunajKw(snagaKs).setScale(0, RoundingMode.HALF_EVEN) + " (u kw)" + "\nStanje automobila: " + super.getStanje() + "\nIzraèun osiguranja automobila: " + izracunOsiguranja + " kn\nCijena automobila: " + super.getCijena() + " kn";
			
		} catch (NemoguceOdreditiGrupuOsiguranjaException ex1) {
			logger.error("Pogreška prilikom odreðivanja cijene osiguranja!", ex1);
			tekst = "Naslov automobila: " + super.getNaslov() + "\nOpis automobila: " + super.getOpis() + "\nSnaga automobila: " + izracunajKw(snagaKs).setScale(0, RoundingMode.HALF_EVEN) + " (u kw)" + "\nStanje automobila: " + super.getStanje() + "\nIzraèun osiguranja automobila: " + "Previše kw, ne mogu odrediti grupu osiguranja." + "\nCijena automobila: " + super.getCijena() + " kn";
		}
		return tekst;
	}
	
	

}
