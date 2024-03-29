package edu.yu.cs.com1320.project.impl;
import edu.yu.cs.com1320.project.Trie;

import java.util.*;

public class TrieImpl<Value> implements Trie<Value> {
    private static final int alphabetSize = 256;
    private Node<Value> root;

    public class Node<Value> {
        protected HashSet<Value> vals = new HashSet<>();
        protected Node[] links = new Node[alphabetSize];
    }

    /**
     * add the given value at the given key
     *
     * @param key
     * @param val
     */
    public void put(String key, Value val) {
        this.root = put(this.root, key, val, 0);
    }

    private Node<Value> put(Node<Value> x, String key, Value val, int d) {
        if (x == null) {
            x = new Node<Value>();
        }
        if (d == key.length()) {
            x.vals.add(val); //add to set
            return x;
        }
        char c = key.charAt(d);
        x.links[c] = this.put(x.links[c], key, val, d + 1);
        return x;
    }

    /**
     * Get all exact matches for the given key, sorted in descending order, where "descending" is defined by the comparator.
     * NOTE FOR COM1320 PROJECT: FOR PURPOSES OF A *KEYWORD* SEARCH, THE COMPARATOR SHOULD DEFINE ORDER AS HOW MANY TIMES THE KEYWORD APPEARS IN THE DOCUMENT.
     * Search is CASE SENSITIVE.
     *
     * @param key
     * @param comparator used to sort values
     * @return a List of matching Values. Empty List if no matches.
     */
    public List<Value> getSorted(String key, Comparator<Value> comparator) {
        Set<Value> x = this.get(key);
        List<Value> list = new LinkedList<>();
        list.addAll(x);
        list.sort(comparator);
        return list;
    }

    /**
     * get all exact matches for the given key.
     * Search is CASE SENSITIVE.
     *
     * @param key
     * @return a Set of matching Values. Empty set if no matches.
     */
    public Set<Value> get(String key) {
        Node node = this.getNode(this.root, key, 0);
        return node == null ? (Set<Value>) new HashSet<Value>() : node.vals;
    }

    private Node<Value> getNode(Node<Value> x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);
        return this.getNode(x.links[c], key, d + 1);
    }

    /**
     * get all matches which contain a String with the given prefix, sorted in descending order, where "descending" is defined by the comparator.
     * NOTE FOR COM1320 PROJECT: FOR PURPOSES OF A *KEYWORD* SEARCH, THE COMPARATOR SHOULD DEFINE ORDER AS HOW MANY TIMES THE KEYWORD APPEARS IN THE DOCUMENT.
     * For example, if the key is "Too", you would return any value that contains "Tool", "Too", "Tooth", "Toodle", etc.
     * Search is CASE SENSITIVE.
     *
     * @param prefix
     * @param comparator used to sort values
     * @return a List of all matching Values containing the given prefix, in descending order. Empty List if no matches.
     */
    public List<Value> getAllWithPrefixSorted(String prefix, Comparator<Value> comparator) {
        Node endOfPrefix = getNode(this.root, prefix, 0);
        List<Value> values = getAllWithPrefixSortedLogic(endOfPrefix);
        values.sort(comparator);
        return values;
    }

    private List<Value> getAllWithPrefixSortedLogic(Node theRest) {
        List<Value> list = new LinkedList<>();
        for (Node letter : theRest.links) {
            if (letter != null) {
                list.addAll(letter.vals);
                list.addAll(getAllWithPrefixSortedLogic(letter));
            }
        }
        return list;
    }

    /**
     * Delete the subtree rooted at the last character of the prefix.
     * Search is CASE SENSITIVE.
     *
     * @param prefix
     * @return a Set of all Values that were deleted.
     */
    public Set<Value> deleteAllWithPrefix(String prefix) {
        Node endOfPrefix = getNode(this.root, prefix, 0);
        Set<Value> set = new HashSet<>();
        set.addAll(getAllWithPrefixSortedLogic(endOfPrefix));
        Arrays.fill(endOfPrefix.links, null);
        deleteExcess(this.root, prefix, 0);
        return set;
    }

    /**
     * Delete all values from the node of the given key (do not remove the values from other nodes in the Trie)
     *
     * @param key
     * @return a Set of all Values that were deleted.
     */
    public Set<Value> deleteAll(String key) {
        Set<Value> set = this.get(key);
        Node<Value> deleted = this.getNode(this.root, key, 0);
        deleted.vals.clear();
        deleteExcess(this.root, key, 0);
        return set;
    }

    /**
     * Remove the given value from the node of the given key (do not remove the value from other nodes in the Trie)
     *
     * @param key
     * @param val
     * @return the value which was deleted. If the key did not contain the given value, return null.
     */
    public Value delete(String key, Value val) {
        Node<Value> node = this.getNode(this.root, key, 0);
        if (node.vals.size() == 1 && node.vals.contains(val)) {
            node.vals.remove(val);
            deleteExcess(this.root, key, 0);
            return val;
        }
        return node.vals.remove(val) ? val : null;
    }

    private void deleteExcess(Node<Value> node, String key, int d) {
        char c = key.charAt(d);
        if (d < key.length() - 1) {
            deleteExcess(node.links[c], key, ++d);
        }
        boolean allNull = true;
        for(Node potentialNull : node.links[c].links){
            if(potentialNull != null){
                allNull = false;
                break;
            }
        }
        if (node.links[c].vals.isEmpty() && allNull) {
            node.links[c] = null;
        }
    }
/* public Node<Value> deleteExcessWorksTest(String key) {
        return this.getNode(this.root, key, 0);
    }*/

}

