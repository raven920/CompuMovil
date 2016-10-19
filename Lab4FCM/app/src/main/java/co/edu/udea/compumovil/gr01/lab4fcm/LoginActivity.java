package co.edu.udea.compumovil.gr01.lab4fcm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button Ir_Chat;
    private Button Logout;
    private TextView nomusuario;
    private int RC_SIGN_IN=50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Ir_Chat=(Button) findViewById(R.id.btn_ir_chat);
        Logout=(Button) findViewById(R.id.logout);

        nomusuario=(TextView) findViewById(R.id.datauser);

        Ir_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irChat=new Intent(LoginActivity.this,ChatActivity.class);
                startActivity(irChat);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(LoginActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // mensaje de deslogueo exitoso
                        Toast.makeText(LoginActivity.this,"Ha salido de la aplicacion",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


            }
        });

        if (auth.getCurrentUser() != null) {
            // StartActivity(Chats.lass)
            Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT);
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.GOOGLE_PROVIDER
                            )
                            .build(),
                    RC_SIGN_IN);
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!

                FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
                nomusuario.setText(currentUser.getDisplayName());

                //startActivity(new Intent(this, ChatActivity.class));
                // finish();
                Toast.makeText(this,"Se ha logueado exitosamente",Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                Toast.makeText(this,"Intente de nuevo más tarde",Toast.LENGTH_SHORT);
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
            }
        }
    }
}
