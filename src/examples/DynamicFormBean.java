package examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DynamicFormBean extends JPanel {
    private List<OrderPanel> orderPanels;
    private JButton addOrderButton;
    private JButton removeOrderButton;
    private JPanel buttonPanel;
    private int orderCount = 0;

    public DynamicFormBean() {
        orderPanels = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        buttonPanel = createButtonPanel(); // Create button panel

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderCount++;
                OrderPanel orderPanel = new OrderPanel(orderCount);
                orderPanels.add(orderPanel);
                add(orderPanel);
                revalidate();
                repaint();
            }
        });

        removeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!orderPanels.isEmpty()) {
                    OrderPanel lastOrderPanel = orderPanels.remove(orderPanels.size() - 1);
                    DynamicFormBean.this.remove(lastOrderPanel); // Remove the last order panel from the DynamicFormBean
                    DynamicFormBean.this.revalidate();
                    DynamicFormBean.this.repaint();
                }
            }
        });

        add(buttonPanel); // Add button panel
    }

    // Create button panel with add and remove buttons
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addOrderButton = new JButton("Add Order");
        removeOrderButton = new JButton("Remove Last Item");

        addOrderButton.setForeground(Color.GREEN);
        removeOrderButton.setForeground(Color.RED);

        panel.add(addOrderButton);
        panel.add(removeOrderButton);

        return panel;
    }

    // Inner class representing an order panel
    private class OrderPanel extends JPanel {
        private List<OrderItemPanel> orderItemPanels;
        private int orderNumber;
        private JButton removeLastItemButton;

        public OrderPanel(int orderNumber) {
            this.orderNumber = orderNumber;
            orderItemPanels = new ArrayList<>();
            setLayout(new FlowLayout(FlowLayout.LEFT));
            add(new JLabel("Order " + orderNumber));
            addItem();
        }

        public void addItem() {
            OrderItemPanel orderItemPanel = new OrderItemPanel();
            orderItemPanels.add(orderItemPanel);
            add(orderItemPanel);
            revalidate();
            repaint();
        }

        public void removeLastItem() {
            if (!orderItemPanels.isEmpty()) {
                OrderItemPanel lastItemPanel = orderItemPanels.get(orderItemPanels.size() - 1);
                remove(lastItemPanel);
                orderItemPanels.remove(lastItemPanel);
                revalidate();
                repaint();
            }
        }
    }

    // Inner class representing an order item panel
    private class OrderItemPanel extends JPanel {
        public OrderItemPanel() {
            setLayout(new GridLayout(1, 3));

            JLabel quantityLabel = new JLabel("Quantity:");
            add(quantityLabel);
            JTextField quantityField = new JTextField(5);
            add(quantityField);

            JLabel priceLabel = new JLabel("Price:");
            add(priceLabel);
            JTextField priceField = new JTextField(5);
            add(priceField);

            JLabel productLabel = new JLabel("Product:");
            add(productLabel);
            String[] products = {"Product A", "Product B", "Product C"};
            JComboBox<String> productComboBox = new JComboBox<>(products);
            add(productComboBox);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Dynamic Form Bean Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new DynamicFormBean());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}

