package com.ginarth.rainbowpuzzle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final ArrayList<Record> records;

    ScoreAdapter(Context context, ArrayList<Record> records) {
        this.records = records;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_score_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Record record = records.get(position);
        holder.number.setText(String.valueOf(position + 1));
        holder.name.setText(record.getName());
        holder.date.setText(record.getDate());
        holder.moves.setText(String.valueOf(record.getMoves()));
        long time = record.getTime();
        int hours = (int) (time / 3600);
        int mins = (int) (time % 3600 / 60);
        int secs = (int) (time % 60);
        String formatTime = String.format("%02d:%02d:%02d", hours, mins, secs);
        holder.time.setText(formatTime);

        holder.cardView.setCardBackgroundColor(Color.HSVToColor(new float[] {(position) * (300F / records.size()), 1.0f, 1.0f}));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        final TextView number, name, date, moves, time;
        ViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.cardView);
            number = view.findViewById(R.id.item_number);
            name = view.findViewById(R.id.item_name);
            date = view.findViewById(R.id.item_date);
            moves = view.findViewById(R.id.item_moves);
            time = view.findViewById(R.id.item_time);
        }
    }
}