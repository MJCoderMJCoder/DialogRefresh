package com.lzf.dialogrefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-29.
 */

public class HeightAdapter {
    private Context context;
    private View view;

    public final List<String> HEIGHT_CM = new ArrayList<String>();
    public final List<String> HEIGHT_FOOT = new ArrayList<String>(); //英尺
    public final List<String> HEIGHT_INCH = new ArrayList<String>(); //英寸

    private PickerView pvCm;
    private PickerView pvFoot;
    private PickerView pvInch;
    private TextView btCm;
    private TextView btInch;

    public HeightAdapter(Context context) {
        this.context = context;

        this.view = LayoutInflater.from(context).inflate(R.layout.item_info_height, null);
        addData();
    }

    /**
     * 身高      最终使用cm 存储      1英尺=30.48厘米；1英寸=2.54厘米；1英尺=12英寸；      范围：30 cm 至 303cm    1foot-9foot   0 inch- 11 inch
     */
    public void addData() {
        for (int i = 30; i < 304; i++) {
            HEIGHT_CM.add(String.valueOf(i));
        }
        for (int i = 0; i < 12; i++) {
            HEIGHT_INCH.add(String.valueOf(i));
        }
        for (int i = 1; i < 10; i++) {
            HEIGHT_FOOT.add(String.valueOf(i));
        }
    }

    public void initViews() {
        pvFoot = (PickerView) view.findViewById(R.id.pv_ft);
        pvInch = (PickerView) view.findViewById(R.id.pv_in);
        pvCm = (PickerView) view.findViewById(R.id.pv_cm);
        btCm = (TextView) view.findViewById(R.id.bt_height_cm);
        btInch = (TextView) view.findViewById(R.id.bt_height_inch);
        pvFoot.setData(HEIGHT_FOOT);
        pvInch.setData(HEIGHT_INCH);
        pvCm.setData(HEIGHT_CM);
    }


    public void loadCm() {
        pvCm.setData(HEIGHT_CM);
    }

    public void loadInch() {
        pvInch.setData(HEIGHT_INCH);
    }

    /**
     * 将实例的值显示到控件
     *
     * @param height
     */
    public void getHeight(String height) {
        pvCm.setSelected(String.valueOf((int) Float.parseFloat(height)));
    }

    public void cmClick(final String height) {
        btCm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCm();
                getHeight(height);
            }
        });
    }

    public void inchClick(final String height) {
        btInch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInch();
                getHeight(height);
            }
        });
    }

    public View getView() {
        return view;
    }

    public PickerView getPv() {
        return pvCm;
    }

    public PickerView getPvCm() {
        return pvCm;
    }

    public PickerView getPvFoot() {
        return pvFoot;
    }

    public PickerView getPvInch() {
        return pvInch;
    }
}
