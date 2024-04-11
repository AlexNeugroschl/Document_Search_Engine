package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.MinHeap;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

public class MinHeapImpl<E extends Comparable<E>> extends MinHeap<E>{
    public MinHeapImpl(){
        this.elements = (E[]) new Comparable[10];
    }
    @Override
    public void reHeapify(E element){
        upHeap(getArrayIndex(element));
        downHeap(getArrayIndex(element));
    }

    @Override
    protected int getArrayIndex(E element){
        for (int i = 1; i < this.elements.length; i++){
            if (elements[i] != null && elements[i].equals(element)){
                return i;
            }
        }
        throw new NoSuchElementException("Element not in heap");
    }
    @Override
    protected void doubleArraySize(){
        E[] temp = (E[])new Object[this.elements.length * 2];
        for (int i = 1; i < this.elements.length; i++){
            temp[i] = this.elements[i];
        }
        this.elements = temp;
    }
}
