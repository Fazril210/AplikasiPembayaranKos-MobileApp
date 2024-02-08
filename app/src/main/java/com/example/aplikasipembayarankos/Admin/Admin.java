package com.example.aplikasipembayarankos.Admin;
import com.example.aplikasipembayarankos.Admin.UserAdapter;
import com.example.aplikasipembayarankos.Admin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.aplikasipembayarankos.Entitas.User;
import com.example.aplikasipembayarankos.LoginDanRegister.Login;
import com.example.aplikasipembayarankos.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    private ImageView IVkeluar;
    private RecyclerView RVuser;

    private DatabaseReference database;

    private UserAdapter adapter;

    private ArrayList<User> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        IVkeluar = findViewById(R.id.IVkeluar);
        RVuser = findViewById(R.id.RVuser);

        IVkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        database = FirebaseDatabase.getInstance().getReference("Users");

        RVuser = findViewById(R.id.RVuser);
        RVuser.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RVuser.setLayoutManager(layoutManager);
        RVuser.setItemAnimator(new DefaultItemAnimator());

        tampilData();
    }

    private void tampilData(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<User>();

                for (DataSnapshot item : snapshot.getChildren()){

                    User user = new User();
                    user.setUsername(item.child("username").getValue(String.class));
                    user.setEmail(item.child("email").getValue(String.class));
                    user.setPassword(item.child("password").getValue(String.class));
                    arrayList.add(user);
                }
                adapter = new UserAdapter(arrayList, Admin.this);
                RVuser.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showLogoutConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Keluar");
        builder.setMessage("Apakah Anda yakin ingin keluar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Admin.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}