Feature: Gestión de Informes de Reparación
  Como técnico del taller
  Quiero poder crear y gestionar informes de reparación
  Para mantener un registro de las reparaciones realizadas

  Scenario: Crear un nuevo informe de reparación
    Given el cliente tiene una batería con número de serie "BAT-2024-001"
    When creo un nuevo informe de reparación con descripción "Cambio de celdas dañadas"
    Then el informe se crea correctamente
    And el estado del informe es "PENDIENTE"

  Scenario: Agregar items al informe de reparación
    Given existe un informe de reparación para la batería "BAT-2024-001"
    When agrego un item "Cambio de terminales" con precio 25.00
    And agrego un item "Nuevas celdas" con precio 150.00
    Then el informe tiene 2 items
    And el precio total del informe es 175.00