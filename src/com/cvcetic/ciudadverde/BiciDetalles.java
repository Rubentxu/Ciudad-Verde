package com.cvcetic.ciudadverde;

import com.cvcetic.ciudadverde.beans.Punto;
import com.cvcetic.ciudadverde.beans.PuntoBici;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Clase para visualizar los detalles de un punto de entrega de bicicletas
 * @author Rub�n Cabrera, Iker Zaldivar, David Santiba�ez, Javier Barambones
 *
 */
public class BiciDetalles extends Activity {
	private Long id;
	private Integer bicis;
	private Aplicacion app;
	private TextView nombre;
	private ImageView imagen;
	private TextView direccion;
	private TextView numbicis;
	private TextView telefono;
	private TextView email;
	private TextView horario;
	private TextView responsable;
	private TextView descripcion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listparadasdetalle);
		
		Bundle extras = getIntent().getExtras();
		id = extras.getLong("id");
		bicis = extras.getInt("numbicis");
		
		app = (Aplicacion)getApplication();
		
		Punto p = app.loadPunto(id);
		
		PuntoBici pb = new PuntoBici(p);
		pb = app.loadPuntoEntregaBici(id);
		pb.setNumerobicis(bicis);		
		
		nombre = (TextView)findViewById(R.id.detalle1_nombre);
		nombre.setText(p.getNombre_es());
		
		imagen = (ImageView)findViewById(R.id.detalle1_imagen);
		imagen.setImageResource(R.drawable.bici);
		
		direccion = (TextView)findViewById(R.id.detalle1_direccion);
		direccion.setText(p.getDireccion());
		
		numbicis = (TextView)findViewById(R.id.detalle1_numbicis);
		numbicis.setText(pb.getNumerobicis().toString());
		
		telefono = (TextView)findViewById(R.id.detalle1_telefono);
		telefono.setText(pb.getTelefono());
		
		email = (TextView)findViewById(R.id.detalle1_email);
		email.setText(pb.getEmail());
		
		horario = (TextView)findViewById(R.id.detalle1_horario);
		horario.setText(pb.getHorainicio() + " - " + pb.getHorafin());
		
		responsable = (TextView)findViewById(R.id.detalle1_responsable);
		responsable.setText(pb.getResponsable());
		
		descripcion = (TextView)findViewById(R.id.detalle1_descripcion);
		descripcion.setText(pb.getDescripcion_es());
	}
	@Override
	public void onResume(){
		super.onResume();
		animacion();
	}
	
	private void animacion() {
	    Animation a = AnimationUtils.loadAnimation(this, R.anim.bicitranslate);
	    a.reset();
	    ImageView iv = (ImageView) findViewById(R.id.detalle1_imagen);
	    iv.clearAnimation();
	    iv.startAnimation(a);	 
	    
	}
}
