import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminGUI extends JFrame {
    private List<Cashier> cashiers;
    private JTextArea outputTextArea;
    private Map<String, Integer> inventory;
    private List<Sale> sales;

    public AdminGUI() {
        cashiers = new ArrayList<>();
        inventory = new HashMap<>();
        sales = new ArrayList<>();

        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Admin Panel");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton createAccountButton = createStyledButton("Create New Cashier Account");
        buttonPanel.add(createAccountButton);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        JButton deleteAccountButton = createStyledButton("Delete Cashier Account");
        buttonPanel.add(deleteAccountButton);
        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });

        JButton updateAccountButton = createStyledButton("Update Account");
        buttonPanel.add(updateAccountButton);
        updateAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAccount();
            }
        });

        JButton viewInventoryButton = createStyledButton("View Inventory");
        buttonPanel.add(viewInventoryButton);
        viewInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewInventory();
            }
        });

        JButton viewSalesButton = createStyledButton("View Sales");
        buttonPanel.add(viewSalesButton);
        viewSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSales();
            }
        });

        JButton addItemButton = createStyledButton("Add Items");
        buttonPanel.add(addItemButton);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItems();
            }
        });

        panel.add(buttonPanel, BorderLayout.CENTER);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setPreferredSize(new Dimension(200, 100)); 
        panel.add(scrollPane, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        return button;
    }
    private void createAccount() {
        String name = JOptionPane.showInputDialog(this, "Enter Cashier Name:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");

        if (name != null && password != null && !name.isEmpty() && !password.isEmpty()) {
            CashierFactory cashierFactory = new CashierFactory();
            Cashier cashier = cashierFactory.createCashier(name, password);
            cashiers.add(cashier);

            outputTextArea.setText("Cashier account created:\n" + cashier.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid cashier name and password.");
        }
    }

    private void deleteAccount() {
        String name = JOptionPane.showInputDialog(this, "Enter Cashier Name:");

        if (name != null && !name.isEmpty()) {
            Cashier cashierToRemove = null;
            for (Cashier cashier : cashiers) {
                if (cashier.getName().equals(name)) {
                    cashierToRemove = cashier;
                    break;
                }
            }

            if (cashierToRemove != null) {
                cashiers.remove(cashierToRemove);
                outputTextArea.setText("Cashier account deleted:\n" + cashierToRemove.toString());
            } else {
                outputTextArea.setText("Cashier account not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid cashier name.");
        }
    }

    private void updateAccount() {
        String name = JOptionPane.showInputDialog(this, "Enter Cashier Name:");

        if (name != null && !name.isEmpty()) {
            Cashier cashierToUpdate = null;
            for (Cashier cashier : cashiers) {
                if (cashier.getName().equals(name)) {
                    cashierToUpdate = cashier;
                    break;
                }
            }

            if (cashierToUpdate != null) {
                String newPassword = JOptionPane.showInputDialog(this, "Enter New Password:");
                String newName = JOptionPane.showInputDialog(this, "Enter New Name:");

                if (newPassword != null && newName != null && !newPassword.isEmpty() && !newName.isEmpty()) {
                    cashierToUpdate.setPassword(newPassword);
                    cashierToUpdate.setName(newName);

                    outputTextArea.setText("Cashier account updated:\n" + cashierToUpdate.toString());
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid new password and name.");
                }
            } else {
                outputTextArea.setText("Cashier account not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid cashier name.");
        }
    }

    private void viewInventory() {
        StringBuilder inventoryText = new StringBuilder("Inventory:\n");
        for (Map.Entry<String, Integer> item : inventory.entrySet()) {
            inventoryText.append(item.getKey()).append(": ").append(item.getValue()).append("\n");
        }

        outputTextArea.setText(inventoryText.toString());
    }

    private void viewSales() {
        StringBuilder salesText = new StringBuilder("Sales:\n");
        for (Sale sale : sales) {
            salesText.append("Cashier: ").append(sale.getCashierName()).append("\n");
            salesText.append("Items Sold:\n");
            for (String item : sale.getItemsSold()) {
                salesText.append(item).append("\n");
            }
            salesText.append("Total Amount: $").append(sale.getTotalAmount()).append("\n\n");
        }

        outputTextArea.setText(salesText.toString());
    }

    private void addItems() {
        String item = JOptionPane.showInputDialog(this, "Enter Item Name:");
        String quantityStr = JOptionPane.showInputDialog(this, "Enter Quantity:");

        if (item != null && quantityStr != null && !item.isEmpty() && !quantityStr.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity >= 0) {
                    if (inventory.containsKey(item)) {
                        int currentQuantity = inventory.get(item);
                        inventory.put(item, currentQuantity + quantity);
                    } else {
                        inventory.put(item, quantity);
                    }

                    outputTextArea.setText("Item added to inventory: " + item);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input. Quantity must be a non-negative integer.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Quantity must be a non-negative integer.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid item name and quantity.");
        }
    }

    private interface CashierFactoryInterface {
        Cashier createCashier(String name, String password);
    }

    private class CashierFactory implements CashierFactoryInterface {
        @Override
        public Cashier createCashier(String name, String password) {
            return new Cashier(name, password);
        }
    }

    private class Cashier {
        private String name;
        private String password;

        public Cashier(String name, String password) {
            this.name = name;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Name: " + name + "\nPassword: " + password;
        }
    }

    private class Sale {
        private String cashierName;
        private List<String> itemsSold;
        private double totalAmount;

        public Sale(String cashierName, List<String> itemsSold, double totalAmount) {
            this.cashierName = cashierName;
            this.itemsSold = itemsSold;
            this.totalAmount = totalAmount;
        }

        public String getCashierName() {
            return cashierName;
        }

        public List<String> getItemsSold() {
            return itemsSold;
        }

        public double getTotalAmount() {
            return totalAmount;
        }
    }

  /*  public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminGUI adminGUI = new AdminGUI();
            }
        });
    }*/
}

