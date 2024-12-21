import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;

class LoginMenuTest {

    private LoginMenu loginMenu;
    private Dotenv dotenv;

    @BeforeEach
    void setUp() {
        // Inisialisasi LoginMenu dan Dotenv sebelum setiap tes
        loginMenu = new LoginMenu();
        dotenv = Dotenv.configure().load();
        loginMenu.display(); // Tampilkan GUI
    }

    @Test
    void testAdminLoginSuccess() {
        // Ambil kredensial admin dari .env
        String adminUsername = dotenv.get("ADMIN_USERNAME");
        String adminPassword = dotenv.get("ADMIN_PASSWORD");

        // Simulasi input username dan password
        loginMenu.getUsernameField().setText(adminUsername);
        loginMenu.getPasswordField().setText(adminPassword);

        // Simulasi klik tombol login
        loginMenu.handleLogin(null);

        // Periksa bahwa tidak ada pesan error
        assertEquals("", loginMenu.getErrorLabel().getText(), "Error label harus kosong untuk login yang berhasil.");
    }

    @Test
    void testStudentLoginSuccess() {
        // Ambil kredensial student dari .env
        String studentUsername = dotenv.get("STUDENT_USERNAME");
        String studentPassword = dotenv.get("STUDENT_PASSWORD");

        // Simulasi input username dan password
        loginMenu.getUsernameField().setText(studentUsername);
        loginMenu.getPasswordField().setText(studentPassword);

        // Simulasi klik tombol login
        loginMenu.handleLogin(null);

        // Periksa bahwa tidak ada pesan error
        assertEquals("", loginMenu.getErrorLabel().getText(), "Error label harus kosong untuk login yang berhasil.");
    }

    @Test
    void testInvalidLogin() {
        // Inputkan username dan password yang salah
        loginMenu.getUsernameField().setText("invalid_user");
        loginMenu.getPasswordField().setText("wrong_password");

        // Simulasi klik tombol login
        loginMenu.handleLogin(null);

        // Periksa bahwa pesan error muncul
        assertEquals("Invalid login credentials!", loginMenu.getErrorLabel().getText(),
                "Pesan error harus muncul untuk kredensial yang salah.");
    }

    @Test
    void testEmptyUsernameAndPassword() {
        // Kosongkan username dan password
        loginMenu.getUsernameField().setText("");
        loginMenu.getPasswordField().setText("");

        // Simulasi klik tombol login
        loginMenu.handleLogin(null);

        // Periksa bahwa pesan error muncul
        assertEquals("Username or Password cannot be empty!", loginMenu.getErrorLabel().getText(),
                "Pesan error harus muncul jika username atau password kosong.");
    }

    @Test
    void testEmptyUsername() {
        // Kosongkan username dan masukkan password
        loginMenu.getUsernameField().setText("");
        loginMenu.getPasswordField().setText("some_password");

        // Simulasi klik tombol login
        loginMenu.handleLogin(null);

        // Periksa bahwa pesan error muncul
        assertEquals("Username or Password cannot be empty!", loginMenu.getErrorLabel().getText(),
                "Pesan error harus muncul jika username kosong.");
    }

    @Test
    void testEmptyPassword() {
        // Masukkan username dan kosongkan password
        loginMenu.getUsernameField().setText("some_user");
        loginMenu.getPasswordField().setText("");

        // Simulasi klik tombol login
        loginMenu.handleLogin(null);

        // Periksa bahwa pesan error muncul
        assertEquals("Username or Password cannot be empty!", loginMenu.getErrorLabel().getText(),
                "Pesan error harus muncul jika password kosong.");
    }

    @Test
    void testErrorLabelResetAfterSuccess() {
        // Simulasi input salah terlebih dahulu
        loginMenu.getUsernameField().setText("invalid_user");
        loginMenu.getPasswordField().setText("wrong_password");
        loginMenu.handleLogin(null);

        // Pastikan ada pesan error
        assertEquals("Invalid login credentials!", loginMenu.getErrorLabel().getText(),
                "Pesan error harus muncul untuk kredensial yang salah.");

        // Simulasi login dengan kredensial valid
        String adminUsername = dotenv.get("ADMIN_USERNAME");
        String adminPassword = dotenv.get("ADMIN_PASSWORD");
        loginMenu.getUsernameField().setText(adminUsername);
        loginMenu.getPasswordField().setText(adminPassword);
        loginMenu.handleLogin(null);

        // Pastikan pesan error direset
        assertEquals("", loginMenu.getErrorLabel().getText(),
                "Pesan error harus kosong setelah login berhasil.");
    }
}
