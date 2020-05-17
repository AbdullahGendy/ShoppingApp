package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class UserMainActivity extends AppCompatActivity {
    Button btnOrders, btnWishList, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        btnOrders = findViewById(R.id.btn_orders);
        btnWishList = findViewById(R.id.btn_wish_list);
        btnHome = findViewById(R.id.btn_home);
        btnOrders.setOnClickListener(v -> {
            Intent intent = new Intent(UserMainActivity.this, UserOrdersActivity.class);
            startActivity(intent);
        });
        btnWishList.setOnClickListener(v -> {
            Intent intent = new Intent(UserMainActivity.this, UserWishListActivity.class);
            startActivity(intent);

        });
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(UserMainActivity.this, UserHomeActivity.class);
            startActivity(intent);

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            //TODO Implement logout

        }
        return true;
    }

}
