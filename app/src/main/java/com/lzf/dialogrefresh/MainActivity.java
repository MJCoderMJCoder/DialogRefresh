package com.lzf.dialogrefresh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PickerView pvCm;

    int delete = 175;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            int what = msg.what;
            if (what == 0) {    //update
                TextView tv = (TextView) mAlertDialog.findViewById(R.id.tv_dialog);
                tv.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", System
                        .currentTimeMillis()).toString());
                if (mAlertDialog.isShowing()) {
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                }
            } else {
                mAlertDialog.cancel();
            }
        }
    };

    private AlertDialog mAlertDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = View.inflate(getApplicationContext(), R.layout.dialog_layout, null);

        mAlertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        mAlertDialog.cancel();
                    }
                }).create();

        mAlertDialog.setTitle("mAlertDialog");

        Button button = (Button) findViewById(R.id.btn_show);
        Button height = (Button) findViewById(R.id.btn_height);
        Button activity = (Button) findViewById(R.id.btn_activity);
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                startActivity(new Intent(MainActivity.this, DialogActivity.class));

                pvCm.setSelected((delete++) + "");
                Log.v("delete", delete + "");
            }
        });
        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLzfHeight();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mAlertDialog.show();

                mHandler.sendEmptyMessage(0);
            }
        });

        setHeight();
    }


    public void setLzfHeight() {
        //初始化Builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //加载自定义的那个View,同时设置下
        final LayoutInflater inflater = this.getLayoutInflater();
        final View heightPickerDialog = inflater.inflate(R.layout.heightpicker_alertdialog, null, false);

        HeightAdapter heightAdapter = new HeightAdapter(this);
        heightAdapter.initViews();
        ((RelativeLayout) heightPickerDialog.findViewById(R.id.heightPickerDialog)).addView(heightAdapter.getView());
        final PickerView pvCm = heightAdapter.getPvCm();
        final PickerView pvFoot = heightAdapter.getPvFoot();
        final PickerView pvInch = heightAdapter.getPvInch();
        final TextView feetTxt = (TextView) findViewById(R.id.feet); //英尺==foot
        final TextView inchTxt = (TextView) findViewById(R.id.inch); //英寸
        final TextView cmTxt = (TextView) findViewById(R.id.cm); //英寸
        pvCm.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                if (temp == 30) {
                    pvFoot.setSelected(1 + "");
                    pvInch.setSelected(0 + "");
                    cmTxt.setText(text);
                    feetTxt.setText(1 + "");
                    inchTxt.setText(0 + "");
                } else {
                    int foot = (int) (temp / 30.48);
                    long inch = Math.round(((temp / 30.48) - foot) * 12);
                    pvFoot.setSelected(foot + "");
                    pvInch.setSelected(inch + "");
                    cmTxt.setText(text);
                    feetTxt.setText(foot + "");
                    inchTxt.setText(inch + "");
                }
                pvFoot.updateHandler.sendEmptyMessage(0);
                pvInch.updateHandler.sendEmptyMessage(0);
            }
        });
        pvFoot.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                long cm = Math.round((temp * 30.48) + Integer.parseInt(inchTxt.getText().toString()) * 2.54);
                pvCm.setSelected(cm + "");
                feetTxt.setText(text);
                cmTxt.setText(cm + "");
                pvCm.updateHandler.sendEmptyMessage(0);
            }
        });
        pvInch.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                long cm = Math.round((temp * 2.54) + Integer.parseInt(feetTxt.getText().toString()) * 30.48);
                pvCm.setSelected(cm + "");
                inchTxt.setText(text);
                cmTxt.setText(cm + "");
                pvCm.updateHandler.sendEmptyMessage(0);
            }
        });
        final int heightUser = 200;
        pvCm.setSelected(heightUser + "");
        int foot = (int) (heightUser / 30.48);
        long inch = Math.round(((heightUser / 30.48) - foot) * 12);
        pvFoot.setSelected(foot + "");
        pvInch.setSelected(inch + "");
        cmTxt.setText(heightUser + "");
        feetTxt.setText(foot + "");
        inchTxt.setText(inch + "");


        builder.setView(heightPickerDialog);
        builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        heightPickerDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        heightPickerDialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                tvHeight.setText(cmTemp[0]);
                alert.dismiss();
            }
        });
        alert.show();
    }

    public void setHeight() {

        HeightAdapter heightAdapter = new HeightAdapter(this);
        heightAdapter.initViews();
        ((RelativeLayout) findViewById(R.id.heightPickerDialogM)).addView(heightAdapter.getView());
        pvCm = heightAdapter.getPvCm();
        final PickerView pvFoot = heightAdapter.getPvFoot();
        final PickerView pvInch = heightAdapter.getPvInch();
        final TextView feetTxt = (TextView) findViewById(R.id.feet); //英尺==foot
        final TextView inchTxt = (TextView) findViewById(R.id.inch); //英寸
        final TextView cmTxt = (TextView) findViewById(R.id.cm); //英寸
        pvCm.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                if (temp == 30) {
                    pvFoot.setSelected(1 + "");
                    pvInch.setSelected(0 + "");
                    cmTxt.setText(text);
                    feetTxt.setText(1 + "");
                    inchTxt.setText(0 + "");
                } else {
                    int foot = (int) (temp / 30.48);
                    long inch = Math.round(((temp / 30.48) - foot) * 12);
                    cmTxt.setText(text);
                    feetTxt.setText(foot + "");
                    inchTxt.setText(inch + "");
                    pvFoot.setSelected(foot + "");
                    pvInch.setSelected(inch + "");
                }
                pvFoot.updateHandler.sendEmptyMessage(0);
                pvInch.updateHandler.sendEmptyMessage(0);
            }
        });
        pvFoot.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                long cm = Math.round((temp * 30.48) + Integer.parseInt(inchTxt.getText().toString()) * 2.54);
                pvCm.setSelected(cm + "");
                feetTxt.setText(text);
                cmTxt.setText(cm + "");

                pvCm.updateHandler.sendEmptyMessage(0);
            }
        });
        pvInch.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                long cm = Math.round((temp * 2.54) + Integer.parseInt(feetTxt.getText().toString()) * 30.48);
                pvCm.setSelected(cm + "");
                inchTxt.setText(text);
                cmTxt.setText(cm + "");
                pvCm.updateHandler.sendEmptyMessage(0);
            }
        });
        final int heightUser = 200;
        pvCm.setSelected(heightUser + "");
        int foot = (int) (heightUser / 30.48);
        long inch = Math.round(((heightUser / 30.48) - foot) * 12);
        //        pvFoot.setSelected(foot + "");
        //        pvInch.setSelected(inch + "");
        cmTxt.setText(heightUser + "");
        feetTxt.setText(foot + "");
        inchTxt.setText(inch + "");

    }

}
