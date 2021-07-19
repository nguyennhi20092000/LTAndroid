package com.example.appbanhangnew.action.Admin.TheoDoiSP;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.Admin.TheoDoiSP.ChartHoatDongController;
import com.example.appbanhangnew.model.SoLieuChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ChartHoatDongActivity extends AppCompatActivity {
    CombinedChart combinedChart;
    Button btnthisyear, btnprevioud, btnnext, btnallyear;
    ArrayList<SoLieuChart> soLieuCharts;
    ChartHoatDongController chartHoatDongController;
    ArrayList<String> month;
    ArrayList<Integer> tongtien;
    Toolbar toolbarbieudo;
    int YearNow;
    boolean check=false;
    ArrayList<String> years= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_hoat_dong);
        AnhXa();
        ActionBar();
        chartHoatDongController.SolieuChart(soLieuCharts,combinedChart);
        btnthisyear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                check=true;

                for(int i=0;i<soLieuCharts.size();i++){
                    if(CheckYear(String.valueOf(soLieuCharts.get(i).getNgaythang().getYear()),years)){
                        years.add(String.valueOf(soLieuCharts.get(i).getNgaythang().getYear()));
                    }
                }
                YearNow=years.size()-1;
                ArrayList<SoLieuChart> soLieuChartsinThisYear=new ArrayList<>();
                month=new ArrayList<>();
                tongtien=new ArrayList<>();
                for (int i=0;i<soLieuCharts.size();i++){
                    if(soLieuCharts.get(i).getNgaythang().getYear()== LocalDate.now().getYear()){
                        soLieuChartsinThisYear.add(soLieuCharts.get(i));
                    }
                }
                for(int i=0;i<soLieuChartsinThisYear.size();i++){
                    if(CheckYear(String.valueOf(soLieuCharts.get(i).getNgaythang().getYear()),month)){
                        month.add(String.valueOf(soLieuChartsinThisYear.get(i).getNgaythang().getMonth()));
                    }

                }
                for(int i=0;i<month.size();i++){
                    int tong=0;
                    for(int j=0;j<soLieuCharts.size();j++){
                        if(String.valueOf(soLieuCharts.get(j).getNgaythang().getMonth()).trim().equals(month.get(i).trim())) {
                            tong = tong +soLieuCharts.get(j).getTongtien();
                        }
                    }
                    tongtien.add(tong);

                }
                setChart(combinedChart,month,tongtien);
            }
        });
        btnallyear.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                check=false;
                tongtien=new ArrayList<>();
                for(int i=0;i<soLieuCharts.size();i++){
                    if(CheckYear(String.valueOf(soLieuCharts.get(i).getNgaythang().getYear()),years)){
                        years.add(String.valueOf(soLieuCharts.get(i).getNgaythang().getYear()));
                    }

                    //_tongtien.add(soLieuCharts.get(i).getTongtien());
                }

                for(int i=0;i<years.size();i++){
                    int tong=0;
                    for(int j=0;j<soLieuCharts.size();j++){
                        if(String.valueOf(soLieuCharts.get(j).getNgaythang().getYear()).trim().equals(years.get(i).trim())) {
                            tong = tong +soLieuCharts.get(j).getTongtien();
                        }
                    }
                    tongtien.add(tong);

                }
                setChart(combinedChart,years,tongtien);
            }
        });

        btnprevioud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check && YearNow>0){
                    YearNow--;
                    ArrayList<SoLieuChart> soLieuChartsinThisYear=new ArrayList<>();
                    month=new ArrayList<>();
                    tongtien=new ArrayList<>();
                    for (int i=0;i<soLieuCharts.size();i++){
                        if(soLieuCharts.get(i).getNgaythang().getYear()== Integer.parseInt(years.get(YearNow))){
                            soLieuChartsinThisYear.add(soLieuCharts.get(i));
                        }
                    }
                    for(int i=0;i<soLieuChartsinThisYear.size();i++){
                        if(CheckYear(String.valueOf(soLieuCharts.get(i).getNgaythang().getYear()),month)){
                            month.add(String.valueOf(soLieuChartsinThisYear.get(i).getNgaythang().getMonth()));
                        }

                    }
                    for(int i=0;i<month.size();i++){
                        int tong=0;
                        for(int j=0;j<soLieuCharts.size();j++){
                            if(String.valueOf(soLieuCharts.get(j).getNgaythang().getMonth()).trim().equals(month.get(i).trim())) {
                                tong = tong +soLieuCharts.get(j).getTongtien();
                            }
                        }
                        tongtien.add(tong);

                    }
                    setChart(combinedChart,month,tongtien);
                }else {
                    if(check==false){
                        Toast.makeText(getApplicationContext(),"Bạn chỉ được phép khi đang xem chế độ từng năm",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Đến giới hạn xem!!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check && YearNow<years.size()-1){
                    YearNow++;
                    ArrayList<SoLieuChart> soLieuChartsinThisYear=new ArrayList<>();
                    month=new ArrayList<>();
                    tongtien=new ArrayList<>();
                    for (int i=0;i<soLieuCharts.size();i++){
                        if(soLieuCharts.get(i).getNgaythang().getYear()== Integer.parseInt(years.get(YearNow))){
                            soLieuChartsinThisYear.add(soLieuCharts.get(i));
                        }
                    }
                    for(int i=0;i<soLieuChartsinThisYear.size();i++){
                        if(CheckYear(String.valueOf(soLieuCharts.get(i).getNgaythang().getYear()),month)){
                            month.add(String.valueOf(soLieuChartsinThisYear.get(i).getNgaythang().getMonth()));
                        }

                    }
                    for(int i=0;i<month.size();i++){
                        int tong=0;
                        for(int j=0;j<soLieuCharts.size();j++){
                            if(String.valueOf(soLieuCharts.get(j).getNgaythang().getMonth()).trim().equals(month.get(i).trim())) {
                                tong = tong +soLieuCharts.get(j).getTongtien();
                            }
                        }
                        tongtien.add(tong);

                    }
                    setChart(combinedChart,month,tongtien);
                }else {
                    if(check==false){
                        Toast.makeText(getApplicationContext(),"Bạn chỉ được phép khi đang xem chế độ từng năm",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Đến giới hạn xem!!",Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
    }


    public boolean CheckYear(String year, ArrayList<String> stringArrayList){
        for(int i=0;i<stringArrayList.size();i++){
            if(stringArrayList.get(i).trim().equals(year)){
                return false;
            }
        }
        return true;
    }
    public void ActionBar() {
        setSupportActionBar(toolbarbieudo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_vector_test);
        toolbarbieudo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //toolbaruser.inflateMenu(R.menu.menu_dangxuat);


    }
    public void AnhXa(){
        combinedChart=(CombinedChart) findViewById(R.id.combinedChart);
        soLieuCharts=new ArrayList<>();
        chartHoatDongController=new ChartHoatDongController(this);
        btnthisyear=(Button) findViewById(R.id.btnthisyear);
        btnnext=(Button) findViewById(R.id.btnNext);
        btnallyear=(Button) findViewById(R.id.btnAllYear);
        btnprevioud=(Button) findViewById(R.id.btnPrevious);
        toolbarbieudo = (Toolbar) findViewById(R.id.toolbarbieudo);
    }

    public void setChart(CombinedChart mChart, ArrayList<String> year, ArrayList<Integer> tongtien){

        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        //mChart.setOnChartValueSelectedListener(this);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);



        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return year.get((int) value % year.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart(tongtien));

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }





    public DataSet dataChart(ArrayList<Integer> data) {

        LineData d = new LineData();


        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < data.size(); index++) {
            entries.add(new Entry(index, data.get(index)));
        }

        LineDataSet set = new LineDataSet(entries, "Request Ots approved");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

}