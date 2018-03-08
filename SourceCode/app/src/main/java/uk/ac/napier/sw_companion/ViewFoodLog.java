package uk.ac.napier.sw_companion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewFoodLog extends AppCompatActivity {



    DailyLog todayLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_log);


        todayLog = (DailyLog) getIntent().getSerializableExtra("DailyLog");

        final TextView curDate = (TextView) findViewById(R.id.txtDate); //TODO change to be non-editable by users in the VIEW screen
        //final EditText syns = (EditText) findViewById(R.id.txtSyns);
        ListView foodItems = (ListView) findViewById(R.id.lstFoodItems); //set listview box to the daily log's .getFoodItems() contents
        final Button btnAddFood = (Button) findViewById(R.id.btnAddFood);
        TextView synsLabel = (TextView) findViewById(R.id.txtSynsLabel);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //Update the listview box
        //http://windrealm.org/tutorials/android/android-listview.php
        if(!todayLog.food.isEmpty()) {

            ArrayAdapter<String> listAdapter;
            ArrayList<String> foodList = new ArrayList<String>();

            for (Food food : todayLog.food) {
                foodList.add(food.toString());
            }

            listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, foodList);

            foodItems.setAdapter(listAdapter);
        }

        if(todayLog.date == null) {
            final long date = System.currentTimeMillis();
            SimpleDateFormat viewFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = viewFormat.format(date);

            curDate.setText(formattedDate);

            //syns.setText("0");
        }
        else {
            Date date = todayLog.date;
            SimpleDateFormat viewFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = viewFormat.format(date);

            curDate.setText(formattedDate);

            //syns.setText(Integer.toString(todayLog.syns));

            Double synTotal = 0.0;
            for (Food foodItem : todayLog.food){
                synTotal = synTotal + foodItem.syns;
            }
            //syns.setText(String.valueOf(synTotal));
            synsLabel.setText("Daily total: " + String.valueOf(synTotal) + " syns");
        }


        //Open the selected object in a new activity when clicked
        foodItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ViewFoodLog.this, "click is working", Toast.LENGTH_LONG).show();
                try{
                    Intent intent = new Intent(ViewFoodLog.this, AmendFoodItem.class);
                    //intent.putExtra("WeightEntry", weightEntry);
                    intent.putExtra("posInt", position);
                    intent.putExtra("DailyLog", todayLog);
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(ViewFoodLog.this, "error getting the food item entry", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        Button btnSave = (Button) findViewById(R.id.btnSaveWeight);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){

                //make sure the date can be parsed into a filename
                SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd");
                String fileDate;
                String filename = "";
                try {
                    Date date = todayLog.date;
                    fileDate = fileFormat.format(date);
                    filename = fileDate + "_DailyLog.txt";
                    //Toast.makeText(ViewFoodLog.this, " Parsing worked: " + filename, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(ViewFoodLog.this, e.toString(), Toast.LENGTH_LONG).show();
                }


                //update the food log object
                /*try{
                    //todayLog.syns = Integer.parseInt(syns.getText().toString());
                    //Toast.makeText(AddFoodLog.this, "Object created", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(ViewFoodLog.this, e.toString(), Toast.LENGTH_LONG).show();
                }*/

                try {
                    File file = getBaseContext().getFileStreamPath(filename);
                    if(file.exists()){file.delete();}
                    FileOutputStream fileOut = new FileOutputStream(file);
                    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                    objOut.writeObject(todayLog);
                    Toast.makeText(ViewFoodLog.this, filename + " saved", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ViewFoodLog.this, filename + " not saved", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ViewFoodLog.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(ViewFoodLog.this, MainMenu.class);
                startActivity(intent);
                finish();

            }
        });

        btnAddFood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                //Toast.makeText(ViewFoodLog.this, "Add some food...", Toast.LENGTH_SHORT).show();
                Date logDate;
                try{
                    logDate = dateFormat.parse(curDate.getText().toString());

                    todayLog.date = logDate;
                    //todayLog.syns = Integer.parseInt(syns.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(ViewFoodLog.this, e.toString(), Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(AddFoodLog.this, "Add some food...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewFoodLog.this, AddFoodItem.class);
                intent.putExtra("DailyLog", todayLog);
                startActivity(intent);

                finish();

            }
        });


    }
}
