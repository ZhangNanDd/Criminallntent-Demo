package com.example.zhangnan.criminallntent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhangnan on 16/9/18.
 */
public class CrimeFragment extends Fragment {//修改过
    private Crime mCrime;
    private EditText mEditTextField;
    private CheckBox solvedCheckbox;
    private Button  dateButton;

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE="DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int H = 0;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //创建javabean
        UUID crimeId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        setHasOptionsMenu(true);
    }

    //crime数据刷新

    @Override
    public void onPause(){
        super.onPause();

        CrimeLab.get(getActivity())
                .updateCrime(mCrime);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crime,container,false);
        mEditTextField = (EditText) v.findViewById(R.id.crime_title);
        mEditTextField.setText(mCrime.getTitle());
        mEditTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //返回DATE
        /*
        try {
        dateButton.setText(mCrime.getDate().toString());
        } catch (ParseException e) {
        e.printStackTrace();
        }
        */


        solvedCheckbox = (CheckBox)v.findViewById(R.id.crime_solved);
        solvedCheckbox.setChecked(mCrime.isSolved());
        solvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });


        dateButton = (Button)v.findViewById(R.id.crime_date);
        //返回字符串
        updateDate();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = null;
                dialog = DatePickerFragment.newInstance(mCrime.getDate());

                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        return v;
    }

    /*删除Crime记录*/
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_crime,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_remove_crime:
                UUID crimeId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
                Crime crime = CrimeLab.get(getActivity()).getCrime(crimeId);
                CrimeLab.get(getActivity()).deleteCrime(crime);
                Intent intent = new Intent(getActivity(),CrimeListActivity.class);
                startActivity(intent);

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    /*删除Crime记录*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_DATE){
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate()  {
        dateButton.setText(mCrime.getDate().toString());
    }
}
