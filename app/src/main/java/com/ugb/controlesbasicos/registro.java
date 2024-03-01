package com.ugb.controlesbasicos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class registro extends AppCompatActivity {
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://prueba-9b957-default-rtdb.firebaseio.com/");
    EditText txtRegistroEmail;
    EditText txtRegistroContraseña;
    Button btnRegistrarUsuario;
    utilidades utls;
    detectarInternet di;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        di = new detectarInternet(getApplicationContext());
        utls = new utilidades();

        txtRegistroEmail = findViewById(R.id.txtRegistroEmail);
        txtRegistroContraseña = findViewById(R.id.txtRegistroContraseña);
        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);

        mAuth = FirebaseAuth.getInstance();

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtRegistroEmail.getText().toString();
                String password = txtRegistroContraseña.getText().toString();
                startActivity(new Intent(registro.this, inicio_sesion.class));

                createAccount(email, password);
            }
        });
    }
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registro exitoso
                            Intent intent = new Intent(registro.this, inicio_sesion.class);
                            startActivity(intent);
                        } else {
                            // Error de registro
                            Toast.makeText(registro.this, "Error al registrarse",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}