package co.edu.udea.compumovil.gr1.lab3weather;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.edu.udea.compumovil.gr1.lab3weather.Fragments.settings;
import co.edu.udea.compumovil.gr1.lab3weather.Fragments.weatherFr;
import co.edu.udea.compumovil.gr1.lab3weather.POJO.weather;
import co.edu.udea.compumovil.gr1.lab3weather.Services.WeatherService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean active = false;
    public static String TIME_TAG="TIME";
    public static String CITY_TAG="CITY";
    public static  String ACTION_CUSTOM = "action.custom";
    public static String OBJECT_WP="OBJECT";
    public static int time=60;
    public static String ciudad="Medellin";
    public static ProgressDialog progress;
    public static final String PREFS_NAME = "MyPrefsFile";


    FragmentManager fm;

    Fragment fragmentWeather,fragmentSettings,fragmentOption;

    weather w =new weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//--------------------------------------------------------------------------------------
        if (!isMyServiceRunning()) {
            Intent i = new Intent(this, WeatherService.class);
            i.putExtra(MainActivity.TIME_TAG, time);
            this.startService(i);
            Log.d("weather","My service was not running");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fm=getSupportFragmentManager();
        if(savedInstanceState!=null){
            fragmentWeather = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");

        }else{
            fragmentWeather = new weatherFr();
        }
        fragmentWeather=new weatherFr();

        fm.beginTransaction()
                .replace(R.id.content_main,fragmentWeather)
                .commit();



    }
 //-----------------------------------------------------------------------








    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentWeather=null;
        fm=getSupportFragmentManager();


        if (id == R.id.nav_weather) {
            fragmentWeather=new weatherFr();
            // Handle the camera action
        } else if (id == R.id.nav_settings) {
            fragmentWeather=new settings();
        }
        fm.beginTransaction()
                .replace(R.id.content_main,fragmentWeather)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (WeatherService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "mContent", fragmentWeather);
    }
}
