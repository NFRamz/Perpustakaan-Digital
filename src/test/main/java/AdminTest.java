import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.lang.reflect.Field;

public class AdminTest {

    private Admin admin;

    @Before
    public void setUp() {
        admin = new Admin();
        admin.display(); // Memastikan GUI diinisialisasi
    }

    @Test
    public void testAddBook() throws Exception {
        // Menggunakan Reflection untuk mengatur nilai variabel private
        setPrivateField(admin, "titleText", new JTextField("New Book"));
        setPrivateField(admin, "authorText", new JTextField("Author Name"));
        setPrivateField(admin, "categoryText", new JTextField("Programming"));
        setPrivateField(admin, "quantityText", new JTextField("5"));
        setPrivateField(admin, "selectedImageFile", new File("D:\\path\\to\\image.jpg"));

        // Panggil metode addBookAction
        admin.addBookAction(null);

        // Akses model tabel dan periksa apakah buku berhasil ditambahkan
        DefaultTableModel tableModel = getPrivateField(admin, "tableModel");
        boolean bookAdded = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String title = (String) tableModel.getValueAt(i, 1);
            if ("New Book".equals(title)) {
                bookAdded = true;
                break;
            }
        }
        assertTrue("Book should be added successfully", bookAdded);
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Dummy data untuk memulai
        admin.dummy();
        admin.loadBooks();

        // Pilih baris pertama
        JTable bookTable = getPrivateField(admin, "bookTable");
        bookTable.setRowSelectionInterval(0, 0);

        // Mengatur nilai baru
        setPrivateField(admin, "titleText", new JTextField("Updated Title"));
        setPrivateField(admin, "authorText", new JTextField("Updated Author"));
        setPrivateField(admin, "categoryText", new JTextField("Updated Category"));
        setPrivateField(admin, "quantityText", new JTextField("10"));

        // Panggil metode updateBookAction
        admin.updateBookAction(null);

        // Verifikasi data diperbarui
        DefaultTableModel tableModel = getPrivateField(admin, "tableModel");
        String updatedTitle = (String) tableModel.getValueAt(0, 1);
        assertEquals("Updated Title", updatedTitle);
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Dummy data untuk memulai
        admin.dummy();
        admin.loadBooks();

        // Pilih baris pertama
        JTable bookTable = getPrivateField(admin, "bookTable");
        bookTable.setRowSelectionInterval(0, 0);

        // Hitung jumlah baris sebelum penghapusan
        DefaultTableModel tableModel = getPrivateField(admin, "tableModel");
        int rowCountBefore = tableModel.getRowCount();

        // Panggil metode deleteBookAction
        admin.deleteBookAction(null);

        // Verifikasi jumlah baris berkurang
        int rowCountAfter = tableModel.getRowCount();
        assertEquals(rowCountBefore - 1, rowCountAfter);
    }

    // ==== Helper untuk Reflection ====

    private <T> T getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(obj);
    }

    private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
