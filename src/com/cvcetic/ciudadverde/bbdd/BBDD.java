package com.cvcetic.ciudadverde.bbdd;

import org.xmlpull.v1.XmlPullParser;

import com.cvcetic.ciudadverde.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Esta clase sirve para crear, insertar la base de datos SQLite en la aplicaci�n
 * @author Rub�n Cabrera, Iker Zaldivar, David Santiba�ez, Javier Barambones
 */
public class BBDD extends SQLiteOpenHelper {

	public static final int VERSION = 2;

	public static final String DB_NAME = "ciudadverde_db.sqlite";

	public static final String PUNTOS_TABLE = "puntos";
	public static final String PUNTOS_ID = "id";
	public static final String PUNTOS_LONGITUD = "longitud";
	public static final String PUNTOS_LATITUD = "latitud";
	public static final String PUNTOS_DIRECCION = "direccion";
	public static final String PUNTOS_DIRECCION_EU = "direccioneu";
	public static final String PUNTOS_NOMBRE = "nombre";
	public static final String PUNTOS_NOMBRE_EU = "nombreeu";
	public static final String PUNTOS_ID_TIPO = "idtipo";

	public static final String TIPOS_TABLE = "tipos";
	public static final String TIPOS_ID = "id";
	public static final String TIPOS_NOMBRE = "nombre";
	public static final String TIPOS_NOMBRE_EU = "nombreeu";

	public static final String LINEAS_TABLE = "lineas";
	public static final String LINEAS_ID = "id";
	public static final String LINEAS_ID_TIPO = "idtipo";
	public static final String LINEAS_NOMBRE = "nombre";
	public static final String LINEAS_NOMBRE_EU = "nombreeu";
	public static final String LINEAS_HORA_INICIO = "horainicio";
	public static final String LINEAS_HORA_FIN = "horafin";
	public static final String LINEAS_FRECUENCIA = "frecuencia";
	public static final String LINEAS_DATOS = "datos";
	public static final String LINEAS_DATOS_EU = "datoseu";

	public static final String PUNTOS_LINEAS_TABLE = "puntoslineas";
	public static final String PUNTOS_LINEAS_ID = "id";
	public static final String PUNTOS_LINEAS_ID_LINEA = "idlinea";
	public static final String PUNTOS_LINEAS_ID_PUNTO = "idpunto";

	public static final String BICIS_TABLE = "bicis";
	public static final String BICIS_ID = "id";
	public static final String BICIS_ID_PUNTO = "idpunto";
	public static final String BICIS_NUMERO_BICIS = "numerobicis";
	public static final String BICIS_DESCRIPCION = "descripcion";
	public static final String BICIS_TELEFONO = "telefono";
	public static final String BICIS_CP = "cp";
	public static final String BICIS_EMAIL = "email";
	public static final String BICIS_HORA_INICIO = "horainicio";
	public static final String BICIS_HORA_FIN = "horafin";
	public static final String BICIS_RESPONSABLE = "responsable";
	
	public static final int CONSTANTE_TIPOS = 1;
	public static final int CONSTANTE_PUNTOS = 2;
	public static final int CONSTANTE_LINEAS = 3;
	public static final int CONSTANTE_PUNTOS_LINEAS = 4;
	public static final int CONSTANTE_PRESTAMO_BICIS = 5;

	private Context contexto;

