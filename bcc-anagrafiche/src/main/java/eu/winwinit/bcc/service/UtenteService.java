package eu.winwinit.bcc.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import eu.winwinit.bcc.entities.Utente;

public interface UtenteService extends UserDetailsService {
	
	public Utente findByUsername(String username);
	
	public Utente findByUsernameAndPassword(String username, String password);
}
