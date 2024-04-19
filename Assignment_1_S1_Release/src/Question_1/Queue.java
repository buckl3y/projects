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
public class Queue<E extends Comparable> {

    private LinkedList<E> queue = new LinkedList();

    public void enqueue(E data) {
        if (queue.head == null) {
            Node<E> newHead = new Node<>();
            newHead.data = data;
            queue.head = newHead;
            queue.size++;
            return;
        }
        Node<E> currentNode = queue.head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }
        Node<E> newNode = new Node<>();
        newNode.data = data;
        currentNode.next = newNode;
        queue.size++;
    }

    public E dequeue() {
        if (queue.head == null) {
            System.out.println("Cannot remove, Queue is empty.");
            return null;
        } else {
            E dequeueData = queue.head.data;
            if (queue.head.next == null) {
                queue.head = null;
            } else {
                queue.head = queue.head.next;
            }
            queue.size--;
            return dequeueData;
        }
    }

    public void printQueue() {
        Node<E> currentNode = queue.head;
        if (currentNode == null) {
            System.out.println("Queue is empty");
        }
        while (currentNode != null) {
            System.out.print(currentNode.data + " ");
            currentNode = currentNode.next;
        }
    }

    public int getSize() {
        int count = 1;
        Node<E> currentNode = new Node<>();
        currentNode = queue.head;
        if (queue.head == null) {
            return 0;
        } else if (queue.head.next == null) {
            return 1;
        }
        while (currentNode.next != null) {
            count++;
            currentNode = currentNode.next;
        }
        return count;
    }
}
