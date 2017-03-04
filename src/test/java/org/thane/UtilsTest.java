package org.thane;

import org.junit.Test;
import org.thane.entities.HighScore;
import org.thane.enums.Direction;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by GreatThane on 8/24/16.
 */
public class UtilsTest {
    @Test
    public void testFormatTime() throws Exception {

        //verify that we're not rounding minutes up when seconds are over 30
        assertEquals("1:39", Utils.formatTime(99));

        //Make sure we're padding seconds properly
        assertEquals("0:01", Utils.formatTime(1));

    }

    @Test
    public void testSortByValue() throws Exception {

        //Create some high scores
        Map<String, HighScore> highScores = new LinkedHashMap<String, HighScore>();
        highScores.put("UUID2342", new HighScore("myname", 43));
        highScores.put("UUID2433", new HighScore("yourname", 42));
        highScores.put("UUID2942", new HighScore("someoneelse", 100));
        highScores.put("UUID3828", new HighScore("ImBest", 32));

        //Sort them
        highScores = Utils.sortByValue(highScores);

        //Verify Order
        HighScore[] values = highScores.values().toArray(new HighScore[0]);
        assertTrue(values[0].getSeconds() == 32);
        assertTrue(values[1].getSeconds() == 42);
        assertTrue(values[2].getSeconds() == 43);
        assertTrue(values[3].getSeconds() == 100);
    }

    @Test
    public void testDirection() throws Exception {

        String directionString = "south";
        Direction direction = Direction.valueOf(directionString.toUpperCase());
        assertEquals(direction, Direction.SOUTH);
    }

}