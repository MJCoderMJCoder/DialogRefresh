package com.lzf.dialogrefresh;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by MJCoder on 2017-10-12.
 */

public class DialogActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heightpicker_alertdialog);

        HeightAdapter heightAdapter = new HeightAdapter(this);
        heightAdapter.initViews();
        ((RelativeLayout) findViewById(R.id.heightPickerDialog)).addView(heightAdapter.getView());


        final PickerView pvCm = heightAdapter.getPvCm();
        final PickerView pvFoot = heightAdapter.getPvFoot();
        final PickerView pvInch = heightAdapter.getPvInch();
        int heightUser = 200;
        final String[] cmTemp = {heightUser + ""};
        final String[] feetTemp = new String[1];
        final String[] inchTemp = new String[1];
        pvCm.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                if (temp == 30) {
                    pvFoot.setSelected(1 + "");
                    pvInch.setSelected(0 + "");
                    cmTemp[0] = text;
                    feetTemp[0] = 1 + "";
                    inchTemp[0] = 0 + "";
                } else {
                    int foot = (int) (temp / 30.48);
                    long inch = Math.round(((temp / 30.48) - foot) * 12);
                    cmTemp[0] = text;
                    feetTemp[0] = foot + "";
                    inchTemp[0] = inch + "";

                    Log.v("pvCm", text + "cm" + foot + "ft" + inch + "inch");
                    pvFoot.setSelected(foot + "");
                    pvInch.setSelected(inch + "");
                }
            }
        });
        pvFoot.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                Log.v("pvFoot-temp", "" + temp);
                long cm = Math.round((temp * 30.48) + Integer.parseInt(inchTemp[0]) * 2.54);
                pvCm.setSelected(cm + "");
                cmTemp[0] = cm + "";
                feetTemp[0] = text;

                Log.v("pvFoot-cmTemp[0] ", cmTemp[0]);
                Log.v("pvFoot-feetTemp[0] ", feetTemp[0]);
                Log.v("pvFoot-inchTemp[0] ", inchTemp[0]);

            }
        });
        pvInch.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int temp = Integer.parseInt(text);
                Log.v("pvInch-temp", "" + temp);
                long cm = Math.round((temp * 2.54) + Integer.parseInt(feetTemp[0]) * 30.48);
                pvCm.setSelected(cm + "");
                cmTemp[0] = cm + "";
                inchTemp[0] = text;

                Log.v("pvInch-cmTemp[0] ", cmTemp[0]);
                Log.v("pvInch-feetTemp[0] ", feetTemp[0]);
                Log.v("pvInch-inchTemp[0] ", inchTemp[0]);
            }
        });
        pvCm.setSelected(heightUser + "");
        int foot = (int) (heightUser / 30.48);
        long inch = Math.round(((heightUser / 30.48) - foot) * 12);
        Log.v("foot ", foot + "");
        Log.v("inch ", inch + "");
        pvFoot.setSelected(foot + "");
        pvInch.setSelected(inch + "");
        cmTemp[0] = heightUser + "";
        feetTemp[0] = foot + "";
        inchTemp[0] = inch + "";
    }
}
