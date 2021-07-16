package com.app.theimperialpalace;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.theimperialpalace.Network.ApiUtils;
import com.app.theimperialpalace.Network.WebApi;
import com.app.theimperialpalace.Utils.SharedPrefsUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfOrderAdapter extends RecyclerView.Adapter<ListOfOrderAdapter.ListOfOrderViewHolder> {

        private Context context;
        private ArrayList<ListOfOrderResponse.Result> arrayList = new ArrayList<>();
        private ArrayList<ListOfOrderResponse.ProductDetail> arraylist_Details = new ArrayList<>();
        ListOfOrderAdapter listOfOrderAdapter;
        OrderDetailsAdapter orderDetailsAdapter;
        Activity activity;


    public ListOfOrderAdapter(Context context,ArrayList<ListOfOrderResponse.Result> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ListOfOrderViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list,parent,false);
        ListOfOrderAdapter.ListOfOrderViewHolder data = new ListOfOrderAdapter.ListOfOrderViewHolder(v);
        return data;

    }

    @Override
    public void onBindViewHolder(@NonNull  ListOfOrderAdapter.ListOfOrderViewHolder holder, int position) {
        holder.tv_orderId.setText(arrayList.get(position).getOrderId());
        holder.tv_name.setText(arrayList.get(position).getUserName());
        holder.tv_orderNo.setText(arrayList.get(position).getOrderNo());
        //holder.tv_status.setText(arrayList.get(position).getStatus());

        holder.tv_address1.setText(arrayList.get(position).getAddress1());
        holder.tv_address2.setText(arrayList.get(position).getAddress2());
        holder.tv_landmark.setText(arrayList.get(position).getLandmark());

        if (arrayList.get(position).getStatus().equalsIgnoreCase("0")){
            holder.tv_status.setText("Pendding");

        }
        else if(arrayList.get(position).getStatus().equalsIgnoreCase("1")){
            holder.tv_status.setText("Accept");
        }
        else if(arrayList.get(position).getStatus().equalsIgnoreCase("2")){
            holder.tv_status.setText("Packing");
        }
        else if(arrayList.get(position).getStatus().equalsIgnoreCase("3")){
            holder.tv_status.setText("Dispatch");
        }
        else if(arrayList.get(position).getStatus().equalsIgnoreCase("4")){
            holder.tv_status.setText("Out of Delivery ");
        }
        else if(arrayList.get(position).getStatus().equalsIgnoreCase("5")){
            holder.tv_status.setText("Cancel");
        }
        else if(arrayList.get(position).getStatus().equalsIgnoreCase("6")){
            holder.tv_status.setText("Delivered");
        }


        for (int i =0; i < arrayList.get(position).getProductDetails().size();i++){
            arraylist_Details.add(arrayList.get(position).getProductDetails().get(i));
        }

        holder.rv_orderDetails.setHasFixedSize(true);
        orderDetailsAdapter = new OrderDetailsAdapter(context,arraylist_Details);
        holder.rv_orderDetails.setAdapter(orderDetailsAdapter);
        holder.rv_orderDetails.setNestedScrollingEnabled(true);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
        holder.rv_orderDetails.setLayoutManager(LayoutManager);

    }



    @Override
    public int getItemCount() {

        return arrayList.size();
    }
    public class ListOfOrderViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_orderDetails;
        TextView tv_orderId,tv_name,tv_orderNo,tv_status,tv_address1,tv_address2,tv_landmark;
        CardView card_view;
        public ListOfOrderViewHolder(@NonNull  View itemView) {
            super(itemView);
            tv_orderId = itemView.findViewById(R.id.tv_orderId);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_orderNo = itemView.findViewById(R.id.tv_orderNo);
            tv_status = itemView.findViewById(R.id.tv_status);
            card_view = itemView.findViewById(R.id.card_view);
            rv_orderDetails = itemView.findViewById(R.id.rv_orderDetails);
            tv_address1 = itemView.findViewById(R.id.tv_address1);
            tv_address2 = itemView.findViewById(R.id.tv_address2);
            tv_landmark = itemView.findViewById(R.id.tv_landmark);
        }
    }


    public void updateList(ArrayList<ListOfOrderResponse.Result> list){
        arrayList = list;
        notifyDataSetChanged();
    }
}
