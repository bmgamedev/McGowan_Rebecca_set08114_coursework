package uk.ac.napier.sw_companion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WeightTracker extends AppCompatActivity {

    WeightFile weightFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_tracker);

        weightFile = (WeightFile) getIntent().getSerializableExtra("WeightFile");

        ListView weightTracker = (ListView) findViewById(R.id.lstWeight);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        TextView txtProgress = (TextView) findViewById(R.id.txtProgress);

        //Update the listview box + progress textbox
        //http://windrealm.org/tutorials/android/android-listview.php
        if(!weightFile.weightList.isEmpty()) {

            ArrayAdapter<String> listAdapter;
            ArrayList<String> weightList = new ArrayList<String>();

            for (WeightEntry weightEntry : weightFile.weightList) {
                weightList.add(weightEntry.toString());
            }

            listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, weightList);

            weightTracker.setAdapter(listAdapter);
            weightTracker.setSelection(listAdapter.getCount() - 1);

            if(weightFile.weightList.size() > 1){
                String progress = getProgress();
                txtProgress.setText(progress);
            }
        }


        //Open the selected object in a new activity when clicked
        weightTracker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WeightTracker.this, "click is working", Toast.LENGTH_LONG).show();
                WeightEntry weightEntry = null;
                try{
                    //int pos = (int) parent.getAdapter().getItem(position);
                    //weightEntry = weightFile.weightList.get(position);

                    try {
                        Intent intent = new Intent(WeightTracker.this, AmendWeight.class);
                        //intent.putExtra("WeightEntry", weightEntry);
                        intent.putExtra("posInt", position);
                        intent.putExtra("WeightFile", weightFile);
                        startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(WeightTracker.this, "error opening new activity", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(WeightTracker.this, "error getting the weight entry", Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });


        //Add a new weight entry
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                Intent intent = new Intent(WeightTracker.this, AddWeight.class);
                intent.putExtra("WeightFile", weightFile);
                startActivity(intent);

                finish();
            }
        });

    }

    //Calculate the weight change to update the "Current progress..." string
    private String getProgress() {
        WeightEntry weight = weightFile.weightList.get(weightFile.weightList.size() - 1);
        double lastWeight = (weight.stones * 14) + weight.lbs;
        weight = weightFile.weightList.get(0);
        double firstWeight = (weight.stones * 14) + weight.lbs;
        double delta = lastWeight - firstWeight;
        String movement = "";
        if (delta < 0) {
            movement = "loss";
        } else if (delta > 0) {
            movement = "gain";
        }
        double deltalbs = delta % 14;
        double deltast = (delta - deltalbs) / 14;
        String progress = "Current progress: " + (int)Math.abs(deltast) + "st " + Math.abs(deltalbs) + "lbs " + movement;
        return progress;
    }

}
