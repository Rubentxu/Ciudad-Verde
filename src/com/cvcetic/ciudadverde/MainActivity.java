package com.cvcetic.ciudadverde;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.cvcetic.ciudadverde.adapters.LineasBusAdapter;
import com.cvcetic.ciudadverde.adapters.LineasTranvAdapter;
import com.cvcetic.ciudadverde.beans.Linea;
import com.cvcetic.ciudadverde.beans.Punto;
import com.cvcetic.ciudadverde.utils.LocalizacionUtil;
import com.cvcetic.ciudadverde.utils.PuntoItemizedOverlay;
import com.cvcetic.ciudadverde.utils.PuntoOverlayItem;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends MapActivity implements LocationListener{

	public static final String ADDRESS_RESULT = "address";
	public List<Linea> LineasBus ;	
	public List<Linea> LineasTranv ;	
	public Long OpcionLineaBusId;
	public Long OpcionLineaTranvId;
	public AlertDialog.Builder DialogLinea;	
	private MapView mapView;	
	private ImageButton btnBici;
	private ImageButton btnBus;	
	private ImageButton btnTranvia;	
	private Aplicacion app;
	private LocationManager locationManager;
	private MapController mapController;
	private ProgressDialog progressDialog;
	private ArrayList<PuntoOverlayItem> ptos;
	private PuntoItemizedOverlay puntoOverlay;
	private Drawable marcadorPuntos;
	private Drawable marcador;
	private GeoPoint gp;	
	private RadioButton radio_bici = null;
	private RadioButton radio_bus = null;
	private RadioButton radio_tranvia = null;

	private final Handler handler = new Handler() {

		public void handleMessage(final Message msg) {
			progressDialog.dismiss();
			Log.d("DebugMain",
					"  Entramos en el Handler la cantidad de puntos Overlay son "
							+ ptos.size());
			if (mapView.getOverlays().contains(puntoOverlay)) {
				mapView.getOverlays().remove(puntoOverlay);
			}
			puntoOverlay = new PuntoItemizedOverlay(ptos, marcadorPuntos,
					MainActivity.this);
			mapView.getOverlays().add(puntoOverlay);
			mapView.invalidate();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		app = (Aplicacion) getApplication();
		setUpViews();		
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (radio_bici!=null) radio_bici.setChecked(false);
		if (radio_bus!=null) radio_bus.setChecked(false);
		if (radio_tranvia!=null) radio_tranvia.setChecked(false);
	}
	

	private void setUpViews() {

		// addressText = (EditText) findViewById(R.id.address);
		LineasBus=app.loadLineasAutobus();
		LineasTranv=app.loadLineasTranvia();
		DialogLinea= new AlertDialog.Builder(MainActivity.this);
		mapView = (MapView) findViewById(R.id.map);
		mapView.setBuiltInZoomControls(true);
		btnBici = (ImageButton) findViewById(R.id.imageButton1);
		btnBici.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,
						PuntosBiciActivity.class);
				startActivity(i);
			}
		});
		btnBus = (ImageButton) findViewById(R.id.imageButton3);
		btnBus.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//Hacia la Activity de mostrar puntos de la LINEA
				Intent i = new Intent(MainActivity.this,
						PuntosLineaActivity.class);
				startActivity(i);

			}
		});
		
		btnTranvia = (ImageButton) findViewById(R.id.imageButton4);
		btnTranvia.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//Hacia la Activity de mostrar puntos del TRANVIA
				Intent i = new Intent(MainActivity.this,
						PuntosTranviaActivity.class);
				startActivity(i);

			}
		});

		radio_bici = (RadioButton) findViewById(R.id.radiobici);
		radio_bus = (RadioButton) findViewById(R.id.radiobus);
		radio_tranvia = (RadioButton) findViewById(R.id.radiotranvia);
		radio_bici.setOnClickListener(radio_listener);
		radio_bus.setOnClickListener(radio_listener);
		radio_tranvia.setOnClickListener(radio_listener);
		
		marcador = getResources().getDrawable(R.drawable.ic_mark);
		int markerWidth2 = marcador.getIntrinsicWidth();
		int markerHeight2 = marcador.getIntrinsicHeight();
		marcador.setBounds(0, markerHeight2, markerWidth2, 0);

		
		

	}
	
	@Override
	public void onStart(){
		super.onStart();
		this.locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		this.locationManager.getProvider(LocationManager.GPS_PROVIDER);
		if (locationManager!=null){
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 50, this);
		}
		GeoPoint ultimoPuntoConocido = cogerUltimoPuntoConocido();
		this.mapController = this.mapView.getController();

		this.mapController.setZoom(15);
		this.mapController.animateTo(ultimoPuntoConocido);
		marcarPosicion(ultimoPuntoConocido);
	}

	private GeoPoint cogerUltimoPuntoConocido() {
		GeoPoint punto = null;
		Location localizacion = this.locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (localizacion != null) {
			punto = LocalizacionUtil.getGeoPoint(localizacion);
			Log.d("DebugMain", String.format("Cogemos la posicion del GPS-- Lat: %d Long %d"
					,punto.getLatitudeE6(),punto.getLongitudeE6()));
		} else {
			Log.d("DebugMain",
					"Cogemos vitoria como posicion inicial por defecto");
			punto = LocalizacionUtil.VITORIA;
		}		
		return punto;
	}


	private View.OnClickListener radio_listener = new View.OnClickListener() {
		public void onClick(View v) {
			RadioButton rb = (RadioButton) v;
			rb.getId();
			switch (rb.getId()) {
			case R.id.radiobici:
				getDatosPuntos(R.id.radiobici);
				break;
			case R.id.radiobus:
				
				DialogLinea.setTitle("Escoja Linea de Autobus")
				.setAdapter(new LineasBusAdapter(MainActivity.this, LineasBus), 
						new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	Log.d("DebugMain",
								"  Linea de Autobus escogida es :"+(String) 
								LineasBus.get(item).getNombre_es());
				    	OpcionLineaBusId=(Long) LineasBus.get(item).getId();
				    	getDatosPuntos(R.id.radiobus);
				    }
				}).show();
				
				break;
			case R.id.radiotranvia:
				DialogLinea.setTitle("Escoja Linea de Tranvia")
				.setAdapter(new LineasTranvAdapter(MainActivity.this, LineasTranv), 
						new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	Log.d("DebugMain",
								"  Linea de Tranvia escogida es :"+(String) 
								LineasTranv.get(item).getNombre_es());
				    	OpcionLineaTranvId=(Long) LineasTranv.get(item).getId();
				    	getDatosPuntos(R.id.radiotranvia);
				    }
				}).show();
				
				break;
			default:
				break;
			}
		}
	};	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
		
	}

	private void getDatosPuntos(final int tipo) {
		Log.d("DebugMain",
				"  Entramos en el metodo getDatosPuntos para marcar en el mapa los puntos");
		this.progressDialog = ProgressDialog.show(this,
				"Procesando Datos . . .", "A�adiendo puntos en el Mapa", true,
				false);

		new Thread() {
			@Override
			public void run() {
				List<Punto> apb = null;
				if (tipo == R.id.radiobici) {
					marcadorPuntos = getResources().getDrawable(R.drawable.cc);
					int markerWidth = marcadorPuntos.getIntrinsicWidth();
					int markerHeight = marcadorPuntos.getIntrinsicHeight();
					marcadorPuntos.setBounds(0, markerHeight, markerWidth, 0);
					apb = app.loadPuntosBici();
				} 
				else
				{
					if (tipo == R.id.radiobus) {
						marcadorPuntos = getResources().getDrawable(R.drawable.parada);
						int markerWidth = marcadorPuntos.getIntrinsicWidth();
						int markerHeight = marcadorPuntos.getIntrinsicHeight();
						marcadorPuntos.setBounds(0, markerHeight, markerWidth, 0);
					apb = app.loadPuntosAutobusesLinea(OpcionLineaBusId);
					}
					else
					{
						marcadorPuntos = getResources().getDrawable(R.drawable.tranv);
						int markerWidth = marcadorPuntos.getIntrinsicWidth();
						int markerHeight = marcadorPuntos.getIntrinsicHeight();
						marcadorPuntos.setBounds(0, markerHeight, markerWidth, 0);
						apb = app.loadPuntosTranviasLinea(OpcionLineaTranvId);
					}
				}
				ptos = getPuntoOverlayItems(apb);
				handler.sendEmptyMessage(1);
			};
		}.start();
	}

	private void marcarPosicion(final GeoPoint point) {
		gp = point;
		MapOverlay mapOverlay = new MapOverlay();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(mapOverlay);
		app.setLocalizacion(point);
		mapView.invalidate();
	}

	private ArrayList<PuntoOverlayItem> getPuntoOverlayItems(List<Punto> puntos) {
		ArrayList<PuntoOverlayItem> PuntoOverylayItemList = new ArrayList<PuntoOverlayItem>();
		for (Punto pt : puntos) {

			GeoPoint gp = LocalizacionUtil.getGeoPointconPunto(pt);
			if (gp != null) {
				PuntoOverlayItem poi = new PuntoOverlayItem(gp, pt);
				PuntoOverylayItemList.add(poi);
			} else {
				Log.w("DebugMain",
						" Datos imcompletos para este punto  "
								+ pt.getNombre_es());
			}

		}
		return PuntoOverylayItemList;
	}

	class MapOverlay extends com.google.android.maps.Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// ---translate the GeoPoint to screen pixels---
			Point screenPts = new Point();
			mapView.getProjection().toPixels(gp, screenPts);

			// ---add the marker---
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_mark);
			canvas.drawBitmap(bmp, screenPts.x - 25, screenPts.y - 36, null);
			return true;
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		updateLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) { 
		Intent intent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}	

	@Override
	public void onProviderEnabled(String provider) {}
	
	protected void updateLocation(Location location){
		MapView mapView = (MapView) findViewById(R.id.map);
        MapController mapController = mapView.getController();
        GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
        mapController.animateTo(point);        
        mapController.setZoom(15);
        
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geoCoder.getFromLocation(
                point.getLatitudeE6()  / 1E6, 
                point.getLongitudeE6() / 1E6, 1);

            String address = "";
            if (addresses.size() > 0) {
                for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)
                   address += addresses.get(0).getAddressLine(i) + "\n";
            }

            Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {                
            e.printStackTrace();
        }           

       marcarPosicion(point);
	}


}