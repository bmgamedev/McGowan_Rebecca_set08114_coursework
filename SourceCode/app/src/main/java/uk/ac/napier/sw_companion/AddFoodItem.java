package uk.ac.napier.sw_companion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.lang.Math.round;

public class AddFoodItem extends AppCompatActivity {

    DailyLog todayLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        //Intent intent = getIntent();
        todayLog = (DailyLog) getIntent().getSerializableExtra("DailyLog");

        final EditText name = (EditText) findViewById(R.id.txtName);
        final EditText amount = (EditText) findViewById(R.id.txtAmount);
        final EditText syns = (EditText) findViewById(R.id.txtSyns);
        final CheckBox isFree = (CheckBox) findViewById(R.id.chkIsFree);
        final CheckBox hexA = (CheckBox) findViewById(R.id.chkHexA);
        final CheckBox hexB = (CheckBox) findViewById(R.id.chkHexB);

        final EditText calories = (EditText) findViewById(R.id.txtCal);
        final EditText calculatedSyns = (EditText) findViewById(R.id.txtSynCalc);
        Button btnCalcSyns = (Button) findViewById(R.id.btnCalcSyns);

        syns.setText("0");

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                Food foodItem = new Food();
                try{
                    foodItem.name = name.getText().toString();
                    foodItem.isFree =  isFree.isChecked();
                    foodItem.hexa =  hexA.isChecked();
                    foodItem.hexb =  hexB.isChecked();
                    foodItem.syns =  Double.parseDouble(syns.getText().toString());
                    foodItem.amount =  amount.getText().toString();
                    todayLog.food.add(foodItem);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AddFoodItem.this, "Error creating entry for food item. Please try again.", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(AddFoodItem.this, "Food item stored", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddFoodItem.this, ViewFoodLog.class);
                intent.putExtra("DailyLog", todayLog);
                startActivity(intent);

                finish();
            }

        });

        btnCalcSyns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                double syns = Double.parseDouble(calories.getText().toString());
                syns = round((syns/20) * 2);
                syns = syns /2;

                calculatedSyns.setText(Double.toString(syns));
            }

        });
    }
}
