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
import com.cvcetic.ciudadverde.beans.Linea;

public class LineasTranvAdapter extends BaseAdapter {

	private List<Linea> linea;
	private Context context;

	public LineasTranvAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LineasTranvAdapter(Context context, List<Linea> linea) {
		super();
		this.linea = linea;
		this.context = context;
	}

	public int getCount() {
		return linea.size();
	}

	public Linea getItem(int position) {
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
		parent.setBackgroundResource(R.drawable.fondoitem);
		Log.d("DebugAdapter",
				String.format(
						"Se va introducir Linea %s en la vista item del adapter del Dialog",
						linea.get(position).getNombre_es()));
		dir.setText(linea.get(position).getNombre_es());	
		

		return vista;

	}

	public List<Linea> getLinea() {
		return linea;
	}

	public void setLinea(List<Linea> linea) {
		this.linea = linea;
	}

	public void forceReload() {
		notifyDataSetChanged();
	}

}
