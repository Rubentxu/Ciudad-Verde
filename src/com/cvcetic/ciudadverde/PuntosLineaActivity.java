/**
 * 
 * @author autoformacion
 *
 */
package com.cvcetic.ciudadverde;

import java.util.List;
import com.cvcetic.ciudadverde.adapters.LineaAdapter;
import com.cvcetic.ciudadverde.beans.Punto;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class PuntosLineaActivity extends ListActivity {

	// Adaptador de Linea
	private LineaAdapter ta;
	private Aplicacion app;
	private List<Punto> apb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listparadas);
		app = (Aplicacion) getApplication();
		Punto localizacion= new Punto((float)app.getLocalizacion().getLatitudeE6() / 1000000F,
				(float)app.getLocalizacion().getLongitudeE6() / 1000000F);
		apb = app.getpAutobuses();
		apb=new PuntosHandler().puntosCercanos(localizacion, apb, apb.size());
		ta = new LineaAdapter(PuntosLineaActivity.this, apb);
		setListAdapter(ta);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ta.forceReload();
	}
	
	/**
	 * Va a mostrar los detalles de la parada de tranvia seleccionada
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*super.onListItemClick(l, v, position, id);

		Object o = this.getListAdapter().getItem(position);
		Punto mipunto = (Punto) o;

		Intent intent = new Intent(PuntosLineaActivity.this,
				ParadaDetalles.class);
		intent.putExtra("id", mipunto.getId());
		startActivity(intent);*/
	}

}
