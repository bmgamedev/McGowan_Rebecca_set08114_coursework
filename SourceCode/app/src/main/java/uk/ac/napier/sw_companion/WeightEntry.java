package uk.ac.napier.sw_companion;

/**
 * Created by rcmcg on 08/03/2018.
 */

//for serialization
import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeightEntry implements Serializable{

    //properties
    public Date date;
    public int stones;
    public double lbs;

    //constructor
    public WeightEntry(){}

    @Override
    public String toString(){
        SimpleDateFormat viewFormat = new SimpleDateFormat("dd/MM/yyyy");
        String s = viewFormat.format(date) + ": " + stones + "st " + lbs + "lbs";
        return s;
    }
}
