package co.edu.udea.compumovil.gr1.lab3weather.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import org.json.JSONObject;

import co.edu.udea.compumovil.gr1.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr1.lab3weather.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class weatherFr extends Fragment {

    //private MyReceiver myReceiver;
    ImageView iconView;
    Button getWeather;
    TextView city,humidity,temp,description,icon;
    Gson outGson;
    weatherPOJO wp;
    private final String API_KEY="b5bba053e2710075bb43d91499ed270a";
    ImageLoader imageLoader= ImageLoader.getInstance();

    public weatherFr() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisview=inflater.inflate(R.layout.fragment_weather, container,false);
        getActivity().setTitle("Clima");

        city=(TextView) thisview.findViewById(R.id.txt_city);
        humidity=(TextView) thisview.findViewById(R.id.txt_humidity);
        temp=(TextView) thisview.findViewById(R.id.txt_temp);
        description=(TextView) thisview.findViewById(R.id.txt_description);
        icon= (TextView) thisview.findViewById(R.id.txt_icon);
        iconView=(ImageView) thisview.findViewById(R.id.icon_image);
       getWeather=(Button)thisview.findViewById(R.id.btn_weather);


        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String ciudad="Medellin";
                String url ="http://api.openweathermap.org/data/2.5/weather?q="+ciudad+",CO&appid="+API_KEY+"&lang=en&units=metric";
            final String urlImage="http://openweathermap.org/img/w/";
// Request a string response from the provided URL.
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                //tvWeather.setText("Response: " + response.toString());
                                outGson=new Gson();
                                Log.d("weather.java",response.toString());
                                wp=outGson.fromJson(response.toString(),weatherPOJO.class);
                               // Log.d("weather.java",wp.getWeatherList().get(0).getDescription());

                                city.setText(wp.getName());
                                description.setText(wp.getWeather().get(0).getDescription());
                                temp.setText(Double.toString(wp.getMain().getTemp()));
                                humidity.setText(Double.toString(wp.getMain().getHumidity()));
                                icon.setText(wp.getWeather().get(0).getIcon());

                                imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
                                imageLoader.displayImage(urlImage+wp.getWeather().get(0).getIcon()+".png",iconView);
                               // Log.d("weather.java",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeatherList().get(0).getDescription()+wp.getWeatherList().get(0).getIcon());

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        });
// Add the request to the RequestQueue.
                queue.add(jsObjRequest);
            }
        });




        // Inflate the layout for this fragment
        return thisview;
    }

}
