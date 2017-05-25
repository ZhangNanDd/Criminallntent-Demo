package com.example.zhangnan.criminallntent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by zhangnan on 16/9/21.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity { //FragmentActivity
        protected abstract Fragment createFragment();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fragment);
            FragmentManager fm=getFragmentManager();
            Fragment fragment;
            fragment=fm.findFragmentById(R.id.fragmentContainer);

            if(null == fragment){
                fragment =createFragment();
                fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
            }else {
                //什么都不做
            }
        }
    }


