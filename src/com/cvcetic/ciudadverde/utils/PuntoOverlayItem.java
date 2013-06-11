package com.cvcetic.ciudadverde.utils;

import com.cvcetic.ciudadverde.beans.Punto;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class PuntoOverlayItem extends OverlayItem {

	public final GeoPoint point;
	public final Punto punto;

	public PuntoOverlayItem(GeoPoint point, Punto pt) {
		super(point, pt.getNombre_es(), pt.getDireccion());
		this.point= point;
		this.punto= pt;
	}

}
