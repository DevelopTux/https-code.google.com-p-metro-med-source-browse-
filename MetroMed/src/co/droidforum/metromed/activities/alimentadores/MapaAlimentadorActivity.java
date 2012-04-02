package co.droidforum.metromed.activities.alimentadores;

import co.droidforum.metromed.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MapaAlimentadorActivity extends Activity {
	 WebView mWebView = null;
	  
     @Override
     public void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         Bundle bundle = getIntent().getExtras();
         setContentView(R.layout.alimentadores_mapa);
         mWebView = (WebView)findViewById(R.id.webViewMapaAlimentadores);
         mWebView.getSettings().setBuiltInZoomControls(true);
         mWebView.loadDataWithBaseURL("file:///android_asset/", (String)bundle.get("imagen"), "text/html", "utf-8", null);
         
     }
}
