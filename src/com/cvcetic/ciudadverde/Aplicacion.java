package com.cvcetic.ciudadverde;

import static com.cvcetic.ciudadverde.bbdd.BBDD.*;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_DATOS;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_DATOS_EU;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_FRECUENCIA;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_HORA_FIN;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_HORA_INICIO;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_ID;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_ID_TIPO;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_NOMBRE;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_NOMBRE_EU;
import static com.cvcetic.ciudadverde.bbdd.BBDD.LINEAS_TABLE;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_DIRECCION;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_ID;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_ID_TIPO;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_LATITUD;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_LINEAS_ID_LINEA;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_LINEAS_ID_PUNTO;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_LINEAS_TABLE;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_LONGITUD;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_NOMBRE;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_NOMBRE_EU;
import static com.cvcetic.ciudadverde.bbdd.BBDD.PUNTOS_TABLE;
import static com.cvcetic.ciudadverde.bbdd.BBDD.TIPOS_ID;
import static com.cvcetic.ciudadverde.bbdd.BBDD.TIPOS_NOMBRE;
import static com.cvcetic.ciudadverde.bbdd.BBDD.TIPOS_NOMBRE_EU;
import static com.cvcetic.ciudadverde.bbdd.BBDD.TIPOS_TABLE;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cvcetic.ciudadverde.bbdd.BBDD;
import com.cvcetic.ciudadverde.beans.Linea;
import com.cvcetic.ciudadverde.beans.Punto;
import com.cvcetic.ciudadverde.beans.PuntoBici;
import com.cvcetic.ciudadverde.beans.Tipo;
import com.google.android.maps.GeoPoint;

/**
 * Esta clase Aplicaci�n ser� la que comparta los datos entre las vistas y la
 * base de datos
 * 
 * @author Rub�n Cabrera, Iker Zaldivar, David Santiba�ez, Javier Barambones
 */
public class Aplicacion extends Application {
	private List<Punto> pAutobuses;
	private List<Punto> pBicis;
	private List<Punto> pTranvias;
	private SQLiteDatabase db;
	private GeoPoint localizacion;

	@Override
	public void onCreate() {
		super.onCreate();
		BBDD helper = new BBDD(this);
		db = helper.getWritableDatabase();

		try {
			if (null == pBicis) {
				pBicis = loadPuntosBici();
				setpAutobuses(loadPuntosAutobuses());
				setpTranvias(loadPuntosTranvias());
			}
		} catch (Exception ex) {
			Log.i("Error", ex.toString());
		}

	}

	/**
	 * Metodo para a�adir un nuevo Tipo en base de datos
	 * 
	 * @param t
	 *            Objeto de tipo Tipo
	 */
	public void addTipo(Tipo t) {
		assert (null != t);

		ContentValues values = new ContentValues();
		values.put(TIPOS_NOMBRE, t.getNombre_es());
		values.put(TIPOS_NOMBRE_EU, t.getNombre_eu());

		t.setId(db.insert(TIPOS_TABLE, null, values));
	}

	/**
	 * Metodo que devuelve un listado de Tipos de base de datos
	 * 
	 * @return Listado de Tipos
	 */
	public List<Tipo> loadTipos() {
		Cursor c = db.query(TIPOS_TABLE, null, null, null, null, null, null);
		List<Tipo> tipos = new ArrayList<Tipo>();
		if (!c.isAfterLast()) {
			do {
				Tipo t = new Tipo();
				t.setId(c.getLong(0));
				t.setNombre_es(c.getString(1));
				t.setNombre_eu(c.getString(2));

				tipos.add(t);
			} while (c.moveToNext());
		}
		c.close();

		return tipos;
	}

	/**
	 * Metodo que devuelve un Tipo de base de datos
	 * 
	 * @param idTipo
	 *            Identificador que queremos obtener
	 * @return Objeto de tipo Tipo
	 */
	public Tipo loadTipo(Long idTipo) {
		assert (null != idTipo);

		Cursor c = db.query(TIPOS_TABLE, null, TIPOS_ID + " = ?",
				new String[] { idTipo.toString() }, null, null, null);
		Tipo t = new Tipo();
		if (!c.isAfterLast()) {
			t.setId(c.getLong(0));
			t.setNombre_es(c.getString(1));
			t.setNombre_eu(c.getString(2));
		}
		c.close();

		return t;
	}

