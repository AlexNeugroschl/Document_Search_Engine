import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.impl.StackImpl;
public class StackImplTest {
    @Test
    public void generalTest(){
        StackImpl<Integer> stack = new StackImpl<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        stack.pop();
        stack.peek();
        assertEquals(3, stack.size());
    }
    @Test
    public void peekTest(){
        StackImpl<Integer> stack = new StackImpl<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        assertEquals(4, stack.peek());
    }
    @Test
    public void popTest(){
        StackImpl<Integer> stack = new StackImpl<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        assertEquals(true, stack.pop() == 4 && stack.size() == 3);
    }
    @Test
    public void nullTest1(){
        StackImpl<Integer> stack = new StackImpl<>();
        assertEquals(null, stack.pop());
    }
    @Test
    public void nullTest2(){
        StackImpl<Integer> stack = new StackImpl<>();
        assertThrows(IllegalArgumentException.class,()->{
            stack.push(null);;
        });
    }
}
