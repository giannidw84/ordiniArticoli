package eu.winwinit.bcc.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.VariazioneCliente;
import eu.winwinit.bcc.model.CustomerConfirmedListResponse;
import eu.winwinit.bcc.repository.VariazioneClienteRepository;
import eu.winwinit.bcc.service.ClienteService;
import eu.winwinit.bcc.util.UtilClass;

@RestController
@RequestMapping("/api/v1")
public class CustomerConfirmedListController {

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	VariazioneClienteRepository variazioneClienteRepository;
	
	@RequestMapping(value = "/customer-confirmed-list", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerConfirmedListResponse>> getCustomerConfirmedList(
    		@RequestHeader(value=AuthorityRolesConstants.HEADER_STRING) String jwtToken,
    		@RequestParam(value="branch") String branch,
    		@RequestParam(value="startDate") Date startDate,
    		@RequestParam(value="endDate") Date endDate
    		) {
		UtilClass utilClass = new UtilClass();
		List<Cliente> clienteList = clienteService.findByDateAndConfermato(startDate, endDate);
		List<VariazioneCliente> listVariazioneCliente = new ArrayList<VariazioneCliente>();
		List<CustomerConfirmedListResponse> variazioneClienteListResponse = new ArrayList<CustomerConfirmedListResponse>();
		
		for(Cliente cliente : clienteList) {
			listVariazioneCliente.addAll(variazioneClienteRepository.findAllByClienti(cliente));
			variazioneClienteListResponse.add(utilClass.toCustomerConfirmedListResponse(cliente, listVariazioneCliente)); 
			listVariazioneCliente.clear();
		}
		
		return new ResponseEntity<>(variazioneClienteListResponse, HttpStatus.OK);
	}
    		
    		
}
