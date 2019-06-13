package com.example.timeoutlistdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//參考網址 ： https://youtu.be/Vyqz_-sJGFk
//參考網址 ： https://youtu.be/kaf2dCd8Zfs
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<String> mDepartments = new ArrayList<>();
    ArrayList<String> mDoctors = new ArrayList<>();
    ArrayList<String> mTimes = new ArrayList<>();
    ArrayList<String> mDeleteables = new ArrayList<>();
    ArrayList<Boolean> mChecks = new ArrayList<>();
    RecyclerViewAdapter adapter;

    private View.OnClickListener btnOP_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adapter.addData(mDepartments.size());
        }
    };
    private View.OnClickListener btnOK_click= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0 ; i < mChecks.size() ; i++){
                Log.d(TAG,"review checked item:"+i);
                adapter.addTime(i);
            }

        }
    } ;
    private View.OnClickListener btnTimeOut_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0 ; i < mChecks.size() ; i++){
                Log.d(TAG,"review checked item:"+i);
                adapter.CopyTimeOut(i);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate : start");
        InitialDataSet();
        InitialComponent();

    }

    private void InitialDataSet() {
        Log.d(TAG, "initial data set : start");
        mDepartments.add("牙科");
        mDepartments.add("外科");
        mDepartments.add("心臟科");
        mDoctors.add("卡比獸");
        mDoctors.add("皮卡丘");
        mDoctors.add("綠毛蟲");
        mTimes.add("");
        mTimes.add("");
        mTimes.add("");
        mDeleteables.add("0");
        mDeleteables.add("0");
        mDeleteables.add("0");
        mChecks.add(false);
        mChecks.add(false);
        mChecks.add(false);
        Log.d(TAG, "initial data set : done");
    }

    private void InitialComponent() {
        Log.d(TAG,"initialComponent : call recycler view ");
        recyclerView = findViewById(R.id.timeout_list);
        Log.d(TAG,"initialComponent : call adapter");
        adapter = new RecyclerViewAdapter(this,mDepartments,mDoctors,mTimes,mDeleteables,mChecks);
        Log.d(TAG,"initialComponent : set adapter");
        recyclerView.setAdapter(adapter);
        Log.d(TAG,"initialComponent : set layout manager");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG,"initialComponent : finish");

        btnOP=findViewById(R.id.btnOP);
        btnOP.setOnClickListener(btnOP_click);

        btnOK = findViewById(R.id.btnOK);
        btnOK.setOnClickListener(btnOK_click);

        btnTimeOut =findViewById(R.id.btnTimeOut);
        btnTimeOut.setOnClickListener(btnTimeOut_click);
    }

    Button btnOK,btnOP,btnTimeOut;
    RecyclerView recyclerView;
    TextView lblVer;
}
