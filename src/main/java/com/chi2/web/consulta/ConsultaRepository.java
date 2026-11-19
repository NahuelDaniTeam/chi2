package com.chi2.web.consulta;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	List<Consulta> findByEstadoAviso(EstadoAviso estadoAviso);

	@Modifying
	@Transactional
	@Query("delete from Consulta c where c.creadaEn < :limite")
	int borrarCreadasAntesDe(@Param("limite") Instant limite);

}
