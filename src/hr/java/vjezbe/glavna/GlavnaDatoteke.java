package hr.java.vjezbe.glavna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Predstavlja entitet glavnog programa u kojem se uèitavaju podaci iz datoteka.
 * 
 * @author Matija Topolovec
 *
 */

public class GlavnaDatoteke {

	////////////////////////////////
	/* 
	* Logger za log file
	*/
	////////////////////////////////
	
	private static final Logger logger = LoggerFactory.getLogger(GlavnaDatoteke.class);
	
	////////////////////////////////
	/*
	* Konstante
	*/
	////////////////////////////////
	
	static final String DATUM = "dd.MM.yyyy.";
	
	static final String FILENAMEKORISNICI = "dat/Korisnici.txt";
	static final String FILENAMEARTIKLI = "dat/Artikli.txt";
	static final String FILENAMEKATEGORIJE = "dat/Kategorije.txt";
	static final String FILENAMEPRODAJA = "dat/Prodaja.txt";
	
	static final String SERIALIZATIONFILENAME = "dat/serijalizacija.dat";
	
	static final Integer ARTIKLUSLUGA = 1;
	static final Integer ARTIKLAUTOMOBIL = 2;
	static final Integer ARTIKLSTAN = 3;
	
	static final Integer PRIVATNIKORISNIK = 1;
	static final Integer POSLOVNIKORISNIK = 2;
	
	////////////////////////////////
	/*
	* main metoda
	*/
	////////////////////////////////
	
	/**
	 * Glavna main metoda u kojoj se nalazi glavni programski kod izvršavanja programa.
	 * 
	 * @param args Argumenti sa konzole koji se mogu proslijediti u main.
	 * 
	 */
	
	public static void main(String[] args) {
		
		logger.info("Poèetak rada sa datotekama!");
		// Dohvati podatke
		List<Korisnik> korisnici = new ArrayList<>();
		dohvatiKorisnike(korisnici);
		
		List<Artikl> artikli = new ArrayList<>();
		dohvatiArtikle(artikli);
		
		List<Artikl> listaArtikala = artikli.stream().filter(a -> a.getClass() == Automobil.class).collect(Collectors.toList());
		List<Automobil> listaAutomobila = new ArrayList<>();
		
		for(Artikl a : listaArtikala) {
			listaAutomobila.add((Automobil) a);
		}
		
		List<Automobil> listaFiltriranihAutomobila = listaAutomobila.stream().filter(a->a.getNaslov().toLowerCase().startsWith("z") && a.getOpis().toLowerCase().endsWith("y")).collect(Collectors.toList());
		listaFiltriranihAutomobila.stream().forEach(a->System.out.println(a.getNaslov()));
		
		List<Kategorija<Artikl>> kategorije = new ArrayList<>();
		dohvatiKategorije(kategorije, artikli);
		
		List<Prodaja> aktivniOglasi = new ArrayList<>();
		dohvatiProdaju(aktivniOglasi, kategorije, korisnici);
		
		// Ispis
		try {
			//ispisArtikala(artikli);
			//ispisKorisnika(korisnici);
			ispisAktivnihOglasa(aktivniOglasi);
			ispisPoKategorijama(kategorije);
			serializirajProdaju(aktivniOglasi);
			
			serijalizirajAutomobile(listaFiltriranihAutomobila);
			procitajSerijaliziraneAutomobile();
			
			ispisAutomobila(listaAutomobila);
		}
		catch (NemoguceOdreditiGrupuOsiguranjaException ex1) {
			System.out.println("");
			logger.error("Pogreška prilikom odreðivanja cijene osiguranja!", ex1);
		}
		logger.info("Kraj rada sa datotekama!");
	}
	
	/**
	 * Unos prodaje tj odabir njezinog korisnika, kategorije i artikla iz datoteke.
	 * 
	 * @return Vraæa vrijednost stanja.
	 * 
	 */
	
