import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField display;
    private JButton buttons[];

    public ScientificCalculator() {
        super("Scientific Calculator");

        display = new JTextField("");
        display.setEditable(false);
        getContentPane().add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 5));
        String buttonLabels[] = {"7", "8", "9", "/", "C",
                "4", "5", "6", "*", "sqrt",
                "1", "2", "3", "-", "1/x",
                "0", "+/-", ".", "+", "=",
                "sin", "cos", "tan", "log", "exp"};

        buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("C")) {
            display.setText("");
        } else if (command.equals("=")) {
            String expression = display.getText();
            try {
                double result = evaluateExpression(expression);
                display.setText(Double.toString(result));
            } catch (IllegalArgumentException ex) {
                display.setText(ex.getMessage());
            }
        } else if (command.equals("sqrt")) {
            double value = Double.parseDouble(display.getText());
            double result = Math.sqrt(value);
            display.setText(Double.toString(result));
        } else if (command.equals("1/x")) {
            double value = Double.parseDouble(display.getText());
            double result = 1.0 / value;
            display.setText(Double.toString(result));
        } else if (command.equals("+/-")) {
            double value = Double.parseDouble(display.getText());
            double result = -value;
            display.setText(Double.toString(result));
        } else if (command.equals("sin")) {
            double value = Double.parseDouble(display.getText());
            double result = Math.sin(value);
            display.setText(Double.toString(result));
        } else if (command.equals("cos")) {
            double value = Double.parseDouble(display.getText());
            double result = Math.cos(value);
            display.setText(Double.toString(result));
        } else if (command.equals("tan")) {
            double value = Double.parseDouble(display.getText());
            double result = Math.tan(value);
            display.setText(Double.toString(result));
        } else if (command.equals("log")) {
            double value = Double.parseDouble(display.getText());
            double result = Math.log10(value);
            display.setText(Double.toString(result));
        } else if (command.equals("exp")) {
            double value = Double.parseDouble(display.getText());
            double result = Math.exp(value);
            display.setText(Double.toString(result));
        } else {
            display.setText(display.getText() + command);
        }
    }

    private double evaluateExpression(String expression) {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        return evaluator.evaluate(expression);
    }


    private class ExpressionEvaluator {
        private String expression;
        private int index;

        public double evaluate(String expression) {
            this.expression = expression;
            index = 0;
            double result = parseExpression();
            if (index != expression.length()) {
                throw new IllegalArgumentException("Invalid expression");
            }
            return result;
        }

        private double parseExpression() {
            double result = parseTerm();
            while (index < expression.length()) {
                char operator = expression.charAt(index);
                if (operator != '+' && operator != '-') {
                    break;
                }
                index++;
                double operand = parseTerm();
                if (operator == '+') {
                    result += operand;
                } else {
                    result -= operand;
                }
            }
            return result;
        }

        private double parseTerm() {
            double result = parseFactor();
            while (index < expression.length()) {
                char operator = expression.charAt(index);
                if (operator != '*' && operator != '/') {
                    break;
                }
                index++;
                double operand = parseFactor();
                if (operator == '*') {
                    result *= operand;
                } else {
                    result /= operand;
                }
            }
            return result;
        }

        private double parseFactor() {
            char ch = expression.charAt(index);
            if (ch >= '0' && ch <= '9') {
                return parseNumber();
            } else if (ch == '(') {
                index++;
                double result = parseExpression();
                if (expression.charAt(index) != ')') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                index++;
                return result;
            } else if (ch == '-') {
                index++;
                return -parseFactor();
            } else if (ch == '+') {
                index++;
                return parseFactor();
            } else {
                throw new IllegalArgumentException("Invalid character: " + ch);
            }
        }

        private double parseNumber() {
            int startIndex = index;
            while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                index++;
            }
            if (index < expression.length() && expression.charAt(index) == '.') {
                index++;
                while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                    index++;
                }
            }
            return Double.parseDouble(expression.substring(startIndex, index));
        }
    }
}
