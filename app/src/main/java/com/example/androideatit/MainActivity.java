package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // khai bao cac control
    Button btnSignIn, btnSignUp;
    TextView txtSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ********** Anh xa cac control **********
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        txtSlogan=(TextView)findViewById(R.id.txtSlogan);

        Typeface faceSLogan = Typeface.createFromAsset(getAssets(), "fonts/Parisienne-Regular.ttf");
        txtSlogan.setTypeface(faceSLogan);

        Typeface faceBtn = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Medium.ttf");
        btnSignUp.setTypeface(faceBtn);
        btnSignIn.setTypeface(faceBtn);

        // ********** Begin: Event control **********
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, SignIn.class);
                startActivity(signIn);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUp);
            }
        });
        // ********** End: Event control **********
    }
}