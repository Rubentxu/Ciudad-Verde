/**
 * Presenta una pantalla inicial al usuario con una imagen a modo de portada, 
 * una breve descripción de la finalidad de aplicación y un botón para acceder 
 * a la pantalla principal del sistema.
 * 
 * @author Rubén Cabrera, Iker Zaldívar, David Santibañez, Javier Barambones
 */

package com.cvcetic.ciudadverde;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class portada extends Activity {

	private Aplicacion app;
	SharedPreferences preferences;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.port);
		app = (Aplicacion) getApplication();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	
	}
	@Override
	public void onResume(){
		super.onResume();
		animacion();
	}
	

	public void siguiente(View v){
		Intent i = new Intent(portada.this, MainActivity.class);
		startActivity(i);
	}
	
	private void animacion() {
	    Animation a = AnimationUtils.loadAnimation(this, R.anim.translate);
	    a.reset();
	    ImageView iv = (ImageView) findViewById(R.id.main_image);
	    iv.clearAnimation();
	    iv.startAnimation(a);
	 
	    a = AnimationUtils.loadAnimation(this, R.anim.translate);
	    a.reset();
	    TextView tv = (TextView) findViewById(R.id.location_title);
	    tv.clearAnimation();
	    tv.startAnimation(a);
	 
	    a = AnimationUtils.loadAnimation(this, R.anim.alpha);
	    a.reset();
	    Button bv = (Button) findViewById(R.id.port_sig);
	    bv.clearAnimation();	    
	    bv.startAnimation(a);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.preferences:
			Intent i = new Intent(portada.this, Preferencias.class);
			startActivity(i);
			Toast.makeText(this, "Selecciona la url de disponibilidad de bicicletas.",
				Toast.LENGTH_LONG).show();
			break;

		}
		return true;
	}
}
