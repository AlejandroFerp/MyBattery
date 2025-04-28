// BatteryRepository.java
package com.batteryworkshop.repositories;

import com.batteryworkshop.models.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
}
