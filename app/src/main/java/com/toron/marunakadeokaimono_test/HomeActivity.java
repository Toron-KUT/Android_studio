package com.toron.marunakadeokaimono_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import com.toron.marunakadeokaimono_test.ui.fragmenttest.FragmentTestFragment;

public class HomeActivity extends FragmentActivity implements FragmentTestFragment.Callback{
//public class HomeActivity extends AppCompatActivity{
    private int count;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    TransitionHoldingFoodActivity();
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    TransitionPurchaseHistoryActivity();
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    TransitionSpecialSaleActivity();
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_other:
                    TransitionOtherActivity();
                    mTextMessage.setText(R.string.title_other);
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
        setContentView(R.layout.activity_home);
       // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTabHost host = (FragmentTabHost) findViewById(android.R.id.tabhost);
        host.setup(this, getSupportFragmentManager(), R.id.content);
        TabHost.TabSpec tabSpec1 = host.newTabSpec("tab1");
        Button button1 = new Button(this);
        button1.setBackgroundResource(R.drawable.timerv2);
        tabSpec1.setIndicator(button1);
        Bundle bundle1 = new Bundle();
        bundle1.putString("name", "Tab1");
        host.addTab(tabSpec1, SampleFragment.class, bundle1);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("tab2");
        Button button2 = new Button(this);
        button2.setBackgroundResource(R.drawable.timerv2);
        tabSpec2.setIndicator(button2);
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", "Tab2");
        host.addTab(tabSpec2, SampleFragment.class, bundle2);

        TabHost.TabSpec tabSpec3 = host.newTabSpec("tab3");
        Button button3 = new Button(this);
        button3.setBackgroundResource(R.drawable.timerv2);
        tabSpec3.setIndicator(button3);
        Bundle bundle3 = new Bundle();
        bundle3.putString("name", "Tab3");
        host.addTab(tabSpec3, SampleFragment.class, bundle3);


        //Button button01 = findViewById(R.id.button);
        //button01.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        count++;
        //FragmentTestFragment fs = (FragmentTestFragment)getSupportFragmentManager().findFragmentById(R.id.container);
        //fs.setText(String.valueOf(count));
        //        FragmentManager fragmentManager = getSupportFragmentManager();
        //        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // BackStackを設定
        //        fragmentTransaction.addToBackStack(null);
        //        fragmentTransaction.replace(R.id.container,FragmentTestFragment.newInstance("神"));
        //        fragmentTransaction.commit();

        //    }
        //});
    }

    @Override
    public void onFragmentButtonClick(String string){
        ((TextView)findViewById(R.id.fragment_text1)).setText(string);
    }

    public static class SampleFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setText(getArguments().getString("name"));

            return textView;
        }

    }

}