	/**
	 * Metodo que a�ade un Punto a la base de datos
	 * 
	 * @param p
	 *            Objeto de tipo Punto que se desea insertar
	 */
	public void addPunto(Punto p) {
		assert (null != p);

		ContentValues values = new ContentValues();
		values.put(PUNTOS_LONGITUD, p.getLongitud());
		values.put(PUNTOS_LATITUD, p.getLatitud());
		values.put(PUNTOS_DIRECCION, p.getDireccion());
		values.put(PUNTOS_NOMBRE, p.getNombre_es());
		values.put(PUNTOS_NOMBRE_EU, p.getNombre_eu());
		values.put(PUNTOS_ID_TIPO, p.getIdtipo());

		p.setId(db.insert(PUNTOS_TABLE, null, values));
	}

	/**
	 * Metodo que obtiene un Punto de base de datos
	 * 
	 * @param idPunto
	 *            Identificador del punto que se desea obtener
	 * @return Objeto de tipo Punto
	 */
	public Punto loadPunto(Long idPunto) {
		assert (null != idPunto);

		Cursor c = db.query(PUNTOS_TABLE, new String[] { PUNTOS_ID,
				PUNTOS_DIRECCION, PUNTOS_NOMBRE, PUNTOS_NOMBRE_EU,
				PUNTOS_LONGITUD, PUNTOS_LATITUD },
				PUNTOS_ID + " = " + idPunto.toString(), null, null, null, null);
		Punto p = new Punto();
		if (!c.isAfterLast()) {
			c.moveToFirst();

			p.setId(c.getLong(0));
			p.setDireccion(c.getString(1));
			p.setNombre_es(c.getString(2));
			p.setNombre_eu(c.getString(3));
			p.setLongitud(c.getFloat(4));
			p.setLatitud(c.getFloat(5));
		}
		c.close();

		return p;
	}

	/**
	 * Metodo que obtiene todos los Puntos que corresponden al Tipo "autob�s"
	 * 
	 * @return Listado de Puntos
	 */
	public List<Punto> loadPuntosAutobuses() {

		Cursor c = db.query(PUNTOS_TABLE, new String[] { PUNTOS_ID,
				PUNTOS_DIRECCION, PUNTOS_NOMBRE, PUNTOS_NOMBRE_EU,
				PUNTOS_LONGITUD, PUNTOS_LATITUD }, PUNTOS_ID_TIPO + " = 2",
				null, null, null, null);
		List<Punto> puntos = new ArrayList<Punto>();
		if (!c.isAfterLast()) {
			c.moveToFirst();
			do {
				Punto p = new Punto();

				p.setId(c.getLong(0));
				p.setDireccion(c.getString(1));
				p.setNombre_es(c.getString(2));
				p.setNombre_eu(c.getString(3));
				p.setLongitud(c.getFloat(4));
				p.setLatitud(c.getFloat(5));
				p.setIdtipo((long) 2);

				puntos.add(p);
			} while (c.moveToNext());
		}
		c.close();
		Log.d("autobuses", puntos.size() + "");

		return puntos;
	}

