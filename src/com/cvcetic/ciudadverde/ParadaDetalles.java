/**
 * Detalles de la parada del bus. 
 */

package com.cvcetic.ciudadverde;

import com.cvcetic.ciudadverde.beans.Linea;
import com.cvcetic.ciudadverde.beans.Punto;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ParadaDetalles extends Activity {

	private Long id;
	private Aplicacion app;

	private TextView nombre_es;
	private TextView datos;
	private TextView distancia;
	private TextView frec;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listparadasbusdetalle);

		Bundle extras = getIntent().getExtras();
		id = extras.getLong("id");

		app = (Aplicacion) getApplication();

		Punto p = app.loadPunto(id);

		nombre_es = (TextView) findViewById(R.id.detalle2_nombre);
		nombre_es.setText(p.getNombre_es());
		
		datos = (TextView) findViewById(R.id.detalle2_datos);
		datos.setText(p.getNombre_eu());

		//distancia = (TextView) findViewById(R.id.detalle2_distancia);
		//distancia.setText(p.getDistancia());

	}

}
