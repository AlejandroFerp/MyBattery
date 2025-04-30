/**
 * @brief Repository interface for Battery entity.
 * @details Extends JpaRepository to provide CRUD operations and JPA functionality for Battery entities.
 * The Long parameter represents the type of the primary key.
 * @since 1.0
 */
package com.batteryworkshop.repositories;

import com.batteryworkshop.models.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
}