	/**
	 * Metodo que obtiene todos los Puntos que corresponden al Tipo "autob�s" de
	 * una determinada linea
	 * 
	 * @param linea
	 *            La linea de autobus
	 * @return Listado de Puntos
	 */
	public List<Punto> loadPuntosAutobusesLinea(long linea) {

		Cursor c = db.query(PUNTOS_TABLE, new String[] { PUNTOS_ID,
				PUNTOS_DIRECCION, PUNTOS_NOMBRE, PUNTOS_NOMBRE_EU,
				PUNTOS_LONGITUD, PUNTOS_LATITUD }, PUNTOS_ID_TIPO + " = 2 "
				+ "AND " + PUNTOS_ID + " IN (SELECT " + PUNTOS_LINEAS_ID_PUNTO
				+ " FROM " + PUNTOS_LINEAS_TABLE + " WHERE "
				+ PUNTOS_LINEAS_ID_LINEA + " = " + linea + ")", null, null,
				null, null);
		List<Punto> puntos = new ArrayList<Punto>();
		if (!c.isAfterLast()) {
			c.moveToFirst();
			do {
				Punto p = new Punto();

				p.setId(c.getLong(0));
				p.setDireccion(c.getString(1));
				p.setNombre_es(c.getString(2));
				p.setNombre_eu(c.getString(3));
				p.setLongitud(c.getFloat(4));
				p.setLatitud(c.getFloat(5));
				p.setIdtipo((long) 2);

				Log.d("debugging paradasPorLineaAutobus", p.getNombre_es());

				puntos.add(p);
			} while (c.moveToNext());
		}
		c.close();
		Log.d("autobuses", puntos.size() + "");

		return puntos;
	}

	/**
	 * Metodo que obtiene todos los Puntos que corresponden al Tipo "tranvias",
	 * de una determinada linea
	 * 
	 * @param linea
	 * @return Listado de Puntos de una determinada linea
	 */
	public List<Punto> loadPuntosTranviasLinea(long linea) {

		Cursor c = db.query(PUNTOS_TABLE, new String[] { PUNTOS_ID,
				PUNTOS_DIRECCION, PUNTOS_NOMBRE, PUNTOS_NOMBRE_EU,
				PUNTOS_LONGITUD, PUNTOS_LATITUD }, PUNTOS_ID_TIPO + " = 3"
				+ " AND " + PUNTOS_ID + " IN (SELECT " + PUNTOS_LINEAS_ID_PUNTO
				+ " FROM " + PUNTOS_LINEAS_TABLE + " WHERE "
				+ PUNTOS_LINEAS_ID_LINEA + " = " + linea + ")", null, null,
				null, null);
		List<Punto> puntos = new ArrayList<Punto>();
		if (!c.isAfterLast()) {
			c.moveToFirst();
			do {
				Punto p = new Punto();

				p.setId(c.getLong(0));
				p.setDireccion(c.getString(1));
				p.setNombre_es(c.getString(2));
				p.setNombre_eu(c.getString(3));
				p.setLongitud(c.getFloat(4));
				p.setLatitud(c.getFloat(5));
				p.setIdtipo((long) 3);

				puntos.add(p);
			} while (c.moveToNext());
		}
		c.close();
		Log.d("tranvias", puntos.size() + "");

		return puntos;
	}

	/**
	 * Metodo que obtiene todos los Puntos que corresponden al Tipo "tranvias"
	 * 
	 * @return Listado de Puntos
	 */
	public List<Punto> loadPuntosTranvias() {

		Cursor c = db.query(PUNTOS_TABLE, new String[] { PUNTOS_ID,
				PUNTOS_DIRECCION, PUNTOS_NOMBRE, PUNTOS_NOMBRE_EU,
				PUNTOS_LONGITUD, PUNTOS_LATITUD }, PUNTOS_ID_TIPO + " = 3",
				null, null, null, null);
		List<Punto> puntos = new ArrayList<Punto>();
		if (!c.isAfterLast()) {
			c.moveToFirst();
			do {
				Punto p = new Punto();

				p.setId(c.getLong(0));
				p.setDireccion(c.getString(1));
				p.setNombre_es(c.getString(2));
				p.setNombre_eu(c.getString(3));
				p.setLongitud(c.getFloat(4));
				p.setLatitud(c.getFloat(5));
				p.setIdtipo((long) 3);

				puntos.add(p);
			} while (c.moveToNext());
		}
		c.close();
		Log.d("tranvias", puntos.size() + "");

		return puntos;
	}

