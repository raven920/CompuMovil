package co.edu.udea.compumovil.gr1.lab2activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Users;
import co.edu.udea.compumovil.gr1.lab2activities.services.DbBitmapUtility;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;

public class LugaresMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = "LugaresMainTAG";
    private String userName = null;
    public static final String LOGGED_USER_TAG = "loggedUser";
    public static final String SHARED_PREFS = "PlacesPrefs";

    private static LugaresMainActivity instance;


    private FragmentManager fm;
    private TextView userTV, mailTV;
    private ImageView profilePhotoIV;

    private DbHelper dbHelper;


    public static LugaresMainActivity getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    public String getUserName() {
        return userName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LugaresMainActivity.getInstance(), NewPlaceActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fm = getSupportFragmentManager();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        userTV = (TextView) headerView.findViewById(R.id.usernameTV);
        mailTV = (TextView) headerView.findViewById(R.id.emailTV);
        profilePhotoIV = (ImageView) headerView.findViewById(R.id.userImageIV);

        SharedPreferences placesPrefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        dbHelper = new DbHelper(this);
        if(placesPrefs != null && placesPrefs.contains(LOGGED_USER_TAG)){
            userName = placesPrefs.getString(LOGGED_USER_TAG, null);
            if(userName != null){
                setUserNavDrawer(userName);


            }

        }
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = new LugaresSelectionFragment();
        ft.replace(R.id.lugaresFragmentContainer,f);
        ft.commit();
    }

    private void setUserNavDrawer(String user){
        SQLiteDatabase open = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+ Users.TABLE +" WHERE "+Users.Column.NOMBRE_USUARIO+" = ?";
        Cursor c = open.rawQuery(query, new String[]{user});
        Bitmap image;
        byte[] imageByte;
        if(c.moveToFirst()){
            mailTV.setText(c.getString(c.getColumnIndex(Users.Column.EMAIL)));
            imageByte = c.getBlob(c.getColumnIndex(Users.Column.FOTO));
            image = DbBitmapUtility.getImage(imageByte);
            profilePhotoIV.setImageBitmap(image);
        }
        userTV.setText(userName);
    }

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
        getMenuInflater().inflate(R.menu.lugares_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = new LugaresSelectionFragment();
        ft.replace(R.id.lugaresFragmentContainer,f);
        ft.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = null;


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_lugares){
            f = new LugaresSelectionFragment();
        }else if(id == R.id.nav_perfil){
            f = new UsuarioFragment();
        }else if( id == R.id.nav_acercade){
            f = new AcercaDeFragment();
        }
        else if(id == R.id.nav_logout){
            SharedPreferences placesPrefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = placesPrefs.edit();
            prefsEditor.remove(LOGGED_USER_TAG);
            prefsEditor.commit();
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        if(f != null){
            ft.replace(R.id.lugaresFragmentContainer,f);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
