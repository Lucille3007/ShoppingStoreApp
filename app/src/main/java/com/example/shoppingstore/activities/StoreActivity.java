package com.example.shoppingstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingstore.ItemModel;
import com.example.shoppingstore.ItemsAdapter;
import com.example.shoppingstore.R;
import com.example.shoppingstore.RecyclerViewInterface;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity implements RecyclerViewInterface {

    private TextView username;
    private Button logOut;
    ArrayList<ItemModel> items = new ArrayList<>();
    RecyclerView recyclerView;
    ItemsAdapter adapter;

    int [] itemsImages = {R.drawable.bamba,R.drawable.bambamix,R.drawable.chips,R.drawable.kinder,R.drawable.milka,R.drawable.klik,R.drawable.timtam,R.drawable.rice,
            R.drawable.penne,R.drawable.ravioli,R.drawable.eggs,R.drawable.pesto,R.drawable.nutella,R.drawable.milk,R.drawable.shoko,R.drawable.coca_cola,R.drawable.big_cola,
            R.drawable.coca_zero,R.drawable.big_zero,R.drawable.grape_juice,R.drawable.water,R.drawable.tea,R.drawable.nes_tester,R.drawable.black_coffee};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);

        String user = getIntent().getStringExtra("username");

        logOut = findViewById(R.id.logOutBtn);

        username = findViewById(R.id.textWelcomeUser);
        username.setText("Welcome " +user);

        recyclerView = findViewById(R.id.res);


        setUpItems();

        adapter = new ItemsAdapter(this, items, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(StoreActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
    }
    private void setUpItems() {
        String [] itemsNames = getResources().getStringArray(R.array.items_names);
        int [] itemsPrices = {5,6,6,10,15,7,20,12,10,15,13,12,20,6,10,7,10,7,10,7,5,25,25,15};

        for (int i =0; i < itemsPrices.length; i++) {
            items.add(new ItemModel(itemsNames[i], itemsPrices[i],0, itemsImages[i]));
        }
    }

    ItemModel deletedItem = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    if(items.get(position).getQuantity() == 0){
                        deletedItem = items.get(position);
                        items.remove(position);
                        adapter.notifyItemRemoved(position);
                        Snackbar.make(recyclerView,"Removed "+deletedItem.getName()+" from list", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                items.add(position,deletedItem);
                                adapter.notifyItemInserted(position);
                            }
                        }).show();
                    }
                    else {
                        items.get(position).setQuantity(items.get(position).getQuantity()-1);
                        adapter.notifyItemChanged(position);
                    }
                    break;
                case ItemTouchHelper.RIGHT:
                    items.get(position).setQuantity(items.get(position).getQuantity()+1);
                    adapter.notifyItemChanged(position);
                    break;
            }


        }
    };

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, items.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}