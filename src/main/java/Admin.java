import Book.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class Admin {
    private JFrame frame;
    private JTextField titleText, authorText, categoryText, quantityText;
    private JLabel imageLabel;
    public JTable bookTable;
    public DefaultTableModel tableModel;

    private File selectedImageFile;

    // ==== Elemen-elemen UI ====
    private JButton updateButton, deleteButton, addButton, backButton, imageButton;
    private JPanel mainPanel, headerPanel, tablePanel, tableButtonPanel, formPanel;
    private JLabel headerLabel;

    // ==== Fungsi Lain ====
    private void clearFields() {
        titleText.setText("");
        authorText.setText("");
        categoryText.setText("");
        quantityText.setText("");
        selectedImageFile = null;
        imageLabel.setIcon(null);
        imageLabel.setText("No Image Selected");
    }

    public void loadBooks() {
        tableModel.setRowCount(0);
        for (Book book : BookDatabase.getAvailableBooks()) {
            tableModel.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getQuantity()});
        }
    }

    public void dummy() {
        BookDatabase.addBook(1, "Java Programming", "Dr. Muneer Ahmad Dar", "Programming", 5, "D:\\TUGAS KULIAH\\SEMESTER 3\\Pemograman Lanjut\\library_javaSwing\\src\\main\\java\\Image\\Cover1.jpg");
        BookDatabase.addBook(2, "Python Essentials", "Beazley, David", "Programming", 3, "D:\\TUGAS KULIAH\\SEMESTER 3\\Pemograman Lanjut\\library_javaSwing\\src\\main\\java\\Image\\cover2.jpg");
        BookDatabase.addBook(3, "C++ Guide", "Erik Myers", "Programming", 4, "D:\\TUGAS KULIAH\\SEMESTER 3\\Pemograman Lanjut\\library_javaSwing\\src\\main\\java\\Image\\cover3.jpg");
        BookDatabase.addBook(4, "Modern Art", "Erick", "Design", 2, "D:\\TUGAS KULIAH\\SEMESTER 3\\Pemograman Lanjut\\library_javaSwing\\src\\main\\java\\Image\\Cover4.jpg");
    }

    // ==== Aksi Tombol ====
    public void addBookAction(ActionEvent e) {
        try {
            String title = titleText.getText();
            String author = authorText.getText();
            String category = categoryText.getText();
            int quantity = Integer.parseInt(quantityText.getText());
            int id = BookDatabase.getAvailableBooks().size() + 1;

            if (!title.isEmpty() && !author.isEmpty() && !category.isEmpty() && quantity > 0 && selectedImageFile != null) {
                BookDatabase.addBook(id, title, author, category, quantity, selectedImageFile.getPath());
                JOptionPane.showMessageDialog(frame, "Book added successfully!");
                clearFields();
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields and select an image.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Quantity must be a valid number.");
        }
    }

    public void updateBookAction(ActionEvent e) {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a book to update.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String title = titleText.getText();
        String author = authorText.getText();
        String category = categoryText.getText();
        int quantity;

        try {
            quantity = Integer.parseInt(quantityText.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Quantity must be a valid number.");
            return;
        }

        if (!title.isEmpty() && !author.isEmpty() && !category.isEmpty() && quantity > 0) {
            BookDatabase.updateBook(id, title, author, category, quantity);
            JOptionPane.showMessageDialog(frame, "Book updated successfully!");
            loadBooks();
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
        }
    }

    public void deleteBookAction(ActionEvent e) {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        BookDatabase.deleteBook(id);
        JOptionPane.showMessageDialog(frame, "Book deleted successfully!");
        loadBooks();
    }

    private void backAction(ActionEvent e) {
        frame.dispose();
        new LoginMenu().display();
    }

    private void chooseImageAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(new ImageIcon(selectedImageFile.getAbsolutePath()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            imageLabel.setText("");
            imageLabel.setIcon(icon);
        }
    }

    // ==== Inisialisasi Frame ====
    public void display() {
        dummy();

        // ==== Panel Utama ====
        frame = new JFrame("Admin Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ==== Panel Header ====
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(65, 105, 225));
        headerLabel = new JLabel("Admin Book Management");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // ==== Panel Tabel ====
        tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Category", "Quantity"}, 0);
        bookTable = new JTable(tableModel);
        loadBooks();
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // ==== Panel Tombol Tabel ====
        tableButtonPanel = new JPanel();
        updateButton = new JButton("Update Book");
        deleteButton = new JButton("Delete Book");
        addButton = new JButton("Add Book");
        backButton = new JButton("Back");

        tableButtonPanel.add(updateButton);
        updateButton.addActionListener(this::updateBookAction);

        tableButtonPanel.add(deleteButton);
        deleteButton.addActionListener(this::deleteBookAction);
        tableButtonPanel.add(addButton);
        addButton.addActionListener(this::addBookAction);
        tableButtonPanel.add(backButton);
        backButton.addActionListener(this::backAction);

        tablePanel.add(tableButtonPanel, BorderLayout.SOUTH);

        // ==== Panel Form ====
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ==== Elemen Form ====
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Book Title:"), gbc);

        gbc.gridx = 1;
        titleText = new JTextField(20);
        formPanel.add(titleText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Author:"), gbc);

        gbc.gridx = 1;
        authorText = new JTextField(20);
        formPanel.add(authorText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        categoryText = new JTextField(20);
        formPanel.add(categoryText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        quantityText = new JTextField(20);
        formPanel.add(quantityText, gbc);

        // ==== Pilihan Gambar ====
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Book Cover:"), gbc);

        imageButton = new JButton("Choose Image");
        imageButton.addActionListener(e -> chooseImageAction());

        gbc.gridx = 1;
        formPanel.add(imageButton, gbc);

        // ==== Preview Gambar ====
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        imageLabel = new JLabel("No Image Selected", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(imageLabel, gbc);

        // ==== Menambahkan Semua Panel ke Panel Utama ====
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }


}