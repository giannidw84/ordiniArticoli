package eu.winwinit.bcc.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.winwinit.bcc.constants.ClienteConstants;
import eu.winwinit.bcc.model.StatisticsRequest;
import eu.winwinit.bcc.model.StatisticsResponse;
import eu.winwinit.bcc.repository.StatisticsRepository;
import eu.winwinit.bcc.repository.VariazioneClienteRepository;
import eu.winwinit.bcc.utility.ClienteGroupedInfo;
import eu.winwinit.bcc.utility.VariazioneGroupedInfo;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	StatisticsRepository statisticsRepository;
	
	@Autowired
	VariazioneClienteRepository variazioneClienteRepository;
	
	@Override
	public StatisticsResponse retrieveStatistics(StatisticsRequest statisticsRequest) {
		Date startDate = null;
		Date endDate = null;
		
		StatisticsResponse response = new StatisticsResponse();
		
		List<ClienteGroupedInfo> listConfermati = statisticsRepository.retrieveConfermatoStatistics(startDate, endDate);
		List<VariazioneGroupedInfo> listVariazioni = variazioneClienteRepository.retrieveVariazioniStatistics(startDate, endDate);
		
		Iterator<ClienteGroupedInfo> it = listConfermati.iterator();
		
		while (it.hasNext()) {
			ClienteGroupedInfo record = it.next();
			if (record.getConfermato()){
				response.setTotConfirmedRecords(record.getTot());
			} else if (!record.getConfermato()){
				response.setTotNotConfirmedRecords(record.getTot());
			}
		}
		
		response.setTotCustomers(response.getTotConfirmedRecords() + response.getTotNotConfirmedRecords());
		
		Iterator<VariazioneGroupedInfo> j = listVariazioni.iterator();
		
		while (j.hasNext()) {
			VariazioneGroupedInfo record = j.next();
			if (ClienteConstants.TELEFONO.equals(record.getCampo())){
				response.setTotEditedPhone(record.getTot());
			} else if (ClienteConstants.EMAIL.equals(record.getCampo())){
				response.setTotEditedEmail(record.getTot());
			} else if (ClienteConstants.P1.equals(record.getCampo())){
				response.setTotEditedPrivacy1(record.getTot());
			} else if (ClienteConstants.P2.equals(record.getCampo())){
				response.setTotEditedPrivacy2(record.getTot());
			} else if (ClienteConstants.P3.equals(record.getCampo())){
				response.setTotEditedPrivacy3(record.getTot());
			} else if (ClienteConstants.P4.equals(record.getCampo())){
				response.setTotEditedPrivacy4(record.getTot());
			} else if (ClienteConstants.P5.equals(record.getCampo())){
				response.setTotEditedPrivacy5(record.getTot());
			} else if (ClienteConstants.P6.equals(record.getCampo())){
				response.setTotEditedPrivacy6(record.getTot());
			} else if (ClienteConstants.FIRMA.equals(record.getCampo())){
				response.setTotEditedFirma(record.getTot());
			}
		}
		
		return response;
	}
}
