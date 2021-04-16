package eu.winwinit.bcc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.VariazioneCliente;
import eu.winwinit.bcc.utility.VariazioneGroupedInfo;

@Repository("variazioneClienteRepository")
public interface VariazioneClienteRepository extends JpaRepository<VariazioneCliente, Integer> {
	
	@Query("SELECT new eu.winwinit.bcc.utility.VariazioneGroupedInfo(\r\n" +
			"v.campo, " 
			+ "COUNT(v)) "
			+ "FROM VariazioneCliente v "
			+ "group by v.campo")
	public List<VariazioneGroupedInfo> retrieveVariazioniStatistics(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	public List<VariazioneCliente> findAllByClienti(Cliente cliente);
	
}
