package com.example.timeoutlistdemo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class RecyclerViewAdapter  extends  RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    ArrayList<String> mDepartments = new ArrayList<>();
    ArrayList<String> mDoctors = new ArrayList<>();
    ArrayList<String> mTimes = new ArrayList<>();
    ArrayList<String> mDeleteables = new ArrayList<>();
    ArrayList<Boolean> mChecks = new ArrayList<>();
    Context mContext;

    public RecyclerViewAdapter(Context mContext,
                                                    ArrayList<String> mDepartments,
                                                    ArrayList<String> mDoctors,
                                                    ArrayList<String> mTimes,
                                                    ArrayList<String> mDeleteables,
                                                    ArrayList<Boolean> mChecks) {
        this.mDepartments = mDepartments;
        this.mDoctors = mDoctors;
        this.mTimes = mTimes;
        this.mContext = mContext;
        this.mDeleteables = mDeleteables;
        this.mChecks =  mChecks;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup vg, int i) {
        View view = LayoutInflater.from(vg.getContext()).inflate(R.layout.layout_listitem,vg,false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder vh, final int i) {
        Log.d(TAG,"onBindViewHolder : called");

        vh.lblDepartment.setText(mDepartments.get(i));
        vh.lblDoctor.setText(mDoctors.get(i));
        vh.lblTime.setText(mTimes.get(i));

        if (mDeleteables.get(i) == "0"){
            vh.btnRemove.setBackgroundResource(R.color.md_blue_grey_700);
            vh.btnRemove.setEnabled(false);
        }else{
            vh.btnRemove.setBackgroundResource(R.color.md_red_A100);
            vh.btnRemove.setEnabled(true);
            vh.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(i);
                }
            });
        }

        if(mChecks.get(i)){
            item_checked(vh);
        }else{
            item_unchecked(vh);
        }

        vh.parent_layout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v){
                Toast.makeText(mContext,mDoctors.get(i), Toast.LENGTH_SHORT).show();
                if(mChecks.get(i)){
                    mChecks.set(i,false);
                    item_unchecked(vh);
                }else{
                    mChecks.set(i,true);
                    item_checked(vh);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView lblDepartment,lblDoctor,lblTime;
        LinearLayout parent_layout;
        Button btnRemove;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            lblDepartment= itemView.findViewById(R.id.lblDepartment);
            lblDoctor = itemView.findViewById(R.id.lblDoctor);
            lblTime = itemView.findViewById(R.id.lblTime);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

    //刪除數據
    public void removeData(int position) {
        mDepartments.remove(position);
        mDoctors.remove(position);
        mTimes.remove(position);
        mDeleteables.remove(position);
        mChecks.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    //添加數據
    public void addData(int position) {
        mDepartments.add(position, "<空白>");
        mDoctors.add(position, "<空白>");
        mTimes.add(position, "");
        mDeleteables.add(position,"1");
        mChecks.add(position,false);
        //更新recyclerview頁面
        notifyItemInserted(position);
    }


    public void addTime(int position){
        Log.d(TAG,"addTime:start");

        if(mChecks.get(position)){
            if(mTimes.get(position) == ""){
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sDateFormat.format(new java.util.Date());
                mTimes.set(position,date);
                notifyItemChanged(position);
            }
        }
    }

    //添加數據
    public void CopyTimeOut(int position) {
        if(mChecks.get(position)){
            if(mTimes.get(position)   != ""){
                int indexEnd=mDepartments.size();
                mDepartments.add(indexEnd, mDepartments.get(position));
                mDoctors.add(indexEnd,mDoctors.get(position));
                mTimes.add(indexEnd,"");
                mDeleteables.add(indexEnd,"1");
                mChecks.add(indexEnd,false);
                notifyItemInserted(indexEnd);
            }
        }

    }



    //Recycler View 背景與標題字變色 --start
    private void item_unchecked(viewHolder vh) {
        vh.parent_layout.setBackgroundResource(R.color.md_white_1000);
        vh.lblDepartment.setTextColor(Color.parseColor("#000000"));
        vh.lblDoctor.setTextColor(Color.parseColor("#000000"));
        vh.lblTime.setTextColor(Color.parseColor("#000000"));
    }

    private void item_checked(viewHolder vh) {
        vh.parent_layout.setBackgroundResource(R.color.md_green_500);
        vh.lblDepartment.setTextColor(Color.parseColor("#FFFFFF"));
        vh.lblDoctor.setTextColor(Color.parseColor("#FFFFFF"));
        vh.lblTime.setTextColor(Color.parseColor("#FFFFFF"));
    }
    //Recycler View 背景與標題字變色 --End
}
