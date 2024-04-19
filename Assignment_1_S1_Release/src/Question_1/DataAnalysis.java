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
public class DataAnalysis<E extends Comparable> {

    private E[] data;

    public DataAnalysis(E[] data) {
        this.data = data;
    }

    public boolean bracketEvaluator() {
        Stack<Character> stack = new Stack<>();
        if (this.data == null || data.length == 0) {
            return false;
        }

        for (int i = 0; i < data.length; i++) {
            char currentChar = data[i].toString().charAt(0);
            if (currentChar == '(' || currentChar == '{' || currentChar == '[' || currentChar == '<') {
                stack.push(currentChar);
                stack.printStack();
            } else if (currentChar == ')' || currentChar == '}' || currentChar == ']' || currentChar == '>') {
                if (stack.peek() == null) {
                    return false;
                }
                char topOfStack = stack.pop();
                if ((currentChar == '}' && topOfStack != '{')
                        || (currentChar == ')' && topOfStack != '(')
                        || (currentChar == ']' && topOfStack != '[')
                        || (currentChar == '>' && topOfStack != '<')) {
                    return false;
                }
            }
        }
        System.out.println(stack.getSize());
        return true;
    }

}
