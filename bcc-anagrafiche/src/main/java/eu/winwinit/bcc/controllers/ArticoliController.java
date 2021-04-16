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
import eu.winwinit.bcc.entities.Articoli;
import eu.winwinit.bcc.model.ArticoliResponse;
import eu.winwinit.bcc.model.ArticoloResponse;
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
		List<Articoli> articoliList = new ArrayList<Articoli>();
		try {
			articoliList = articoliService.findAll();
			resp.setArticoli(articoliList);
			resp.success();
		} catch (Exception e) {
			exceptionHandling(resp, e);
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/articoli-insert", method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> articoliInsert(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestBody Articoli art)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoloResponse resp = new ArticoloResponse();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			try {
				Articoli saved = articoliService.saveAndFlush(art);
				resp.setArticolo(saved);
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
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestParam int id, @RequestBody Articoli art)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoloResponse resp = new ArticoloResponse();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			try {
				Articoli changed = articoliService.patch(id,art.getPrezzo());
				resp.setArticolo(changed);
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

	@RequestMapping(value = "/articoli-delete", method = RequestMethod.DELETE)
	public ResponseEntity<BaseResponse> articoliDelete(
			@RequestHeader(value = AuthorityRolesConstants.HEADER_STRING) String jwtToken, @RequestParam int id)
			throws Exception {
		Set<String> rolesSetString = UtilClass
				.fromGrantedAuthorityToStringSet(jwtTokenProvider.getRolesFromJWT(jwtToken));
		ArticoloResponse resp = new ArticoloResponse();
		if (rolesSetString.contains(AuthorityRolesConstants.ROLE_ADMIN)) {
			try {
				Articoli deleted = articoliService.delete(id);
				resp.setArticolo(deleted);
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