	public BBDD(Context context) {
		super(context, DB_NAME, null, VERSION);
		contexto = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		dropAndCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion==1 && newVersion==2)
		{
			dropAndCreate(db);
		}

	}

	/**
	 * Esta funci�n borra, crea y rellena todas las tablas
	 * @param db Base de datos en la que se borran y crean todos los datos necesarios 
	 */
	protected void dropAndCreate(SQLiteDatabase db) {
		db.execSQL("drop table if exists " + PUNTOS_TABLE + ";");
		db.execSQL("drop table if exists " + TIPOS_TABLE + ";");
		db.execSQL("drop table if exists " + LINEAS_TABLE + ";");
		db.execSQL("drop table if exists " + PUNTOS_LINEAS_TABLE + ";");
		db.execSQL("drop table if exists " + BICIS_TABLE + ";");
		createTables(db);
		fillTables(db);
	}

	/**
	 * Este m�todo crea las tablas necesarias para la aplicaci�n
	 * @param db Base de datos donde se crean las tablas
	 */
	protected void createTables(SQLiteDatabase db) {
		db.execSQL("create table " + PUNTOS_TABLE + " (" + PUNTOS_ID
				+ " integer primary key not null,"
				+ PUNTOS_DIRECCION + " text," + PUNTOS_DIRECCION_EU + " text,"
				+ PUNTOS_NOMBRE + " text," + PUNTOS_NOMBRE_EU + " text,"
				+ PUNTOS_LONGITUD + " float," + PUNTOS_LATITUD + " float,"
				+ PUNTOS_ID_TIPO + " integer"
				+ ");");
		db.execSQL("create table " + TIPOS_TABLE + " (" + TIPOS_ID
				+ " integer primary key not null," + TIPOS_NOMBRE
				+ " text," + TIPOS_NOMBRE_EU + " text" + ");");
		db.execSQL("create table " + LINEAS_TABLE + " (" + LINEAS_ID
				+ " integer primary key not null,"
				+ LINEAS_ID_TIPO + " integer," + LINEAS_NOMBRE + " text,"
				+ LINEAS_NOMBRE_EU + " text," + LINEAS_HORA_INICIO + " text,"
				+ LINEAS_HORA_FIN + " text," + LINEAS_FRECUENCIA + " integer,"
				+ LINEAS_DATOS + " text," + LINEAS_DATOS_EU + " text" + ");");
		db.execSQL("create table " + PUNTOS_LINEAS_TABLE + " ("
				+ PUNTOS_LINEAS_ID
				+ " integer primary key not null,"
				+ PUNTOS_LINEAS_ID_LINEA + " integer," + PUNTOS_LINEAS_ID_PUNTO
				+ " integer" + ");");
		db.execSQL("create table " + BICIS_TABLE + " (" + BICIS_ID
				+ " integer primary key not null,"
				+ BICIS_ID_PUNTO + " integer," + BICIS_NUMERO_BICIS
				+ " integer," + BICIS_DESCRIPCION + " text," + BICIS_TELEFONO
				+ " text," + BICIS_CP + " text," + BICIS_EMAIL + " text,"
				+ BICIS_HORA_INICIO + " text," + BICIS_HORA_FIN + " text,"
				+ BICIS_RESPONSABLE + " text" + ");");
	}
	/**
	 * Este m�todo rellena las tablas de la base de datos
	 * @param db La base de datos en la que se rellenan los datos
	 */
	protected void fillTables(SQLiteDatabase db) {

		// Rellenamos tipos
		//ContentValues c = new ContentValues();
		XmlResourceParser xml = contexto.getResources().getXml(R.xml.tipos);
		loadDataFromXML(xml, 1, db);
		xml.close();
		xml = contexto.getResources().getXml(R.xml.puntos);
		loadDataFromXML(xml, 2, db);
		xml.close();
		xml = contexto.getResources().getXml(R.xml.lineas);
		loadDataFromXML(xml, 3, db);
		xml.close();
		xml = contexto.getResources().getXml(R.xml.puntoslineas);
		loadDataFromXML(xml, 4, db);
		xml.close();
		xml = contexto.getResources().getXml(R.xml.puntosbici);
		loadDataFromXML(xml, 5, db);
		xml.close();
	}
	
	/**
	 * M�todo que carga un determinado fichero xml, parsea sus datos e inserta los datos en la base de datos
	 * @param xml fichero xml cargado en memoria, para extraer la informacion
	 * @param tipo tipo de fichero: 1, 2, 3, 4, 5 (Lineas, Paradas, Puntos de prestamo, ...)
	 * @param db base de datos
	 */
	protected void loadDataFromXML(XmlResourceParser xml, int tipo, SQLiteDatabase db)
	{
		switch (tipo)
		{
			case 1:
				ContentValues c = new ContentValues();

				try { 
		        	xml.next();
		        	int eventType = xml.getEventType();
		        	while (eventType != XmlPullParser.END_DOCUMENT) {
		                if(eventType == XmlPullParser.START_DOCUMENT) {
		                    
		                } else if(eventType == XmlPullParser.START_TAG) {
		                	if (xml.getName().equals("id"))
		                	{
		                		Long l= new Long(xml.nextText());
		                		c.put(TIPOS_ID, l);
		                	}
		                	if (xml.getName().equals("nombre"))
		                	{
		                		c.put(TIPOS_NOMBRE, xml.nextText());
		                	}
		                } else if(eventType == XmlPullParser.END_TAG) {
		                	if (xml.getName().equals("Tipo"))
		                	{
		                		db.insert(TIPOS_TABLE, null, c);
		                	}
		                }
		                eventType = xml.next();
		               }

		        }
				catch(Exception ex)
				{
					Log.e("ERROR", ex.toString());
				}
				break;
			case 2:
				ContentValues punto = new ContentValues();
				Log.d("debugging","Entro a evaluar puntos...");
				try { 
		        	xml.next();
		        	int eventType = xml.getEventType();
		        	while (eventType != XmlPullParser.END_DOCUMENT) {
		                if(eventType == XmlPullParser.START_DOCUMENT) {
		                    
		                } else if(eventType == XmlPullParser.START_TAG) {
		                	if (xml.getName().equals("id"))
		                	{
		                		Long l= new Long(xml.nextText());
		                		//Log.d("punto",l.toString());
		                		punto.put(PUNTOS_ID, l);
		                	}
		                	if (xml.getName().equals("direccion"))
		                	{
		                		//Log.d("punto",xml.nextText());
		                		punto.put(PUNTOS_DIRECCION, xml.nextText());
		                	}
		                	if (xml.getName().equals("direccioneu"))
		                	{
		                		punto.put(PUNTOS_DIRECCION_EU, xml.nextText());
		                	}
		                	if (xml.getName().equals("nombre"))
		                	{
		                		punto.put(PUNTOS_NOMBRE, xml.nextText());
		                	}
		                	if (xml.getName().equals("nombreeu"))
		                	{
		                		punto.put(PUNTOS_NOMBRE_EU, xml.nextText());
		                	}
		                	if (xml.getName().equals("longitud"))
		                	{
		                		Double longitud = new Double(xml.nextText());
		                		punto.put(PUNTOS_LONGITUD, longitud);
		                	}
		                	if (xml.getName().equals("latitud"))
		                	{
		                		Double latitud = new Double(xml.nextText());
		                		punto.put(PUNTOS_LATITUD, latitud);
		                	}
		                	if (xml.getName().equals("idtipo"))
		                	{
		                		Long idtipo = new Long(xml.nextText());
		                		Log.d("d",idtipo.toString());
		                		punto.put(PUNTOS_ID_TIPO, idtipo);
		                	}
		                } else if(eventType == XmlPullParser.END_TAG) {
		                	if (xml.getName().equals("Punto"))
		                	{
		                		Log.d("punto",punto.toString());
		                		db.insert(PUNTOS_TABLE, null, punto);
		                		
		                	}
		                }
		                eventType = xml.next();
		               }

		        }
				catch(Exception ex)
				{
					Log.e("ERROR", ex.toString());
				}
				break;
			case 3:
				ContentValues linea = new ContentValues();
				Log.d("debugging","Entro a evaluar lineas...");
				try { 
		        	xml.next();
		        	int eventType = xml.getEventType();
		        	while (eventType != XmlPullParser.END_DOCUMENT) {
		                if(eventType == XmlPullParser.START_DOCUMENT) {
		                    
		                } else if(eventType == XmlPullParser.START_TAG) {
		                	if (xml.getName().equals("id"))
		                	{
		                		Long l= new Long(xml.nextText());
		                		linea.put(LINEAS_ID, l);
		                	}
		                	if (xml.getName().equals("nombre"))
		                	{
		                		linea.put(LINEAS_NOMBRE, xml.nextText());
		                	}
		                	if (xml.getName().equals("nombreeu"))
		                	{
		                		linea.put(LINEAS_NOMBRE_EU, xml.nextText());
		                	}
		                	if (xml.getName().equals("horainicio"))
		                	{
		                		linea.put(LINEAS_HORA_INICIO, xml.nextText());
		                	}
		                	if (xml.getName().equals("horafin"))
		                	{
		                		linea.put(LINEAS_HORA_FIN, xml.nextText());
		                	}
		                	if (xml.getName().equals("datos"))
		                	{
		                		linea.put(LINEAS_DATOS, xml.nextText());
		                	}
		                	if (xml.getName().equals("datoseu"))
		                	{
		                		linea.put(LINEAS_DATOS_EU, xml.nextText());
		                	}
		                	if (xml.getName().equals("frecuencia"))
		                	{
		                		Long frec = new Long(xml.nextText());
		                		linea.put(LINEAS_FRECUENCIA, frec);
		                	}
		                	if (xml.getName().equals("idtipo"))
		                	{
		                		Long idtipo = new Long(xml.nextText());
		                		linea.put(LINEAS_ID_TIPO, idtipo);
		                	}
		                } else if(eventType == XmlPullParser.END_TAG) {
		                	if (xml.getName().equals("Linea"))
		                	{
		                		Log.d("punto",linea.toString());
		                		db.insert(LINEAS_TABLE, null, linea);
		                	}
		                }
		                eventType = xml.next();
		               }

		        }
				catch(Exception ex)
				{
					Log.e("ERROR", ex.toString());
				}
				break;
			case 4:
				ContentValues lp = new ContentValues();

				try { 
		        	xml.next();
		        	int eventType = xml.getEventType();
		        	while (eventType != XmlPullParser.END_DOCUMENT) {
		                if(eventType == XmlPullParser.START_DOCUMENT) {
		                    
		                } else if(eventType == XmlPullParser.START_TAG) {
		                	if (xml.getName().equals("id"))
		                	{
		                		Long l= new Long(xml.nextText());
		                		lp.put(PUNTOS_LINEAS_ID, l);
		                	}
		                	if (xml.getName().equals("idlinea"))
		                	{
		                		Long l= new Long(xml.nextText());
		                		lp.put(PUNTOS_LINEAS_ID_LINEA, l);
		                	}
		                	if (xml.getName().equals("idpunto"))
		                	{
		                		Long l= new Long(xml.nextText());
		                		lp.put(PUNTOS_LINEAS_ID_PUNTO, l);
		                	}
		                } else if(eventType == XmlPullParser.END_TAG) {
		                	if (xml.getName().equals("PL"))
		                	{
		                		db.insert(PUNTOS_LINEAS_TABLE, null, lp);
		                	}
		                }
		                eventType = xml.next();
		               }

		        }
				catch(Exception ex)
				{
					Log.e("ERROR", ex.toString());
				}
				break;
			case 5:
				ContentValues puntob = new ContentValues();
				try { 
		        	xml.next();
		        	int eventType = xml.getEventType();
		        	while (eventType != XmlPullParser.END_DOCUMENT) {
		                if(eventType == XmlPullParser.START_DOCUMENT) {
		                    
		                } else if(eventType == XmlPullParser.START_TAG) {
		                	if (xml.getName().equals("id"))
		                	{
		                		Long l= new Long(xml.nextText());
		                		puntob.put(BICIS_ID, l);
		                	}
		                	if (xml.getName().equals("idpunto"))
		                	{
		                		Long idpunto = new Long(xml.nextText());
		                		puntob.put(BICIS_ID_PUNTO, idpunto);
		                	}
		                	if (xml.getName().equals("numerobicis"))
		                	{
		                		Long numbicis = new Long(xml.nextText());
		                		puntob.put(BICIS_NUMERO_BICIS, numbicis);
		                	}
		                	if (xml.getName().equals("descripcion"))
		                	{
		                		puntob.put(BICIS_DESCRIPCION, xml.nextText());
		                	}
		                	if (xml.getName().equals("telefono"))
		                	{
		                		puntob.put(BICIS_TELEFONO, xml.nextText());
		                	}
		                	if (xml.getName().equals("cp"))
		                	{
		                		puntob.put(BICIS_CP, xml.nextText());
		                	}
		                	if (xml.getName().equals("email"))
		                	{
		                		puntob.put(BICIS_EMAIL, xml.nextText());
		                	}
		                	if (xml.getName().equals("horainicio"))
		                	{
		                		puntob.put(BICIS_HORA_INICIO, xml.nextText());
		                	}
		                	if (xml.getName().equals("horafin"))
		                	{
		                		puntob.put(BICIS_HORA_FIN, xml.nextText());
		                	}
		                	if (xml.getName().equals("responsable"))
		                	{
		                		puntob.put(BICIS_RESPONSABLE, xml.nextText());
		                	}
		                } else if(eventType == XmlPullParser.END_TAG) {
		                	if (xml.getName().equals("PuntoBici"))
		                	{		                		
		                		long r = db.insert(BICIS_TABLE, null, puntob);
		                		Log.i("DebugBBDD","El id del puntoBici insertado es: "+r);
		                	}
		                }
		                eventType = xml.next();
		               }

		        }
				catch(Exception ex)
				{
					Log.e("ERROR", ex.toString());
				}
				break;
			default: 
				break;
		}
		
	}

}
