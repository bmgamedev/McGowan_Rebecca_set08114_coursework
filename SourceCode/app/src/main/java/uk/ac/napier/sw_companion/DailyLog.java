package uk.ac.napier.sw_companion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * Created by rcmcg on 08/03/2018.
 */

public class DailyLog implements Serializable {

    //properties
    public Date date;
    public List<Food> food  = new ArrayList<Food>();

    //constructors
    public DailyLog(){}
}