package eu.winwinit.bcc.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Ordini;
import eu.winwinit.bcc.model.BaseResponse;
import eu.winwinit.bcc.model.OrdineResponse;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.service.ArticoliService;
import eu.winwinit.bcc.service.OrdiniService;
import eu.winwinit.bcc.util.UtilClass;

@RestController
@RequestMapping("/api/v2")
public class OrdiniController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

	@Autowired
	ArticoliService articoliService;

	@Autowired
	OrdiniService ordiniService;

	@RequestMapping(value = "/ordine-insert", method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> ordineInsert(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestBody Ordini ord)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		OrdineResponse resp = new OrdineResponse();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			try {
				Ordini saved = ordiniService.saveAndFlush(ord);
				resp.setOrdine(saved);
				resp.success();
			} catch (Exception e) {
				exceptionHandling(resp, e);
			}
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			resp.accessDeniedNoAdmin();
			return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
		}

	}
	
	private void exceptionHandling(BaseResponse resp, Exception ex) {
		resp.error();
		String cause = "";

		if (ex.getCause() != null) {
			cause = " cause= " + ex.getCause().toString();
		}
		resp.getResult().setExceptionDetails(ex.getMessage() + cause);
	}

}
