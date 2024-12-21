package Book;

import java.util.ArrayList;

public class BookDatabase {
    private static ArrayList<Book> books = new ArrayList<>();

    public static void addBook(int id, String title, String author, String category, int quantity, String coverImagePath) {
        books.add(new Book(id, title, author, category, quantity, coverImagePath));
    }

    public static ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getQuantity() > 0) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public static ArrayList<Book> getBorrowedBooks() {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getQuantity() < book.getTotalQuantity()) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }

    public static boolean borrowBook(String bookId) {
        try {
            int id = Integer.parseInt(bookId);
            for (Book book : books) {
                if (book.getId() == id && book.getQuantity() > 0) {
                    book.setQuantity(book.getQuantity() - 1);
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public static boolean returnBook(String bookId) {
        try {
            int id = Integer.parseInt(bookId);
            for (Book book : books) {
                if (book.getId() == id && book.getQuantity() < book.getTotalQuantity()) {
                    book.setQuantity(book.getQuantity() + 1);
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public static Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static boolean updateBook(int id, String title, String author, String category, int quantity) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(title);
                book.setAuthor(author);
                book.setCategory(category);
                book.setTotalQuantity(quantity);
                book.setQuantity(quantity); // Memperbarui jumlah buku
                return true;
            }
        }
        return false;
    }

    public static boolean deleteBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                return true;
            }
        }
        return false;
    }
}
