import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.stage5.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MinHeapImplTest {
    @Test
    public void basicTest1(){
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
    @Test
    public void basicTest2(){
        MinHeapImpl<Integer> heap = new MinHeapImpl<>();
        heap.insert(6);
        heap.insert(3);
        heap.insert(4);
        heap.insert(7);
        heap.insert(1);
        heap.insert(2);
        heap.insert(5);
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        assertEquals(6, heap.peek());
    }
    @Test
    public void arrayDoubling(){
        MinHeapImpl<Integer> heap = new MinHeapImpl<>();
        heap.insert(6);
        heap.insert(3);
        heap.insert(4);
        heap.insert(7);
        heap.insert(1);
        heap.insert(2);
        heap.insert(5);
        heap.insert(8);
        heap.insert(9);
        heap.insert(10);
        heap.insert(11);
        heap.insert(12);
        heap.insert(13);
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        heap.remove();
        assertEquals(13, heap.peek());
    }
}
