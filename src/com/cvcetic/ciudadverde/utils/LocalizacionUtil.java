package com.cvcetic.ciudadverde.utils;

import android.location.Location;
import android.util.Log;

import com.cvcetic.ciudadverde.beans.Punto;
import com.google.android.maps.GeoPoint;
import java.text.DecimalFormat;
public class LocalizacionUtil {

    public static final String CLASSTAG = LocalizacionUtil.class.getSimpleName();
    public static final double MILLON = 1e6;
    private static final DecimalFormat DEC_FORMAT = new DecimalFormat("###.##");
    public static final GeoPoint VITORIA = new GeoPoint((int) (42.84944221384491 * LocalizacionUtil.MILLON ),
        (int) (-2.6754283905029297 * LocalizacionUtil.MILLON ));

    public static GeoPoint getGeoPoint(final Location loc) {
        int lat = (int) (loc.getLatitude() * LocalizacionUtil.MILLON);
        int lon = (int) (loc.getLongitude() * LocalizacionUtil.MILLON);
        return new GeoPoint(lat, lon);
    }   
  
    
    public static GeoPoint getGeoPointconPunto(Punto p) {
        Log.d("DebugUtils", LocalizacionUtil.CLASSTAG + " Cogiendo GeoPoint a traves de un Punto Beans " );
        GeoPoint returnPoint = null;
      
            int lat = (int) (p.getLatitud() * LocalizacionUtil.MILLON);
            int lon = (int) (p.getLongitud() * LocalizacionUtil.MILLON);
            returnPoint = new GeoPoint(lat, lon);
        
        return returnPoint;
    }

   
    public static String parsePoint(final double point, final boolean isLat) {
        Log.d("DebugUtils", LocalizacionUtil.CLASSTAG + " parsePoint - point - " + point + " isLat - " + isLat);
        String result = LocalizacionUtil.DEC_FORMAT.format(point);
        if (result.indexOf("-") != -1) {
            result = result.substring(1, result.length());
        }
       
        if (isLat) {
            if (point < 0) {
                result += "S";
            } else {
                result += "N";
            }
        }
       
        else {
            if (point < 0) {
                result += "W";
            } else {
                result += "E";
            }
        }
        Log.d("DebugUtils", LocalizacionUtil.CLASSTAG + " parsePoint result - " + result);
        return result;
    }
}
