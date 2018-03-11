package uk.ac.napier.sw_companion;

/**
 * Created by rcmcg on 08/03/2018.
 */

//for serialization
import java.io.Serializable;
import java.text.DecimalFormat;

public class Food implements Serializable{

    //properties
    public String name;
    public Boolean isFree;
    public Boolean hexa;
    public Boolean hexb;
    public double syns;
    public String amount;

    //constructor
    public Food() {}


    @Override
    public String toString(){
        String extras = "";
        if (isFree) {
            extras = " (Free)";
        } else if (hexa){
            extras = " (hexA)";
        } else if (hexb) {
            extras = " (hexB)";
        }
        DecimalFormat df = new DecimalFormat("#.#");
        String s = name + ", " + df.format(syns) + " syns" + extras;
        return s;
    }
}