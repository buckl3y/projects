/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_1;

/**
 *
 * @author xhu
 * @param <E>
 */
public class Stack<E extends Comparable> {

    LinkedList<E> stack = new LinkedList();

    public void push(E data) {
        Node nodeToAdd = new Node();
        nodeToAdd.data = data;
        if (stack.head == null) {
            stack.head = nodeToAdd;
            return;
        }
        stack.addHead(data);

    }

    public E pop() {
        if (stack.head == null) {
            return null;
        } else {
            Node nodeToPop = stack.removeFromHead();
            if (nodeToPop == null) {
                return null;
            }
            return (E) nodeToPop.data;
        }
    }

    public E peek() {
        if (stack.head == null) {
            return null;
        } else {
            Node nodeToPeek = stack.head;
            return (E) nodeToPeek.data;
        }
    }

    public void printStack() {
        Node<E> currentNode = stack.head;
        if (currentNode == null) {
            System.out.println("Stack is empty");
        }
        while (currentNode != null) {
            System.out.print(currentNode.data + " ");
            currentNode = currentNode.next;
        }
    }

    public int getSize() {
        if (stack.head == null) {
            return 0;
        } else {
            return stack.size;
        }
    }
}
