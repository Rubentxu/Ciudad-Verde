/**
 * Definición del adaptador para una lista de CCListaItem.
 * 
 * @author Rubén Cabrera, Iker Zaldívar, David Santibañez, Javier Barambones
 */

package com.cvcetic.ciudadverde.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvcetic.ciudadverde.R;
import com.cvcetic.ciudadverde.beans.Punto;
import com.cvcetic.ciudadverde.beans.PuntoBici;

public class CentrosCivAdapter extends BaseAdapter {

	private List<Punto> pbici;
	private Context contexto;

	public CentrosCivAdapter(Context context, List<Punto> pb) {
		super();
		this.pbici =  pb;
		this.contexto = context;
	}

	@Override
	public int getCount() {
		return pbici.size();
	}

	@Override
	public Object getItem(int position) {

		return (pbici == null) ? null : pbici.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// un item de un ccivico contiene nombre, bicis disponibles, btnmapa,
		// btndetalles
		Log.d("DebugAdapter", "Entra en getView del Adapter CentrosCivAdapter");
		LayoutInflater inflater = (LayoutInflater) contexto
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vista = inflater.inflate(R.layout.itemcentrocivico, null, true);
		TextView nombrecc = (TextView) vista.findViewById(R.id.nombrecc);
		Log.d("DebugAdapter",
				String.format(
						"Se va introducir Centro civico %s en la vista item del adapter",
						pbici.get(position).getNombre_es()));
		nombrecc.setText(pbici.get(position).getNombre_es());
		TextView dist = (TextView) vista
				.findViewById(R.id.distancia);
		dist.setText(String.format("%.0f m",((PuntoBici) pbici.get(position)).getDistancia()));
		TextView bicletasdisp = (TextView) vista
				.findViewById(R.id.bicletasdisp);
		bicletasdisp.setText(Integer.toString(((PuntoBici) pbici.get(position))
				.getNumerobicis()));
		return vista;
	}

	public List<Punto> getPbici() {
		return pbici;
	}

	public void setPbici(List<Punto> pbici) {
		this.pbici = pbici;
	}

	public void forceReload() {
		notifyDataSetChanged();
	}

}
