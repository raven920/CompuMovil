package co.edu.udea.compumovil.gr1.lab3weather.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import co.edu.udea.compumovil.gr1.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr1.lab3weather.POJO.weather;
import co.edu.udea.compumovil.gr1.lab3weather.POJO.weatherPOJO;

/**
 * Created by gary on 24/09/2016.
 */
public class WeatherService extends Service {


    private static final String TAG ="WeatherService.java";
    private final String API_KEY="b5bba053e2710075bb43d91499ed270a";
    Gson outGson;
    weather w=new weather();
    public weatherPOJO wp;
    TimerTask timerTask;

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer= new Timer();
        //DEFINIR VARIABLE
        /*int time=intent.getIntExtra(MainActivity.CITY_TAG,50);
        String ciudad =intent.getStringExtra(MainActivity.CITY_TAG);*/


        timerTask =new TimerTask() {
            @Override
            public void run() {
                RequestQueue queue= Volley.newRequestQueue(getBaseContext());
                String url="http://api.openweathermap.org/data/2.5/weather?q=medellin,CO&appid="+API_KEY+"&lang=en&units=metric";


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        outGson=new Gson();
                        wp=outGson.fromJson(response.toString(),weatherPOJO.class);
                        Log.d(TAG,wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
                        Intent intent = new Intent();
                        intent.setAction(MainActivity.ACTION_CUSTOM);
                        intent.putExtra(MainActivity.OBJECT_WP,wp);
                        // weather.mBroadcastManager.sendBroadcastSync(intent);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
                queue.add(jsonObjectRequest);


            }








        };

        timer.scheduleAtFixedRate(timerTask, 0, 10*1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;


    }






    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
