import com.cboefx.bats.OutputHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class OutputHelperTest {
    @Test
    public void testSortAscendingHappyPath(){
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("A", 5);
        testMap.put("B", 2);
        testMap.put("C", 3);
        testMap.put("D", 1);
        var sortedMap = OutputHelper.sortByValue(testMap, true, String::compareTo);

        var expected = Arrays.asList("D", "B", "C", "A");
        var actual = new ArrayList<>(sortedMap.keySet());
        assertEquals(expected, actual);

    }
    @Test
    public void testSortDescendingHappyPath(){
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("A", 5);
        testMap.put("B", 2);
        testMap.put("C", 3);
        testMap.put("D", 1);
        var sortedMap = OutputHelper.sortByValue(testMap, false, String::compareTo);

        var expected = Arrays.asList("A", "C", "B", "D");
        var actual = new ArrayList<>(sortedMap.keySet());
        assertEquals(expected, actual);

    }
    @Test
    public void testEqualsDegenerateCase(){
        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("A", 5);
        testMap.put("B", 5);
        testMap.put("C", 5);
        testMap.put("D", 5);
        var sortedMap = OutputHelper.sortByValue(testMap, false, String::compareTo);

        var expected = Arrays.asList("A", "B", "C", "D");
        var actual = new ArrayList<>(sortedMap.keySet());
        assertEquals(expected, actual);
    }


}
