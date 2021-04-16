package eu.winwinit.bcc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.utility.ClienteGroupedInfo;

@Repository("statisticsRepository")
public interface StatisticsRepository extends JpaRepository<Cliente, Integer> {

	@Query("SELECT new eu.winwinit.bcc.utility.ClienteGroupedInfo(\r\n" + 
			"c.confermato, " 
			+ "COUNT(c)) "
			+ "FROM Cliente c "
			+ "group by c.confermato")
	public List<ClienteGroupedInfo> retrieveConfermatoStatistics(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}
