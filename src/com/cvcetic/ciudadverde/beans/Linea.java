package com.cvcetic.ciudadverde.beans;

import java.util.List;

/**
 * Clase que define la estructura de una Linea (tranvia o autobús)
 * @author Rubén Cabrera, Iker Zaldivar, David Santibañez, Javier Barambones
 *
 */
public class Linea {
	private Long id;
	private String nombre_es;
	private String nombre_eu;
	private String horainicio;
	private String horaFin;
	private Integer frecuencia;
	private String datos_es;
	private String datos_eu;
	private Tipo tipo;
	private List<Punto> paradas;

	public Linea() {

	}

	public Linea(Long id, String nombre_es, String nombre_eu,
			String horainicio, String horaFin, Integer frecuencia,
			String datos_es, String datos_eu, Tipo tipo, List<Punto> paradas) {
		this.id = id;
		this.nombre_es = nombre_es;
		this.nombre_eu = nombre_eu;
		this.horainicio = horainicio;
		this.horaFin = horaFin;
		this.frecuencia = frecuencia;
		this.datos_es = datos_es;
		this.datos_eu = datos_eu;
		this.tipo = tipo;
		this.setParadas(paradas);
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

	public String getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(String horainicio) {
		this.horainicio = horainicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public Integer getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Integer frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getDatos_es() {
		return datos_es;
	}

	public void setDatos_es(String datos_es) {
		this.datos_es = datos_es;
	}

	public String getDatos_eu() {
		return datos_eu;
	}

	public void setDatos_eu(String datos_eu) {
		this.datos_es = datos_eu;
	}

	public Tipo getIdtipo() {
		return tipo;
	}

	public void setIdtipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<Punto> getParadas() {
		return paradas;
	}

	public void setParadas(List<Punto> paradas) {
		this.paradas = paradas;
	}

	@Override
	public String toString() {
		return "Linea [id=" + id + ", nombre_es=" + nombre_es + ", nombre_eu="
				+ nombre_eu + ", horainicio=" + horainicio + ", horaFin="
				+ horaFin + ", frecuencia=" + frecuencia + ", datos_es="
				+ datos_es + ", datos_eu=" + datos_eu + ", tipo=" + tipo
				+ ", paradas=" + paradas + "]";
	}

}
