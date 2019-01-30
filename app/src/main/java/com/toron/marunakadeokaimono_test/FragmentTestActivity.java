package com.toron.marunakadeokaimono_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.toron.marunakadeokaimono_test.ui.fragmenttest.FragmentTestFragment;

public class FragmentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FragmentTestFragment.newInstance("Fragment"))
                    .commitNow();
        }
    }
}
