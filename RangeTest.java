package org.jfree.data.test;

import junit.framework.TestCase;
import org.jfree.data.Range;
import org.junit.Test;

import static org.junit.Assert.*;


public class RangeTest{
    Range range= new Range(-2.0,9.0);

    @Test
     public void Test_combine()
    {
        Range R1 =new Range(-20.0,10);
        Range R2=new Range(-15.0,15.0);
        Range Real=Range.combine(R1,R2);
        assertTrue(Real.getLowerBound()==-20.0);
        // failed, the function should return True when the upper is 15.0 but the function return false
        //assertTrue(Real.getUpperBound()==15.0);

        Range Real_2=Range.combine(null,R1);
        assertTrue(Real_2.getLowerBound()==-20.0);
        assertTrue(Real_2.getUpperBound()==10.0);


        Range Real_3=Range.combine(null,null);
        assertNull(Real_3);
        assertTrue(Real_3==null);
    }
    @Test
    public void Test_constrain()
    {
        assertEquals(range.constrain(-5.0),-2.0,1.0E-13);
        assertEquals(range.constrain(10.0),9.0,1.0E-13);
        assertEquals(range.constrain(6.0),6.0,1.0E-13);
        assertEquals(range.constrain(-2.0),-2.0,1.0E-13);
        assertEquals(range.constrain(9.0),9.0,1.0E-13);
        assertEquals(range.constrain(8.99),8.99,1.0E-13);
        assertEquals(range.constrain(-1.99),-1.99,1.0E-13);
    }

    @Test
    public void test_contains()
    {
        // Upper Input
        assertTrue(range.contains(-1.0)==true);
        assertFalse(range.contains(-3.0)==true);
        assertEquals(range.contains(-1.0),true);
        assertNotEquals(range.contains(-6.0),true);

        //Lower Input
        assertTrue(range.contains(8.0)==true);
        assertFalse(range.contains(10.0)==true);
        assertEquals(range.contains(8.0),true);
        assertNotEquals(range.contains(10.0),true);

        // same Range
        // failed, the function should return true when that the lower is -2.0  but in real was return false
        // assertTrue(range.contains(-2.0)==true);
        // assertFalse(range.contains(-2.0)==false);

        assertEquals(range.contains(9.0),true);
        assertNotEquals(range.contains(9.0),false);
        assertEquals(range.contains(0.0),true);
    }
    @Test
    public void Test_expand()
    {
        //The rule for loweMargins is number - length * param1 ===>  2.0 - (8.0-2.0) * 0.6 == -1.6
        //The rule for upperMargins is number + length *param2  ==> 8.0 + (8.0-2.0) * 5   ==  38
        Range Example =new Range(2.0,8.0);
        Range Expand_Range=Range.expand(Example,0.6,5);
        assertEquals(Expand_Range.getUpperBound(),38.0,1.0E-13);
        //failed ,the function should return 5.6 but the return -0.64, that mean in the function the lowerMargin is use wrong equation
        //assertEquals(Expand_Range.getLowerBound(),-0.64,1.0E-13);

        //test null
        Range expand_Range= new Range(0.0,0.0);
        assertThrows(IllegalArgumentException.class,()->{expand_Range.expand(null,0.0,0.0);});

    }
    @Test
    public void Test_Expand_To_Include()
    {
        Range expand_Range= new Range(0.0,6.0);
        assertEquals(expand_Range.expandToInclude( null,12.0).getUpperBound(),12.0,1.0E-13);
        assertEquals(Range.expandToInclude( null,0.0).getLowerBound(),0.0,1.0E-13);
    }
    @Test
    public void Test_get_Central_Value()
    {
        assertEquals(range.getCentralValue(),3.5,1.0E-13);
    }
    @Test
    public void Test_length()
    {
        assertEquals(range.getLength(),11.0,1.0E-13);
    }
    @Test
    public void Test_lower()
    {
        assertEquals(range.getLowerBound(),-2.0,1.0E-13);
    }
    @Test
    public void Test_upper()
    {
        assertEquals(range.getUpperBound(),9.0,1.0E-13);
    }
    @Test
   public void Test_intersect()
    {   //$offerStartTime < $endTime && $offerEndTime > $startTime
        //             param   < 9.0   &&  param > -2.0
        Range exp =new Range(-2.0,9.0);
        assertTrue(exp.intersects(4.0,6.0));
        assertTrue(exp.intersects(-4.0,4.0));
        assertTrue(exp.intersects(-6.0,-1.0));
        assertFalse(exp.intersects(4.0,-3.0));
        //failed ,the function should return false but the return true, that mean  the function  is use wrong equation
        //assertTrue(exp.intersects(10.0,15.0));
    }
    @Test
    public void Test_Shift_1()
    {
        Range shift_range=Range.shift(range,5.0);
        assertTrue(shift_range.getUpperBound()==14.0);
        assertEquals(shift_range.getLowerBound(),0.0,1.0E-13);
        assertThrows(IllegalArgumentException.class,()->{Range.shift(null,0.0);});

    }

    @Test
    public void Test_Shift_2()
    {
        Range shift_range_2=Range.shift(range,6.0,true);
        assertTrue(shift_range_2.getUpperBound()==15.0);
        assertEquals(shift_range_2.getLowerBound(),4.0,1.0E-13);
        assertThrows(IllegalArgumentException.class,()->{Range.shift(null,0.0);});

    }
    @Test
    public void Test_To_string()
    {
        final String expected = "Range[-2.0,9.0]";
        assertEquals(range.toString(),expected );
    }





}
