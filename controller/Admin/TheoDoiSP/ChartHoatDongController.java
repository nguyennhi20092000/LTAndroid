package com.example.appbanhangnew.controller.Admin.TheoDoiSP;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.adapter.Admin.SPBanChayAdapter;
import com.example.appbanhangnew.model.SPBanChay;
import com.example.appbanhangnew.model.SoLieuChart;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;

public class ChartHoatDongController {
    Context context;

    public ChartHoatDongController(Context context) {
        this.context = context;
    }
    public  void SolieuChart(ArrayList<SoLieuChart> soLieuCharts, CombinedChart combinedChart){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetsolieuChart, new com.android.volley.Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                int tongtien;
                String  ngaythang;

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            tongtien=jsonObject.getInt("tongtien");
                            ngaythang=jsonObject.getString("ngaythang");
                            soLieuCharts.add(new SoLieuChart(tongtien, LocalDate.parse(ngaythang) ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("SPSpinner",e.toString()+"");
                        }
                    }

                    ArrayList<String> years= new ArrayList<>();
                    ArrayList<Integer> _tongtien=new ArrayList<>();
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
                        _tongtien.add(tong);

                    }
                    setChart(combinedChart,years,_tongtien);



                }else {
                    Log.d("SPSpinner",null+"");
                }


            }
        } , new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context,error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);
    }
    public boolean CheckYear(String year, ArrayList<String> stringArrayList){
        for(int i=0;i<stringArrayList.size();i++){
            if(stringArrayList.get(i).trim().equals(year)){
                return false;
            }
        }
        return true;
    }

    public void setChart(CombinedChart mChart, ArrayList<String> year, ArrayList<Integer> tongtien){

        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        //mChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) context);

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
