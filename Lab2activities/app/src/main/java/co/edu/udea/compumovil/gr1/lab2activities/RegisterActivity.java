package co.edu.udea.compumovil.gr1.lab2activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import co.edu.udea.compumovil.gr1.lab2activities.domain.system.dto.schema.Users;
import co.edu.udea.compumovil.gr1.lab2activities.services.DbBitmapUtility;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.DbHelper;
import co.edu.udea.compumovil.gr1.lab2activities.services.db.util.SHA256HashProvider;

public class RegisterActivity extends AppCompatActivity {

    private DbHelper dbHelper;
   private EditText usuario;
    private EditText contrasena;
    private EditText email;
    private EditText edad;
    private ImageView perfil;
    private final int PICK_IMAGE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DbHelper(this);
        usuario = (EditText)findViewById(R.id.usernameRegisterTxt);
        contrasena = (EditText)findViewById(R.id.passRegisterTxt);
        email = (EditText)findViewById(R.id.emailRegisterTxt);
        edad = (EditText) findViewById(R.id.edadTxt);
        perfil = (ImageView) findViewById(R.id.fotoPerfilReg);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Seleccione Foto de Perfil");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });
    }

    public void handleRegister(View v){
        boolean error = false;
        String usrStr = usuario.getText().toString().trim();
        String passStr = contrasena.getText().toString().trim();
        String emailStr = email.getText().toString().trim();


        Bitmap fotoBitmap = ((BitmapDrawable)perfil.getDrawable()).getBitmap();


        int edadUsr;
        try{
            edadUsr = Integer.parseInt(edad.getText().toString().trim());
        }catch(NumberFormatException nfe){
            error = true;
            edad.setError("Este campo contiene valores incorrectos.");
            return;
        }
        ContentValues contentValues;
        SQLiteDatabase db;

        if(edadUsr < 1 || edadUsr > 120){
            error = true;
            edad.setError("Usted debe estar vivo.");
        }

        if("".equals(usrStr)){
            error = true;
            usuario.setError("Este campo no puede estar vacío");
        }

        if("".equals(passStr)){
            error = true;
            contrasena.setError("Este campo no puede estar vacío");
        }

        if("".equals(emailStr)){
            error = true;
            email.setError("Este campo no puede estar vacío");
        }

        if(error){
            return;
        }

        if(existsInDB(Users.TABLE, Users.Column.NOMBRE_USUARIO, usrStr)){
            usuario.setError("El usuario ya existe en la base de datos.");
            return;
        }

        if(existsInDB(Users.TABLE, Users.Column.EMAIL, emailStr)){
            usuario.setError("Este email ya existe en la base de datos.");
            return;
        }

        contentValues = new ContentValues();
        contentValues.put(Users.Column.NOMBRE_USUARIO, usrStr);
        try {
            contentValues.put(Users.Column.CONTRASENA, SHA256HashProvider.getProvider().createHash(passStr));
        }catch(Exception e){
            contrasena.setError("Contraseña no válida.");
            return;
        }

        contentValues.put(Users.Column.EMAIL, emailStr);
        contentValues.put(Users.Column.EDAD, edadUsr);


        contentValues.put(Users.Column.FOTO, DbBitmapUtility.getBytes(fotoBitmap));

        db = dbHelper.getWritableDatabase();
        db.insertWithOnConflict(Users.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        Intent returnIntent = new Intent();
        setResult(AppCompatActivity.RESULT_OK,returnIntent);
        finish();
    }

    private boolean existsInDB(String table, String field, String value) {
        String[] values = new String[]{value};
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        String Query = "select * from " + table + " where "
                + field + " = ?";
        Cursor cursor = sqldb.rawQuery(Query, values);
        if(cursor == null || cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "No seleccionó una imagen", Toast.LENGTH_LONG).show();
                return;
            }
            //try {
                //InputStream inputStream = getContentResolver().openInputStream(data.getData());
            perfil.setImageURI(data.getData());

            /*} catch (FileNotFoundException e) {
                Toast.makeText(this, "Seleccionó una imagen inexistente", Toast.LENGTH_LONG).show();
            }*/
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
}