	/**
	 * Metodo que obtiene todos los Puntos que corresponden al Tipo "bicicletas"
	 * 
	 * @return Listado de Puntos
	 */
	public List<Punto> loadPuntosBici() {
		Cursor c = db.query(PUNTOS_TABLE, new String[] { PUNTOS_ID,
				PUNTOS_DIRECCION, PUNTOS_NOMBRE, PUNTOS_NOMBRE_EU,
				PUNTOS_LONGITUD, PUNTOS_LATITUD }, PUNTOS_ID_TIPO + " = 1",
				null, null, null, null);
		List<Punto> puntos = new ArrayList<Punto>();
		if (!c.isAfterLast()) {
			c.moveToFirst();
			do {
				Punto p = new Punto();

				p.setId(c.getLong(0));
				p.setDireccion(c.getString(1));
				p.setNombre_es(c.getString(2));
				p.setNombre_eu(c.getString(3));
				p.setLongitud(c.getFloat(4));
				p.setLatitud(c.getFloat(5));
				p.setIdtipo((long) 1);

				puntos.add(p);
			} while (c.moveToNext());
		}
		c.close();
		Log.d("bicicletas", puntos.size() + "");

		return puntos;
	}

	/**
	 * Metodo que a�ade una nueva Linea en base de datos
	 * 
	 * @param l
	 *            Objeto de tipo Linea a insertar
	 */
	public void addLinea(Linea l) {
		assert (null != l);

		ContentValues values1 = new ContentValues();
		values1.put(LINEAS_NOMBRE, l.getNombre_es());
		values1.put(LINEAS_NOMBRE_EU, l.getNombre_eu());
		values1.put(LINEAS_HORA_INICIO, l.getHorainicio().toString());
		values1.put(LINEAS_HORA_FIN, l.getHoraFin().toString());
		values1.put(LINEAS_FRECUENCIA, l.getFrecuencia());
		values1.put(LINEAS_DATOS, l.getDatos_es());
		values1.put(LINEAS_DATOS_EU, l.getDatos_eu());
		values1.put(LINEAS_ID_TIPO, l.getIdtipo().getId());

		l.setId(db.insert(LINEAS_TABLE, null, values1));

		for (Punto p : l.getParadas()) {
			ContentValues values2 = new ContentValues();
			values2.put(PUNTOS_LINEAS_ID_LINEA, l.getId());
			values2.put(PUNTOS_LINEAS_ID_PUNTO, p.getId());

			db.insert(PUNTOS_LINEAS_TABLE, null, values2);
		}
	}

	/**
	 * Metodo que obtiene una Linea de base de datos
	 * 
	 * @param idLinea
	 *            Identificador de la linea
	 * @return Objeto de tipo Linea
	 */
	public Linea loadLinea(Long idLinea) {
		Cursor c = db.query(LINEAS_TABLE, new String[] { LINEAS_ID,
				LINEAS_NOMBRE, LINEAS_NOMBRE_EU, LINEAS_HORA_INICIO,
				LINEAS_HORA_FIN, LINEAS_FRECUENCIA, LINEAS_DATOS,
				LINEAS_DATOS_EU, LINEAS_ID_TIPO },
				LINEAS_ID + " = " + idLinea.toString(), null, null, null, null);
		Linea l = new Linea();
		if (!c.isAfterLast()) {
			l.setId(c.getLong(0));
			l.setNombre_es(c.getString(1));
			l.setNombre_eu(c.getString(2));
			l.setHorainicio(c.getString(3));
			l.setHoraFin(c.getString(4));
			l.setFrecuencia(c.getInt(5));
			l.setDatos_es(c.getString(6));
			l.setDatos_eu(c.getString(7));

			l.setIdtipo(loadTipo(c.getLong(8)));
		}
		c.close();

		return l;
	}

