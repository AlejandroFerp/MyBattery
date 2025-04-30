/**
 * @brief Repository interface for repair items
 * @details Extends JpaRepository to provide CRUD operations and JPA functionality for ItemReparacion entities.
 * The Long parameter represents the type of the primary key.
 * @since 1.0
 */
package com.batteryworkshop.repositories;

import com.batteryworkshop.models.ItemReparacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReparacionRepository extends JpaRepository<ItemReparacion, Long> {
}