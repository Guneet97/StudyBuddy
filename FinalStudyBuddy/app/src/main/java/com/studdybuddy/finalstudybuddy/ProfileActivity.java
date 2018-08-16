package com.studdybuddy.finalstudybuddy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class ProfileActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText grade;
    private EditText UserName;
    private Button compBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference StudyDatabaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        StudyDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        firstName = (EditText) findViewById(R.id.firstName);;
        lastName = (EditText) findViewById(R.id.lastName);;
        grade = (EditText) findViewById(R.id.grade);;
        UserName = (EditText) findViewById(R.id.UserName);;
        compBtn = (Button) findViewById(R.id.compBtn);


        compBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fname = firstName.getText().toString().trim();
                final String lname = lastName.getText().toString().trim();
                final String gl = grade.getText().toString().trim();
                final String name = UserName.getText().toString().trim();
                final String userID = mAuth.getCurrentUser().getUid();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(gl)) {

                    StudyDatabaseUsers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            StudyDatabaseUsers.child(userID).child("name").setValue(name);
                            StudyDatabaseUsers.child(userID).child("firstName").setValue(fname);
                            StudyDatabaseUsers.child(userID).child("lastName").setValue(lname);
                            StudyDatabaseUsers.child(userID).child("grade").setValue(gl);
                            Toast.makeText(ProfileActivity.this, "Profile completed", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

}