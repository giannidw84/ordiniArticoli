package eu.winwinit.bcc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.entities.Ordine;
import eu.winwinit.bcc.model.ArticoloDettaglioOrdine;
import eu.winwinit.bcc.model.BaseResponse;
import eu.winwinit.bcc.model.OrdiniResponse;
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

	@RequestMapping(value = "/ordini-all", method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> ordiniSearch(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken) throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		OrdiniResponse resp = new OrdiniResponse();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			try {
				List<Ordine> ordiniList = ordiniService.findAll();
				resp.setOrdini(ordiniList);
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

	@RequestMapping(value = "/ordine-id", method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> ordineSearch(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestParam int idOrdine)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		OrdiniResponse resp = new OrdiniResponse();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			try {
				List<Ordine> ordiniList = ordiniService.findById(idOrdine);
				resp.setOrdini(ordiniList);
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

	@RequestMapping(value = "/ordine-insert", method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> ordineInsert(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestBody Ordine ord)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		OrdiniResponse resp = new OrdiniResponse();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			try {
				List<Ordine> ordiniList = ordiniService.saveAndFlush(ord);
				resp.setOrdini(ordiniList);
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

	@RequestMapping(value = "/varia-ordine", method = RequestMethod.PATCH)
	public ResponseEntity<BaseResponse> ordiniAddArticolo(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestParam int idOrdine,
			@RequestBody Ordine ord) throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		OrdiniResponse resp = new OrdiniResponse();

		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			try {
				List<Ordine> ordiniList = ordiniService.variaOrdine(idOrdine, ord);
				resp.setOrdini(ordiniList);
				resp.updateSuccess();
			} catch (Exception e) {
				exceptionHandling(resp, e);
			}
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			resp.accessDeniedNoAdmin();
			return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
		}

	}

	@RequestMapping(value = "/ordini-delete-all", method = RequestMethod.DELETE)
	public ResponseEntity<BaseResponse> ordiniDeleteAll(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestParam int idOrdine)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		OrdiniResponse resp = new OrdiniResponse();

		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			try {
				List<Ordine> ordiniList = ordiniService.deleteOrdine(idOrdine);
				resp.setOrdini(ordiniList);
				resp.deleteSuccess();
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
