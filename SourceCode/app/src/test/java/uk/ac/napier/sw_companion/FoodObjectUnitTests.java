package uk.ac.napier.sw_companion;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rcmcg on 13/03/2018.
 */

public class FoodObjectUnitTests {

    Food food = new Food();
    DailyLog dailyLog = new DailyLog();

    public Food storeFoodData(){
        food.amount = "1 portion";
        food.hexa = true;
        food.hexb = false;
        food.isFree = false;
        food.name = "food";
        food.syns = 0;

        return food;
    }

    @Test
    public void createFoodObject() throws Exception {
        assertNotNull(food);
    }

    @Test
    public void saveFoodName() throws Exception {
        food = storeFoodData();
        assertNotNull("Error storing data for 'Name'", food.name);
    }

    @Test
    public void saveFoodAmount() throws Exception {
        food = storeFoodData();
        assertNotNull("Error storing data for 'Amount'", food.amount);
    }

    @Test
    public void saveFoodSyns() throws Exception {
        food = storeFoodData();
        assertNotNull("Error storing data for 'Syns'", food.syns);
    }

    @Test
    public void saveFoodIsFree() throws Exception {
        food = storeFoodData();
        assertNotNull("Error storing data for 'isFree'", food.isFree);
    }

    @Test
    public void saveFoodHexA() throws Exception {
        food = storeFoodData();
        assertNotNull("Error storing data for 'HexA'", food.hexa);
    }

    @Test
    public void saveFoodHexB() throws Exception {
        food = storeFoodData();
        assertNotNull("Error storing data for 'HexB'", food.hexb);
    }

    @Test
    public void emptyFoodList() throws Exception {
        assertTrue(dailyLog.food.isEmpty());
    }

    @Test
    public void createFoodList() throws Exception {
        dailyLog.food.add(storeFoodData());

        assertFalse(dailyLog.food.isEmpty());
    }
}
