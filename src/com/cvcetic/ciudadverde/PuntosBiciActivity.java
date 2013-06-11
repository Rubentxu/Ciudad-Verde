package com.cvcetic.ciudadverde;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.cvcetic.ciudadverde.adapters.CentrosCivAdapter;
import com.cvcetic.ciudadverde.asynctask.DownloadWebTask;
import com.cvcetic.ciudadverde.beans.Punto;
import com.cvcetic.ciudadverde.beans.PuntoBici;

public class PuntosBiciActivity extends ListActivity {
	private ProgressDialog progreso;
	private List<Punto> pb;
	private CentrosCivAdapter cca;
	private Aplicacion app;
	private Button actualizar;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listcentrocivicos);
		pb = new ArrayList<Punto>();
		app = (Aplicacion) getApplication();
		
		conectar();
		cca = new CentrosCivAdapter(PuntosBiciActivity.this, pb);
		setListAdapter(cca);
		
		actualizar=(Button)findViewById(R.id.act);
		actualizar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				conectar();
				
			}
		});
	}
	
	@Override
	public void onResume(){
		super.onResume();
		animacion();
	}

	public class WebTask extends AsyncTask<String, Void, List<Punto>> {

		

		@Override
		protected List<Punto> doInBackground(String... urls) {
			return DownloadWebTask.cogerPuntosBiciPage(PuntosBiciActivity.this, urls);
		}

		@Override
		protected void onPostExecute(List<Punto> result) {
			progreso.dismiss();
			if (!result.isEmpty())
				pb=result;
				cca.setPbici(pb);
			Log.d("DebugPuntosBiciActivity", String.format(
					"Se recuperaron (%d)  centros civicos ", result.size()));
			cca.forceReload();

		}
	}

	public void conectar() {
		progreso = ProgressDialog.show(PuntosBiciActivity.this, "Conectando..",
				"Recuperando Datos.", true, false);
		WebTask task = new WebTask();
		//String url = "http://www.vitoria-gasteiz.org/we001/was/we001Action.do?idioma=es&menu=ciudad&menuInicio=menu10_07_07&urlDestino=/W11/WAS/disponibilidadAction.do";
		//String url = getString(R.string.urlbicis);
		String url = PreferenceManager.getDefaultSharedPreferences(this).getString("urlbicis", "http://www.vitoria-gasteiz.org/we001/was/we001Action.do?idioma=es&menu=ciudad&menuInicio=menu10_07_07&urlDestino=/W11/WAS/disponibilidadAction.do");
		Log.d("DebugPuntosBiciActivity",
				String.format("La url para conectar es %s: ", url));
		task.execute(new String[] { url });
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		PuntoBici mipunto = (PuntoBici)o;
		
		//String keyword = o.toString();
		//Toast.makeText(this, "Tu seleccion es : " + keyword, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(PuntosBiciActivity.this, BiciDetalles.class);
		intent.putExtra("id", mipunto.getId());
		intent.putExtra("numbicis", mipunto.getNumerobicis());
		startActivity(intent);
	}
	
	private void animacion() {
	    Animation a = AnimationUtils.loadAnimation(this, R.anim.translate);
	    a.reset();
	    LinearLayout iv = (LinearLayout) findViewById(R.id.llcc);
	    iv.clearAnimation();
	    iv.startAnimation(a);	 
	    
	}

	 

}
