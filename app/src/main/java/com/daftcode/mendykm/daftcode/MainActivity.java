package com.daftcode.mendykm.daftcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    public Boolean generateBalls = false;
    Random rand = new Random();
    private BallGenerator doApplication ;
    public volatile ArrayList<Ball> balls = new ArrayList<Ball>();
    BallAdapter ballAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        ballAdapter = new BallAdapter(this);
        ballAdapter.setmBall(balls);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(ballAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void generateNextBallEvent(){
        if(balls.size()<5){
            balls.add(new Ball(rand.nextInt(2)));
        }
        else{
            int luck = rand.nextInt(100)+1;
            if(isBetween(luck,1,50)){
                balls.get(rand.nextInt(4)).licznik++;
            }
            else if(isBetween(luck,51,85)){
                balls.get(rand.nextInt(4)).licznik=0;
            }
            else if(isBetween(luck,86,95)){
                balls.remove(rand.nextInt(4));
            }
            else if(isBetween(luck,96,100)){
                int idx = rand.nextInt(4);
                if(isBetween(idx,1,4))
                    balls.get(idx).licznik+=balls.get(idx-1).licznik;
                else
                    balls.get(0).licznik+=balls.get(4).licznik;
            }
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ballAdapter.notifyDataSetChanged();
            }
        });
    }

    public void start(View view) {
        if(!generateBalls) {
            generateBalls = true;
            doApplication = new BallGenerator();
            doApplication.execute();
        }
        else {
            Toast.makeText(getApplicationContext(), "already working" , Toast.LENGTH_SHORT).show();
        }
    }

    public void stop(View view) {
        if(generateBalls) {
            generateBalls = false;
            doApplication.cancel(true);
        }
        else {
          Toast.makeText(getApplicationContext(), "already stoped" , Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public class BallGenerator extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... pram) {
            while (generateBalls) {
                try {
                    Log.i("bunia",""+ balls.size());
                    TimeUnit.SECONDS.sleep(1);
                    generateNextBallEvent();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


    }
}


class Ball{
    public enum Color
    {
        RED,
        BLUE


    }
    public static Color fromInteger(int x) {
        switch(x) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
        }
        return null;
    }
    public Color colorOfBall;
    public int licznik = 0;
    public Ball(Integer a){
        setColorOfBall(fromInteger(a));
       licznik = 0;
    }
    

    public Color getColorOfBall() {
        return colorOfBall;
    }

    public void setColorOfBall(Color colorOfBall) {
        this.colorOfBall = colorOfBall;
    }


}

