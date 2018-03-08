package uk.ac.napier.sw_companion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class AddWeight extends AppCompatActivity {

    WeightFile weightFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        weightFile = (WeightFile) getIntent().getSerializableExtra("WeightFile");

        final EditText curDate = (EditText) findViewById(R.id.txtDate);
        final EditText txtStones = (EditText) findViewById(R.id.txtStones);
        final EditText txtLbs = (EditText) findViewById(R.id.txtLbs);
        Button btnSave = (Button) findViewById(R.id.btnSaveWeight);

        final long date = System.currentTimeMillis();
        SimpleDateFormat viewFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = viewFormat.format(date);

        curDate.setText(formattedDate);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                //save the file when they click save or create a new one if it doesn't exist
                //pass the updated object back into this window

                WeightEntry newWeight = new WeightEntry();

                if (txtLbs.getText().toString() == ""){
                    txtLbs.setText("0");
                }
                if (txtStones.getText().toString() == ""){
                    txtStones.setText("0");
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date entryDate = null;
                try {
                    entryDate = dateFormat.parse(curDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(AddWeight.this, "Current date format cannot be saved. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                }

                try{
                    newWeight.date = entryDate;
                    newWeight.stones = Integer.parseInt(txtStones.getText().toString());
                    newWeight.lbs = Double.parseDouble(txtLbs.getText().toString());
                    weightFile.weightList.add(newWeight);
                }
                catch (Exception e){
                    Toast.makeText(AddWeight.this, "Issue creating object", Toast.LENGTH_SHORT).show();
                }

                try {
                    Collections.sort(weightFile.weightList, new Comparator<WeightEntry>() {
                        public int compare(WeightEntry o1, WeightEntry o2) {
                            if (o1.date == null || o2.date == null)
                                return 0;
                            return o1.date.compareTo(o2.date);
                        }
                    });

                }
                catch (Exception e){
                    Toast.makeText(AddWeight.this, "Issue ordering the list", Toast.LENGTH_SHORT).show();
                }



                try{
                    File file = getBaseContext().getFileStreamPath("WeightLogFile.txt");

                    if(file.exists()){file.delete();}
                    FileOutputStream fileOut = new FileOutputStream(file);
                    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                    objOut.writeObject(weightFile);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AddWeight.this, "File not saved", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(AddWeight.this, WeightTracker.class);
                intent.putExtra("WeightFile", weightFile);
                startActivity(intent);

                finish();

            }
        });

    }
}
