package eu.winwinit.bcc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import eu.winwinit.bcc.entities.Cliente;

public interface ClienteService {
	
	public Cliente findByNag(String nag);

	public List<Cliente> findByNome(String nome);
	
	public Optional<Cliente> findById(Integer id);

	public void save(Cliente cliente);
	
	public List<Cliente> findByDateAndConfermato(Date startDate, Date endDate);
	
	public List<Cliente> findByBranchAndNagAndCustomerNameAndBirthDate(Integer branch, String nag, String customerName, String birthDate);

}
