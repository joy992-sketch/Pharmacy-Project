import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PharmacyManagementSystem extends JFrame {
    public static class Medicine {
        private String id, name, category, dosage;
        private double price;
        private int stock, limit;

        public Medicine(String id, String name, String category, String dosage, double price, int stock, int limit) {
            this.id = id; this.name = name; this.category = category; this.dosage = dosage;
            this.price = price; this.stock = stock; this.limit = limit;
        }
        public String getId() { return id; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getDosage() { return dosage; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }
        public void addStock(int qty) { this.stock += qty; }
        public int getLimit() { return limit; }
    }

    public static class CartItem {
        private Medicine medicine;
        private int quantity;

        public CartItem(Medicine medicine, int quantity) {
            this.medicine = medicine;
            this.quantity = quantity;
        }
        public Medicine getMedicine() { return medicine; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getTotalPrice() { return medicine.getPrice() * quantity; }
    }

    public static class Invoice {
        private String invoiceId, customer, phone, doctor, date;
        private List<CartItem> items;
        private double subtotal, discount, tax, total, cashPaid, change, due;

        public Invoice(String invoiceId, String customer, String phone, String doctor, String date, List<CartItem> items, double subtotal, double discount, double tax, double total, double cashPaid, double change, double due) {
            this.invoiceId = invoiceId; this.customer = customer; this.phone = phone; this.doctor = doctor; this.date = date;
            this.items = new ArrayList<>(items); this.subtotal = subtotal; this.discount = discount; this.tax = tax; this.total = total;
            this.cashPaid = cashPaid; this.change = change; this.due = due;
        }
        public String getInvoiceId() { return invoiceId; }
        public String getCustomer() { return customer; }
        public String getPhone() { return phone; }
        public String getDoctor() { return doctor; }
        public String getDate() { return date; }
        public List<CartItem> getItems() { return items; }
        public double getSubtotal() { return subtotal; }
        public double getDiscount() { return discount; }
        public double getTax() { return tax; }
        public double getTotal() { return total; }
        public double getCashPaid() { return cashPaid; }
        public double getChange() { return change; }
        public double getDue() { return due; }
    }

    private List<Medicine> medicines = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContent = new JPanel(cardLayout);
    private DashboardPanel dashboardPanel;
    private POSPanel posPanel;
    private InventoryPanel inventoryPanel;
    private InvoiceHistoryPanel historyPanel;

    public PharmacyManagementSystem() {
        setTitle("Pharmacy Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        initData();

        JPanel topHeader = new JPanel(new BorderLayout());
        topHeader.setBackground(new Color(245, 245, 245));

        JLabel titleLbl = new JLabel("PHARMACY MANAGEMENT SYSTEM", SwingConstants.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLbl.setForeground(new Color(17, 94, 89));
        titleLbl.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        topHeader.add(titleLbl, BorderLayout.NORTH);

        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        navBar.setBackground(new Color(17, 94, 89));

        JButton dashBtn = createNavBtn("Dashboard"), posBtn = createNavBtn("POS / Sales Terminal"), invBtn = createNavBtn("Medicine Stock"), recBtn = createNavBtn("Invoices History");
        dashBtn.addActionListener(e -> switchTab("Dashboard"));
        posBtn.addActionListener(e -> switchTab("POS"));
        invBtn.addActionListener(e -> switchTab("Inventory"));
        recBtn.addActionListener(e -> switchTab("Invoices"));

        navBar.add(dashBtn); navBar.add(posBtn); navBar.add(invBtn); navBar.add(recBtn);
        topHeader.add(navBar, BorderLayout.SOUTH);

        add(topHeader, BorderLayout.NORTH);

        dashboardPanel = new DashboardPanel();
        posPanel = new POSPanel();
        inventoryPanel = new InventoryPanel();
        historyPanel = new InvoiceHistoryPanel();

        mainContent.add(dashboardPanel, "Dashboard");
        mainContent.add(posPanel, "POS");
        mainContent.add(inventoryPanel, "Inventory");
        mainContent.add(historyPanel, "Invoices");

        add(mainContent, BorderLayout.CENTER);
        refreshAllViews();
    }

    private JButton createNavBtn(String title) {
        JButton btn = new JButton(title);
        btn.setBackground(new Color(15, 118, 110));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    public void switchTab(String name) { cardLayout.show(mainContent, name); refreshAllViews(); }
    public void refreshAllViews() { dashboardPanel.refreshData(); posPanel.refreshCatalog(); inventoryPanel.refreshTable(); historyPanel.refreshTable(); }

    private void initData() {
        medicines.add(new Medicine("MED-101", "Paracetamol", "Fever", "500 mg", 2.00, 120, 20));
        medicines.add(new Medicine("MED-102", "Atorvastatin", "Heart", "10 mg", 14.50, 85, 20));
        medicines.add(new Medicine("MED-103", "Atorvastatin", "Heart", "20 mg", 25.00, 60, 15));
        medicines.add(new Medicine("MED-104", "Montelukast", "Lung", "10 mg", 12.00, 90, 15));
        medicines.add(new Medicine("MED-105", "Naproxen", "Orthopedic", "500 mg", 8.00, 100, 20));
        medicines.add(new Medicine("MED-106", "Calcium + D3", "Orthopedic", "500 mg", 6.50, 150, 25));
        medicines.add(new Medicine("MED-107", "Omeprazole", "Gastroenterology", "20 mg", 7.00, 110, 20));
        medicines.add(new Medicine("MED-108", "Metformin", "Endocrinology", "500 mg", 5.00, 200, 30));
        medicines.add(new Medicine("MED-109", "Gabapentin", "Neurology", "300 mg", 18.00, 45, 10));
        medicines.add(new Medicine("MED-110", "Isotretinoin", "Dermatology", "10 mg", 22.50, 30, 10));
        medicines.add(new Medicine("MED-111", "Azithromycin", "Anti-Infective", "500 mg", 35.00, 80, 15));
        medicines.add(new Medicine("MED-112", "Ciprofloxacin Drops", "Ophthalmology", "0.3%", 15.00, 40, 10));
        medicines.add(new Medicine("MED-113", "Sertraline", "Psychiatry", "50 mg", 16.00, 50, 10));
        medicines.add(new Medicine("MED-114", "Fexofenadine", "ENT", "120 mg", 9.50, 95, 20));
    }

    private class DashboardPanel extends JPanel {
        private JLabel totalSalesLbl = new JLabel("৳0.00", SwingConstants.CENTER), totalInvLbl = new JLabel("0", SwingConstants.CENTER), totalMedLbl = new JLabel("0", SwingConstants.CENTER), lowStockLbl = new JLabel("0", SwingConstants.CENTER);
        private DefaultTableModel alertModel;
        private JTable alertTable;

        public DashboardPanel() {
            setLayout(new BorderLayout(15, 15));
            setBackground(new Color(248, 250, 252));
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JPanel metrics = new JPanel(new GridLayout(1, 4, 15, 0));
            metrics.setOpaque(false);
            metrics.add(card("Total Sales", totalSalesLbl, new Color(240, 253, 250)));
            metrics.add(card("Total Invoices", totalInvLbl, new Color(239, 246, 255)));
            metrics.add(card("Medicines in Stock", totalMedLbl, new Color(250, 245, 255)));
            metrics.add(card("Low Stock Warning", lowStockLbl, new Color(255, 241, 242)));
            add(metrics, BorderLayout.NORTH);

            alertModel = new DefaultTableModel(new String[]{"ID", "Medicine", "Category", "Dosage", "Current Stock", "Limit Alert"}, 0);
            alertTable = new JTable(alertModel);

            JPanel alertPanel = new JPanel(new BorderLayout(10, 10));
            alertPanel.setBorder(BorderFactory.createTitledBorder("Stock Threshold Alerts"));
            alertPanel.add(new JScrollPane(alertTable), BorderLayout.CENTER);

            JButton quickStockBtn = new JButton("+ Stock In Selected Low Item");
            quickStockBtn.setBackground(new Color(13, 148, 136));
            quickStockBtn.setForeground(Color.WHITE);
            quickStockBtn.addActionListener(e -> stockInFromDashboard());

            alertPanel.add(quickStockBtn, BorderLayout.SOUTH);
            add(alertPanel, BorderLayout.CENTER);
        }

        private JPanel card(String title, JLabel val, Color bg) {
            JPanel p = new JPanel(new BorderLayout());
            p.setBackground(bg);
            p.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
            JLabel t = new JLabel(title, SwingConstants.CENTER);
            t.setFont(new Font("SansSerif", Font.BOLD, 12));
            val.setFont(new Font("SansSerif", Font.BOLD, 22));
            p.add(t, BorderLayout.NORTH);
            p.add(val, BorderLayout.CENTER);
            return p;
        }

        private void stockInFromDashboard() {
            int row = alertTable.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a low stock medicine from the list!"); return; }
            String medId = alertModel.getValueAt(row, 0).toString();
            Medicine med = medicines.stream().filter(m -> m.getId().equals(medId)).findFirst().orElse(null);
            if (med != null) {
                String input = JOptionPane.showInputDialog(this, "Enter refill quantity for " + med.getName() + ":", "100");
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int addQty = Integer.parseInt(input.trim());
                        if (addQty > 0) { med.addStock(addQty); JOptionPane.showMessageDialog(this, "Successfully added " + addQty + " units to " + med.getName()); refreshAllViews(); }
                    } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Invalid number entered!"); }
                }
            }
        }

        public void refreshData() {
            double sales = invoices.stream().mapToDouble(Invoice::getTotal).sum();
            long lowStock = medicines.stream().filter(m -> m.getStock() <= m.getLimit()).count();
            totalSalesLbl.setText(String.format("৳%.2f", sales));
            totalInvLbl.setText(String.valueOf(invoices.size()));
            totalMedLbl.setText(String.valueOf(medicines.size()));
            lowStockLbl.setText(String.valueOf(lowStock));

            alertModel.setRowCount(0);
            for (Medicine m : medicines) {
                if (m.getStock() <= m.getLimit()) {
                    alertModel.addRow(new Object[]{m.getId(), m.getName(), m.getCategory(), m.getDosage(), m.getStock(), m.getLimit()});
                }
            }
        }
    }

    private class POSPanel extends JPanel {
        private JPanel catalogGrid = new JPanel(new GridLayout(0, 2, 8, 8));
        private JTextField searchField = new JTextField(8);
        private JComboBox<String> categoryFilter = new JComboBox<>(new String[]{
            "All Categories", "Fever", "Heart", "Lung", "Orthopedic", "Gastroenterology", 
            "Neurology", "Dermatology", "Anti-Infective", "Endocrinology", 
            "Ophthalmology", "ENT", "Psychiatry", "General"
        });
        private JComboBox<String> dosageFilter = new JComboBox<>(new String[]{"All Dosages", "5 mg", "10 mg", "20 mg", "50 mg", "120 mg", "300 mg", "500 mg"});
        private JTextField custName = new JTextField("Walk-in Customer"), custPhone = new JTextField(), custDoc = new JTextField(), discountField = new JTextField("0"), cashPaidField = new JTextField("0.00");
        private JLabel subtotalLbl = new JLabel("৳0.00"), taxLbl = new JLabel("৳0.00"), totalLbl = new JLabel("৳0.00", SwingConstants.RIGHT), changeLbl = new JLabel("৳0.00"), dueLbl = new JLabel("৳0.00");
        private JPanel cartItemsContainer = new JPanel();
        private List<CartItem> cart = new ArrayList<>();

        public POSPanel() {
            setLayout(new GridLayout(1, 2, 15, 0));
            setBackground(new Color(248, 250, 252));
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JPanel left = new JPanel(new BorderLayout(10, 10));
            left.setBorder(BorderFactory.createTitledBorder("Medicine Selection"));

            JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 5));

            searchField.addActionListener(e -> refreshCatalog());
            categoryFilter.addActionListener(e -> refreshCatalog());
            dosageFilter.addActionListener(e -> refreshCatalog());

            filterPanel.add(new JLabel("Search:"));
            filterPanel.add(searchField);
            filterPanel.add(new JLabel("Category:"));
            filterPanel.add(categoryFilter);
            filterPanel.add(new JLabel("Strength:"));
            filterPanel.add(dosageFilter);

            left.add(filterPanel, BorderLayout.NORTH);
            left.add(new JScrollPane(catalogGrid), BorderLayout.CENTER);

            JPanel right = new JPanel(new BorderLayout(10, 10));
            right.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

            JPanel custInfo = new JPanel(new GridLayout(3, 2, 4, 4));
            custInfo.add(new JLabel("Customer:")); custInfo.add(custName);
            custInfo.add(new JLabel("Phone:")); custInfo.add(custPhone);
            custInfo.add(new JLabel("Doctor/Ref:")); custInfo.add(custDoc);
            right.add(custInfo, BorderLayout.NORTH);

            cartItemsContainer.setLayout(new BoxLayout(cartItemsContainer, BoxLayout.Y_AXIS));
            cartItemsContainer.setBackground(Color.WHITE);
            right.add(new JScrollPane(cartItemsContainer), BorderLayout.CENTER);

            JPanel calc = new JPanel(new GridLayout(7, 2, 4, 2));
            discountField.addActionListener(e -> calculate());
            cashPaidField.addActionListener(e -> calculate());

            calc.add(new JLabel("Subtotal:")); calc.add(subtotalLbl);
            calc.add(new JLabel("Discount (%):")); calc.add(discountField);
            calc.add(new JLabel("Tax (5%):")); calc.add(taxLbl);
            
            JLabel totalTitleLbl = new JLabel("Total:");
            totalTitleLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
            totalLbl.setFont(new Font("SansSerif", Font.BOLD, 18));

            calc.add(totalTitleLbl); calc.add(totalLbl);
            calc.add(new JLabel("Cash Paid:")); calc.add(cashPaidField);
            calc.add(new JLabel("Change Return:")); calc.add(changeLbl);
            calc.add(new JLabel("Due / Balance:")); calc.add(dueLbl);

            JPanel actionBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            JButton checkoutBtn = new JButton("CHECKOUT");
            checkoutBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            checkoutBtn.setBackground(new Color(13, 148, 136));
            checkoutBtn.setForeground(Color.WHITE);
            checkoutBtn.addActionListener(e -> checkout());

            JButton clearBtn = new JButton("CLEAR");
            clearBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            clearBtn.addActionListener(e -> {
                cart.clear();
                renderCartUI();
            });

            actionBtns.add(checkoutBtn);
            actionBtns.add(clearBtn);

            JPanel rightSouth = new JPanel(new BorderLayout(5, 5));
            rightSouth.add(calc, BorderLayout.CENTER);
            rightSouth.add(actionBtns, BorderLayout.SOUTH);
            right.add(rightSouth, BorderLayout.SOUTH);

            add(left); add(right);
        }

        public void refreshCatalog() {
            catalogGrid.removeAll();
            String search = searchField.getText().trim().toLowerCase();
            String selectedCat = (String) categoryFilter.getSelectedItem();
            String selectedDosage = (String) dosageFilter.getSelectedItem();

            for (Medicine m : medicines) {
                boolean matchSearch = m.getName().toLowerCase().contains(search) || m.getCategory().toLowerCase().contains(search);
                boolean matchCat = selectedCat.equals("All Categories") || m.getCategory().equalsIgnoreCase(selectedCat);
                boolean matchDosage = selectedDosage.equals("All Dosages") || m.getDosage().equalsIgnoreCase(selectedDosage);

                if (matchSearch && matchCat && matchDosage) {
                    JPanel card = new JPanel(new BorderLayout(5, 5));
                    card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    card.setBackground(m.getStock() <= m.getLimit() ? new Color(254, 243, 199) : Color.WHITE);

                    JLabel info = new JLabel("<html><b>" + m.getName() + "</b> [" + m.getCategory() + "]<br>" + m.getDosage() + " | Stock: " + m.getStock() + "</html>");
                    
                    JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
                    btnBox.setOpaque(false);

                    JButton addBtn = new JButton(String.format("+৳%.2f", m.getPrice()));
                    addBtn.addActionListener(e -> addToCart(m));
                    btnBox.add(addBtn);

                    if (m.getStock() <= m.getLimit()) {
                        JButton stockInBtn = new JButton("+ Stock In");
                        stockInBtn.setBackground(new Color(217, 119, 6));
                        stockInBtn.setForeground(Color.WHITE);
                        stockInBtn.setFont(new Font("SansSerif", Font.PLAIN, 10));
                        stockInBtn.addActionListener(e -> {
                            String input = JOptionPane.showInputDialog(this, "Refill quantity for " + m.getName() + ":", "50");
                            if (input != null && !input.trim().isEmpty()) {
                                try { m.addStock(Integer.parseInt(input.trim())); refreshAllViews(); } catch (Exception ignored) {}
                            }
                        });
                        btnBox.add(stockInBtn);
                    }

                    card.add(info, BorderLayout.CENTER);
                    card.add(btnBox, BorderLayout.EAST);
                    catalogGrid.add(card);
                }
            }
            catalogGrid.revalidate();
            catalogGrid.repaint();
        }

        private void addToCart(Medicine m) {
            if (m.getStock() <= 0) { JOptionPane.showMessageDialog(this, "Item is completely out of stock!"); return; }
            
            String input = JOptionPane.showInputDialog(this, "Enter Tablet Quantity for " + m.getName() + " (" + m.getDosage() + "):", "3");
            if (input == null || input.trim().isEmpty()) return;

            int qtyToAdd;
            try {
                qtyToAdd = Integer.parseInt(input.trim());
                if (qtyToAdd <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid positive tablet quantity!");
                return;
            }

            for (CartItem ci : cart) {
                if (ci.getMedicine().getId().equals(m.getId())) {
                    if (ci.getQuantity() + qtyToAdd > m.getStock()) {
                        JOptionPane.showMessageDialog(this, "Cannot exceed available stock limit!");
                        return;
                    }
                    ci.setQuantity(ci.getQuantity() + qtyToAdd);
                    renderCartUI();
                    return;
                }
            }

            if (qtyToAdd > m.getStock()) {
                JOptionPane.showMessageDialog(this, "Requested quantity exceeds available stock (" + m.getStock() + ")!");
                return;
            }

            cart.add(new CartItem(m, qtyToAdd));
            renderCartUI();
        }

        private void renderCartUI() {
            cartItemsContainer.removeAll();
            for (CartItem ci : cart) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
                row.setMaximumSize(new Dimension(3000, 35));
                row.setBackground(Color.WHITE);

                JLabel nameLbl = new JLabel(ci.getMedicine().getName() + " (" + ci.getMedicine().getDosage() + ")");
                nameLbl.setPreferredSize(new Dimension(140, 20));

                JButton minusBtn = new JButton("-"), plusBtn = new JButton("+"), removeBtn = new JButton("X");
                JLabel qtyLbl = new JLabel(ci.getQuantity() + " tabs");
                JLabel totalItemLbl = new JLabel(String.format("৳%.2f", ci.getTotalPrice()));
                totalItemLbl.setPreferredSize(new Dimension(55, 20));
                removeBtn.setForeground(Color.RED);

                minusBtn.addActionListener(e -> {
                    if (ci.getQuantity() > 1) { ci.setQuantity(ci.getQuantity() - 1); } else { cart.remove(ci); }
                    renderCartUI();
                });

                plusBtn.addActionListener(e -> {
                    if (ci.getQuantity() + 1 <= ci.getMedicine().getStock()) { ci.setQuantity(ci.getQuantity() + 1); } else { JOptionPane.showMessageDialog(this, "Stock Limit Reached!"); }
                    renderCartUI();
                });

                removeBtn.addActionListener(e -> { cart.remove(ci); renderCartUI(); });

                row.add(nameLbl); row.add(minusBtn); row.add(qtyLbl); row.add(plusBtn); row.add(totalItemLbl); row.add(removeBtn);
                cartItemsContainer.add(row);
            }
            cartItemsContainer.revalidate();
            cartItemsContainer.repaint();
            calculate();
        }

        private void calculate() {
            double sub = cart.stream().mapToDouble(CartItem::getTotalPrice).sum(), disc = 0, cash = 0;
            try { disc = Double.parseDouble(discountField.getText().trim()); } catch (Exception ignored) {}
            try { cash = Double.parseDouble(cashPaidField.getText().trim()); } catch (Exception ignored) {}

            double discAmt = sub * (disc / 100.0), tax = (sub - discAmt) * 0.05, tot = (sub - discAmt) + tax;
            double change = cash >= tot ? cash - tot : 0, due = cash < tot ? tot - cash : 0;

            subtotalLbl.setText(String.format("৳%.2f", sub));
            taxLbl.setText(String.format("৳%.2f", tax));
            totalLbl.setText(String.format("৳%.2f", tot));
            changeLbl.setText(String.format("৳%.2f", change));
            dueLbl.setText(String.format("৳%.2f", due));
        }

        private void checkout() {
            if (cart.isEmpty()) { JOptionPane.showMessageDialog(this, "Cart is empty!"); return; }
            calculate();
            double sub = cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
            double disc = Double.parseDouble(discountField.getText().trim());
            double discAmt = sub * (disc / 100.0);
            double tax = (sub - discAmt) * 0.05;
            double tot = (sub - discAmt) + tax;
            double cash = Double.parseDouble(cashPaidField.getText().trim());

            for (CartItem ci : cart) { ci.getMedicine().setStock(ci.getMedicine().getStock() - ci.getQuantity()); }

            Invoice inv = new Invoice("INV-" + (int)(1000 + Math.random() * 9000), custName.getText(), custPhone.getText(), custDoc.getText(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), cart, sub, discAmt, tax, tot, cash, cash >= tot ? cash - tot : 0, cash < tot ? tot - cash : 0);
            invoices.add(0, inv);
            new ReceiptDialog(PharmacyManagementSystem.this, inv).setVisible(true);

            cart.clear();
            renderCartUI();
            refreshAllViews();
        }
    }

    public static class ReceiptDialog extends JDialog {
        private JTextArea receiptArea = new JTextArea();

        public ReceiptDialog(JFrame parent, Invoice inv) {
            super(parent, "Invoice Receipt #" + inv.getInvoiceId(), true);
            setSize(400, 560);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());

            receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            receiptArea.setEditable(false);

            StringBuilder sb = new StringBuilder();
            sb.append("=========================================\n       PHARMACY MANAGEMENT SYSTEM        \n       123 Health Ave, Medical Zone      \n            Phone: +880 1700000000       \n=========================================\n");
            sb.append("Invoice: #").append(inv.getInvoiceId()).append("\nDate: ").append(inv.getDate()).append("\nCustomer: ").append(inv.getCustomer()).append("\nPhone: ").append(inv.getPhone()).append("\nRef Doctor: ").append(inv.getDoctor()).append("\n-----------------------------------------\n");
            sb.append(String.format("%-18s %-5s %-7s %-7s\n", "Item", "Qty", "Price", "Total")).append("-----------------------------------------\n");

            for (CartItem item : inv.getItems()) {
                String name = item.getMedicine().getName() + "(" + item.getMedicine().getDosage() + ")";
                if (name.length() > 17) name = name.substring(0, 15) + "..";
                sb.append(String.format("%-18s %-5d %-7.2f %-7.2f\n", name, item.getQuantity(), item.getMedicine().getPrice(), item.getTotalPrice()));
            }

            sb.append("-----------------------------------------\n");
            sb.append(String.format("Subtotal:                     ৳%.2f\n", inv.getSubtotal())).append(String.format("Discount:                    -৳%.2f\n", inv.getDiscount())).append(String.format("Tax (5%%):                     ৳%.2f\n", inv.getTax())).append(String.format("TOTAL:                        ৳%.2f\n", inv.getTotal())).append(String.format("Cash Paid:                    ৳%.2f\n", inv.getCashPaid())).append(String.format("Change Return:                ৳%.2f\n", inv.getChange())).append(String.format("Balance Due:                  ৳%.2f\n", inv.getDue())).append("=========================================\n       Get Well Soon! Thank You!         \n=========================================\n");

            receiptArea.setText(sb.toString());
            add(new JScrollPane(receiptArea), BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton printBtn = new JButton("Print Receipt"), closeBtn = new JButton("Close");
            printBtn.setBackground(new Color(13, 148, 136));
            printBtn.setForeground(Color.WHITE);

            printBtn.addActionListener(e -> {
                try { if (receiptArea.print()) JOptionPane.showMessageDialog(this, "Printing Completed!"); }
                catch (PrinterException ex) { JOptionPane.showMessageDialog(this, "Printing Failed: " + ex.getMessage()); }
            });

            closeBtn.addActionListener(e -> dispose());
            btnPanel.add(printBtn); btnPanel.add(closeBtn);
            add(btnPanel, BorderLayout.SOUTH);
        }
    }

    private class InventoryPanel extends JPanel {
        private DefaultTableModel model;
        private JTable table;
        private JTextField search = new JTextField(15);

        public InventoryPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
            search.addActionListener(e -> refreshTable());

            JButton stockInBtn = new JButton("+ Refill Selected Medicine"), singleAddBtn = new JButton("+ New Medicine"), bulkAddBtn = new JButton("⚡ Bulk Add CSV / Text");
            stockInBtn.setBackground(new Color(217, 119, 6)); stockInBtn.setForeground(Color.WHITE); stockInBtn.addActionListener(e -> stockInSelected());
            singleAddBtn.setBackground(new Color(13, 148, 136)); singleAddBtn.setForeground(Color.WHITE); singleAddBtn.addActionListener(e -> addSingleMedDialog());
            bulkAddBtn.setBackground(new Color(17, 94, 89)); bulkAddBtn.setForeground(Color.WHITE); bulkAddBtn.addActionListener(e -> addBulkMedDialog());

            top.add(new JLabel("Search Stock:")); top.add(search); top.add(stockInBtn); top.add(singleAddBtn); top.add(bulkAddBtn);
            add(top, BorderLayout.NORTH);

            model = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Dosage", "Price/Tablet", "Stock Qty", "Alert Limit", "Status"}, 0);
            table = new JTable(model);
            add(new JScrollPane(table), BorderLayout.CENTER);
        }

        private void stockInSelected() {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a medicine row first to add stock!"); return; }
            String medId = model.getValueAt(row, 0).toString();
            Medicine med = medicines.stream().filter(m -> m.getId().equals(medId)).findFirst().orElse(null);
            if (med != null) {
                String input = JOptionPane.showInputDialog(this, "Refill Quantity for " + med.getName() + ":", "50");
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int addQty = Integer.parseInt(input.trim());
                        if (addQty > 0) { med.addStock(addQty); refreshAllViews(); }
                    } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Invalid quantity number!"); }
                }
            }
        }

        public void refreshTable() {
            model.setRowCount(0);
            String q = search.getText().trim().toLowerCase();
            for (Medicine m : medicines) {
                if (m.getName().toLowerCase().contains(q) || m.getCategory().toLowerCase().contains(q)) {
                    model.addRow(new Object[]{m.getId(), m.getName(), m.getCategory(), m.getDosage(), String.format("৳%.2f", m.getPrice()), m.getStock(), m.getLimit(), m.getStock() <= m.getLimit() ? "LOW STOCK" : "IN STOCK"});
                }
            }
        }

        private void addSingleMedDialog() {
            JTextField name = new JTextField(), cat = new JTextField("Fever"), dosage = new JTextField("500 mg"), price = new JTextField(), qty = new JTextField(), limit = new JTextField("15");
            JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
            panel.add(new JLabel("Name:")); panel.add(name);
            panel.add(new JLabel("Category (Fever/Heart/Lung...):")); panel.add(cat);
            panel.add(new JLabel("Dosage (500mg/10mg/...):")); panel.add(dosage);
            panel.add(new JLabel("Price per Tablet (৳):")); panel.add(price);
            panel.add(new JLabel("Tablet Stock Qty:")); panel.add(qty);
            panel.add(new JLabel("Alert Limit:")); panel.add(limit);

            if (JOptionPane.showConfirmDialog(this, panel, "Add Single Medicine Item", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    medicines.add(new Medicine("MED-" + (101 + medicines.size()), name.getText().trim(), cat.getText().trim(), dosage.getText().trim(), Double.parseDouble(price.getText().trim()), Integer.parseInt(qty.getText().trim()), Integer.parseInt(limit.getText().trim())));
                    refreshAllViews();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid Inputs!"); }
            }
        }

        private void addBulkMedDialog() {
            JTextArea textArea = new JTextArea(10, 40);
            textArea.setText("// Name, Dosage, Price, Stock, Category\nParacetamol, 500 mg, 2.00, 100, Fever\nMontelukast, 10 mg, 12.00, 200, Lung\nOmeprazole, 20 mg, 7.00, 150, Gastroenterology\nAzithromycin, 500 mg, 35.00, 80, Anti-Infective");
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.add(new JLabel("Paste CSV/Text Items Below:"), BorderLayout.NORTH);
            panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

            if (JOptionPane.showConfirmDialog(this, panel, "Fast Bulk Medicine Entry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String[] lines = textArea.getText().split("\n");
                int added = 0;
                for (String line : lines) {
                    if (line.trim().startsWith("//") || line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        try {
                            medicines.add(new Medicine("MED-" + (101 + medicines.size()), parts[0].trim(), parts.length >= 5 ? parts[4].trim() : "General", parts[1].trim(), Double.parseDouble(parts[2].trim()), Integer.parseInt(parts[3].trim()), 15));
                            added++;
                        } catch (Exception ignored) {}
                    }
                }
                JOptionPane.showMessageDialog(this, "Successfully added " + added + " new items into stock!");
                refreshAllViews();
            }
        }
    }

    private class InvoiceHistoryPanel extends JPanel {
        private DefaultTableModel model;

        public InvoiceHistoryPanel() {
            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            model = new DefaultTableModel(new String[]{"Invoice ID", "Date", "Customer", "Items Count", "Total Amount"}, 0);
            add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
        }

        public void refreshTable() {
            model.setRowCount(0);
            for (Invoice inv : invoices) {
                model.addRow(new Object[]{"#" + inv.getInvoiceId(), inv.getDate(), inv.getCustomer(), inv.getItems().size() + " items", String.format("৳%.2f", inv.getTotal())});
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PharmacyManagementSystem().setVisible(true));
    }
}