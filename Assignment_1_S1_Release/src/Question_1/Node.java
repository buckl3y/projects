/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_1;

/**
 *
 * @author xhu
 */
public class Node<E extends Comparable> {

    public E data;
    public Node<E> next;

    public boolean equals(Node node) {
        //Checking if argument node contains data.
        if (node == null) {
            return false;
        }
        //can use .equals from Comparable interface.
        return this.data.equals(node.data);
    }

    public int compareTo(Node node) {
        if (this.data.equals(node.data)) {
            return 0;
        } else if (this.data.compareTo(node.data) < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
