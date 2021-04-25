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
import eu.winwinit.bcc.entities.Articolo;
import eu.winwinit.bcc.model.ArticoliResponse;
import eu.winwinit.bcc.model.BaseResponse;
import eu.winwinit.bcc.security.JwtTokenProvider;
import eu.winwinit.bcc.service.ArticoliService;
import eu.winwinit.bcc.util.UtilClass;

@RestController
@RequestMapping("/api/v2")
public class ArticoliController {

	@Autowired
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

	@Autowired
	ArticoliService articoliService;

	@RequestMapping(value = "/articoli-all", method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> articoliSearch(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken) throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoliResponse resp = new ArticoliResponse();
		List<Articolo> articoliList = new ArrayList<Articolo>();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			try {
				articoliList = articoliService.findAll();
				resp.setArticoli(articoliList);
				resp.success();
				resp.setArticoli(articoliList);
			} catch (Exception e) {
				exceptionHandling(resp, e);
			}
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			resp.accessDeniedNoAdmin();
			return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/articoli-search-filter", method = RequestMethod.GET)
	public ResponseEntity<BaseResponse> articoliSearchFilter(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestParam String descArticolo, @RequestParam String categoria) throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoliResponse resp = new ArticoliResponse();
		List<Articolo> articoliList = new ArrayList<Articolo>();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_USER)) {
			try {
				articoliList = articoliService.findByFilter(descArticolo, categoria);
				if (articoliList.isEmpty()) {
					resp.noDataFound();
				} else {
					resp.success();
				}
				resp.setArticoli(articoliList);

			} catch (Exception e) {
				exceptionHandling(resp, e);
			}
			return new ResponseEntity<>(resp, HttpStatus.OK);
		} else {
			resp.accessDeniedNoAdmin();
			return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/articoli-insert", method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> articoliInsert(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken,
			@RequestBody Articolo articoloInsert) throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoliResponse resp = new ArticoliResponse();
		List<Articolo> articoliList = new ArrayList<Articolo>();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			try {
				articoliList = articoliService.saveAndFlush(articoloInsert);
				resp.setArticoli(articoliList);
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

	@RequestMapping(value = "/articoli-update", method = RequestMethod.PATCH)
	public ResponseEntity<BaseResponse> articoliUpdate(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestParam int idArticolo,
			@RequestBody Articolo articoloDaVariare) throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoliResponse resp = new ArticoliResponse();
		List<Articolo> articoliList = new ArrayList<Articolo>();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			try {
				articoliList = articoliService.patch(idArticolo, articoloDaVariare.getPrezzo());
				resp.setArticoli(articoliList);
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

	@RequestMapping(value = "/articoli-delete", method = RequestMethod.DELETE)
	public ResponseEntity<BaseResponse> articoliDelete(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestParam int idArticolo)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoliResponse resp = new ArticoliResponse();
		List<Articolo> articoliList = new ArrayList<Articolo>();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			try {
				articoliList = articoliService.delete(idArticolo);
				resp.setArticoli(articoliList);
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
