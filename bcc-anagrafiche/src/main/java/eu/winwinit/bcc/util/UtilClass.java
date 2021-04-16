package eu.winwinit.bcc.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import eu.winwinit.bcc.constants.ClienteConstants;
import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.VariazioneCliente;
import eu.winwinit.bcc.model.CustomerConfirmedListResponse;
import eu.winwinit.bcc.repository.FilialeRepository;

@Component
public class UtilClass {

	@Autowired
	FilialeRepository filialeRepository;
	
	public static Set<String> fromGrantedAuthorityToStringSet(Set<GrantedAuthority> grantedAuthoritySet) {
		Set<String> rolesSetString = new HashSet<>();
		for (GrantedAuthority grantedAuthority: grantedAuthoritySet) {
			 rolesSetString.add(grantedAuthority.toString());
		}
		return rolesSetString;
	}
	
	public CustomerConfirmedListResponse toCustomerConfirmedListResponse(Cliente cliente, List<VariazioneCliente> listVariazioneCliente) {
		CustomerConfirmedListResponse customerConfirmedListResponse = new CustomerConfirmedListResponse();
		customerConfirmedListResponse = valorizeEditedField(listVariazioneCliente);
		customerConfirmedListResponse.setId(cliente.getId());
		customerConfirmedListResponse.setCodice(cliente.getCodice());
		customerConfirmedListResponse.setNag(cliente.getNag());
		customerConfirmedListResponse.setData(cliente.getLastModify());
		customerConfirmedListResponse.setFiliale(cliente.getFiliali().getNome());
		customerConfirmedListResponse.setEditedFieldsSummary(editedFieldsSummaryCalculator(customerConfirmedListResponse));
		return customerConfirmedListResponse;
	}
	
	
	private CustomerConfirmedListResponse valorizeEditedField(List<VariazioneCliente> listVariazioneCliente) {
		CustomerConfirmedListResponse customerConfirmedListResponse = new CustomerConfirmedListResponse();
		for (VariazioneCliente variazioneCliente : listVariazioneCliente) {
			switch(variazioneCliente.getCampo()) {
				case ClienteConstants.TELEFONO:
					customerConfirmedListResponse.setTelefonoEdited(true);
					break;
				case ClienteConstants.EMAIL:
					customerConfirmedListResponse.setEmailEdited(true);
					break;
				case ClienteConstants.P1:
					customerConfirmedListResponse.setP1Edited(true);
					break;
				case ClienteConstants.P2:
					customerConfirmedListResponse.setP2Edited(true);
					break;	
				case ClienteConstants.P3:
					customerConfirmedListResponse.setP3Edited(true);
					break;	
				case ClienteConstants.P4:
					customerConfirmedListResponse.setP4Edited(true);
					break;	
				case ClienteConstants.P5:
					customerConfirmedListResponse.setP5Edited(true);
					break;	
				case ClienteConstants.P6:
					customerConfirmedListResponse.setP6Edited(true);
					break;		
				case ClienteConstants.FIRMA:
					customerConfirmedListResponse.setFirmaEdited(true);
					break;		
			}
		}
		
		return customerConfirmedListResponse;
	}
	
	private String editedFieldsSummaryCalculator(CustomerConfirmedListResponse customerConfirmedListResponse) {
		String editedFieldSummary = "";
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getTelefonoEdited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getEmailEdited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getP1Edited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getP2Edited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getP3Edited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getP4Edited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getP5Edited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getP6Edited());
		editedFieldSummary = editedFieldSummary + setZeroOrOne(customerConfirmedListResponse.getFirmaEdited());
		return editedFieldSummary;
	}
	
	private String setZeroOrOne(boolean booleanValue) {
		if(booleanValue) {
			return "1";
		} else {
			return "0";
		}
	}
	
}
