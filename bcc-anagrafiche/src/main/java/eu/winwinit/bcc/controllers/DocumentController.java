package eu.winwinit.bcc.controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import eu.winwinit.bcc.model.DocumentFile;

@RestController
@RequestMapping("/api/v1")
public class DocumentController {
	
	@RequestMapping(value = "document", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	@Secured({AuthorityRolesConstants.ROLE_USER, AuthorityRolesConstants.ROLE_ADMIN})
	public @ResponseBody DocumentFile getFile() throws IOException {
		InputStream in = getClass().getResourceAsStream("/WEB-INF/classes/Document.pdf");
		//return IOUtils.toByteArray(in);		
		
		byte[] fileBytes = IOUtils.toByteArray(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(fileBytes);
        fileBytes = baos.toByteArray();
        List<Byte> adjustedFile = new ArrayList<>();
        for (byte fileByte : fileBytes) {
            adjustedFile.add(fileByte);
        }
        
        DocumentFile documentFile = new DocumentFile();
        documentFile.setFilename("Document.pdf");
        documentFile.setFilebytes(adjustedFile);
        return documentFile;
	}

}
