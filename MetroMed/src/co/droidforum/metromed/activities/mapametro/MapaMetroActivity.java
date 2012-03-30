package co.droidforum.metromed.activities.mapametro;

import co.droidforum.metromed.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MapaMetroActivity extends Activity {
	 WebView mWebView = null;
	  
     @Override
     public void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);

         setContentView(R.layout.mapa_metro);
         mWebView = (WebView)findViewById(R.id.webViewMapa);
         mWebView.getSettings().setBuiltInZoomControls(true);
         mWebView.loadDataWithBaseURL("file:///android_asset/", "<img src='mapa_lineas.jpg' width='300' />", "text/html", "utf-8", null);
         
     }
}
