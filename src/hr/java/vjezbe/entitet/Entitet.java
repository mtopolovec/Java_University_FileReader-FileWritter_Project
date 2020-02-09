package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Predstavlja entitet koji je definiran id-em.
 * 
 * @author Matija topolovec
 *
 */
public abstract class Entitet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4349402761542347960L;
	
	private Long id;

	public Entitet(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
