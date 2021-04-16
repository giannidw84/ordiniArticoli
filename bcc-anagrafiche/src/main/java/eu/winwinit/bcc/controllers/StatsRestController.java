package eu.winwinit.bcc.controllers;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.winwinit.bcc.model.StatisticsRequest;
import eu.winwinit.bcc.model.StatisticsResponse;
import eu.winwinit.bcc.repository.StatisticsRepository;
import eu.winwinit.bcc.service.ClienteService;
import eu.winwinit.bcc.service.StatisticsService;

@RestController
@RequestMapping("/api/v1")
public class StatsRestController {
	
	@Autowired
	StatisticsService statisticsService;

	@Autowired
	ClienteService clienteService;

	
    private Logger log = LoggerFactory.getLogger(StatsRestController.class);

    @RequestMapping(value = "get-stats", method = RequestMethod.GET)
    public ResponseEntity<StatisticsResponse> getStats(StatisticsRequest StatisticsRequest) 
    		throws NamingException, SQLException {
    	
    	log.debug("getStats() START");

        //StatisticsResponse statisticsResponse = getMockStatisticsResponse();
        StatisticsResponse statisticsResponse = statisticsService.retrieveStatistics(StatisticsRequest);
        
    	return ResponseEntity.ok(statisticsResponse);
    }

	@SuppressWarnings("unused")
	private StatisticsResponse getMockStatisticsResponse() {
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        statisticsResponse.setTotCustomers(10);
        statisticsResponse.setTotConfirmedRecords(20);
        statisticsResponse.setTotChangedRecords(30);
        statisticsResponse.setTotNotConfirmedRecords(40);
        statisticsResponse.setTotEditedPhone(50);
        statisticsResponse.setTotEditedEmail(60);
        statisticsResponse.setTotEditedPrivacy1(70);
        statisticsResponse.setTotEditedPrivacy2(80);
        statisticsResponse.setTotEditedPrivacy3(90);
        statisticsResponse.setTotEditedPrivacy4(80);
        statisticsResponse.setTotEditedPrivacy5(70);
        statisticsResponse.setTotEditedPrivacy6(60);
        return statisticsResponse;
	}
}
