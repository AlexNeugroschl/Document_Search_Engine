import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MinHeapImplTest {
    @Test
    public void basicTest(){
        MinHeapImpl<Integer> heap = new MinHeapImpl<>();
        heap.insert(6);
        heap.insert(3);
        heap.insert(4);
        heap.insert(7);
        heap.insert(1);
        heap.insert(2);
        heap.insert(5);
        assertEquals(true, heap.peek() == 1);
    }
}
