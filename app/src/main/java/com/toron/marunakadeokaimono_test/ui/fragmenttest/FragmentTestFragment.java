package com.toron.marunakadeokaimono_test.ui.fragmenttest;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toron.marunakadeokaimono_test.R;

public class FragmentTestFragment extends Fragment {

    private FragmentTestViewModel mViewModel;

    public static FragmentTestFragment newInstance() {
        return new FragmentTestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FragmentTestViewModel.class);
        // TODO: Use the ViewModel
    }

}
