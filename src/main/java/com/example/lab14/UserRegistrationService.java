package com.example.lab14;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Servicio encargado del registro de usuarios.
 * Incluye validación de datos, manejo adecuado de errores
 * y almacenamiento básico en memoria.
 */
public class UserRegistrationService {

    private static final int MIN_PASSWORD_LENGTH = 8;

    // Mensaje de error encapsulado
    private String lastErrorMessage = "";

    // Lista tipada de usuarios (modelo simple)
    private final List<User> users = new ArrayList<>();

    // Expresión regular para validar correo electrónico correctamente
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    /**
     * Obtiene el último mensaje de error registrado.
     */
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    /**
     * Registra un nuevo usuario validando sus datos.
     * @param username nombre de usuario
     * @param password contraseña
     * @param email correo electrónico
     * @return true si el registro fue exitoso, false si ocurrió un error
     */
    public boolean registerUser(String username, String password, String email) {

        // VALIDACIONES
        if (username == null || username.trim().isEmpty()) {
            lastErrorMessage = "El nombre de usuario no puede estar vacío.";
            return false;
        }

        if (password == null) {
            lastErrorMessage = "La contraseña no puede ser null.";
            return false;
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            lastErrorMessage = "La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres.";
            return false;
        }

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            lastErrorMessage = "El correo electrónico no es válido.";
            return false;
        }

        // Validar duplicados
        if (isUsernameTaken(username)) {
            lastErrorMessage = "El nombre de usuario ya está registrado.";
            return false;
        }

        if (isEmailTaken(email)) {
            lastErrorMessage = "El correo electrónico ya está registrado.";
            return false;
        }

        // GUARDAR
        try {
            saveUser(new User(username, password, email));
        } catch (Exception e) {
            lastErrorMessage = "Ocurrió un error al guardar el usuario: " + e.getMessage();
            return false;
        }

        lastErrorMessage = "Usuario registrado correctamente.";
        return true;

    }

    /**
     * Verifica si un nombre de usuario ya está registrado.
     */
    private boolean isUsernameTaken(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    /**
     * Verifica si un correo ya está registrado.
     */
    private boolean isEmailTaken(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Guarda el usuario en memoria.
     * @throws Exception si el nombre de usuario está prohibido
     */
    private void saveUser(User user) throws Exception {
        if (user.getUsername().equalsIgnoreCase("error")) {
            throw new Exception("Nombre de usuario no permitido.");
        }

        users.add(user);
    }

    /**
     * Retorna la longitud de un texto.
     * @param s texto a analizar
     * @return longitud o -1 si es null
     */
    public int countCharacters(String s) {
        return (s == null) ? -1 : s.length();
    }

    /**
     * Clase interna que modela un usuario básico.
     */
    private static class User {
        private final String username;
        private final String password; // Nota: solo para laboratorio, no almacenar así en producción
        private final String email;

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }
    }
}
