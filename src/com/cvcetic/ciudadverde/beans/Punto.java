package com.cvcetic.ciudadverde.beans;

/**
 * Clase que define la estructura de una un Punto
 * @author Rubén Cabrera, Iker Zaldivar, David Santibañez, Javier Barambones
 *
 */
public class Punto {
	private Long id;
	private Float latitud;
	private Float longitud;
	private String direccion;
	private String nombre_es;
	private String nombre_eu;
	private Long idtipo;
	private Float distancia;

	public Punto() {

	}

	public Punto(Long id, Float latitud, Float longitud, String direccion,
			String nombre_es, String nombre_eu, Long idtipo,Float distancia) {
		this.id = id;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.nombre_es = nombre_es;
		this.nombre_eu = nombre_eu;
		this.idtipo = idtipo;
		this.distancia= distancia;
	}

	public Punto(Float latitud, Float longitud) {		
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getLatitud() {
		return latitud;
	}

	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}

	public Float getLongitud() {
		return longitud;
	}

	public void setLongitud(Float longitud) {
		this.longitud = longitud;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public Long getIdtipo() {
		return idtipo;
	}

	public void setIdtipo(Long idtipo) {
		this.idtipo = idtipo;
	}


	public Float getDistancia() {
		return distancia;
	}

	public void setDistancia(Float distancia) {
		this.distancia = distancia;
	}
	@Override
	public String toString() {
		return "Puntos [id=" + id + ", latitud=" + latitud + ", longitud="
				+ longitud + ", direccion=" + direccion + ", nombre_es="
				+ nombre_es + ", nombre_eu=" + nombre_eu + ", idtipo=" + idtipo
				+ "]";
	}


}
