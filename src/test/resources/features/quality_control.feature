Feature: Control de calidad de baterías

  Scenario: La batería cumple con los estándares de calidad
    Given una batería con voltaje 12.5, amperaje 1.2, carga 1 y descarga 1
    When realizo el control de calidad con voltaje 12.5, amperaje 1.2, carga 1 y descarga 1
    Then el resultado de calidad debe ser true

  Scenario: La batería no cumple con los estándares de calidad
    Given una batería con voltaje 10.5, amperaje 0.8, carga 1 y descarga 1
    When realizo el control de calidad con voltaje 10.5, amperaje 0.8, carga 0 y descarga 1
    Then el resultado de calidad debe ser false
