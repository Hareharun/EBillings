package com.example.ebillings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class FinancialAnalysisChart extends AppCompatActivity {
    private String income,groceryTotal;
    private PieChart fa_chart;
    float gt,food,party,medicine,msc,dress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_analysis_chart);
        income = getIntent().getStringExtra("income");
        groceryTotal = getIntent().getStringExtra("groceryTotall");
        fa_chart = findViewById(R.id.FA_chart);
        fa_chart.setUsePercentValues(true);
        gt = (float)((Integer.parseInt(groceryTotal)%Integer.parseInt(income))/100);
        dress = (float)((4065%Integer.parseInt(income))/100);
        food = (float)((3000%Integer.parseInt(income))/100);
        party = (float)((5000%Integer.parseInt(income))/100);
        medicine = (float)((2000%Integer.parseInt(income))/100);
        msc = (float)((3000%Integer.parseInt(income))/100);
        ArrayList<PieEntry> expenses = new ArrayList<>();
        expenses.add(new PieEntry(gt,"Grocery"));
        expenses.add(new PieEntry(dress,"Dresses"));
        expenses.add(new PieEntry(food,"Food"));
        expenses.add(new PieEntry(party,"Accomadation"));
        expenses.add(new PieEntry(medicine,"Medicine"));
        expenses.add(new PieEntry(msc,"Miscellaneous"));

        PieDataSet pieDataSet = new PieDataSet(expenses," ");

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        fa_chart.setData(pieData);
        fa_chart.getDescription().setEnabled(false);
        fa_chart.setCenterText("Income Vs Expense");
        fa_chart.animate();
        fa_chart.spin(2000,0f,360f, Easing.EaseInOutQuad);
    }
}