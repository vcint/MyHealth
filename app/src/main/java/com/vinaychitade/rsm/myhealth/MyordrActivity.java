package com.vinaychitade.rsm.myhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyordrActivity extends AppCompatActivity {
    ImageView imageView2, imageView3, imageView4;
    private DatabaseReference ordersRef;
    private List<Order> ordersList;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myordr);

        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        ordersRef = firebaseDatabase.getReference();

        recyclerView = findViewById(R.id.recyclerView);
        ordersList = new ArrayList<>();
        orderAdapter = new OrderAdapter(ordersList);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

        // Retrieve and display orders
        retrieveOrders();

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadredirect = new Intent(MyordrActivity.this, UploadprescripActivity.class);
                startActivity(uploadredirect);
                finish();
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hometocalldoctor = new Intent(MyordrActivity.this, CalldoctorActivity.class);
                startActivity(hometocalldoctor);
                finish();
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Myprofile = new Intent(MyordrActivity.this, MyProfilebtnActivity.class);
                startActivity(Myprofile);
                finish();
            }
        });
    }

    private void retrieveOrders() {
        String currentUserEmail = getCurrentUserEmail(); // Replace with your method to get current user's email

        ordersRef.child("orders").orderByChild("userId").equalTo(currentUserEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ordersList.clear();
                        for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                            String userId = orderSnapshot.child("userId").getValue(String.class);
                            String imageUrl = orderSnapshot.child("imageUrl").getValue(String.class);
                            boolean isPendingOrder = false; // Assuming orders are not pending
                            ordersList.add(new Order(userId, imageUrl, isPendingOrder));
                        }
                        orderAdapter.notifyDataSetChanged();
                        retrievePendingOrders();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
    }

    private void retrievePendingOrders() {
        String currentUserEmail = getCurrentUserEmail(); // Replace with your method to get current user's email

        ordersRef.child("pending_orders").orderByChild("userId").equalTo(currentUserEmail)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                            String userId = orderSnapshot.child("userId").getValue(String.class);
                            String imageUrl = orderSnapshot.child("imageUrl").getValue(String.class);
                            boolean isPendingOrder = true; // Orders from pending_orders collection
                            ordersList.add(0, new Order(userId, imageUrl, isPendingOrder)); // Add at the beginning
                        }
                        orderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
    }

    private String getCurrentUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String user=currentUser.getEmail();
        return user;
    }
}