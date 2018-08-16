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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SPA240 extends AppCompatActivity {


    private TextView singleTitle, singleDesc;
    private RecyclerView recycle;
    String post_key = null;
    private DatabaseReference StudyDatabase;
    private DatabaseReference StudyDatabase2;
    private Button deleteBtn;
    private Button cmtBtn;
    private FirebaseAuth SBAuth;
    private FirebaseAuth.AuthStateListener AuthState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);


        singleTitle = (TextView) findViewById(R.id.singleTitle);
        singleDesc = (TextView) findViewById(R.id.singleDesc);
        post_key = getIntent().getExtras().getString("PostID");
        StudyDatabase = FirebaseDatabase.getInstance().getReference().child("Class3");
        StudyDatabase2 = FirebaseDatabase.getInstance().getReference().child("Class3").child(post_key).child("comment");

        recycle = (RecyclerView) findViewById(R.id.recyclerview);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setHasFixedSize(true);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        cmtBtn = (Button) findViewById(R.id.cmtBtn);
        SBAuth = FirebaseAuth.getInstance();
        AuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (SBAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(SPA240.this, RegisterActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };
    }
    protected void onStart() {
        super.onStart();
        SBAuth.addAuthStateListener(AuthState);
        FirebaseRecyclerAdapter<DiscussionBoard, MainActivity.DBViewHolder> FBRA = new FirebaseRecyclerAdapter<DiscussionBoard, MainActivity.DBViewHolder>(
                DiscussionBoard.class,
                R.layout.card_item,
                MainActivity.DBViewHolder.class,
                StudyDatabase2
        ) {
            @Override
            protected void populateViewHolder(MainActivity.DBViewHolder viewHolder, DiscussionBoard model, int position) {
                final String post_key = getRef(position).getKey().toString();
                final String post_key2 = getIntent().getExtras().getString("PostID");
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setUserName(model.getUsername());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleActivity = new Intent(SPA240.this, CSPA3.class);
                        singleActivity.putExtra("PostID1", post_key);
                        singleActivity.putExtra("PostID", post_key2);
                        startActivity(singleActivity);
                    }
                });

            }
        };
        recycle.setAdapter(FBRA);
        deleteBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StudyDatabase.child(post_key).removeValue();

                Intent mainintent = new Intent(SPA240.this, MAtwoforty.class);
                startActivity(mainintent);
            }
        });
        cmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainintent = new Intent(SPA240.this, PASPA3.class);
                mainintent.putExtra("PostID", post_key);
                startActivity(mainintent);

            }
        });

        StudyDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();

                singleTitle.setText(post_title);
                singleDesc.setText(post_desc);
                if (SBAuth.getCurrentUser().getUid().equals(post_uid)){

                    deleteBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
