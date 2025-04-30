/**
 * @brief Repository interface for repair reports.
 * @details Extends JpaRepository to provide CRUD operations and JPA functionality for RepairReport entities.
 * The Long parameter represents the type of the primary key.
 * @since 1.0
 */
package com.batteryworkshop.repositories;

import com.batteryworkshop.models.RepairReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteRepository extends JpaRepository<RepairReport, Long> {
}