package com.cvcetic.ciudadverde;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.location.Location;
import android.util.Log;

import com.cvcetic.ciudadverde.beans.Punto;

/**
 * Clase para determinar los puntos más cercanos sin hacer uso de nungún api externo
 * @author Rubén Cabrera, Iker Zaldivar, David Santibañez, Javier Barambones
 *
 */
public class PuntosHandler {

	/**
	 * Metodo que devuelve los puntos más cercanos al punto indicado
	 * @param localizacion Objeto de tipo Punto con la localización actual
	 * @param puntos Listado de Puntos que hay que reordenar
	 * @param cantidad Numero de elementos que se van a devolver en el listado final
	 * @return Listado de Puntos ordenados
	 */
	public List<Punto> puntosCercanos(Punto localizacion, List<Punto> puntos,
			Integer cantidad) {
		Log.d("DebugInternet",
				String.format(
						"Numero de centros con Bicis en PuntosHandler : (%d) ", puntos.size()));
		
		float misdistancias[] = new float[puntos.size()];
		
		for (Punto punto : puntos) {
			
			Location.distanceBetween(localizacion.getLatitud(), localizacion.getLongitud(),
					punto.getLatitud(), punto.getLongitud(), misdistancias);
			punto.setDistancia(misdistancias[0]);
			Log.i("DebugInternet",String.format(
					"Punto: %s Lat: %.5f  Long: %.5f Distancia: %.5f", punto.getNombre_es(),
					punto.getLatitud(),punto.getLongitud(),punto.getDistancia()));
		}
		
		Collections.sort(puntos, new Comparator<Punto>() {
		    public int compare(Punto o1, Punto o2) {
		        return (o1.getDistancia()).compareTo(o2.getDistancia());
		    }
		});

		return puntos;
	}
}
