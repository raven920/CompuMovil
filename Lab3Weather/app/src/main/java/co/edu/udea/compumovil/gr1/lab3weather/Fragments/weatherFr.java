package co.edu.udea.compumovil.gr1.lab3weather.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.edu.udea.compumovil.gr1.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr1.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr1.lab3weather.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class weatherFr extends Fragment {
    private MyReceiver myReceiver;

    public static LocalBroadcastManager mBroadcastManager;
    final String  urlImage="http://openweathermap.org/img/w/";
    ImageView iconView;
    //Button getWeather;
    TextView city,humidity,temp,description,icon,ultimaVista;
    //Gson outGson;
    weatherPOJO wp;
   // private final String API_KEY="b5bba053e2710075bb43d91499ed270a";
    ImageLoader imageLoader= ImageLoader.getInstance();

    Calendar cal;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public weatherFr() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisview=inflater.inflate(R.layout.fragment_weather, container,false);
        getActivity().setTitle("Clima Computación Movil");

        city=(TextView) thisview.findViewById(R.id.txt_city);
        humidity=(TextView) thisview.findViewById(R.id.txt_humidity);
        temp=(TextView) thisview.findViewById(R.id.txt_temp);
        description=(TextView) thisview.findViewById(R.id.txt_description);
        //icon= (TextView) thisview.findViewById(R.id.txt_icon);
        ultimaVista=(TextView) thisview.findViewById(R.id.txt_actual);
        iconView=(ImageView) thisview.findViewById(R.id.icon_image);

       //getWeather=(Button)thisview.findViewById(R.id.btn_weather);
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        myReceiver = new MyReceiver();

        //Creating the filter
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.ACTION_CUSTOM);

        mBroadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
        mBroadcastManager.registerReceiver(myReceiver, filter);
        if ((savedInstanceState != null)
                && (savedInstanceState.getParcelable(MainActivity.OBJECT_WP) != null)) {
            wp=savedInstanceState.getParcelable(MainActivity.OBJECT_WP);
            Log.d("Saving",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
            updateUI(wp);
        }
        
        // Inflate the layout for this fragment
        return thisview;
    }



    @Override
    public void onDestroy() {
        mBroadcastManager.unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            wp=savedInstanceState.getParcelable(MainActivity.OBJECT_WP);
            Log.d("Saving",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
            updateUI(wp);
            // Restore last state for checked position.

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("Saving",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());

        outState.putParcelable(MainActivity.OBJECT_WP,wp);
        //Save the fragment's state here

    }


    public class MyReceiver extends BroadcastReceiver {

        private final String TAG = "weather.java";

        @Override
        public void onReceive(Context context, Intent intent) {
            // if(MainActivity.progress.isShowing()){
            //MainActivity.progress.dismiss();
            //}

            wp=intent.getParcelableExtra(MainActivity.OBJECT_WP);
            if(wp!=null){
                updateUI(wp);
                // Log.d("weather.java",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
            }

            Log.d(TAG, "INTENT RECEIVED");



            Toast.makeText(getContext(), " Se ha Actualizado el CLIMA ", Toast.LENGTH_SHORT).show();

        }
    }

    public void updateUI(weatherPOJO wp){
        this.city.setText("\t"+wp.getName());
        description.setText("\t"+ WordUtils.capitalize(wp.getWeather().get(0).getDescription()));
        temp.setText("\t"+Double.toString(wp.getMain().getTemp()));
        humidity.setText("\t"+Double.toString(wp.getMain().getHumidity()));
        imageLoader.displayImage(urlImage+wp.getWeather().get(0).getIcon()+".png",iconView);
        cal=Calendar.getInstance();
        ultimaVista.setText(sdf.format(cal.getTime()));



    }


}
