package com.studdybuddy.finalstudybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CSPA3 extends AppCompatActivity {


    private TextView singleTitle, singleDesc;
    String post_key = null;
    String post_key2 = null;
    private Button deleteBtn;
    private Button dltBtn;
    private Button cmtBtn;
    private RecyclerView recycle;
    private DatabaseReference StudyDatabase;
    private DatabaseReference StudyDatabase2;
    private FirebaseAuth SBAuth;
    private FirebaseAuth.AuthStateListener AuthState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);


        singleTitle = (TextView) findViewById(R.id.singleTitle);
        singleDesc = (TextView) findViewById(R.id.singleDesc);
        post_key = getIntent().getExtras().getString("PostID1");
        post_key2 = getIntent().getExtras().getString("PostID");
        recycle = (RecyclerView)findViewById(R.id.recyclerview);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setHasFixedSize(true);
        StudyDatabase = FirebaseDatabase.getInstance().getReference().child("Class3");
        StudyDatabase2 = FirebaseDatabase.getInstance().getReference().child("Class3").child(post_key2).child("comment");

        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        cmtBtn = (Button) findViewById(R.id.cmtBtn);
        SBAuth = FirebaseAuth.getInstance();
        AuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (SBAuth.getCurrentUser()==null){
                    Intent loginIntent = new Intent(CSPA3.this, RegisterActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);startActivity(loginIntent);
                }
            }
        };
        deleteBtn.setVisibility(View.INVISIBLE);
        cmtBtn.setText("Back To Main Activity");

    }

    protected void onStart() {
        super.onStart();
        SBAuth.addAuthStateListener(AuthState);


        cmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainintent = new Intent(CSPA3.this, MAtwoforty.class);
                mainintent.putExtra("PostID", post_key2);
                startActivity(mainintent);


            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StudyDatabase2.child(post_key).removeValue();

                Intent mainintent = new Intent(CSPA3.this, MAtwoforty.class);
                startActivity(mainintent);
                mainintent.putExtra("PostID", post_key);
            }
        });



        StudyDatabase2.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();

                singleTitle.setText(post_title);
                singleDesc.setText(post_desc);
                if (SBAuth.getCurrentUser().getUid().equals(post_uid)) {

                    deleteBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