	// Unos stanja
	private static Stanje unosStanja(Integer stanjeRedniBroj) {
		while (true) {
			if (stanjeRedniBroj >= 1 && stanjeRedniBroj <= Stanje.values().length) {
				return Stanje.values()[stanjeRedniBroj - 1];
			}
		}
	}
	/*
	// Ispis korisnika
	private static void ispisKorisnika(List<Korisnik> korisnici) {
		for(Korisnik k : korisnici) {
			if(k instanceof PrivatniKorisnik) {
				System.out.println(k.dohvatiKontakt());
			}
			else if(k instanceof PoslovniKorisnik) {
				System.out.println(k.dohvatiKontakt());
			}
		}
	}
	*/
	/**
	 * Ispis artikala.
	 * 
	 * @param artikli artikli koje æemo ispisivati.
	 * 
	 * @throws NemoguceOdreditiGrupuOsiguranjaException Ovdje proslijeðuje metodi tekstOglasa da obradi iznimku.
	 * 
	 */
	// Ispis artikala sortirano prvo po naslovu pa ako je isti naslov po opisu!
	private static void ispisArtikala(List<Artikl> artikli) throws NemoguceOdreditiGrupuOsiguranjaException {
		List<Artikl> listaArtikala = artikli.stream().sorted(Comparator.comparing(Artikl::getNaslov).thenComparing(Artikl::getOpis)).collect(Collectors.toList());
		for(Artikl a : listaArtikala) {
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println(a.tekstOglasa());
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
	}
	/**
	 * Ispis kategorija.
	 * 
	 * @param kategorije kategorije koje æemo ispisivati.
	 * 
	 * @throws NemoguceOdreditiGrupuOsiguranjaException Ovdje proslijeðuje metodi tekstOglasa da obradi iznimku.
	 * 
	 */
	// Ispis po kategorijama!
	private static void ispisPoKategorijama(List<Kategorija<Artikl>> kategorije) throws NemoguceOdreditiGrupuOsiguranjaException {
		System.out.println("Ispis po kategorijama: ");
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
		for(Kategorija<Artikl> k : kategorije) {
			System.out.println("Kategorija: " + k.getNaziv());
			ispisArtikala(k.getArtikli());
		}
	}
	/**
	 * Ispis aktivnih oglasa na prodaji.
	 * 
	 * @param aktivniOglasi oglasi koje æemo ispisivati.
	 * 
	 * @throws NemoguceOdreditiGrupuOsiguranjaException Ovdje proslijeðuje metodi tekstOglasa da obradi iznimku.
	 * 
	 */
	// Ispis aktivnih oglasa na prodaji!
	private static void ispisAktivnihOglasa(List<Prodaja> aktivniOglasi) throws NemoguceOdreditiGrupuOsiguranjaException {
	
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
		System.out.println("Trenutno su oglasi na prodaju: ");
		for(Prodaja p : aktivniOglasi) {
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println(p.getArtikl().tekstOglasa());
			String datum = p.getDatumObjave().format(DateTimeFormatter.ofPattern(DATUM));
			System.out.println("Datum objave: " + datum);
			System.out.println(p.getKorisnik().dohvatiKontakt());
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------");
	}
	/**
	 * Dohvaèanje korisnika iz datoteke u kojoj se korisnici nalaze.
	 * 
	 * @param korisnici korisnici koje æemo uèitati iz datoteke.
	 * 
	 */
	private static List<Korisnik> dohvatiKorisnike(List<Korisnik> korisnici) {
		System.out.println("Uèitavanje korisnika...");
		try(BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAMEKORISNICI), Charset.forName("UTF-8")))) {
			String line = null;	
				while((line = input.readLine()) != null) {
					Integer tip = Integer.parseInt(line);
					if(tip == PRIVATNIKORISNIK) {
						Long id = Long.parseLong(input.readLine());
						String ime = input.readLine();
						String prezime = input.readLine();
						String email = input.readLine();
						String telefon = input.readLine();
						PrivatniKorisnik tempPrivatniKorisnik = new PrivatniKorisnik(id,ime,prezime,email,telefon); 
						korisnici.add(tempPrivatniKorisnik);
					} else if(tip == POSLOVNIKORISNIK) {
						Long id = Long.parseLong(input.readLine());
						String naziv = input.readLine();
						String web = input.readLine();
						String email = input.readLine();
						String telefon = input.readLine();
						PoslovniKorisnik tempPoslovniKorisnik = new PoslovniKorisnik(id,naziv,web,email,telefon); 
						korisnici.add(tempPoslovniKorisnik);
					}
				}
		}
		catch (IOException ex) {
			System.err.println("Pogreška kod èitanja datoteke " + FILENAMEKORISNICI);
			ex.printStackTrace();
		}
		return korisnici;
	}
	/**
	 * Dohvaèanje artikala iz datoteke u kojoj se artikli nalaze.
	 * 
	 * @param artikli Artikli koje æemo uèitati iz datoteke.
	 * 
	 */
	private static List<Artikl> dohvatiArtikle(List<Artikl> artikli) {
		System.out.println("Uèitavanje artikala...");
		try(BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAMEARTIKLI), Charset.forName("UTF-8")))) {
			String line = null;	
				while((line = input.readLine()) != null) {
					Integer tip = Integer.parseInt(line);
					if(tip == ARTIKLUSLUGA) {
						Long id = Long.parseLong(input.readLine());
						String naslov = input.readLine();
						String opis = input.readLine();
						BigDecimal cijena = new BigDecimal(input.readLine());
						Stanje stanje = unosStanja(Integer.parseInt(input.readLine()));
						Usluga tempUsluga = new Usluga(id,naslov,opis,cijena,stanje);
						artikli.add(tempUsluga);	
					} else if(tip == ARTIKLAUTOMOBIL) {
						Long id = Long.parseLong(input.readLine());
						String naslov = input.readLine();
						String opis = input.readLine();
						BigDecimal snaga = new BigDecimal(input.readLine());
						BigDecimal cijena = new BigDecimal(input.readLine());
						Stanje stanje = unosStanja(Integer.parseInt(input.readLine()));
						Automobil tempAutomobil = new Automobil(id,naslov,opis,cijena,snaga,stanje);
						artikli.add(tempAutomobil);
					} else if (tip == ARTIKLSTAN) {
						Long id = Long.parseLong(input.readLine());
						String naslov = input.readLine();
						String opis = input.readLine();
						int kvadratura = Integer.parseInt(input.readLine());
						BigDecimal cijena = new BigDecimal(input.readLine());
						Stanje stanje = unosStanja(Integer.parseInt(input.readLine()));
						Stan tempStan = new Stan(id,naslov,opis,cijena,kvadratura,stanje);
						artikli.add(tempStan);
					}
			}
		}
		catch (IOException ex) {
			System.err.println("Pogreška kod èitanja datoteke " + FILENAMEARTIKLI);
			ex.printStackTrace();
		}
		return artikli;
	}
	/**
	 * Dohvaèanje kategorija iz datoteke u kojoj se kategorije nalaze.
	 * 
	 * @param artikli lista artikala koje æe biti odabrane unutar datoteke.
	 * @param kategorije Kategorije koje æemo uèitati iz datoteke sa odabranim artiklima.
	 * 
	 */
	private static List<Kategorija<Artikl>> dohvatiKategorije(List<Kategorija<Artikl>> kategorije, List<Artikl> artikli) {
		System.out.println("Uèitavanje kategorija...");
		try(BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAMEKATEGORIJE), Charset.forName("UTF-8")))) {
			String line = null;	
			while((line = input.readLine()) != null) {
				Long id = Long.parseLong(line);
				String naslov = input.readLine();
				
				List<Artikl> tempArtikli = new ArrayList<>();
				
				String odabirArtikala = input.readLine();
				for(char c : odabirArtikala.toCharArray()) {
					if(Character.isDigit(c)) {
						Integer index = Character.getNumericValue(c);
						tempArtikli.add(artikli.get(index-1));
					}
				}
				Kategorija<Artikl> tempKategorija = new Kategorija<Artikl>(id,naslov,tempArtikli);
				kategorije.add(tempKategorija);	
			}
		}
		catch (IOException ex) {
			System.err.println("Pogreška kod èitanja datoteke " + FILENAMEKATEGORIJE);
			ex.printStackTrace();
		}
		return kategorije;
	}
	/**
	 * Dohvaèanje prodaje iz datoteke u kojoj se prodaje nalaze.
	 * 
	 * @param aktivniOglasi lista oglasa koje æemo uèitati iz datoteke.
	 * @param kategorije Sve kategorije koje smo prethodno uèitali.
	 * @param korisnici Svi korisnici koje smo prethodno uèitali.
	 * 
	 */
	private static List<Prodaja> dohvatiProdaju(List<Prodaja> aktivniOglasi, List<Kategorija<Artikl>> kategorije, List<Korisnik> korisnici) {
		System.out.println("Uèitavanje prodaje...");
		try(BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(FILENAMEPRODAJA), Charset.forName("UTF-8")))) {
			String line = null;	
			while((line = input.readLine()) != null) {
				Long id = Long.parseLong(line);
				
				Integer IxKorisnika = Integer.parseInt(input.readLine());
				Integer IxKategorije = Integer.parseInt(input.readLine());
				Integer IxArtikla = Integer.parseInt(input.readLine());
				DateTimeFormatter format = DateTimeFormatter.ofPattern(DATUM);
				String datum = input.readLine();
				LocalDate datumObjave = LocalDate.parse(datum, format);
				// Veæ si u klasi napravio da ti od indexa oduzme -1 tako da ovdje u IxArtikla nisi morao oduzimati!!!
				Prodaja tempProdaja = new Prodaja(id,kategorije.get(IxKategorije-1).dohvatiArtikl(IxArtikla),korisnici.get(IxKorisnika-1), datumObjave);
				aktivniOglasi.add(tempProdaja);
			}
		}
		catch (IOException ex) {
			System.err.println("Pogreška kod èitanja datoteke " + FILENAMEPRODAJA);
			ex.printStackTrace();
		}
		return aktivniOglasi;
	}
	/**
	 * Serijalizacija prodaje u datoteku serijalizacija.dat unutar dat foldera.
	 * 
	 * @param aktivniOglasi lista oglasa koje æemo uèitati iz datoteke.
	 * @param kategorije Sve kategorije koje smo prethodno uèitali.
	 * @param korisnici Svi korisnici koje smo prethodno uèitali.
	 * 
	 */
	private static void serializirajProdaju(List<Prodaja> aktivniOglasi) throws NemoguceOdreditiGrupuOsiguranjaException  {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZATIONFILENAME))) {
			out.writeObject(aktivniOglasi);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	private static void serijalizirajAutomobile(List<Automobil> automobili) throws NemoguceOdreditiGrupuOsiguranjaException  {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dat/SerijalizacijaFiltriranihAutomobila.dat"))) {
			out.writeObject(automobili);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	private static void procitajSerijaliziraneAutomobile() {
		
		List<Automobil> automobili = null;
		
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("dat/SerijalizacijaFiltriranihAutomobila.dat"))) {
			automobili = (List<Automobil>) in.readObject();
			
			System.out.println("Podaci o proèitanim objektima:");
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			System.out.println("Filtrirani Automobili: ");
			for(Automobil a : automobili) {
				System.out.println("-------------------------------------------------------------------------------------------------------------------");
				System.out.println(a.tekstOglasa());
			}
			System.out.println("-------------------------------------------------------------------------------------------------------------------");
			
		} catch (IOException ex) {
			System.err.println(ex);
		} catch (ClassNotFoundException ex) {
			System.err.println(ex);
		}
	}
	
	private static void ispisAutomobila(List<Automobil> automobili) {
		
		IntSummaryStatistics stat = automobili.stream().mapToInt(a->a.getNaslov().length()).summaryStatistics();
		
		try(PrintWriter out = new PrintWriter(new FileWriter(new File("vjezbeZadatak2.txt")))) {
			out.printf("Suma duljine iznosi: %d\nProsjek duljine iznosi: %f\nNajkraci opis je dug: %d\nNajduzi opis je dug: %d\n", stat.getSum(),stat.getAverage(),stat.getMin(),stat.getMax());
		} catch(IOException e) {
			System.out.println(e);
		}
	}
	
}
