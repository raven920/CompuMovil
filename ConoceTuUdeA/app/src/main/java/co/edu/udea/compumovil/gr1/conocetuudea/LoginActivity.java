package co.edu.udea.compumovil.gr1.conocetuudea;


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
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.udea.compumovil.gr1.conocetuudea.domain.db.User;

public class LoginActivity extends AppCompatActivity {

    public static final int LOGIN_REQ = 50;

    private static boolean persistedEnabled = false;

    public static final String LOGGED_USER = "loggedUser";
    public static final String TAG = "LoginTAG";
    public static final String SHARED_PREFS = "PlacesPrefs";

    //private DbHelper dbHelper;
    private EditText usuario;
    private EditText contrasena;

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);


        //usuario = (EditText) findViewById(R.id.usernameTxt);
        //contrasena = (EditText) findViewById(R.id.passTxt);

        /*
        dbHelper = new DbHelper(this);

        SharedPreferences placesPrefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        if(placesPrefs != null && placesPrefs.contains(LOGGED_USER)){
            Intent i = new Intent(this, LugaresMainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }*/

        auth = FirebaseAuth.getInstance();
        if(!LoginActivity.persistedEnabled){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            persistedEnabled = true;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();


        checkCharacter();


    }

    public void checkCharacter(){

        if(auth.getCurrentUser() == null){
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER
                            )
                            .build(),
                    LOGIN_REQ);
            return;
        }

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent i;
                // Get Post object and use the values to update the UI

                User user = dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).getValue(User.class);
                if(user == null){
                    user= new User(auth.getCurrentUser().getDisplayName(), auth.getCurrentUser().getEmail(), -1, 0);
                    mDatabase.child("users").child(auth.getCurrentUser().getUid()).setValue(user);
                }

                if(user.getCharId() != -1){
                    i = new Intent(LoginActivity.this, MainActivity.class);

                }else{
                    i = new Intent(LoginActivity.this, CharacterSelectActivity.class);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Error while reading data", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(userListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == LOGIN_REQ) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                checkCharacter();
            }else{
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setProviders(
                                        AuthUI.EMAIL_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER
                                        )
                                .build(),
                        LOGIN_REQ);
            }
        }
    }
}