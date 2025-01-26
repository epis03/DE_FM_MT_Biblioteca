package main;

import main.DatabaseManager;
import main.GestioneUtenti;
import main.HashPasswords;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestioneUtentiTest {

    private GestioneUtenti gestioneUtenti;

    @BeforeEach
    void setup() {
        gestioneUtenti = new GestioneUtenti();
    }

    @Test
    void testRegistraUtente() throws Exception {
        // Mock delle classi statiche e delle connessioni al database
        try (MockedStatic<DatabaseManager> mockedDbManager = Mockito.mockStatic(DatabaseManager.class);
             MockedStatic<HashPasswords> mockedHashPasswords = Mockito.mockStatic(HashPasswords.class)) {

            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

            // Configura i comportamenti dei metodi mockati
            mockedDbManager.when(DatabaseManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            String email = "utente@example.com";
            char[] password = "Password123".toCharArray();
            mockedHashPasswords.when(() -> HashPasswords.hashPassword(new String(password)))
                               .thenReturn("hashedPassword");

            // Testa il metodo
            boolean result = gestioneUtenti.registraUtente(email, password);

            // Verifica
            assertTrue(result, "La registrazione dovrebbe avere successo.");
            verify(mockPreparedStatement).setString(1, email);
            verify(mockPreparedStatement).setString(2, "hashedPassword");
            verify(mockPreparedStatement).setString(3, "utente");
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void testAutenticaUtente() throws Exception {
        // Mock delle classi statiche e delle connessioni al database
        try (MockedStatic<DatabaseManager> mockedDbManager = Mockito.mockStatic(DatabaseManager.class);
             MockedStatic<HashPasswords> mockedHashPasswords = Mockito.mockStatic(HashPasswords.class)) {

            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            // Configura i comportamenti dei metodi mockati
            mockedDbManager.when(DatabaseManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            String email = "utente@example.com";
            char[] password = "Password123".toCharArray();
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getString("password")).thenReturn("hashedPassword");
            mockedHashPasswords.when(() -> HashPasswords.verificaPassword(new String(password), "hashedPassword"))
                               .thenReturn(true);

            // Testa il metodo
            boolean result = gestioneUtenti.autenticaUtente(email, password);

            // Verifica
            assertTrue(result, "L'autenticazione dovrebbe avere successo.");
            verify(mockPreparedStatement).setString(1, email);
        }
    }

    @Test
    void testEmailEsiste() throws Exception {
        // Mock delle classi statiche e delle connessioni al database
        try (MockedStatic<DatabaseManager> mockedDbManager = Mockito.mockStatic(DatabaseManager.class)) {

            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            // Configura i comportamenti dei metodi mockati
            mockedDbManager.when(DatabaseManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            String email = "utente@example.com";
            when(mockResultSet.next()).thenReturn(true);

            // Testa il metodo
            boolean result = gestioneUtenti.emailEsiste(email);

            // Verifica
            assertTrue(result, "L'email dovrebbe esistere.");
            verify(mockPreparedStatement).setString(1, email);
        }
    }

    @Test
    void testChangeUserPassword() throws Exception {
        // Mock delle classi statiche e delle connessioni al database
        try (MockedStatic<DatabaseManager> mockedDbManager = Mockito.mockStatic(DatabaseManager.class);
             MockedStatic<HashPasswords> mockedHashPasswords = Mockito.mockStatic(HashPasswords.class)) {

            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

            // Configura i comportamenti dei metodi mockati
            mockedDbManager.when(DatabaseManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            String email = "utente@example.com";
            char[] newPassword = "NewPassword123".toCharArray();
            mockedHashPasswords.when(() -> HashPasswords.hashPassword(new String(newPassword)))
                               .thenReturn("newHashedPassword");
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            // Testa il metodo
            boolean result = gestioneUtenti.changeUserPassword(email, newPassword);

            // Verifica
            assertTrue(result, "La password dovrebbe essere aggiornata.");
            verify(mockPreparedStatement).setString(1, "newHashedPassword");
            verify(mockPreparedStatement).setString(2, email);
        }
    }

    @Test
    void testVerificaPrestitoScaduto() throws Exception {
        // Mock delle classi statiche e delle connessioni al database
        try (MockedStatic<DatabaseManager> mockedDbManager = Mockito.mockStatic(DatabaseManager.class)) {

            Connection mockConnection = mock(Connection.class);
            PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
            ResultSet mockResultSet = mock(ResultSet.class);

            // Configura i comportamenti dei metodi mockati
            mockedDbManager.when(DatabaseManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            String email = "utente@example.com";
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("prestitoScaduto")).thenReturn(1);

            // Testa il metodo
            boolean result = GestioneUtenti.verificaPrestitoScaduto(email);

            // Verifica
            assertTrue(result, "Il prestito scaduto dovrebbe essere segnalato.");
            verify(mockPreparedStatement).setString(1, email);
        }
    }
}