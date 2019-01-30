package com.toron.marunakadeokaimono_test.ui.fragmenttest;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.toron.marunakadeokaimono_test.R;

import javax.security.auth.callback.Callback;

public class FragmentTestFragment extends Fragment {

    private FragmentTestViewModel mViewModel;
    private View rootView;
    private int count;
    private Callback callback;
    public FragmentTestFragment(){
    }


    public static FragmentTestFragment newInstance(String str) {
        // Fragemnt01 インスタンス生成
        FragmentTestFragment fragment = new FragmentTestFragment();
        // Bundle にパラメータを設定
        Bundle barg = new Bundle();
        barg.putString("Message", str);
        fragment.setArguments(barg);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //count = 0;

        //rootView = inflater.inflate(R.layout.fragment_test_fragment,container,false);
        //((Button)rootView.findViewById(R.id.fragment_button1)).setOnClickListener(new View.OnClickListener(){
        //    @Override
        //    public void onClick(View v){
        //        count++;
        //        callback.onFragmentButtonClick(String.valueOf(count));
        //    }
        //});
        return  inflater.inflate(R.layout.fragment_test_fragment,container,false);
    }
    //public void setText(String string){
    //    ((TextView)rootView.findViewById(R.id.fragment_text1)).setText(string);
    //}
    public static interface Callback{
        public void onFragmentButtonClick(String string);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if(args != null ){
            String str = args.getString("Message");
            TextView textView = view.findViewById(R.id.fragment_text1);
            textView.setText(str);
        }
        mViewModel = ViewModelProviders.of(this).get(FragmentTestViewModel.class);
        // TODO: Use the ViewModel
    }




}
