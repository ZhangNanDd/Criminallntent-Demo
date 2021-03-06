package com.example.zhangnan.criminallntent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by zhangnan on 16/9/23.
 */
public class CrimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID="com.zhangnan.android.criminalintent.crime_id";
    private ViewPager mViewPager;
    private List<Crime> mCrimeS;

    public static Intent newIntent(Context packgeContext, UUID crimeId){
        Intent intent = new Intent(packgeContext,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager=(ViewPager)findViewById(R.id.activity_crime_pager_view_pager);
        mCrimeS = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimeS.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimeS.size();
            }
        });

        for (int i =0 ;i<mCrimeS.size();i++){
            if (mCrimeS.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }
}
