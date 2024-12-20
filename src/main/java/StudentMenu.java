import Book.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentMenu {
    private JFrame frame;
    private JPanel panel;
    private JPanel booksPanel;

    public void menu() {
        // Frame utama
        frame = new JFrame("Student Menu");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Menempatkan frame di tengah layar

        // Panel utama dengan BorderLayout
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));  // Warna latar belakang lembut
        frame.add(panel);

        // Header Label
        JLabel headerLabel = new JLabel("Borrowed Books", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));  // Font lebih besar
        headerLabel.setForeground(new Color(50, 50, 50));  // Warna teks header
        panel.add(headerLabel, BorderLayout.NORTH);

        // Panel buku (card view) untuk menampilkan buku yang dipinjam
        booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 3, 20, 20));  // 3 kolom, jarak antar card 20px
        booksPanel.setBackground(new Color(245, 245, 245));  // Sesuaikan dengan latar belakang panel
        JScrollPane scrollPane = new JScrollPane(booksPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Mengambil data buku yang dipinjam
        ArrayList<Book> borrowedBooks = BookDatabase.getBorrowedBooks();
        for (Book book : borrowedBooks) {
            // Card untuk setiap buku
            JPanel card = createBookCard(book);
            booksPanel.add(card);
        }

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));  // Sesuaikan dengan latar belakang panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Tombol untuk pinjam buku
        JButton borrowButton = new JButton("Borrow Book");
        styleButton(borrowButton);
        buttonPanel.add(borrowButton);
        borrowButton.addActionListener(e -> {
            frame.dispose();
            pinjamBuku();
        });

        // Tombol untuk kembalikan buku
        JButton returnButton = new JButton("Return Book");
        styleButton(returnButton);
        buttonPanel.add(returnButton);
        returnButton.addActionListener(e -> {
            frame.dispose();
            pengembalianBuku();
        });

        // Tombol logout
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        buttonPanel.add(logoutButton);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginMenu().display();
        });

        // Menampilkan frame
        frame.setVisible(true);
    }

    private JPanel createBookCard(Book book) {
        // Panel card untuk menampilkan informasi buku
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Border tipis

        // Gambar sampul buku
        JLabel coverLabel = new JLabel(new ImageIcon(new ImageIcon(book.getCoverImagePath()).getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH)));
        card.add(coverLabel, BorderLayout.CENTER);

        // Panel untuk informasi buku
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Judul, Penulis, Kategori
        JLabel titleLabel = new JLabel("<html><b>" + book.getTitle() + "</b></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 50));
        textPanel.add(titleLabel);

        JLabel authorLabel = new JLabel(book.getAuthor());
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        authorLabel.setForeground(new Color(100, 100, 100));
        textPanel.add(authorLabel);

        JLabel categoryLabel = new JLabel(book.getCategory());
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryLabel.setForeground(new Color(150, 150, 150));
        textPanel.add(categoryLabel);

        card.add(textPanel, BorderLayout.SOUTH);

        return card;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 122, 255));  // Warna tombol
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));  // Font tombol lebih besar
        button.setPreferredSize(new Dimension(180, 45));  // Ukuran tombol lebih besar
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void pinjamBuku() {
        frame = new JFrame("Borrow Book");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        frame.add(panel);

        // Header Label
        JLabel headerLabel = new JLabel("Available Books", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(50, 50, 50));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Tabel buku yang tersedia
        String[] columns = {"ID", "Title", "Author", "Category", "Quantity"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable availableBooksTable = new JTable(tableModel);
        availableBooksTable.setFillsViewportHeight(true);
        availableBooksTable.setRowHeight(30);

        ArrayList<Book> availableBooks = BookDatabase.getAvailableBooks();
        for (Book book : availableBooks) {
            Object[] row = {book.getId(), book.getTitle(), book.getAuthor(), book.getCategory(), book.getQuantity()};
            tableModel.addRow(row);
        }

        // Menambahkan JScrollPane
        JScrollPane scrollPane = new JScrollPane(availableBooksTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel input dan tombol
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(245, 245, 245));
        panel.add(inputPanel, BorderLayout.SOUTH);

        JLabel idLabel = new JLabel("Book ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(idLabel);

        JTextField idField = new JTextField(10);
        inputPanel.add(idField);

        // Tombol untuk meminjam buku
        JButton borrowButton = new JButton("Borrow");
        styleButton(borrowButton);
        inputPanel.add(borrowButton);
        borrowButton.addActionListener(e -> {
            String bookId = idField.getText();
            if (BookDatabase.borrowBook(bookId)) {
                JOptionPane.showMessageDialog(frame, "Book borrowed successfully!");
                frame.dispose();
                menu();
            } else {
                JOptionPane.showMessageDialog(frame, "Book borrowing failed. Check ID or availability.");
            }
        });

        // Tombol kembali ke menu
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        inputPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();
            menu();
        });

        // Menampilkan frame
        frame.setVisible(true);
    }

    public void pengembalianBuku() {
        frame = new JFrame("Return Book");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        frame.add(panel);

        JLabel headerLabel = new JLabel("Borrowed Books", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(50, 50, 50));
        panel.add(headerLabel, BorderLayout.NORTH);

        String[] columns = {"ID", "Title", "Author", "Category"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable borrowedBooksTable = new JTable(tableModel);
        borrowedBooksTable.setFillsViewportHeight(true);
        borrowedBooksTable.setRowHeight(30);

        ArrayList<Book> borrowedBooks = BookDatabase.getBorrowedBooks();
        for (Book book : borrowedBooks) {
            Object[] row = {book.getId(), book.getTitle(), book.getAuthor(), book.getCategory()};
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(borrowedBooksTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(245, 245, 245));
        panel.add(inputPanel, BorderLayout.SOUTH);

        JLabel idLabel = new JLabel("Book ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(idLabel);

        JTextField idField = new JTextField(10);
        inputPanel.add(idField);

        JButton returnButton = new JButton("Return");
        styleButton(returnButton);
        inputPanel.add(returnButton);
        returnButton.addActionListener(e -> {
            String bookId = idField.getText();
            if (BookDatabase.returnBook(bookId)) {
                JOptionPane.showMessageDialog(frame, "Book returned successfully!");
                frame.dispose();
                menu();
            } else {
                JOptionPane.showMessageDialog(frame, "Book return failed. Check ID or availability.");
            }
        });

        JButton backButton = new JButton("Back");
        styleButton(backButton);
        inputPanel.add(backButton);
        backButton.addActionListener(e -> {
            frame.dispose();
            menu();
        });

        frame.setVisible(true);
    }
}
