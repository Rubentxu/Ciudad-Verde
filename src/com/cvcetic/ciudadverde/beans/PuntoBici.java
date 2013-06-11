package com.cvcetic.ciudadverde.beans;

/**
 * Clase que define la estructura de un punto que presta bicicletas
 * @author Rubén Cabrera, Iker Zaldivar, David Santibañez, Javier Barambones
 *
 */
public class PuntoBici extends Punto {
	private Integer numerobicis;
	private String descripcion_es;
	private String descripcion_eu;
	private String telefono;
	private String cp;
	private String email;
	private String horainicio;
	private String horafin;
	private String responsable;

	public PuntoBici() {

	}

	public PuntoBici(Integer numerobicis, String descripcion_es,
			String descripcion_eu, String telefono, String cp, String email,
			String horainicio, String horafin, String responsable) {
		super();
		this.numerobicis = numerobicis;
		this.descripcion_es = descripcion_es;
		this.descripcion_eu = descripcion_eu;
		this.telefono = telefono;
		this.cp = cp;
		this.email = email;
		this.horainicio = horainicio;
		this.horafin = horafin;
		this.responsable = responsable;
	}

	public PuntoBici(Punto punto) {
		super.setId(punto.getId());
		super.setNombre_es(punto.getNombre_es());
		super.setNombre_eu(punto.getNombre_eu());
		super.setLatitud(punto.getLatitud());
		super.setLongitud(punto.getLongitud());
		super.setIdtipo(punto.getIdtipo());
	}

	public Integer getNumerobicis() {
		return numerobicis;
	}

	public void setNumerobicis(Integer numerobicis) {
		this.numerobicis = numerobicis;
	}

	public String getDescripcion_es() {
		return descripcion_es;
	}

	public void setDescripcion_es(String descripcion_es) {
		this.descripcion_es = descripcion_es;
	}

	public String getDescripcion_eu() {
		return descripcion_eu;
	}

	public void setDescripcion_eu(String descripcion_eu) {
		this.descripcion_eu = descripcion_eu;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(String horainicio) {
		this.horainicio = horainicio;
	}

	public String getHorafin() {
		return horafin;
	}

	public void setHorafin(String horafin) {
		this.horafin = horafin;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	@Override
	public String toString() {
		return "PuntoBici [numerobicis=" + numerobicis + ", descripcion_es="
				+ descripcion_es + ", descripcion_eu=" + descripcion_eu
				+ ", telefono=" + telefono + ", cp=" + cp + ", email=" + email
				+ ", horainicio=" + horainicio + ", horafin=" + horafin
				+ ", responsable=" + responsable + "]";
	}

}
