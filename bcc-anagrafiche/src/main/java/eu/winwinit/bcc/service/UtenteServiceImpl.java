package eu.winwinit.bcc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Filiale;
import eu.winwinit.bcc.entities.RuoloUtente;
import eu.winwinit.bcc.entities.Utente;
import eu.winwinit.bcc.repository.FilialeRepository;
import eu.winwinit.bcc.repository.RuoloRepository;
import eu.winwinit.bcc.repository.UtenteRepository;

@Service("utenteService")
public class UtenteServiceImpl implements UtenteService{
	
	@Autowired
	private UtenteRepository utenteRepository;	
	
	@Autowired
	private FilialeRepository filialeRepository;	
	
	@Autowired
	private RuoloRepository ruoloRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Utente findByUsername(String username) {
		return utenteRepository.findByUsername(username);
	}
	
	public Utente findByUsernameAndPassword(String username, String password) {
		String encPassword = bCryptPasswordEncoder.encode(password);
		return utenteRepository.findByUsernameAndPassword(username, encPassword);
	}

	public void salvaNuovoUtente(Utente utente, String ruolo, String cabFiliale) {
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setStatoAttivo(Boolean.TRUE);
		utente.setCambioPassword(Boolean.TRUE);
		RuoloUtente userRole = ruoloRepository.findByRuolo(ruolo);
		utente.setRuoliUtenti(userRole);
		Filiale filiale = filialeRepository.findByCab(cabFiliale);
		utente.setFiliali(filiale);
		utenteRepository.save(utente);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Utente user = utenteRepository.findByUsername(username);
		if (user == null){
			throw new UsernameNotFoundException("Username non valida.");
		}
		if(!user.getStatoAttivo()) {
			throw new DisabledException("Utente disabilitato.");
		}
		Collection<String> roles = mapRoles(user.getRuoliUtenti());
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(),
				mapRolesToAuthorities(roles));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles){
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
	}
	
	private Collection<String> mapRoles(RuoloUtente ruoloUtente){
		switch (ruoloUtente.getRuolo()) {
		case AuthorityRolesConstants.USER : return Arrays.asList(AuthorityRolesConstants.ROLE_USER); 
		case AuthorityRolesConstants.ADMIN : return Arrays.asList(AuthorityRolesConstants.ROLE_USER, AuthorityRolesConstants.ROLE_ADMIN); 
		default: return new ArrayList<String>();
		}
	}

}
