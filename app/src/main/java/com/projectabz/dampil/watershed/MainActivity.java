package com.projectabz.dampil.watershed;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projectabz.dampil.watershed.Filter.NumberInputFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    double layer;
    double K1h;
    double K1Hпр;
    double K2H;
    double K2Hпр;
    double L;
    static final double STEP = 5;
    Button buttonDetail;
    Button buttonClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDetail = (Button) findViewById(R.id.buttonDetail);
        buttonDetail.setVisibility(View.INVISIBLE);

        final EditText editLayer = (EditText) findViewById(R.id.editLayer);
        editLayer.setFilters(new InputFilter[] {new NumberInputFilter(2, 1)});

        final EditText editK1 = (EditText) findViewById(R.id.editK1);
        editK1.setFilters(new InputFilter[] {new NumberInputFilter(3, 1)});

        final EditText editK2 = (EditText) findViewById(R.id.editK2);
        editK2.setFilters(new InputFilter[] {new NumberInputFilter(3, 1)});

        final EditText editL = (EditText) findViewById(R.id.editL);
        editL.setFilters(new InputFilter[] {new NumberInputFilter(3, 1)});
    }

    public double unknown() {
        return (this.L + (this.K1h - this.K2H) / 0.4) / 2;
    }

    public double Hпр() {
        return this.K1Hпр - unknown() * 0.4;
    }

    public double unknown2() {
        return this.L - unknown();
    }

    public void onClickButton(View view) {

        EditText editLayer = (EditText) findViewById(R.id.editLayer);
        EditText editK1 = (EditText) findViewById(R.id.editK1);
        EditText editK2 = (EditText) findViewById(R.id.editK2);
        EditText editL = (EditText) findViewById(R.id.editL);

        if (editLayer.getText().toString().startsWith(".") || editK1.getText().toString().startsWith(".") || editK2.getText().toString().startsWith(".") ||
                editL.getText().toString().startsWith(".")) {
            Toast.makeText(getApplicationContext(), "Введите корректное значение", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            this.layer = Double.parseDouble(editLayer.getText().toString());
            this.K1h = Double.parseDouble(editK1.getText().toString());
            this.K1Hпр = this.K1h - this.layer;
            this.K2H = Double.parseDouble(editK2.getText().toString());
            this.K2Hпр = this.K2H - this.layer;
            this.L = Double.parseDouble(editL.getText().toString());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Введите данные", Toast.LENGTH_LONG).show();
            return;
        }

        TextView bigH = (TextView) findViewById(R.id.textView5);
        bigH.setTextColor(Color.RED);
        bigH.setText("Hпр = " + String.format("%.2f", Hпр()));

        TextView smallH1 = (TextView) findViewById(R.id.textView42);
        smallH1.setTextColor(Color.RED);
        smallH1.setText(String.format("%.2f", unknown()));

        TextView smallH2 = (TextView) findViewById(R.id.textView37);
        smallH2.setTextColor(Color.RED);
        smallH2.setText(String.format("%.2f", unknown2()));

        buttonDetail.setVisibility(View.VISIBLE);

    }

        public void onClickDetail (View view){
            try {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("Htext", "Hпр = " + String.format("%.2f", Hпр()));
                intent.putExtra("K1пр", K1Hпр);
                intent.putExtra("K2пр", K2Hпр);
                intent.putExtra("unknown", unknown());
                intent.putExtra("unknown2", unknown2());
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Введите данные", Toast.LENGTH_LONG).show();
            }
    }

    public void onClickButtonClear(View view) {
        buttonClear = (Button) findViewById(R.id.buttonClear);
        EditText editLayer = (EditText) findViewById(R.id.editLayer);
        EditText editK1 = (EditText) findViewById(R.id.editK1);
        EditText editK2 = (EditText) findViewById(R.id.editK2);
        EditText editL = (EditText) findViewById(R.id.editL);
        TextView bigH = (TextView) findViewById(R.id.textView5);


        bigH.setText("Hпр = ");

        TextView smallH1 = (TextView) findViewById(R.id.textView42);
        smallH1.setText("");

        TextView smallH2 = (TextView) findViewById(R.id.textView37);
        smallH2.setText("");

        editLayer.setText("");
        editK1.setText("");
        editK2.setText("");
        editL.setText("");
        buttonDetail = (Button) findViewById(R.id.buttonDetail);
        buttonDetail.setVisibility(View.INVISIBLE);
    }

    public void onClickCalc(View view) {
        ArrayList<HashMap<String,Object>> items = new ArrayList<HashMap<String,Object>>();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
