Feature: Autenticación de usuarios
  Como un usuario
  Quiero iniciar sesión en el sistema
  Para acceder a las funcionalidades autorizadas

  Scenario: Inicio de sesión exitoso
    Given un usuario con nombre "admin" y contraseña "1234"
    When inicia sesión con nombre "admin" y contraseña "1234"
    Then el inicio de sesión es exitoso

  Scenario: Inicio de sesión fallido
    Given un usuario con nombre "admin" y contraseña "1234"
    When inicia sesión con nombre "admin" y contraseña "incorrecta"
    Then el inicio de sesión falla