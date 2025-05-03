Feature: Control de acceso por permisos
  Como administrador o usuario normal
  Quiero que mis permisos determinen qué puedo hacer en el sistema
  Para garantizar que solo acceda a lo que me corresponde

  Scenario: Acceso permitido para administradores
    Given un usuario con nombre "admin", contraseña "1234" y tipo "SUPERVISOR"
    And el usuario está autenticado
    When intenta acceder a un método restringido
    Then se permite el acceso

  Scenario: Acceso denegado para usuarios normales
    Given un usuario con nombre "user", contraseña "abcd" y tipo "OPERATOR"
    And el usuario está autenticado
    When intenta acceder a un método restringido
    Then se deniega el acceso