	/**
	 * Metodo que devuelve un PuntoBici donde se prestan las bicicletas
	 * 
	 * @param idpunto
	 *            Indentificador del Punto
	 * @return Objeto de tipo PuntoBici
	 */
	public PuntoBici loadPuntoEntregaBici(Long idpunto) {
		Log.d("DebugAplicacion", "El id del punto pasado al metodo loadPuntoEntregaBici es: "+idpunto);
		Cursor c = db.query(BICIS_TABLE, new String[] { BICIS_ID,
				BICIS_NUMERO_BICIS, BICIS_DESCRIPCION, BICIS_TELEFONO,
				BICIS_CP, BICIS_EMAIL, BICIS_HORA_INICIO, BICIS_HORA_FIN,
				BICIS_RESPONSABLE }, BICIS_ID_PUNTO + " = " + idpunto, null,
				null, null, null);
		Log.i("DebugAplicacion", "Es nulo el puntoBici cogido de la bd?: "+c.isAfterLast());
		PuntoBici pb = new PuntoBici();
		if (!c.isAfterLast()) {
			c.moveToFirst();

			pb.setId(c.getLong(0));
			// pb.setNumerobicis(c.getInt(1));
			pb.setDescripcion_es(c.getString(2));
			pb.setTelefono(c.getString(3));
			pb.setCp(c.getString(4));
			pb.setEmail(c.getString(5));
			pb.setHorainicio(c.getString(6));
			pb.setHorafin(c.getString(7));
			pb.setResponsable(c.getString(8));
		}
		c.close();
		Log.d("DebugAplicacion", pb.toString());
		return pb;
	}


	/**
	 * Metodo que obtiene todas las Lineas de autobus
	 * 
	 * @see Linea
	 * @return Listado de L�neas de autob�s
	 */
	public List<Linea> loadLineasAutobus() {

		Log.d("debug", "entro1");
		Cursor c = db.query(LINEAS_TABLE, new String[] { LINEAS_ID,
				LINEAS_NOMBRE, LINEAS_NOMBRE_EU, LINEAS_HORA_INICIO,
				LINEAS_HORA_FIN, LINEAS_FRECUENCIA, LINEAS_DATOS,
				LINEAS_DATOS_EU }, LINEAS_ID_TIPO + " = 2", null, null, null,
				null);
		List<Linea> lineas = new ArrayList<Linea>();
		Log.d("debug", "entro2");
		if (!c.isAfterLast()) {
			c.moveToFirst();
			do {
				Linea linea = new Linea();

				linea.setId(c.getLong(0));
				linea.setNombre_es(c.getString(1));
				linea.setNombre_eu(c.getString(2));
				linea.setHorainicio(c.getString(3));
				linea.setHoraFin(c.getString(4));
				linea.setFrecuencia(c.getInt(5));
				linea.setDatos_es(c.getString(6));
				linea.setDatos_eu(c.getString(7));

				lineas.add(linea);
			} while (c.moveToNext());
		}
		c.close();
		Log.d("lineas autobus", lineas.size() + "");

		return lineas;
	}

	/**
	 * Metodo que obtiene todas las Lineas de tranv�a
	 * 
	 * @see Linea
	 * @return Listado de Lineas de tranv�a
	 */
	public List<Linea> loadLineasTranvia() {

		Cursor c = db.query(LINEAS_TABLE, new String[] { LINEAS_ID,
				LINEAS_NOMBRE, LINEAS_NOMBRE_EU, LINEAS_HORA_INICIO,
				LINEAS_HORA_FIN, LINEAS_FRECUENCIA, LINEAS_DATOS,
				LINEAS_DATOS_EU }, PUNTOS_ID_TIPO + " = 3", null, null, null,
				null);
		List<Linea> lineas = new ArrayList<Linea>();
		if (!c.isAfterLast()) {
			c.moveToFirst();
			do {
				Linea linea = new Linea();

				linea.setId(c.getLong(0));
				linea.setNombre_es(c.getString(1));
				linea.setNombre_eu(c.getString(2));
				linea.setHorainicio(c.getString(3));
				linea.setHoraFin(c.getString(4));
				linea.setFrecuencia(c.getInt(5));
				linea.setDatos_es(c.getString(6));
				linea.setDatos_eu(c.getString(7));

				lineas.add(linea);
			} while (c.moveToNext());
		}
		c.close();
		Log.d("lineas tranvia", lineas.size() + "");

		return lineas;
	}

	public GeoPoint getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(GeoPoint localizacion) {
		this.localizacion = localizacion;
	}

	public List<Punto> getpTranvias() {
		return pTranvias;
	}

	public void setpTranvias(List<Punto> pTranvias) {
		this.pTranvias = pTranvias;
	}

	public List<Punto> getpAutobuses() {
		return pAutobuses;
	}

	public void setpAutobuses(List<Punto> pAutobuses) {
		this.pAutobuses = pAutobuses;
	}
}
