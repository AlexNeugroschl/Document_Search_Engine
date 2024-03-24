package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.Stack;

public class StackImpl<T> implements Stack<T> {
    private class Node<T> {
        T contents;
        Node next;

        private Node(T contents) {
            this.contents = contents;
        }
    }

    T element;
    Node topOfStack;
    int stackSize;
    public StackImpl(){
        this.stackSize = 0;
    }

    /**
     * @param element object to add to the Stack
     */
    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Stack cannot take null value");
        }
        Node newTop = new Node(element);
        newTop.next = this.topOfStack;
        this.topOfStack = newTop;
        this.stackSize ++;
    }
    /**
     * removes and returns element at the top of the stack
     * @return element at the top of the stack, null if the stack is empty
     */
    public T pop(){
        if(this.topOfStack == null){
            return null;
        }
        T returnValue = (T) this.topOfStack.contents;
        this.topOfStack = this.topOfStack.next;
        this.stackSize --;
        return returnValue;
    }

    /**
     *
     * @return the element at the top of the stack without removing it
     */
    public T peek(){
        if(this.topOfStack == null){
            return null;
        }
        return (T) this.topOfStack.contents;
    }

    /**
     *
     * @return how many elements are currently in the stack
     */
    public int size(){
        return this.stackSize;
    }
}
