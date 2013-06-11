package com.cvcetic.ciudadverde.utils;

import java.util.List;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;
import com.cvcetic.ciudadverde.beans.Punto;
import com.google.android.maps.ItemizedOverlay;

public class PuntoItemizedOverlay extends ItemizedOverlay<PuntoOverlayItem> {

	private List<PuntoOverlayItem> items;
	private final Context contexto;

	public PuntoItemizedOverlay(List<PuntoOverlayItem> items,
			Drawable MarcaDefecto, Context contexto) {
		super(boundCenterBottom(MarcaDefecto));
		this.items = items;
		Log.d("DebugPuntoItemized", "  Items en PuntoItemized  ----------> "
				+ items.size());
		Log.d("DebugPuntoItemized", String.format(
				"Items en PuntoItemized (%d)  Marca por defecto (%s)",
				+items.size(), MarcaDefecto.toString()));
		this.contexto = contexto;
		super.populate();
	}

	@Override
	protected boolean onTap(int i) {
		final Punto pt = this.items.get(i).punto;		
		String msg = String.format(" %s \r\n %s", pt.getNombre_es(),pt.getDireccion() );
		Toast toast = Toast.makeText(contexto, msg, Toast.LENGTH_LONG);		
		toast.show();
		return true;
	}

	@Override
	protected PuntoOverlayItem createItem(int i) {
		return this.items.get(i);
	}

	@Override
	public int size() {
		return this.items.size();
	}

}
