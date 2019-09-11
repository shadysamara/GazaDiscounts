package com.example.gazadiscounts.AdminPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.gazadiscounts.CustomerPage.AllMarkets;
import com.example.gazadiscounts.MarketPage.AddProductFragment;
import com.example.gazadiscounts.MarketPage.ProductsFragment;
import com.example.gazadiscounts.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class adminPage extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_comments:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new Comments()).commit();
                    return true;

                case R.id.nav_delete_markets:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new AllMarkets()).commit();

                    return true;

                case R.id.nav_markets:
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new addMarket()).commit();

                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navView.setSelectedItemId(R.id.nav_comments);
    }
}
