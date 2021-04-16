package eu.winwinit.bcc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.winwinit.bcc.entities.Cliente;

@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Cliente findByNag(String nag);
	
	@Query("select c from Cliente c where nome like ?1%")
	List<Cliente> findByNome(String nome); 
	
	@Query(value = "FROM Cliente c "
				 + "WHERE last_modify BETWEEN :startDate AND :endDate "
				 + "AND confermato = 1")
	public List<Cliente> findByDateAndConfermato(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	@Query(value = "select c "
				 + "FROM Cliente c "
				 + "WHERE filiale = :branch "
				 + "AND nag LIKE :nag% "
				 + "AND nome LIKE :customerName% "
				 + "AND DATE_FORMAT(data_nascita,'%Y%m%d') = :birthDate")
	public List<Cliente> findByBranchAndNagAndCustomerNameAndBirthDate(	@Param("branch") Integer branch,
																		@Param("nag") String nag,
																		@Param("customerName") String customerName,
																		@Param("birthDate") String birthDate );
	 
	@Query(value = "select c "
			 + "FROM Cliente c "
			 + "WHERE filiale = :branch "
			 + "AND nag LIKE :nag% "
			 + "AND nome LIKE :customerName% ")
	public List<Cliente> findByBranchAndNagAndCustomerName(	@Param("branch")Integer branch,
																	@Param("nag")String nag,
																	@Param("customerName")String customerName);
	
	
	@Query(value = "select c "
			 + "FROM Cliente c "
			 + "WHERE filiale = :branch "
			 + "AND nag LIKE :nag% ")
	public List<Cliente> findByBranchAndNag(@Param("branch")Integer branch,
											@Param("nag")String nag);
	
	
	@Query(value = "select c "
			 + "FROM Cliente c "
			 + "WHERE nag LIKE :nag% ")
	public List<Cliente> findByNagLike(String nag);
}
