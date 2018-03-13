package uk.ac.napier.sw_companion;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by rcmcg on 13/03/2018.
 */

public class WeightObjectUnitTest {

    WeightEntry weight = new WeightEntry();
    WeightFile weightFile = new WeightFile();

    public WeightEntry storeWeightData(){
        weight.date = new Date(2018, 1, 1);;
        weight.lbs = 10.1;
        weight.stones = 10;

        return weight;
    }

    @Test
    public void createWeightObject() throws Exception {
        assertNotNull(weight);
    }

    @Test
    public void saveWeightLbs() throws Exception {
        weight = storeWeightData();
        assertNotNull("Error storing data for 'Lbs'", weight.lbs);
    }

    @Test
    public void saveWeightStones() throws Exception {
        weight = storeWeightData();
        assertNotNull("Error storing data for 'Stones'", weight.stones);
    }

    @Test
    public void emptyWeightList() throws Exception {
        assertTrue(weightFile.weightList.isEmpty());
    }

    @Test
    public void createWeightList() throws Exception {
        weightFile.weightList.add(storeWeightData());

        assertFalse(weightFile.weightList.isEmpty());
    }
}
