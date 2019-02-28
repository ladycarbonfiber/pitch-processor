import com.cboefx.bats.Processor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ProcessorTest {
    @Test
    public void testPITCHRead(){
        Map<String, Long> expected = new HashMap<>();
        expected.put("SPY", 1000L);
        expected.put("APPL", 2500L);
        expected.put("IBM", 500L);
        Processor p = new Processor();
        p.processTarget(ProcessorTest.class.getResourceAsStream("example_data"));
        assertEquals(expected.entrySet(),p.getExecutedOrders().entrySet());

    }
}
