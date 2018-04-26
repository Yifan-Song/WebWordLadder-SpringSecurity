package wordladderweb.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Test
    public void testAppToStandardString()
    {
        Assert.assertTrue( Controller.ToStandardString("Apple").equals("apple") );
        Assert.assertTrue( Controller.ToStandardString("orangE").equals("orange") );
        Assert.assertTrue( Controller.ToStandardString("SUN").equals("sun") );
        Assert.assertTrue( Controller.ToStandardString("ENTER").equals("enter") );
        Assert.assertTrue( Controller.ToStandardString("lo ve").equals("love") );
        Assert.assertTrue( Controller.ToStandardString("Hom e").equals("home") );
        Assert.assertTrue( Controller.ToStandardString("h u r T").equals("hurt") );
    }

    @Test
    public void testAppIsWord()
    {
        Set<String> testSet = new HashSet<String>();
        testSet.add("apple");
        testSet.add("orange");
        testSet.add("sun");
        Assert.assertTrue( Controller.IsWord("apple",testSet) );
        Assert.assertTrue( Controller.IsWord("orange",testSet) );
        Assert.assertTrue( Controller.IsWord("sun",testSet) );
        Assert.assertFalse( Controller.IsWord("abc",testSet) );
        Assert.assertFalse( Controller.IsWord("123456",testSet) );
        Assert.assertFalse( Controller.IsWord("&*^%&",testSet) );
        Assert.assertFalse( Controller.IsWord("app%le",testSet) );
        Assert.assertFalse( Controller.IsWord("A pp le",testSet) );
    }
}
