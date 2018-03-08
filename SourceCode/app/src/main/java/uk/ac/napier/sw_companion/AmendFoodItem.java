package uk.ac.napier.sw_companion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AmendFoodItem extends AppCompatActivity {

    Food foodItem;
    DailyLog todayLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_food_item);

        todayLog = (DailyLog) getIntent().getSerializableExtra("DailyLog");
        final int pos = getIntent().getIntExtra("posInt", 0);
        foodItem = todayLog.food.get(pos);

        final EditText name = (EditText) findViewById(R.id.txtName);
        final EditText amount = (EditText) findViewById(R.id.txtAmount);
        final EditText syns = (EditText) findViewById(R.id.txtSyns);
        final CheckBox isFree = (CheckBox) findViewById(R.id.chkIsFree);
        final CheckBox hexA = (CheckBox) findViewById(R.id.chkHexA);
        final CheckBox hexB = (CheckBox) findViewById(R.id.chkHexB);
        Button btnAmend = (Button) findViewById(R.id.btnAmendFood);
        Button btnDelete = (Button) findViewById(R.id.btnDeleteFood);

        try{
            name.setText(foodItem.name.toString());
            amount.setText(foodItem.amount.toString());
            syns.setText(String.valueOf(foodItem.syns));
            if(foodItem.isFree) {
                isFree.setChecked(true);
            }
            if(foodItem.hexa) {
                hexA.setChecked(true);
            }
            if(foodItem.hexb) {
                hexB.setChecked(true);
            }

        }
        catch (Exception e){
            Toast.makeText(AmendFoodItem.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd");
        String fileDate;
        String filename = "";
        try {
            Date date = todayLog.date;
            fileDate = fileFormat.format(date);
            filename = fileDate + "_DailyLog.txt";
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO amend
        final String finalFilename1 = filename;
        btnAmend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                //Food foodItem = new Food();
                try{
                    //(String name, Boolean isFree, int syns, String amount)
                    foodItem.name = name.getText().toString();
                    foodItem.isFree =  isFree.isChecked();
                    foodItem.hexa =  hexA.isChecked();
                    foodItem.hexb =  hexB.isChecked();
                    foodItem.syns =  Double.parseDouble(syns.getText().toString());
                    foodItem.amount =  amount.getText().toString();
                }
                catch (Exception e){
                    Toast.makeText(AmendFoodItem.this, e.toString(), Toast.LENGTH_LONG).show();
                }

                Toast.makeText(AmendFoodItem.this, "food item stored", Toast.LENGTH_SHORT).show();

                try{
                    File file = getBaseContext().getFileStreamPath(finalFilename1);
                    if(file.exists()){file.delete();}
                    FileOutputStream fileOut = new FileOutputStream(file);
                    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                    objOut.writeObject(todayLog);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AmendFoodItem.this, "File not saved", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(AmendFoodItem.this, ViewFoodLog.class);
                intent.putExtra("DailyLog", todayLog);
                startActivity(intent);

                finish();
            }
        });

        //TODO Delete
        final String finalFilename = filename;
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                todayLog.food.remove(pos);

                try{
                    File file = getBaseContext().getFileStreamPath(finalFilename);
                    if(file.exists()){file.delete();}
                    FileOutputStream fileOut = new FileOutputStream(file);
                    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                    objOut.writeObject(todayLog);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AmendFoodItem.this, "File not saved", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(AmendFoodItem.this, ViewFoodLog.class);
                intent.putExtra("DailyLog", todayLog);
                startActivity(intent);

                finish();
            }
        });

    }
}
