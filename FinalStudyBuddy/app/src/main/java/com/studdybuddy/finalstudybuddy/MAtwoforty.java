package com.studdybuddy.finalstudybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MAtwoforty extends AppCompatActivity {
    private RecyclerView recycle;
    private DatabaseReference StudyDatabase;
    private FirebaseAuth SBauthentication;
    private FirebaseAuth.AuthStateListener AuthState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recycle = (RecyclerView)findViewById(R.id.recyclerview);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setHasFixedSize(true);
        StudyDatabase = FirebaseDatabase.getInstance().getReference().child("Class3");
        SBauthentication = FirebaseAuth.getInstance();
        AuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (SBauthentication.getCurrentUser()==null){
                    Intent loginIntent = new Intent(MAtwoforty.this, RegisterActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);startActivity(loginIntent);
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        SBauthentication.addAuthStateListener(AuthState);
        FirebaseRecyclerAdapter<DiscussionBoard, DBViewHolder> FBRA = new FirebaseRecyclerAdapter<DiscussionBoard, DBViewHolder>(
                DiscussionBoard.class,
                R.layout.card_item,
                DBViewHolder.class,
                StudyDatabase
        ) {
            @Override
            protected void populateViewHolder(DBViewHolder viewHolder, DiscussionBoard model, int position) {
                final String post_key = getRef(position).getKey().toString();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setUserName(model.getUsername());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent singleActivity = new Intent(MAtwoforty.this, SPA240.class);
                        singleActivity.putExtra("PostID", post_key);
                        startActivity(singleActivity);
                    }
                });
            }
        };
        recycle.setAdapter(FBRA);
    }
    public static class DBViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public DBViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = mView.findViewById(R.id.post_title_txtview);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc = mView.findViewById(R.id.post_desc_txtview);
            post_desc.setText(desc);
        }

        public void setUserName(String userName){
            TextView postUserName = mView.findViewById(R.id.post_user);
            postUserName.setText(userName);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MAtwoforty.this,Classlist.class));
        }
        else if (id == R.id.action_add) {
            startActivity(new Intent(MAtwoforty.this, PA240.class));
        } else if (id == R.id.logout){
            SBauthentication.signOut();
            Intent logouIntent = new Intent(MAtwoforty.this, RegisterActivity.class);
            logouIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logouIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}