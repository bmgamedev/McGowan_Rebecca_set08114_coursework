package uk.ac.napier.sw_companion;

/**
 * Created by rcmcg on 08/03/2018.
 */

//for serialization
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeightFile implements Serializable{

    public List<WeightEntry> weightList= new ArrayList<WeightEntry>();

    public WeightFile() {}
}
