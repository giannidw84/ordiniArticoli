package eu.winwinit.bcc.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.repository.ClienteRepository;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente findByNag(String nag) {
		return clienteRepository.findByNag(nag);
	}

	@Override
	public List<Cliente> findByNome(String nome) {
		return clienteRepository.findByNome(nome);
	}

	@Override
	public Optional<Cliente> findById(Integer id) {
		return clienteRepository.findById(id);
	}

	@Override
	public void save(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	public List<Cliente> findByDateAndConfermato(Date startDate, Date endDate) {
		return clienteRepository.findByDateAndConfermato(startDate, endDate);
	}

	@Override
	public List<Cliente> findByBranchAndNagAndCustomerNameAndBirthDate(Integer branch, String nag, String customerName, String birthDate) {

		if(branch != null && nag != null && customerName != null && birthDate != null)
			return clienteRepository.findByBranchAndNagAndCustomerNameAndBirthDate(branch, nag, customerName, birthDate);
		else if(branch != null && nag != null && customerName != null)
			return clienteRepository.findByBranchAndNagAndCustomerName(branch, nag, customerName);
		else if(branch != null && nag != null)
			return clienteRepository.findByBranchAndNag(branch, nag);
		else
			return Arrays.asList(clienteRepository.findByNag(nag));
	}

}
