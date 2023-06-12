import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cashier extends JFrame {
    private JButton startSaleButton;
    private JButton viewInventoryButton;
    private JButton changePasswordButton;
    private JButton logoutButton;
    private JTextField itemIdTextField;
    private JTextField quantityTextField;
    private JTextField cashPaidTextField;
    private JLabel billLabel;
    private JLabel messageLabel;

    public Cashier() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Cashier POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        startSaleButton = new JButton("Start Sale Session");
        startSaleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startSaleSession();
            }
        });
        add(startSaleButton);

        viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                viewInventory();
            }
        });
        add(viewInventoryButton);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                changePassword();
            }
        });
        add(changePasswordButton);

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                logout();
            }
        });
        add(logoutButton);

        itemIdTextField = new JTextField();
        add(new JLabel("Item ID:"));
        add(itemIdTextField);

        quantityTextField = new JTextField();
        add(new JLabel("Quantity:"));
        add(quantityTextField);

        cashPaidTextField = new JTextField();
        add(new JLabel("Cash Paid:"));
        add(cashPaidTextField);

        billLabel = new JLabel();
        add(new JLabel("Bill Amount:"));
        add(billLabel);

        messageLabel = new JLabel();
        add(new JLabel("Message:"));
        add(messageLabel);

        pack();
    }

    private void startSaleSession() {
        // item buttin, entering ids etc
    }

    private void viewInventory() {
        // Code for viewing inventory from the database
    }

    private void changePassword() {
        // Code for changing the password
    }

    private void logout() {
        // Code for logging out and displaying the login screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Cashier cashier = new Cashier();
                cashier.setVisible(true);
            }
        });
    }
}
