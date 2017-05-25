package com.example.zhangnan.criminallntent;

import android.app.Fragment;

/**
 * Created by zhangnan on 16/9/21.
 */
public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }


}
