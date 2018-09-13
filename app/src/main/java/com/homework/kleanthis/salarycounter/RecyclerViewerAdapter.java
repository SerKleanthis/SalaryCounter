package com.homework.kleanthis.salarycounter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewerAdapter extends RecyclerView.Adapter<RecyclerViewerAdapter.ItemViewHolder>{
    private ArrayList<Model> list;

    public RecyclerViewerAdapter(ArrayList<Model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        //TODO stuff here
        Model rowModel = list.get(position);
        holder.date.setText(rowModel.getDate());
        holder.day.setText(rowModel.getDay());
        holder.startingTime.setText(rowModel.getStartingTime() +"");
        holder.endTime.setText(rowModel.getEndTime() +"");
        holder.cash.setText(rowModel.getCash() +"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView date, day, startingTime, endTime, cash;

        ItemViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.rowDate);
            day = view.findViewById(R.id.rowDay);
            startingTime = view.findViewById(R.id.rowStartingTime);
            endTime = view.findViewById(R.id.rowEndTime);
            cash = view.findViewById(R.id.rowCash);
        }
    }
}
