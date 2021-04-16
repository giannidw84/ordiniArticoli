package eu.winwinit.bcc.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.Utente;
import eu.winwinit.bcc.entities.VariazioneCliente;
import eu.winwinit.bcc.model.MarkAsEditedRequest;
import eu.winwinit.bcc.repository.VariazioneClienteRepository;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.service.ClienteService;
import eu.winwinit.bcc.service.UtenteService;

@RestController
@RequestMapping("/api/v1")
public class CustomerMarkAsEditedController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	VariazioneClienteRepository variazioneClienteRepository;
	
	@RequestMapping(value = "/customer-mark-as-edited", method = RequestMethod.POST)
    public ResponseEntity<String> customerMarkAsEdited(
    		@RequestHeader(value=AuthorityRolesConstants.HEADER_STRING) String jwtToken,
    		@RequestBody MarkAsEditedRequest markAsEditedRequest) {
	 
	 Utente utente = utenteService.findByUsername(jwtTokenProvider.getUsernameFromJWT(jwtToken));
	 Cliente cliente = clienteService.findById(markAsEditedRequest.getId()).get();
	 HashMap<String, Boolean> editedHashMap = markAsEditedRequest.fieldsToHashMap(markAsEditedRequest);
	 
	 for(Map.Entry<String, Boolean> entrySet : editedHashMap.entrySet()) {
		 String key = entrySet.getKey();
		 Boolean value = entrySet.getValue();
		 if(value.booleanValue() == true) {
			 VariazioneCliente variazioneCliente = new VariazioneCliente();
			 variazioneCliente.setUtenti(utente);
			 variazioneCliente.setClienti(cliente);
			 variazioneCliente.setCampo(key);
			 variazioneClienteRepository.save(variazioneCliente);
		 }
	 }
	 cliente.setConfermato(true);
	 clienteService.save(cliente);
	 return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
    }
}
