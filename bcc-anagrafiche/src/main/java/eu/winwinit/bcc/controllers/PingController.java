package eu.winwinit.bcc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;

@RestController
@RequestMapping("/api/v1")
public class PingController {
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("PING", HttpStatus.OK);
    }

    @RequestMapping(value = "/userPing", method = RequestMethod.GET)
    @Secured({AuthorityRolesConstants.ROLE_USER, AuthorityRolesConstants.ROLE_ADMIN})
    public ResponseEntity<String> userPing() {
        return ResponseEntity.ok().body("Ping for users");
    }

    @RequestMapping(value = "/adminPing", method = RequestMethod.GET)
    @Secured({AuthorityRolesConstants.ROLE_ADMIN})
    public ResponseEntity<String> adminPing() {
        return ResponseEntity.ok().body("Ping for admins");
    }
}
