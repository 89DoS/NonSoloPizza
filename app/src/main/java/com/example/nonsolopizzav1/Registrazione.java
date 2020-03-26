package com.example.nonsolopizzav1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registrazione extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    EditText firstName, lastName, address, address1;
    Button saveBtn;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        address = findViewById(R.id.address);
        address1 =findViewById(R.id.address1);
        saveBtn = findViewById(R.id.saveBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference docRef = fStore.collection("clienti").document(userID);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()){
                    String nome = firstName.getText().toString();
                    String cognome = lastName.getText().toString();
                    String citta = address.getText().toString();
                    String via =address1.getText().toString();


                    Map<String,Object> clienti = new HashMap<>();
                    clienti.put("nome", nome);
                    clienti.put("cognome", cognome);
                    clienti.put("città", citta);
                    clienti.put("via", via);

                    docRef.set(clienti).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                finish();
                            }else{
                                Toast.makeText(Registrazione.this, "Dati mancanti", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }else {
                    Toast.makeText(Registrazione.this, "Tutti i campi sono richiesti", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

}

