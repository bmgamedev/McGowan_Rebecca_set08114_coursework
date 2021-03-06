package uk.ac.napier.sw_companion;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnAdd = (Button) findViewById(R.id.btnAddLog);
        Button btnView = (Button) findViewById(R.id.btnViewLog);
        Button btnWeight = (Button) findViewById(R.id.btnWeightTracker);
        Button btnWebsite = (Button) findViewById(R.id.btnWebsite);
        final EditText logDate = (EditText) findViewById(R.id.txtLogDate);

        long date = System.currentTimeMillis();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(date);

        logDate.setText(formattedDate);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
            DailyLog todayLog = new DailyLog();

            Intent intent = new Intent(MainMenu.this, ViewFoodLog.class);
            intent.putExtra("DailyLog", todayLog);
            startActivity(intent);

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
            String fileDate = "";

            try {
                SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd");
                Date date = dateFormat.parse(logDate.getText().toString());
                fileDate = fileFormat.format(date);

            }
            catch (Exception e){
                Toast.makeText(MainMenu.this, "Date is in the wrong format. Try again with dd/MM/yyyy", Toast.LENGTH_LONG).show();
            }

            String filename = fileDate + "_DailyLog.txt";
            File file = getBaseContext().getFileStreamPath(filename);

            if(file.exists()){
                //since file exists, read the data into a food log object
                DailyLog todayLog = null;

                try{
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream objIn = new ObjectInputStream(fileIn);

                    todayLog = (DailyLog) objIn.readObject();

                    objIn.close();
                }
                catch (Exception e){
                    Toast.makeText(MainMenu.this, e.toString(), Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(MainMenu.this, ViewFoodLog.class);
                intent.putExtra("DailyLog", todayLog);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainMenu.this, "Sorry, no file found for " + filename, Toast.LENGTH_SHORT).show();
            }

            }
        });

        btnWeight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){

                File file = getBaseContext().getFileStreamPath("WeightLogFile.txt");

                WeightFile weightFile = null;

                if(file.exists()){
                    try{
                        FileInputStream fileIn = new FileInputStream(file);
                        ObjectInputStream objIn = new ObjectInputStream(fileIn);

                        weightFile = (WeightFile) objIn.readObject();

                        objIn.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainMenu.this, "Error retrieving weight data", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    weightFile = new WeightFile();
                }

                Intent intent = new Intent(MainMenu.this, WeightTracker.class);
                intent.putExtra("WeightFile", weightFile);
                startActivity(intent);
            }
        });

        btnWebsite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://www.slimmingworld.co.uk/"));
                    startActivity(intent);
            }
        });

    }

}
