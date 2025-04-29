// ReporteRepository.java
package com.batteryworkshop.repositories;

import com.batteryworkshop.models.RepairReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReporteRepository extends JpaRepository<RepairReport, Long> {
}
