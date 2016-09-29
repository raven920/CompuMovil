package co.edu.udea.compumovil.gr1.lab3weather.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import co.edu.udea.compumovil.gr1.lab3weather.Fragments.weatherFr;
import co.edu.udea.compumovil.gr1.lab3weather.MainActivity;
import co.edu.udea.compumovil.gr1.lab3weather.MyWidgetProvider;
import co.edu.udea.compumovil.gr1.lab3weather.POJO.weather;
import co.edu.udea.compumovil.gr1.lab3weather.POJO.weatherPOJO;
import co.edu.udea.compumovil.gr1.lab3weather.R;
import co.edu.udea.compumovil.gr1.lab3weather.Singleton.MySingleton;

/**
 * Created by gary on 24/09/2016.
 */
public class WeatherService extends Service {


    private static final String TAG ="WeatherService.java";
    private final String API_KEY="b5bba053e2710075bb43d91499ed270a";
    Gson outGson;
    weatherFr w= new weatherFr();
    public weatherPOJO wp;
    TimerTask timerTask;
    public  int tiempo=MainActivity.time;
    public  String ciudad=MainActivity.ciudad;
    Timer timer= new Timer();


    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio CREADO...");
        super.onCreate();



            timerCreate();

            //EL TIEMPO HAY QUE CAMBIARLO EN LOS SETTINGS
        Schedule();
    }

    public void timerCreate(){
        timerTask =new TimerTask() {
            @Override
            public void run() {
                //RequestQueue queue= Volley.newRequestQueue(getBaseContext());
                String url="http://api.openweathermap.org/data/2.5/weather?q="+ciudad+",CO&appid="+API_KEY+"&lang=en&units=metric";


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        outGson=new Gson();
                        wp=outGson.fromJson(response.toString(),weatherPOJO.class);
                        Log.d("weather.java",wp.getName()+wp.getMain().getTemp()+wp.getMain().getHumidity()+wp.getWeather().get(0).getDescription()+wp.getWeather().get(0).getIcon());
                        Intent intent = new Intent();
                        intent.setAction(MainActivity.ACTION_CUSTOM);
                        intent.putExtra(MainActivity.OBJECT_WP,wp);
                        // weather.mBroadcastManager.sendBroadcastSync(intent);

                        if(MainActivity.active) {
                            weatherFr.mBroadcastManager.sendBroadcastSync(intent);
                        }else{

                            //codigo para Notificacion

                            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                            PendingIntent resultPendingIntent =
                                    PendingIntent.getActivity(
                                            getApplicationContext(),
                                            1,
                                            resultIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                            StringBuilder contenido =new StringBuilder();
                            contenido.append("Ciudad: ");
                            contenido.append(WordUtils.capitalize(wp.getName()));
                            contenido.append("\nTemperatura: ");
                            contenido.append((int)wp.getMain().getTemp());
                            contenido.append("°C");
                            contenido.append("\nHumedad: ");
                            contenido.append((int)wp.getMain().getHumidity());
                            contenido.append("%");
                            contenido.append("\nDescripción: ");
                            contenido.append(WordUtils.capitalize(wp.getWeather().get(0).getDescription()));

                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(getApplicationContext())
                                            .setSmallIcon(R.drawable.rainy)
                                            .setContentTitle("Clima Para este Dia")
                                            .setContentText(WordUtils.capitalize(wp.getWeather().get(0).getDescription()))
                                            .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                                            .setContentIntent(resultPendingIntent)
                                            .setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText(contenido.toString()));



                            NotificationManager notificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0, mBuilder.build() );

                        }





                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
                // queue.add(jsonObjectRequest);

                MySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
            }

        };

    }

    public void Schedule(){
        timer.scheduleAtFixedRate(timerTask, 0, tiempo*1000);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getBooleanExtra(("WID"),true)){
            timerTask.cancel();
            try {
                tiempo = intent.getIntExtra(MainActivity.TIME_TAG, 60);

                if (intent.getStringExtra(MainActivity.CITY_TAG) != null) {
                    ciudad = intent.getStringExtra(MainActivity.CITY_TAG);
                } else {
                    ciudad = MainActivity.ciudad;
                }
            } catch (NullPointerException e) {
                tiempo = 60;
                ciudad = "Medellin";
            }
            timerCreate();
            Schedule();

        }else {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                    .getApplicationContext());

            int[] allWidgetIds = intent
                    .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);



            for (int widgetId : allWidgetIds) {
                // create some random data

                RemoteViews remoteViews = new RemoteViews(this
                        .getApplicationContext().getPackageName(),
                        R.layout.my_widget_provider);


                remoteViews.setTextViewText(R.id.ciudad,wp.getName());
                remoteViews.setTextViewText(R.id.clima,String.valueOf(wp.getMain().getTemp()));
                remoteViews.setTextViewText(R.id.descripcion, WordUtils.capitalize(wp.getWeather().get(0).getDescription()));

                // Register an onClickListener
                Intent clickIntent = new Intent(this.getApplicationContext(),
                        MyWidgetProvider.class);

                clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                        allWidgetIds);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 0, clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }

        return START_REDELIVER_INTENT;
    }

    public WeatherService() {

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
