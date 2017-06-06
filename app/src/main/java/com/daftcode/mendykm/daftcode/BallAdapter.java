package com.daftcode.mendykm.daftcode;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BallAdapter extends RecyclerView.Adapter<BallAdapter.ViewHolder> {
    LayoutInflater inflater;
     ArrayList<Ball> mBall;

    public void setmBall(ArrayList<Ball> mBall) {
        this.mBall = mBall;
    }

    public BallAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ball_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.counter.setText(""+ mBall.get(position).licznik);
        Log.i("bunia",""+ mBall.get(position).getColorOfBall());
        holder.v.getBackground().setColorFilter(Color.parseColor(mBall.get(position).colorOfBall.toString().toLowerCase()), PorterDuff.Mode.SRC);
    }

    @Override
    public int getItemCount() {
        return mBall.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView counter;
        public View v;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            counter = (TextView) itemView.findViewById(R.id.TextBall);
            v = (View) itemView.findViewById(R.id.ViewBall);

        }




    }
}
