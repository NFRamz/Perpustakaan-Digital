package Book;

public class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private int totalQuantity;
    private int quantity;
    private String coverImagePath; // Path gambar cover buku

    // Constructor baru dengan coverImagePath
    public Book(int id, String title, String author, String category, int quantity, String coverImagePath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalQuantity = quantity;
        this.quantity = quantity;
        this.coverImagePath = (coverImagePath != null) ? coverImagePath : ""; // Pastikan tidak null
    }

    // Constructor lama untuk kompatibilitas
    public Book(int id, String title, String author, String category, int quantity) {
        this(id, title, author, category, quantity, ""); // Path gambar kosong sebagai default
    }

    // Getter dan setter untuk coverImagePath
    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = (coverImagePath != null) ? coverImagePath : ""; // Validasi input
    }

    // Getters and Setters untuk atribut lainnya
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    // Method toString untuk debugging
    @Override
    public String toString() {
        return "ID: " + id +
                ", Title: " + title +
                ", Author: " + author +
                ", Category: " + category +
                ", Available: " + quantity + "/" + totalQuantity +
                ", Cover Image: " + (coverImagePath.isEmpty() ? "No Image" : coverImagePath);
    }
}
