package org.thane;

import com.avaje.ebean.validation.AssertTrue;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jprice on 8/24/16.
 */
public class UtilsTest {
    @Test
    public void testFormatTime() throws Exception {

        //verify that we're not rounding minutes up when seconds are over 30
        assertEquals("1:39", Utils.formatTime(99));

        //Make sure we're padding seconds properly
        assertEquals("0:01", Utils.formatTime(1));

    }

}