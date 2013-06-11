/**
 * Definición del item para un centro cívico (puntobici).
 * 
 * @author Rubén Cabrera, Iker Zaldívar, David Santibañez, Javier Barambones
 */

package com.cvcetic.ciudadverde.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvcetic.ciudadverde.R;
import com.cvcetic.ciudadverde.beans.PuntoBici;

public class CCListaItem extends RelativeLayout {

	private TextView nombre_es;
	private TextView numBicis;
	private PuntoBici pB;
	private Button iramapacc;
	private Button iradetallecc;
	private Context context;

	public CCListaItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	// el item de un centro civico (puntobici) contiene nombre, numero de bicis
	// disponibles, un boton para ir a ver detalles y otro para ir a verlo al
	// mapa
	protected void onFinishInflate() {
		super.onFinishInflate();
		nombre_es = (TextView) findViewById(R.id.nombrecc);
		numBicis = (TextView) findViewById(R.id.bicletasdisp);
		

		// hacia la activity que muestra el mapa del centro civico que
		// presta bicis
		iramapacc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// decomentar cuando se cree la act mapCC
				/*
				 * Intent intent = new Intent(context, mapCC.class);
				 * intent.putExtra("id", pB.getId());
				 * context.startActivity(intent);
				 */
			}
		});

		// hacia la activity que muestra los detalles del centro civico
		// que presta bicis
		iradetallecc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// decomentar cuando se cree la act detalleCC
				/*
				 * Intent intent = new Intent(context, detalleCC.class);
				 * intent.putExtra("id", pB.getId());
				 * context.startActivity(intent);
				 */
			}
		});

	}

	public void setCC(PuntoBici pB) {
		this.pB = pB;
		this.nombre_es.setText(pB.getNombre_es());
		this.numBicis.setText(pB.getNumerobicis());
	}

	public PuntoBici getPunto() {
		return pB;
	}

}
