import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

public class LoginMenuTest {

    @Mock
    private LoginMenu loginMenu;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mock objects
    }

    @Test
    public void testHandleLogin() {
        // Buat objek ActionEvent palsu
        ActionEvent mockEvent = mock(ActionEvent.class);

        // Setel perilaku dari mock loginMenu
        when(loginMenu.getUsername()).thenReturn("admin");
        when(loginMenu.getPassword()).thenReturn("123");

        // Panggil metode handleLogin dengan ActionEvent palsu
        loginMenu.handleLogin(mockEvent);

        // Verifikasi bahwa handleLogin telah dipanggil
        verify(loginMenu).handleLogin(mockEvent);
    }
}
