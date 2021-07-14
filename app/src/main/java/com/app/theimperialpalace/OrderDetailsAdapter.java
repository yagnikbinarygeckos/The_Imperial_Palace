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

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder> {



    private Context context;
    private ArrayList<ListOfOrderResponse.ProductDetail> arrayList = new ArrayList<>();
    OrderDetailsAdapter orderDetailsAdapter;


    public OrderDetailsAdapter(Context context, ArrayList<ListOfOrderResponse.ProductDetail> arrayList) {

        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @NotNull
    @Override
    public OrderDetailsAdapter.OrderDetailsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details,parent,false);
        OrderDetailsAdapter.OrderDetailsViewHolder data = new OrderDetailsAdapter.OrderDetailsViewHolder(v);
        return data;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderDetailsAdapter.OrderDetailsViewHolder holder, int position) {

        holder.tv_foodName.setText(arrayList.get(position).getProductName());
        holder.tv_qty.setText(arrayList.get(position).getQty());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_foodName,tv_qty;

        public OrderDetailsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_foodName = itemView.findViewById(R.id.tv_foodName);
            tv_qty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
