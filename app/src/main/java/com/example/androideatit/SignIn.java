package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    // Khai bao cac control
    EditText edtPhone, edtPassword;
    Button btnSignIn;

    private void StyleControl(){
        Typeface faceText = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        edtPhone.setTypeface(faceText);
        edtPassword.setTypeface(faceText);

        Typeface faceBtn = Typeface.createFromAsset(getAssets(), "fonts/FjallaOne-Regular.ttf");
        btnSignIn.setTypeface(faceBtn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // ***** Begin: Anh xa cac control *****
        edtPassword=(EditText) findViewById(R.id.edtPassword);
        edtPhone=(EditText) findViewById(R.id.edtPhone);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        // ***** End: Anh xa cac control *****
        StyleControl();
        // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        // Event control
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPhone.getText().toString()==""){
                    Toast.makeText(SignIn.this, "Please enter your phone.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtPassword.getText().toString()==""){
                    Toast.makeText(SignIn.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Please waiting...");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // check if user does not exist in database
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                // Get user information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Toast.makeText(SignIn.this, "thanh cong.!!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignIn.this, "Wrong password.!!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "User does not exist in database", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}