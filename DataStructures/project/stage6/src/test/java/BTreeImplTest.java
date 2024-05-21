import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
public class BTreeImplTest {
    @Test
    public void putTest1(){
        BTreeImpl<String, String> tree = new BTreeImpl<>();
        tree.put("key1", "val1");
        tree.put("key2", "val2");
        tree.put("key3", "val3");
        tree.put("key4", "val4");
        tree.put("key5", "val5");
        tree.put("key6", "val6");
        assertEquals("val3", tree.get("key3"));
    }
    @Test
    public void putTest2(){
        BTreeImpl<String, String> tree = new BTreeImpl<>();
        tree.put("key1", "val1");
        tree.put("key2", "val2");
        tree.put("key3", "val3");
        tree.put("key4", "val4");
        tree.put("key5", "val5");
        tree.put("key6", "val6");
        tree.put("key7", "val7");
        tree.put("key8", "val8");
        tree.put("key9", "val9");
        tree.put("key10", "val10");
        tree.put("key11", "val11");
        tree.put("key12", "val12");
        tree.put("key13", "val13");
        tree.put("key14", "val14");
        tree.put("key15", "val15");
        tree.put("key16", "val16");
        tree.put("key17", "val17");
        tree.put("key18", "val18");
        tree.put("key19", "val19");
        tree.put("key20", "val20");
        tree.put("key21", "val21");
        assertEquals("val13", tree.get("key13"));
    }
}
