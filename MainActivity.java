package com.botoninsultos;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import android.webkit.WebView;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
import android.util.Log;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.os.Handler;

public class MainActivity extends ActionBarActivity {

    TextToSpeech tts;
    ImageView boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS) {
                    Log.d("myapp", "TextToSpeech enabled");
                }

                Locale loc = new Locale("es", "",""); // "es" es para que pronuncie en espanol
                if(tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
                    tts.setLanguage(loc);
                }

                WebView adsDisplay = (WebView) findViewById(R.id.webView);
                adsDisplay.getSettings().setJavaScriptEnabled(true);
                adsDisplay.loadUrl("http://www.url.com/adsense");
                adsDisplay.setScrollbarFadingEnabled(true);

                boton = (ImageView) findViewById(R.id.imageView);

                final String insultos[]={"Eres un hijo de puta","Cara Mierda","Basurero"}; //aqui pongo todo el listado de insultos
                final Integer numero_insultos=insultos.length;

                boton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        boton.setImageResource(R.drawable.imagen_off);
                        Integer aleatorio = (int) Math.floor( Math.random()*numero_insultos + 1 );
                        tts.speak(insultos[aleatorio-1], TextToSpeech.QUEUE_FLUSH, null);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 1 seconds
                                boton.setImageResource(R.drawable.imagen_on);
                            }
                        }, 1000);

                    }
                });

            }

        });

        /*
        if( Build.VERSION.SDK_INT  >= 15 ){
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                public void onStart(String status) {

                }
                public void onDone(String status) {
                    //boton.setImageResource(R.drawable.imagen_on);
                }
                public void onError(String status) {

                }
            });
        } else {
            this.tts.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
                @Override
                public void onUtteranceCompleted(String utteranceId) {
                    //boton.setImageResource(R.drawable.imagen_on);
                }
            });
        }
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
