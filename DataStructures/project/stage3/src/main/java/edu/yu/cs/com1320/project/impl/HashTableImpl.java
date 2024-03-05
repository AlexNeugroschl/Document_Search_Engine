package  edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.HashTable;
import java.util.*;

public class HashTableImpl<Key, Value> implements HashTable<Key, Value>{
    List<?>[] table;
    class Entry<Key, Value>{
        Key key;
        Value value;
        Entry(Key k, Value v){
            if(k == null){
                throw new IllegalArgumentException();
            }
            key = k;
            value = v;
        }
    }
    private class List<Thing>{
        Thing thing;
        Node head;
        public List(Thing thing){
            this.thing = thing;
        }
        public Value insert(Key k, Value v){
            if(head == null){
              head = new Node(new Entry(k, v));
              return null;
            }else if(head.contents.key.equals(k)) {
                Value temp = (Value) head.contents.value;
                head.contents.value = v;
                return temp;
            }else{
                Node iteration = head;
                while(iteration.next != null){
                    if(iteration.next.contents.key.equals(k)){
                        Value temp = (Value) iteration.next.contents.value;
                        iteration.next.contents.value = v;
                        return temp;
                    }
                   iteration = iteration.next;
                }
                iteration.next = new Node(new Entry(k, v));
                return null;
            }
        }
        public Value get(Key k){
            if(head == null){
                return null;
            }
            if(head.contents.key.equals(k)){
                return (Value) head.contents.value;
            }else{
                Node iteration = head.next;
                while(iteration != null) {
                    if(iteration.contents.key.equals(k)){
                        return (Value) iteration.contents.value;
                    }
                    iteration = iteration.next;
                }
            }
            return null;
        }
        public Value delete(Key k){
            if(head == null){
                return null;
            }
            if(head.contents.key.equals(k)){
                Value temp = (Value) this.head.contents.value;
                this.head = head.next;
                return temp;
            }else{
                Node iteration = head;
                while(iteration != null){
                    if(iteration.next != null && iteration.next.contents.key.equals(k)){
                        Value temp = (Value) iteration.next.contents.value;
                        iteration.next = iteration.next.next;
                        return temp;
                    }
                    iteration = iteration.next;
                }
            }
            return null;
        }

        public Entry[] getAll() {
            Entry[] entries = new Entry[10];
            Node iteration = head;
            int arrayLength = 0;
            while (iteration != null) {
                entries[arrayLength] = iteration.contents;
                iteration = iteration.next;
                arrayLength++;
                if (arrayLength == entries.length) {
                    Entry[] temp = (Entry[]) new Entry[entries.length];
                    for (int i = 0; i < temp.length; i++) {
                        temp[i] = entries[i];
                    }
                    entries = (Entry[]) new Entry[temp.length * 2];
                    for (int i = 0; i < temp.length; i++) {
                        entries[i] = temp[i];
                    }
                }
            }
            return entries;
        }

        private class Node{
            Node next;
            Entry contents;
            public Node(Entry entry){
                this.contents = entry;
                this.next = null;
            }
        }
    }

    public HashTableImpl(){
        this.table = new List[5];
        for(int i = 0; i < table.length; i++){
            this.table[i] = new List<>("This should be fine");
        }
    }
    /**
     * @param k the key whose value should be returned
     * @return the value that is stored in the HashTable for k, or null if there is no such key in the table
     */
    public Value get(Key k){
        int arrayIndex = hashFunction(k);
        return (Value) table[arrayIndex].get(k);
    }

    /**
     * @param k the key at which to store the value
     * @param v the value to store
     *          To delete an entry, put a null value.
     * @return if the key was already present in the HashTable, return the previous value stored for the key. If the key was not already present, return null.
     */
    public Value put(Key k, Value v){
        int arrayIndex = hashFunction(k);
        if(v == null){
            return (Value) table[arrayIndex].delete(k);
        }else{
            return (Value) table[arrayIndex].insert(k, v);
        }
    }

    /**
     * @param key the key whose presence in the hashtable we are inquiring about
     * @return true if the given key is present in the hashtable as a key, false if not
     * @throws NullPointerException if the specified key is null
     */
    public boolean containsKey(Key key){
        if(key == null){
            throw new NullPointerException("key is null");
        }
        for(int i = 0; i < table.length; i++){
            if(table[i].get(key) != null){
                return true;
            }
        }
        return false;
    }

    /**
     * @return an unmodifiable set of all the keys in this HashTable
     * @see java.util.Collections#unmodifiableSet(Set)
     */
    public Set<Key> keySet(){
        HashSet<Key> keys = new HashSet<Key>();
        for(int i = 0; i < table.length; i++){
            Entry[] entries = table[i].getAll();
            for(Entry thing : entries){
                if(thing != null) {
                    keys.add((Key) thing.key);
                }
            }
        }
        return Collections.unmodifiableSet(keys);
    }

    /**
     * @return an unmodifiable collection of all the values in this HashTable
     * @see java.util.Collections#unmodifiableCollection(Collection)
     */
    public Collection<Value> values(){
        ArrayList<Value> values = new ArrayList<>();
        for(int i = 0; i < table.length; i++){
            Entry[] entries = table[i].getAll();
            for(Entry thing : entries){
                if(thing!= null) {
                    values.add((Value) thing.value);
                }
            }
        }
        return Collections.unmodifiableList(values);
    }

    /**
     * @return how entries there currently are in the HashTable
     */
    public int size(){
        return this.keySet().size();
    }
    private int hashFunction(Key key){
        return (key.hashCode() & 0x7fffffff) % 5;
    }
}
