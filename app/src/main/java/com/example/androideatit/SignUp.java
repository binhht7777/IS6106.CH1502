package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {

    // Khai bao cac control
    MaterialEditText edtPhone, edtPassword, edtName;
    Button btnSignUp;

    private void StyleControl(){
        Typeface faceText = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        edtPhone.setTypeface(faceText);
        edtPassword.setTypeface(faceText);
        edtName.setTypeface(faceText);

        Typeface faceBtn = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Medium.ttf");
        btnSignUp.setTypeface(faceBtn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // ***** Begin: Anh xa cac control *****
        edtPassword=(MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone=(MaterialEditText) findViewById(R.id.edtPhone);
        edtName=(MaterialEditText)findViewById(R.id.edtName);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        // ***** End: Anh xa cac control *****

        StyleControl();

        // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString()==null){
                    Toast.makeText(SignUp.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtPassword.getText().toString()==null){
                    Toast.makeText(SignUp.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Please waiting...");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // check if already user phone
                            if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Phone number already register", Toast.LENGTH_SHORT).show();
                            }else{
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                                table_user.child(edtPhone.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "Sign up successfuly.!", Toast.LENGTH_SHORT).show();
                                finish();
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