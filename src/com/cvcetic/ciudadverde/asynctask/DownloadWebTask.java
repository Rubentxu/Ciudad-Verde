package com.cvcetic.ciudadverde.asynctask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.util.Log;

import com.cvcetic.ciudadverde.Aplicacion;
import com.cvcetic.ciudadverde.PuntosHandler;
import com.cvcetic.ciudadverde.beans.Punto;
import com.cvcetic.ciudadverde.beans.PuntoBici;

public class DownloadWebTask {

	public static List<Punto> cogerPuntosBiciPage(Context contexto,
			String... urls) {
		Aplicacion app = (Aplicacion) contexto.getApplicationContext();
		
		Punto localizacion= new Punto((float)app.getLocalizacion().getLatitudeE6() / 1000000F,
				(float)app.getLocalizacion().getLongitudeE6() / 1000000F);
		Log.d("DebugInternet",
				"Entra en DownloadWebTask cogerPuntosBiciPage---->");
		String html = "<h3>Disponibilidad</h3><p>Disponibilidad de bicicletas en los distintos puntos de prestamo:</p><table summary='Disponibilidad de bicicletas en los distintos puntos de prestamo' class='estrecha' cellpadding='0' cellspacing='0'><caption>Disponibilidad</caption><tbody><tr><th id='tipo' class='sinbordeL izquierda'>Ubicacion</th><th id='obras' class='derecha sinbordeR'>Disponibles</th>"+
				"</tr><tr><td headers='tipo' class='bordeTop'>Centro Comercial Boulevard  </td><td headers='obras' class='centro sinbordeR bordeTop'>10</td></tr><tr><td headers='tipo'>Oficina Municipal de Turismo</td><td headers='obras' class='centro sinbordeR'>7</td></tr><tr><td headers='tipo'>Polideportivo de Mendizorrotza</td><td headers='obras' class='centro sinbordeR'>11</td></tr><tr><td headers='tipo'>Centro Civico Hegoalde"+      
				"</td><td headers='obras' class='centro sinbordeR'>3</td></tr><tr><td headers='tipo'>Polideportivo Abetxuko</td><td headers='obras' class='centro sinbordeR'>7</td></tr><tr><td headers='tipo'>Centro Civico Iparralde</td><td headers='obras' class='centro sinbordeR'>2</td></tr><tr><td headers='tipo'>Centro Civico Judimendi	</td><td headers='obras' class='centro sinbordeR'>3</td></tr><tr><td headers='tipo'>"+
				"Centro Civico Lakua</td>	<td headers='obras' class='centro sinbordeR'>3</td></tr><tr><td headers='tipo'>Centro Civico Aldabe</td><td headers='obras' class='centro sinbordeR'>11</td></tr><tr><td headers='tipo'>	Polideportivo Aranalde	</td><td headers='obras' class='centro sinbordeR'>5</td></tr> <tr><td headers='tipo'>Centro-Museo Artium </td><td headers='obras' class='centro sinbordeR'>	0</td> </tr>	        <tr>"+
				"<td headers='tipo'>	Lakua Center</td><td headers='obras' class='centro sinbordeR'>5</td></tr><tr><td headers='tipo'>Polideportivo Ariznabarra</td><td headers='obras' class='centro sinbordeR'>5</td></tr><tr> <td headers='tipo'>Centro Civico El Pilar</td><td headers='obras' class='centro sinbordeR'>16	</td></tr><tr><td headers='tipo'>Centro Civico Arriaga</td><td headers='obras' class='centro sinbordeR'>"+
				"3</td></tr><tr><td headers='tipo'>Centro Civico Ibaiondo</td><td headers='obras' class='centro sinbordeR'>7	</td> </tr> <tr><td headers='tipo'>	Centro Sociocultural de San Martin</td><td headers='obras' class='centro sinbordeR'>3</td></tr><tr><td headers='tipo'>Centro Comercial Leclerc</td><td headers='obras' class='centro sinbordeR'>5</td></tr>	</tbody></table>";
		

		Element tabla = null;
		List<Punto> listaP =  app.loadPuntosBici();
		Log.d("DebugInternet",
				String.format(
						"Numero de centros con Bicis : (%d) ", listaP.size()));
		List<Punto> listaPB =new ArrayList<Punto>();
		
		Document doc = null;
		
		for (String url : urls) {
			try {
				Log.d("DebugInternet", "url es  " + url);
				doc = Jsoup.connect(url).timeout(15000).get();
			
			} catch (IOException e) {
				Log.e("ERROR JSOUP CONNECT-->","Fallo Jsoup");
				//doc= Jsoup.parse(html);
			}
			if (doc != null) {

				tabla = doc.getElementsByClass("estrecha").first();
				Elements trs = tabla.select("tr");

				Log.d("DebugInternet", String.format(
						"Hay : (%d) elementos tag tr", trs.size()));
				int i=0;
				for (Element tr : trs) {
					Elements tds = tr.select("td");
					
					if (!tds.isEmpty()) {
						Log.d("DebugInternet", String.format(
								"Centro : %s <-----> numero bicis: %s ", tds
										.get(0).text(), tds.get(1).text()));
						
						PuntoBici pb= new PuntoBici(listaP.get(i));						
						pb.setNombre_es(tds.get(0).text());						
						pb.setNumerobicis(Integer.parseInt(tds.get(1).text()));
						pb.setLongitud(listaP.get(i).getLongitud());
						pb.setLatitud(listaP.get(i).getLatitud());
						listaPB.add(pb);
						i++;
						
					}
					
				}
			}

		}

		Log.d("DebugInternet",
				String.format("Hay (%d)  centros civicos ", listaPB.size()));
		
		return new PuntosHandler().puntosCercanos(localizacion, listaPB, listaPB.size());

	}

}
