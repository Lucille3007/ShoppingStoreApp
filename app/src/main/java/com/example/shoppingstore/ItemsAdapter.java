package com.example.shoppingstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ItemModel> items;
    public ItemsAdapter(Context context, ArrayList<ItemModel> items, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.res_row,parent, false);
        return new ItemsAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.MyViewHolder holder, int position) {

        String price_str = Integer.toString(items.get(position).getPrice());
        String quantity_str = Integer.toString(items.get(position).getQuantity());

        holder.name.setText(items.get(position).getName());
        holder.price.setText(price_str+ "₪");
        holder.quantity.setText("quantity: "+quantity_str);
        holder.image.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, price, quantity;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.nameOfItem);
            price = itemView.findViewById(R.id.priceOfItem);
            quantity = itemView.findViewById(R.id.quantityOfItem);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }

                }
            });

        }
    }
}
