/**
 * Definición del item para una parada en la línea de autobuses (puntobici).
 * 
 * @author Rubén Cabrera, Iker Zaldívar, David Santibañez, Javier Barambones
 */

package com.cvcetic.ciudadverde.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvcetic.ciudadverde.R;
import com.cvcetic.ciudadverde.beans.Punto;

public class LineaListItem extends LinearLayout {

	private TextView direccion;
	// private TextView proxBus;
	private TextView nombre_es;
	private Punto p;
	private Button iramapa;
	private Button iradetalle;
	private Context context;

	public LineaListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	// el item de una parada (punto) contiene direccion, nombre, un boton para
	// ir a ver detalles y otro para ir a verlo al
	// mapa
	protected void onFinishInflate() {
		super.onFinishInflate();
		direccion = (TextView) findViewById(R.id.direccionparada);
		// proxBus = (TextView) findViewById(R.id.proxbus);
		//iradetalle = (Button) findViewById(R.id.iradetalle);
		//iramapa = (Button) findViewById(R.id.iramapa);

		// hacia la activity que muestra el mapa
		// comentado hasta crear la act mapPunto
		

	}

	public void setPunto(Punto p) {
		this.p = p;
		this.direccion.setText(p.getDireccion());
		this.nombre_es.setText(p.getNombre_es());
	}

	public Punto getPunto() {
		return p;
	}

}
