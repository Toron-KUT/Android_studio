package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    TransitionHoldingFoodActivity();
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    TransitionPurchaseHistoryActivity();
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    TransitionSpecialSaleActivity();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_other:
                    TransitionOtherActivity();
                    //mTextMessage.setText(R.string.title_other);
                    return true;
            }
            return false;
        }
    };
    private void TransitionHoldingFoodActivity() {
        Intent intent = new Intent(this, HoldingFoodActivity.class);
        startActivity(intent);
    }
    private void TransitionPurchaseHistoryActivity() {
        Intent intent = new Intent(this, PurchaseHistroryActivity.class);
        startActivity(intent);
    }
    private void TransitionSpecialSaleActivity() {
        Intent intent = new Intent(this, SpecialSaleActivity.class);
        startActivity(intent);
    }

    private void TransitionOtherActivity() {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button readButton = findViewById(R.id.OtherButton1);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionFavoriteShopActivity();
            }
        });

    }
    private void TransitionFavoriteShopActivity() {
        Intent intent = new Intent(this, FavoriteShopActivity.class);
        startActivity(intent);
    }
}
