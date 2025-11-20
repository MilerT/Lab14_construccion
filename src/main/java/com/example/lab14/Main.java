package com.example.lab14;

public class Main {

    public static void main(String[] args) {

        UserRegistrationService service = new UserRegistrationService();

        // Caso 1: contraseña muy corta
        boolean case1 = service.registerUser("juan", "123", "juan@correo.com");
        System.out.println("Caso 1 (contraseña corta): " + case1);
        System.out.println("Mensaje: " + service.getLastErrorMessage());
        System.out.println();

        // Caso 2: username null
        boolean case2 = service.registerUser(null, "12345678", "correo@dominio.com");
        System.out.println("Caso 2 (username null): " + case2);
        System.out.println("Mensaje: " + service.getLastErrorMessage());
        System.out.println();

        // Caso 3: fuerza excepción artificial
        boolean case3 = service.registerUser("error", "12345678", "error@correo.com");
        System.out.println("Caso 3 (excepción artificial): " + case3);
        System.out.println("Mensaje: " + service.getLastErrorMessage());
        System.out.println();

        // Caso 4: registro válido
        boolean case4 = service.registerUser("maria", "contrasenaSegura123", "maria@correo.com");
        System.out.println("Caso 4 (registro válido): " + case4);
        System.out.println("Mensaje: " + service.getLastErrorMessage());
    }
}
