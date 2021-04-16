package eu.winwinit.bcc.service;

import eu.winwinit.bcc.model.StatisticsRequest;
import eu.winwinit.bcc.model.StatisticsResponse;

public interface StatisticsService{
	
	public StatisticsResponse retrieveStatistics(StatisticsRequest statisticsRequest);
}
