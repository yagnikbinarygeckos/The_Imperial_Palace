package com.app.theimperialpalace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    private ArrayList<NotificationResponse.Result> arrayList = new ArrayList<>();
    NotificationAdapter notificationAdapter;

    public NotificationAdapter(ArrayList<NotificationResponse.Result> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent,false);
        NotificationAdapter.NotificationViewHolder data = new NotificationAdapter.NotificationViewHolder(v);
        return data;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.NotificationViewHolder holder, int position) {

        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_message.setText(arrayList.get(position).getMessage());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title,tv_message;

        public NotificationViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_message = itemView.findViewById(R.id.tv_message);

        }
    }
}
