package com.projectabz.dampil.watershed;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dampil on 23.04.16.
 */
public class DetailActivity extends Activity {

    String title;
    double K1пр;
    double K2пр;
    double unknown1;
    double unknown2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);


        title = getIntent().getExtras().getString("Htext");
        K1пр = getIntent().getExtras().getDouble("K1пр");
        K2пр = getIntent().getExtras().getDouble("K2пр");
        unknown1 = getIntent().getExtras().getDouble("unknown");
        unknown2 = getIntent().getExtras().getDouble("unknown2");

        TextView t0 = (TextView) findViewById(R.id.titleDetail);
        t0.setTextColor(Color.RED);
        t0.setText(title);

        TextView end1 = (TextView) findViewById(R.id.textView24);
        end1.setText(title.substring(6));

        TextView end2 = (TextView) findViewById(R.id.textView36);
        end2.setText(title.substring(6));

        TextView end3 = (TextView) findViewById(R.id.textView25);
        end3.setText("0");

        TextView end4 = (TextView) findViewById(R.id.textView43);
        end4.setText("0");

        TextView m1 = (TextView) findViewById(R.id.textView38);
        m1.setText("" + String.format("%.2f", unknown1));

        TextView m2 = (TextView) findViewById(R.id.textView39);
        m2.setText("" + String.format("%.2f", unknown2));

        TextView t1 = (TextView) findViewById(R.id.textView30);
        t1.setText("" + String.format("%.2f",K1пр));

        TextView t14 = (TextView) findViewById(R.id.textView14);
        t14.setText(String.format("%.2f",(unknown1 / 5)));

        TextView t3 = (TextView) findViewById(R.id.textView29);
        t3.setText("" + String.format("%.2f", (K1пр - (unknown1 / 5) * 0.4)));

        TextView t15 = (TextView) findViewById(R.id.textView15);
        t15.setText("" + String.format("%.2f",(unknown1 / 5)));

        TextView t5 = (TextView) findViewById(R.id.textView28);
        t5.setText("" + String.format("%.2f",((K1пр - (unknown1 / 5) * 0.4) - (unknown1 / 5) * 0.4)));

        TextView t16 = (TextView) findViewById(R.id.textView16);
        t16.setText("" + String.format("%.2f",(unknown1 / 5)));

        TextView t7 = (TextView) findViewById(R.id.textView27);
        t7.setText("" + String.format("%.2f",(((K1пр - (unknown1 / 5) * 0.4) - (unknown1 / 5) * 0.4) - (unknown1 / 5) * 0.4)));

        TextView t17 = (TextView) findViewById(R.id.textView17);
        t17.setText("" + String.format("%.2f",(unknown1 / 5)));

        TextView t9 = (TextView) findViewById(R.id.textView26);
        t9.setText("" + String.format("%.2f",((((K1пр - (unknown1 / 5) * 0.4) - (unknown1 / 5) * 0.4) - (unknown1 / 5) * 0.4) - (unknown1 / 5) * 0.4)));

        TextView t18 = (TextView) findViewById(R.id.textView18);
        t18.setText("0");



        TextView t10 = (TextView) findViewById(R.id.textView35);
        t10.setText("" + String.format("%.2f",K2пр));

        TextView t19 = (TextView) findViewById(R.id.textView19);
        t19.setText(String.format("%.2f",(unknown2 / 5)));

        TextView t12 = (TextView) findViewById(R.id.textView34);
        t12.setText("" + String.format("%.2f",(K2пр - (unknown2 / 5) * 0.4)));

        TextView t20 = (TextView) findViewById(R.id.textView20);
        t20.setText("" + String.format("%.2f",(unknown2 / 5)));

        TextView tt14 = (TextView) findViewById(R.id.textView33);
        tt14.setText("" + String.format("%.2f",((K2пр - (unknown2 / 5) * 0.4) - (unknown2 / 5) * 0.4)));

        TextView t21 = (TextView) findViewById(R.id.textView21);
        t21.setText("" + String.format("%.2f",(unknown2 / 5)));

        TextView tt16 = (TextView) findViewById(R.id.textView32);
        tt16.setText("" + String.format("%.2f",(((K2пр - (unknown2 / 5) * 0.4) - (unknown2 / 5) * 0.4) - (unknown2 / 5) * 0.4)));

        TextView t22 = (TextView) findViewById(R.id.textView22);
        t22.setText("" + String.format("%.2f",(unknown2 / 5)));

        TextView tt18 = (TextView) findViewById(R.id.textView31);
        tt18.setText("" + String.format("%.2f",(((((K2пр - (unknown2 / 5) * 0.4) - (unknown2 / 5) * 0.4) - (unknown2 / 5) * 0.4) - (unknown2 / 5) * 0.4))));

        TextView t23 = (TextView) findViewById(R.id.textView23);
        t23.setText("0");
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }

    public void onClickCalc2(View view) {
        ArrayList<HashMap<String,Object>> items =new ArrayList<HashMap<String,Object>>();
        final PackageManager pm = getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if( pi.packageName.toString().toLowerCase().contains("calcul")){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }

        if(!items.isEmpty()){
            String packageName = (String) items.get(0).get("packageName");
            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null)
                startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(), "На вашем телефоне не установлен калькулятор", Toast.LENGTH_SHORT).show();
        }
    }
}
