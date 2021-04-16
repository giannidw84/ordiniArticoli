package eu.winwinit.bcc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Filiale;
import eu.winwinit.bcc.entities.Utente;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.service.FilialeService;
import eu.winwinit.bcc.service.UtenteService;
import eu.winwinit.bcc.util.UtilClass;

@RestController
@RequestMapping("/api/v1")
public class BranchSearchController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	
	@Autowired
	FilialeService filialeService;
	
	@Autowired
	UtenteService utenteService;
	
	 @RequestMapping(value = "/branch-search", method = RequestMethod.GET)
	    public ResponseEntity<List<Filiale>> branchSearch(
	    		@RequestHeader(value=AuthorityRolesConstants.HEADER_STRING) String jwtToken) {
		 Set<String> rolesSetString = UtilClass.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		 List<Filiale> filialeList = new ArrayList<Filiale>();
		 if(rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			 filialeList.addAll(filialeService.findAll());
		 } else if(rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			 Utente utente = utenteService.findByUsername(jwtTokenProvider.getUsernameFromJWT(jwtToken));
			 filialeList.add(utente.getFiliali());
		 }
		 return new ResponseEntity<>(filialeList, HttpStatus.OK);
	    }
}
