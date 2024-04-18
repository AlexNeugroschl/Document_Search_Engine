import edu.yu.cs.com1320.project.Trie;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.stage5.impl.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class TrieImplTest {
    @Test
    public void basicTest1(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "that's my name");
        Set<String> words = trie.get("Alex");
        assertEquals(true, words.contains("that's my name"));
    }
    @Test
    public void basicTest2(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex'sname", "that's my name");
        trie.put("Alex'sname", "sure is");
        trie.put("Alex'sname", "thats me");
        Set<String> words = trie.get("Alex'sname");
        assertEquals(true, words.contains("sure is"));
    }
    @Test
    public void basicTest3(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex'sname", "that's my name");
        trie.put("Alex'sname", "sure is");
        trie.put("Alex'sname", "thats me");
        List<String> words = trie.getSorted("Alex'sname", new ExampleComparator());
        assertEquals(true, words.get(2).equals("thats me"));
    }
    @Test
    public void getAllWithPrefixSortedTest(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        List<String> words = trie.getAllWithPrefixSorted("AlexN", new ExampleComparator());
        assertEquals("Value5", words.get(2));
    }
    @Test
    public void deleteAllWithPrefixTest1(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        Set<String> words = trie.deleteAllWithPrefix("AlexN");
        assertEquals(true, words.contains("Value10"));
    }
    @Test
    public void deleteAllWithPrefixTest2(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        Set<String> words = trie.deleteAllWithPrefix("AlexN");
        assertEquals(false, words.contains("Value13"));
    }
    @Test
    public void deleteAllWithPrefixTest3(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        Set<String> words = trie.deleteAllWithPrefix("AlexN");
        assertEquals(0, trie.get("AlexNeugroschl4").size());
    }
    @Test
    public void deleteAllTest1(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        Set<String> words = trie.deleteAll("Alexander");
        assertEquals(0, trie.get("Alexander").size());
    }
    @Test
    public void deleteAllTest2(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        Set<String> words = trie.deleteAll("Alexander");
        assertEquals(3, words.size());
    }
    @Test
    public void deleteTest(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        String word = trie.delete("Alexander", "Value12");
        assertEquals(2, trie.get("Alexander").size());
    }
    @Test
    public void deleteExcessTest1(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        Set<String> word = trie.deleteAllWithPrefix("AlexNeugr");
        assertEquals(1, trie.get("AlexNeug1").size());
    }
    @Test
    public void getNonexistentItem(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        assertEquals(0, trie.get("AlexNeug16789").size());
    }
    /*
    TESTS RELY ON COMMENTED OUT METHOD
    @Test
    public void deleteExcessTest2(){
        Trie<String> trie = new TrieImpl<>();
        trie.put("Alex", "Value1");
        trie.put("Alex", "Value2");
        trie.put("Alex", "Value3");
        trie.put("AlexNeugroschl1", "Value4");
        trie.put("AlexNeugroschl2", "Value5");
        trie.put("AlexNeugroschl3", "Value6");
        trie.put("AlexNeugroschl4", "Value7");
        trie.put("AlexNeug1", "Value7");
        trie.put("AlexNeug2", "Value8");
        trie.put("AlexNeug3", "Value9");
        trie.put("AlexNeug4", "Value10");
        trie.put("Alexander", "Value11");
        trie.put("Alexander", "Value12");
        trie.put("Alexander", "Value13");
        Set<String> word = trie.deleteAllWithPrefix("AlexNeugroschl");
        assertEquals(null, ((TrieImpl<String>) trie).deleteExcessWorksTest("AlexNeugr"));
    }
     */
    public class ExampleComparator  implements Comparator<String> {
        public int compare(String obj1, String obj2) {
            if (obj1 == obj2) {
                return 0;
            }
            if (obj1 == null) {
                return -1;
            }
            if (obj2 == null) {
                return 1;
            }
            return obj1.compareTo(obj2);
        }
    }
}
