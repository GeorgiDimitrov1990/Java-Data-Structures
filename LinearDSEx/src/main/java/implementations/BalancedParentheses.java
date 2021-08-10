package implementations;

import interfaces.Deque;
import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<Character> deque = new ArrayDeque<>();
        if (parentheses.length() % 2 != 0){
            return false;
        }
        if (parentheses.charAt(0) == ')' || parentheses.charAt(0) == '}' || parentheses.charAt(0) == ']'){
            return false;
        }


        for (int i = 0; i < parentheses.length(); i++) {
            char current = parentheses.charAt(i);
            switch (current) {
                case '(':
                    deque.push(')');
                    break;
                case '[':
                    deque.push(']');
                    break;
                case '{':
                    deque.push('}');
                    break;
                case ')':
                case ']':
                case '}':
                    if (deque.isEmpty() || deque.pop() != current) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return deque.isEmpty();
    }
}
