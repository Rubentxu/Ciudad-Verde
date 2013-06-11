package com.cvcetic.ciudadverde.beans;

/**
 * Clase que define la estructura de los tipos de Puntos que puede haber
 * @author Rubén Cabrera, Iker Zaldivar, David Santibañez, Javier Barambones
 *
 */
public class Tipo {
	private Long id;
	private String nombre_es;
	private String nombre_eu;

	public Tipo() {

	}

	public Tipo(Long id, String nombre_es, String nombre_eu) {
		this.id = id;
		this.nombre_es = nombre_es;
		this.nombre_eu = nombre_eu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre_es() {
		return nombre_es;
	}

	public void setNombre_es(String nombre_es) {
		this.nombre_es = nombre_es;
	}

	public String getNombre_eu() {
		return nombre_eu;
	}

	public void setNombre_eu(String nombre_eu) {
		this.nombre_eu = nombre_eu;
	}

	@Override
	public String toString() {
		return "Tipo [id=" + id + ", nombre_es=" + nombre_es + ", nombre_eu="
				+ nombre_eu + "]";
	}

}
