package com.example.aplikasipembayarankos.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasipembayarankos.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditDataUser extends AppCompatActivity {

    private EditText ETusername, ETpassword, ETemail;
    private Button btnSimpan, btnBatal;
    private String username, email, password;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_user);

        ETusername = findViewById(R.id.ETusername);
        ETemail = findViewById(R.id.ETemail);
        ETpassword = findViewById(R.id.ETpassword);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        if(getIntent().hasExtra("username") && getIntent().hasExtra("email") && getIntent().hasExtra("password")){
            username = getIntent().getStringExtra("username");
            email = getIntent().getStringExtra("email");
            password = getIntent().getStringExtra("password");
        }

        ETusername.setText(username);
        ETemail.setText(email);
        ETpassword.setText(password);

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailBaru = ETemail.getText().toString();
                String passwordBaru = ETpassword.getText().toString();

                HashMap hashMap = new HashMap();
                hashMap.put("email", emailBaru);
                hashMap.put("password", passwordBaru);

                database.child(username).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getApplicationContext(), "Update Data User Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Admin.class));
                    }
                });

            }
        });
    }
}