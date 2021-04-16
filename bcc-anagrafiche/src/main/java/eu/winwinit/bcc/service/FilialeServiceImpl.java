package eu.winwinit.bcc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.entities.Filiale;
import eu.winwinit.bcc.repository.FilialeRepository;

@Service("filialeService")
public class FilialeServiceImpl implements FilialeService{

	@Autowired
	private FilialeRepository filialeRepository;
	
	@Override
	public List<Filiale> findAll() {
		return filialeRepository.findAll();
	}
	
}
