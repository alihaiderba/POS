import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Cashier extends JFrame {
    private JButton startSaleButton;
    private JButton viewInventoryButton;
    private JTextField itemIdTextField;
    private JTextField quantityTextField;
    private JLabel billLabel;
    private JTextArea inventoryTextArea;
    private JLabel messageLabel;
    private List<Item> inventory;
    private double billAmount;

    public Cashier() {
        initComponents();
        initializeInventory();
    }

    private void initComponents() {
        setTitle("Cashier POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(600, 400));
        setContentPane(contentPane);
        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        startSaleButton = createStyledButton("Start Sale Session", Color.BLUE);
        startSaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startSaleSession();
            }
        });

        viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                viewInventory();
            }
        });

        itemIdTextField = new JTextField();
        JLabel itemIdLabel = new JLabel("Item ID:");
        itemIdLabel.setLabelFor(itemIdTextField);

        quantityTextField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setLabelFor(quantityTextField);

        billLabel = new JLabel();
        JLabel billAmountLabel = new JLabel("Bill Amount:");

        inventoryTextArea = new JTextArea();
        inventoryTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(inventoryTextArea);
        JLabel inventoryLabel = new JLabel("Inventory:");

        messageLabel = new JLabel();
        JLabel messageTextLabel = new JLabel("Message:");

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(itemIdLabel)
                        .addComponent(quantityLabel)
                        .addComponent(billAmountLabel)
                        .addComponent(inventoryLabel)
                        .addComponent(messageTextLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(itemIdTextField)
                        .addComponent(quantityTextField)
                        .addComponent(billLabel)
                        .addComponent(scrollPane)
                        .addComponent(messageLabel)
                        .addComponent(startSaleButton)
                        .addComponent(viewInventoryButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(itemIdLabel)
                        .addComponent(itemIdTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(quantityLabel)
                        .addComponent(quantityTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(billAmountLabel)
                        .addComponent(billLabel))
                .addComponent(startSaleButton)
                .addComponent(viewInventoryButton)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(inventoryLabel)
                        .addComponent(scrollPane))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(messageTextLabel)
                        .addComponent(messageLabel)));

        pack();
        setLocationRelativeTo(null);
    }

   private JButton createStyledButton(String text, Color backgroundColor) {
    JButton button = new JButton(text);
    button.setBackground(backgroundColor);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Arial", Font.BOLD, 20)); 
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(15, 35, 15, 35)); 
    return button;
}

    private void startSaleSession() {
        String itemId = itemIdTextField.getText();
        String quantity = quantityTextField.getText();

        // Perform validation and processing logic for the entered item ID and quantity
        // Generate bill amount based on the item ID and quantity
        billAmount = calculateBillAmount(itemId, quantity);

        // Display the bill amount
        billLabel.setText(String.format("$%.2f", billAmount));
        messageLabel.setText("");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String cashPaidText = JOptionPane.showInputDialog("Enter cash paid amount:");

                // Validate the cash paid input
                if (cashPaidText != null && !cashPaidText.isEmpty()) {
                    try {
                        double cashPaid = Double.parseDouble(cashPaidText);

                        if (cashPaid >= billAmount) {
                            double change = cashPaid - billAmount;
                            messageLabel.setText("Change: $" + String.format("%.2f", change));
                        } else {
                            JOptionPane.showMessageDialog(null, "Payment is less than the total bill. Please reenter the amount.");
                            startSaleSession(); // Restart the sale session
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid cash paid amount. Please reenter the amount.");
                        startSaleSession(); // Restart the sale session
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Enter cash paid amount. Please reenter the amount.");
                    startSaleSession(); // Restart the sale session
                }
            }
        });
    }

    private double calculateBillAmount(String itemId, String quantity) {
        // Placeholder method for calculating the bill amount based on item ID and quantity
        // Replace with your own logic to fetch item details and calculate the bill
        double itemPrice = 10.00;  // Example price
        int itemQuantity = Integer.parseInt(quantity);

        return itemPrice * itemQuantity;
    }

    private void viewInventory() {
        inventoryTextArea.setText(""); // Clear previous inventory display

        for (Item item : inventory) {
            String itemDetails = "ID: " + item.getId() + " | Name: " + item.getName() + " | Quantity: " + item.getQuantity();
            inventoryTextArea.append(itemDetails + "\n");
        }

        messageLabel.setText("Inventory Loaded");
    }

    private void initializeInventory() {
        // Placeholder method to initialize inventory data
        // Replace with your own logic to fetch inventory from a database or data source
        inventory = new ArrayList<>();
        inventory.add(new Item("1", "Rice", 10));
        inventory.add(new Item("2", "Eggs", 5));
        inventory.add(new Item("3", "Bread", 15));
        inventory.add(new Item("4", "Biscuits", 20));
        inventory.add(new Item("5", "Flour", 25));
        inventory.add(new Item("6", "Oil", 35));
    }

    private static class Item {
        private String id;
        private String name;
        private int quantity;

        public Item(String id, String name, int quantity) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }
    }

   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Use the platform look and feel for a more native appearance
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Cashier cashier = new Cashier();
                cashier.setVisible(true);
            }
        });
    }*/
}
