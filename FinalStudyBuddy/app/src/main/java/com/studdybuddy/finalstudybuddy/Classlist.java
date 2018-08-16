package com.studdybuddy.finalstudybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Classlist extends AppCompatActivity {
    private Button Cbtn1;
    private Button Cbtn2;
    private Button Cbtn3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

       Cbtn1 = findViewById(R.id.Cbtn1);
       Cbtn1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(Classlist.this, MAtwohun.class));
           }
       });

       Cbtn2 = findViewById(R.id.Cbtn2);
       Cbtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Classlist.this, MainActivity.class));
            }
        });

        Cbtn3 = findViewById(R.id.Cbtn3);
        Cbtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Classlist.this, MAtwoforty.class));
            }
        });



    }



}
