package com.sakina.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class scoreActivity extends AppCompatActivity {

    static ArrayList<Integer> scores=new ArrayList<Integer>();
    static ArrayAdapter<Integer> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.sakina.braintrainer",Context.MODE_PRIVATE);
        Intent intent=getIntent();
       int newScore= intent.getIntExtra("newScore",0);




        if(scores.size()<5)
        {
            scores.add(newScore);
            Collections.sort(scores, Collections.reverseOrder());
        }
        else
        {
            for(int i=1;i<6;i++)
            {
                if(newScore > scores.get(i))
                {
                    scores.set(i,newScore);
                    break;
                }
            }

        }


        ListView scoreListView=(ListView)findViewById(R.id.scoreListView);
        arrayAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1,scores);
        scoreListView.setAdapter(arrayAdapter);
        try {
            sharedPreferences.edit().putString("scores",ObjectSerializer.serialize(scores)).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            scores=(ArrayList<Integer>) ObjectSerializer.deserialize(sharedPreferences.getString("scores",ObjectSerializer.serialize(new ArrayList<Integer>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("check", String.valueOf(scores));


    }
}