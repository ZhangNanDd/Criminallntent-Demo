package com.example.zhangnan.criminallntent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zhangnan.criminallntent.database.CrimeDbSchema.CrimeBaseHelper;
import com.example.zhangnan.criminallntent.database.CrimeDbSchema.CrimeCursorWrapper;
import com.example.zhangnan.criminallntent.database.CrimeDbSchema.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangnan on 16/9/21.
 */
public class CrimeLab {

    private static  CrimeLab sCrimeLab;
    //private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
        //mCrimes = new ArrayList<>();
//        for(int i=0;i<100;i++){
//            Crime crime = new Crime();
//            crime.setTitle("Crime #"+i);
//            crime.setSolved(true);//Every other one
//            mCrimes.add(crime);
//        }
    }

    public void addCrime(Crime c){
        //mCrimes.add(c);
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME,null,values);
    }

    public void deleteCrime(Crime c){
        String uuidString = c.getId().toString();

        mDatabase.delete(CrimeDbSchema.CrimeTable.NAME, CrimeDbSchema.CrimeTable.Cols.UUID + "=?",
                new String[]{ uuidString });
    }

    public List<Crime> getCrimes(){
        //return mCrimes;
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null,null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id){
//        for (Crime crime : mCrimes)
//        {
//            if (crime.getId().equals(id)){
//                return crime;
//            }
//        }
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{ id.toString() }
        );

        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        String date = crime.getDate().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeDbSchema.CrimeTable.NAME,values, CrimeDbSchema.CrimeTable.Cols.UUID + "=?",
                new String[]{ uuidString });
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED,crime.isSolved() ? 1 : 0);
        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null,
                whereClause,//查询条件
                whereArgs,//where的参数
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }
}
