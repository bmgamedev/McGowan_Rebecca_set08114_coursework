package uk.ac.napier.sw_companion;

/**
 * Created by rcmcg on 08/03/2018.
 */

//for serialization
import java.io.Serializable;
import java.text.DecimalFormat;

public class Food implements Serializable{

    //properties
    //TODO - make private?
    public String name;
    public Boolean isFree;
    public Boolean hexa; //TODO add to UI
    public Boolean hexb; //TODO add to UI
    public double syns; //TODO change to double (gonna need to update casts etc in the rest of the code
    public String amount; //TODO int for quantity, string for unit of measurement
    //private String type; //breakfast/lunch/dinner/other

    //constructor
    public Food() {}

    /*public Food(String name, Boolean isFree, int syns, String amount) {
        this.name = name;
        this.isFree = isFree;
        this.syns = syns;
        this.amount = amount;
    }*/

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