/**
 * Definición del adaptador para una lista de Lineas (linea).
 * 
 * @author Rubén Cabrera, Iker Zaldívar, David Santibañez, Javier Barambones
 */

package com.cvcetic.ciudadverde.adapters;

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

public class LineaTranviaAdapter extends BaseAdapter {

	private List<Punto> linea;
	private Context context;

	public LineaTranviaAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LineaTranviaAdapter(Context context, List<Punto> linea) {
		super();
		this.linea = linea;
		this.context = context;
	}

	public int getCount() {
		return linea.size();
	}

	public Punto getItem(int position) {
		return (null == linea) ? null : linea.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vista = inflater.inflate(R.layout.itemparadatranvia, null, true);
		TextView dir = (TextView) vista.findViewById(R.id.direccionparadatranvia);
		Log.d("DebugAdapter",
				String.format(
						"Se va introducir Linea %s en la vista item del adapter",
						linea.get(position).getNombre_es()));
		dir.setText(linea.get(position).getNombre_es());
		
		TextView dist = (TextView) vista.findViewById(R.id.distanciatranvia);
		dist.setText(String.format("%.0f m",
				((Punto) linea.get(position)).getDistancia()));

		return vista;

	}

	public List<Punto> getPunto() {
		return linea;
	}

	public void setP(List<Punto> linea) {
		this.linea = linea;
	}

	public void forceReload() {
		notifyDataSetChanged();
	}

}
