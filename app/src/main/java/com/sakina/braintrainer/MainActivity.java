package com.sakina.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView text,timeView,scoreView,quesView;
    TableLayout options;
    Button playButton;
    boolean flag=true;


    Button option0,option1,option2,option3;
    int ans;
    int option;
    int score=0;
    int no_of_ques=0;
    MediaPlayer right;
    MediaPlayer wrong;
    MediaPlayer start;


    public void vis()//makes the elements visible and invisble
    {
        if(flag)
        {

            text.setVisibility(View.INVISIBLE);
            text.setEnabled(false);
            timeView.setVisibility(View.INVISIBLE);
            timeView.setEnabled(false);
            scoreView.setVisibility(View.INVISIBLE);
            scoreView.setEnabled(false);
            quesView.setVisibility(View.INVISIBLE);
            quesView.setEnabled(false);
            options.setVisibility(View.INVISIBLE);
            options.setEnabled(false);
            playButton.setVisibility(View.VISIBLE);
            playButton.setEnabled(true);

            flag=false;

        }
        else
        {


            text.setVisibility(View.VISIBLE);
            text.setEnabled(true);
            timeView.setVisibility(View.VISIBLE);
            timeView.setEnabled(true);
            scoreView.setVisibility(View.VISIBLE);
            scoreView.setEnabled(true);
            quesView.setVisibility(View.VISIBLE);
            quesView.setEnabled(true);
            options.setVisibility(View.VISIBLE);
            options.setEnabled(true);
            playButton.setVisibility(View.INVISIBLE);
            playButton.setEnabled(false);
            flag=true;
        }
    }
    public void settext(int i)//sets the text of the timeview
    {
        int min,sec;
        min=i/60;
        sec=i%60;
        if(sec<10)
        {
            timeView.setText("0"+Integer.toString(min)+":"+"0"+Integer.toString(sec));

        }
        else
        {
            timeView.setText("0"+Integer.toString(min)+":"+Integer.toString(sec));
        }

    }
    public void play(View view)//play button starts the game
    {
        vis();
        start.start();
        new CountDownTimer(60000+100,1000)//timer
        {
            public void onTick(long milisecsuntildone)
            {

                settext((int)milisecsuntildone/1000);

            }
            public void onFinish()
            {
                timeView.setText("00:00");
                Toast.makeText(MainActivity.this, "Time Up!", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getApplicationContext(),scoreActivity.class);
                intent.putExtra("newScore",score);
                startActivity(intent);
                score=0;
                no_of_ques=0;
                setscoreView();
                vis();
            }

        }.start();

        ques();//creates the questions



    }
    public void ques()
    {
        int a=(int)(Math.random()*100);
        int b=(int)(Math.random()*100);
        String operators[]={"+","-"};
        Random ran=new Random();
        String op=operators[ran.nextInt(operators.length)];

        switch(op)
        {
            case "+":quesView.setText("Q. "+a+"+"+b);
            ans=a+b;

                break;

            case "-":quesView.setText("Q. "+a+"-"+b);
            ans=a-b;


            break;
        }
        setoptions();

    }
    void setoptions()
    {
        Random ran=new Random();
        option=ran.nextInt(4);//this takes one of the option


       if(option==0)
       {

           option1.setText(Integer.toString(ans+10));


           option2.setText(Integer.toString(ans-14));

           option3.setText(Integer.toString(ans+2));

           option0.setText(Integer.toString(ans));
       }
       else if(option==1)
       {

           option0.setText(Integer.toString(ans+7));


           option2.setText(Integer.toString(ans-4));


           option3.setText(Integer.toString(ans-6));

           option1.setText(Integer.toString(ans));
       }
       else if(option==2)
       {

           option1.setText(Integer.toString(ans+3));


           option0.setText(Integer.toString(ans-6));


           option3.setText(Integer.toString(ans-7));

           option2.setText(Integer.toString(ans));
       }
       else
       {

           option1.setText(Integer.toString(ans-10));


           option2.setText(Integer.toString(ans+5));


           option0.setText(Integer.toString(ans-8));

           option3.setText(Integer.toString(ans));
       }


    }
   public void  setscoreView()
    {
        scoreView.setText(Integer.toString(score)+"/"+Integer.toString(no_of_ques));
    }

    public void checkAns(View view)
    {

       int buttonClicked= Integer.parseInt((String)view.getTag());
       int id=view.getId();
       Button selectedopt=(Button)findViewById(id);

        //Log.i("click","clicked "+Integer.toString(buttonClicked));
       //int count=0;
       if(buttonClicked==option)
       {
           right.start();
           Toast.makeText(this, "CORRECT..", Toast.LENGTH_SHORT).show();
           selectedopt.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);


           score++;
       }

       else
       {
           wrong.start();
           Toast.makeText(this, "INCORRECT..", Toast.LENGTH_SHORT).show();
           selectedopt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

       }
       //introduce delay
       selectedopt.getBackground().clearColorFilter();
       no_of_ques++;
        setscoreView();
        ques();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(TextView) findViewById(R.id.text);
        timeView=(TextView) findViewById(R.id.timeView);
        scoreView=(TextView) findViewById(R.id.scoreView);
        quesView=(TextView) findViewById(R.id.quesView);
        options=(TableLayout)findViewById(R.id.options);
        playButton=(Button)findViewById(R.id.playButton);
        option0=(Button)findViewById(R.id.option0);
        option1=(Button)findViewById(R.id.option1);
        option2=(Button)findViewById(R.id.option2);
        option3=(Button)findViewById(R.id.option3);
        right=MediaPlayer.create(this,R.raw.right);
        wrong=MediaPlayer.create(this,R.raw.wrong1);
        start=MediaPlayer.create(this,R.raw.start);

        vis();
    }
}