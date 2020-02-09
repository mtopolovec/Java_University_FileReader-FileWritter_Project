package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Predstavlja entitet artikla koji je definiran naslovom, opisom i cijenom.
 * 
 * @author Matija Topolovec
 *
 */
public abstract class Artikl extends Entitet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072856779337823701L;
	
	private String naslov;
	private String opis;
	private BigDecimal cijena;
	private Stanje stanje;
	/**
	 * Inicijalizira podatak o naslovu, opisu i cijeni.
	 * 
	 * @param naslov podatak o naslovu.
	 * @param opis podatak o opisu.
	 * @param cijena podatak o cijeni.
	 * @param stanje podatak o stanju.
	 *
	 */	
	public Artikl(Long id,String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id);
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
		this.stanje = stanje;
	}
	/**
	 * Dohvaæa parametar String naslov iz Artikl objekta
	 * 
	 * @return Vraæa String vrijednost naslova iz Artikl objekta.
	 * 
	 */
	public String getNaslov() {
		return naslov;
	}
	/**
	 * Postavlja parametar String naslov iz Artikl objekta
	 * 
	 * @param naslov String podatak o naslovu.
	 *
	 */
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	/**
	 * Dohvaæa parametar String opis iz Artikl objekta
	 * 
	 * @return Vraæa String vrijednost opisa iz Artikl objekta.
	 *
	 */
	public String getOpis() {
		return opis;
	}
	/**
	 * Postavlja parametar String opis iz Artikl objekta
	 * 
	 * @param opis String podatak o opisu.
	 *
	 */
	public void setOpis(String opis) {
		this.opis = opis;
	}
	/**
	 * Dohvaæa parametar BigDecimal cijena iz Artikl objekta
	 * 
	 * @return Vraæa BigDecimal vrijednost cijene iz Artikl objekta.
	 *
	 */
	public BigDecimal getCijena() {
		return cijena;
	}
	/**
	 * Postavlja parametar BigDecimal cijena iz Artikl objekta
	 * 
	 * @param cijena BigDecimal podatak o cijeni.
	 *
	 */
	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}
	/**
	 * Dohvaæa parametar stanje iz Artikl objekta.
	 * 
	 * @return stanje podatak o stanju.
	 *
	 */
	public Stanje getStanje() {
		return stanje;
	}
	/**
	 * Postavlja parametar stanje iz Artikl objekta.
	 * 
	 * @param stanje podatak o stanju.
	 *
	 */
	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cijena == null) ? 0 : cijena.hashCode());
		result = prime * result + ((naslov == null) ? 0 : naslov.hashCode());
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
		result = prime * result + ((stanje == null) ? 0 : stanje.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikl other = (Artikl) obj;
		if (cijena == null) {
			if (other.cijena != null)
				return false;
		} else if (!cijena.equals(other.cijena))
			return false;
		if (naslov == null) {
			if (other.naslov != null)
				return false;
		} else if (!naslov.equals(other.naslov))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		if (stanje != other.stanje)
			return false;
		return true;
	}
	/**
	 * Dohvaæa formatirani tekst za ispis artikla.
	 * 
	 * @return Vraæa String formatiranog teksta za ispis oglasa artikla.
	 *
	 */
	public abstract String tekstOglasa();
}
