package com.vinaychitade.rsm.myhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView imageView,imageView3,imageView4;
    Button Emergencybtn;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView3=findViewById(R.id.textView3);
        imageView = findViewById(R.id.imageView);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        Emergencybtn = findViewById(R.id.Emergencybtn);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            textView3.setText("Welcome "+currentUser.getDisplayName());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent uploadredirect = new Intent(MainActivity.this, UploadprescripActivity.class);
                    startActivity(uploadredirect);
                    finish();
                }
            });

            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent hometocalldoctor = new Intent(MainActivity.this, CalldoctorActivity.class);
                    startActivity(hometocalldoctor);
                    finish();
                }
            });

            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Myprofile = new Intent(MainActivity.this, MyProfilebtnActivity.class);
                    startActivity(Myprofile);
                    finish();
                }
            });

            Emergencybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Emergenyclick = new Intent(MainActivity.this, Emergencybtn_Activity.class);
                    startActivity(Emergenyclick);
                    finish();
                }
            });


        }
        else {
            // User is not logged in, redirect to LoginActivity
            startActivity(new Intent(this, loginActivity.class));
            finish(); // Prevent the user from coming back to MainActivity using the back button
        }
    }


}