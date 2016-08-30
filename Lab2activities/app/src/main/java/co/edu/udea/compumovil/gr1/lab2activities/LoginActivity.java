package co.edu.udea.compumovil.gr1.lab2activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Users;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.util.SHA256HashProvider;

public class LoginActivity extends AppCompatActivity {

    public static final int REGISTER_REQUEST = 50;

    public static final String LOGGED_USER = "loggedUser";
    public static final String TAG = "LoginTAG";
    public static final String SHARED_PREFS = "PlacesPrefs";

    private DbHelper dbHelper;
    private EditText usuario;
    private EditText contrasena;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usuario = (EditText) findViewById(R.id.usernameTxt);
        contrasena = (EditText) findViewById(R.id.passTxt);

        dbHelper = new DbHelper(this);

        SharedPreferences placesPrefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        if(placesPrefs != null && placesPrefs.contains(LOGGED_USER)){
            Intent i = new Intent(this, LugaresMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }


    public void onRegisterClick(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent,REGISTER_REQUEST);
    }

    public void onLoginClick(View v){
        boolean error = false;
        String usrStr = usuario.getText().toString().trim();
        String passStr = contrasena.getText().toString().trim();
        SQLiteDatabase db;

        if("".equals(usrStr)){
            error = true;
            usuario.setError("Este campo no puede estar vacío");
        }

        if("".equals(passStr)){
            error = true;
            contrasena.setError("Este campo no puede estar vacío");
        }
        if(error){
            return;
        }

        if(checkLogin(usrStr, passStr)){
            SharedPreferences placesPrefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = placesPrefs.edit();
            prefsEditor.putString(LOGGED_USER, usrStr);
            prefsEditor.commit();

            Intent i = new Intent(this, LugaresMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }else{
            Snackbar snack = Snackbar.make(v, "Usuario o contraseña incorrecto.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null);

            snack.getView().setBackgroundColor(getResources().getColor(R.color.colorAccent));
            snack.show();
        }
    }

    private boolean checkLogin(String user, String password) {
        String[] values = new String[]{user};
        String passHash;
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        String Query = "select * from " + Users.TABLE + " where "
                + Users.Column.NOMBRE_USUARIO + " = ?";
        Cursor cursor = sqldb.rawQuery(Query, values);
        if(cursor == null || cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        try {
            if (cursor.moveToFirst()) {
                passHash = cursor.getString(cursor.getColumnIndex(Users.Column.CONTRASENA));
                cursor.close();
                return SHA256HashProvider.getProvider().verifyPassword(password, passHash);
            }
        }catch(Exception e){}

        cursor.close();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REGISTER_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
            }
        }
    }
}
