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
public class LinkedList<E extends Comparable> {

    public int size = 0;
    public Node<E> head;

    public void addHead(E data) {
        Node<E> node = new Node<>();
        node.data = data;
        node.next = head;
        head = node;
        this.size++;
    }

    public Node getHead() {
        return null;
    }

    public void add(E data) {
        // node to be added
        Node<E> node = new Node<>();
        //set traversingNode to first node
        Node<E> traversingNode = head;

        node.data = data;
        node.next = null;
        //check if list is empty first
        if (head == null) {
            head = node;
            this.size++;
        } else {
            add(traversingNode, node);
            this.size++;
        }
    }

    public void setLastNodeRecursive(Node<E> traversingNode, Node<E> node) {
        //traverse through nodes until traversingNode.next == null is true;
        //and set the new node as the last node (tail)
        if (traversingNode.next == null) {
            traversingNode.next = node;
        } else {
            setLastNodeRecursive(traversingNode.next, node);
        }

    }

    private void add(Node head, Node node) {
        if (head.next == null) {
            head.next = node;
        } else {
            setLastNodeRecursive(head.next, node);
        }
    }

    public void printLinkedList() {
        printLinkedList(head);
        System.out.println();
    }

    private void printLinkedList(Node<E> currentNode) {
        if (currentNode == null) {
            return;
        }
        System.out.print(currentNode.data + " ");
        printLinkedList(currentNode.next);
    }

    public boolean contains(E data) {
        return contains(head, data);
    }

    private boolean contains(Node<E> currentNode, E data) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.data.equals(data)) {
            return true;
        }
        return contains(currentNode.next, data);
    }

    public void remove(E data) {
        head = removeFromBody(head, data);
    }

    private Node<E> removeFromBody(Node<E> currentNode, E data) {
        if (currentNode == null) {
            return null;
        }
        if (currentNode.data.equals(data)) {
            size--;
            return currentNode.next;
        }
        currentNode.next = removeFromBody(currentNode.next, data);
        return currentNode;
    }

    public void remove(int position) {
        head = removeByIndex(head, position);
    }

    private Node<E> removeByIndex(Node<E> currentNode, int position) {
        if (currentNode == null) {
            System.out.println("Out of Bounds!");
            return null;
        }
        if (position == 0) {
            size--;
            return currentNode.next;
        }
        currentNode.next = removeByIndex(currentNode.next, position - 1);
        return currentNode;
    }

    public Node removeFromHead() {
        Node<E> removedHead = head;
        if (head == null) {
            System.out.println("Empty");
            return null;
        } else {
            head = head.next;
        }
        this.size -= 1;
        return removedHead;
    }

    public Node<E> removeFromTail() {
        if (head == null) {
            System.out.println("Empty");
            return null;
        }
        if (head.next == null) {
            Node<E> removedNode = head;
            head = null;
            size--;
            return removedNode;
        }

        return removeFromTail(head);
    }

    private Node<E> removeFromTail(Node<E> currentNode) {
        if (currentNode.next.next == null) {
            Node<E> removedNode = currentNode.next;
            currentNode.next = null;
            size--;
            return removedNode;
        }
        return removeFromTail(currentNode.next);
    }

    public void addInOrder(E data) {
        Node<E> nodeToAdd = new Node<>();
        nodeToAdd.data = data;
        nodeToAdd.next = null;

        if (head == null || nodeToAdd.data.compareTo(head.data) <= 0) {
            nodeToAdd.next = head;
            head = nodeToAdd;
            return;
        }

        addInOrder(head, nodeToAdd);
        this.size++;
    }

    private void addInOrder(Node<E> currentNode, Node<E> newNode) {
        if (currentNode.next == null || newNode.data.compareTo(currentNode.next.data) <= 0) {
            newNode.next = currentNode.next;
            currentNode.next = newNode;
            return;
        }
        addInOrder(currentNode.next, newNode);
    }

    public Node<E> getNode(int index) {
        return getNode(index, head);
    }

    private Node<E> getNode(int index, Node<E> currentNode) {
        if (currentNode == null || index < 0) {
            System.out.println("Index out of bounds");
            return null;
        }
        if (index == 0) {
            return currentNode;
        }
        return getNode(index - 1, currentNode.next);
    }

    public E getData(int index) {
        return getData(index, head);
    }

    private E getData(int index, Node<E> currentNode) {
        if (currentNode == null || index < 0) {
            System.out.println("Index out of bounds");
            return null;
        }
        if (index == 0) {
            return currentNode.data;
        }
        return getData(index - 1, currentNode.next);
    }
}
