package scu.csci187.fall2018.mealtracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import scu.csci187.fall2018.mealtracker.R;

public class TestActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        lv = findViewById(R.id.listView);

        data = new ArrayList<>();
        data.add("1");
        data.add("2");

        ArrayAdapter<String> lvAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        lv.setAdapter(lvAdapter);


    }
}
