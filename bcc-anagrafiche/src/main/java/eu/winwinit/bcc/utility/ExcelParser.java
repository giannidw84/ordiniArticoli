package eu.winwinit.bcc.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import eu.winwinit.bcc.entities.Cliente;
import eu.winwinit.bcc.entities.Filiale;

public class ExcelParser {

	public static Map<Integer, Filiale> filiali = null;
	public static Integer idFiliale = 1;

	public static void caricaClienti(File excel, PrintWriter out) throws FileNotFoundException, IOException{

		FileInputStream fis1 = null;
		XSSFWorkbook inWorkbook = null;

		try {
			fis1 = new FileInputStream(excel);
			inWorkbook = new XSSFWorkbook(fis1);
			XSSFSheet sheet = inWorkbook.getSheetAt(0);

			DataFormatter objDefaultFormat = new DataFormatter();
			FormulaEvaluator objFormulaEvaluator = inWorkbook.getCreationHelper().createFormulaEvaluator();

			Iterator<Row> rowIterator = sheet.iterator();    
			// Estraggo la riga di intestazione
			rowIterator.next();   

			List<Cliente> listaClienti = new ArrayList<Cliente>();

			int emptyCount = 0;
			while(rowIterator.hasNext() && emptyCount < 2) {
				// Parto dalla riga 2 con i dati
				Row row = rowIterator.next();

				//				0	NAG
				//				1	NOME
				//				2	TELEFONO
				//				3	E-MAIL SOCIO
				//				4	FILIALE
				//				5	DESCRIZIONE FILIALE
				//				6	DATA DI NASCITA
				//				7	CONSENSO GESTIONE RAPPORTO
				//				8	CONSENSO RILEVAZIONE GRADO
				//				9	CONSENSO PROMOZIONE DI INIZ.
				//				10	CONSENSO INVIO DI PUBBL.
				//				11	CONSENSO MAIL RILEVAZIONE GRADO
				//				12	CONSENSO MAIL PROMOZIONE DI INIZ.
				//				13	CONSENSO MAIL INVIO DI PUBBL.

				Cell cellValue = row.getCell(0);
				objFormulaEvaluator.evaluate(cellValue); // This will evaluate the cell, And any type of cell will return string value

				String nag = objDefaultFormat.formatCellValue(cellValue,objFormulaEvaluator);	

				if(StringUtils.isEmpty(nag)) {
					emptyCount++;
					continue;
				}	

				String nome = row.getCell(1).getStringCellValue();
				cellValue = row.getCell(2);
				objFormulaEvaluator.evaluate(cellValue);
				String telefono = objDefaultFormat.formatCellValue(cellValue,objFormulaEvaluator);
				String email = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

				Date dataNascita = row.getCell(6).getDateCellValue();
				String p1 = row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String p2 = row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String p3 = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String p4 = row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String p5 = row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String p6 = row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				String p7 = row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

				Integer codFiliale = (int)row.getCell(4).getNumericCellValue();	

				Cliente cliente = new Cliente();
				cliente.setNag(nag);
				cliente.setNome(nome);
				cliente.setTelefono(telefono);
				cliente.setEmail(email);
				cliente.setDataNascita(dataNascita);
				cliente.setFirma(Boolean.FALSE);
				cliente.setConfermato(Boolean.FALSE);
				cliente.setP1(evaluate(p1));
				cliente.setP2(evaluate(p2));
				cliente.setP3(evaluate(p3));
				cliente.setP4(evaluate(p4));
				cliente.setP5(evaluate(p5));
				cliente.setP6(evaluate(p6));
				cliente.setP7(evaluate(p7));
				Filiale filiale = filiali.get(codFiliale);				
				if(filiale != null) {
					cliente.setCab(filiale.getCab());
					cliente.setFiliali(filiale);
				} else {
					System.out.println("CAB non trovato per filiale "+codFiliale+" ["+row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue()+"]");
					continue;
				}

				listaClienti.add(cliente);
			}

			writeSqlClienti(listaClienti, out);

		}finally {
			inWorkbook.close();
			fis1.close();
		}

	}

	private static boolean evaluate(String flag) {
		if(StringUtils.isEmpty(flag)) {
			return Boolean.FALSE;
		} else {
			if(flag.equalsIgnoreCase("S") || flag.equalsIgnoreCase("SI")) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private static void writeSqlClienti(List<Cliente> listaClienti, PrintWriter out) throws IOException {

		String query = "INSERT INTO clienti (nag,cab,nome,data_nascita,telefono,email,p1,p2,p3,p4,p5,p6,p7,firma,codice,confermato,id_filiale) " + 
				"VALUES('{nag}','{cab}','{nome}',STR_TO_DATE('{data_nascita}','%d/%m/%Y'),'{telefono}','{email}',{p1},{p2},{p3},{p4},{p5},{p6},{p7},0,'{codice}',0,{id_filiale});";

		for (Cliente cliente : listaClienti) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
			String sql = query.replace("{nag}", cliente.getNag()).replace("{nome}", cliente.getNome()).replace("{data_nascita}", sdf.format(cliente.getDataNascita()))
					.replace("{telefono}", cliente.getTelefono()).replace("{email}", cliente.getEmail())
					.replace("{p1}", cliente.getP1()?"1":"0")
					.replace("{p2}", cliente.getP2()?"1":"0")
					.replace("{p3}", cliente.getP3()?"1":"0")
					.replace("{p4}", cliente.getP4()?"1":"0")
					.replace("{p5}", cliente.getP5()?"1":"0")
					.replace("{p6}", cliente.getP6()?"1":"0")
					.replace("{p7}", cliente.getP7()?"1":"0")	
					.replace("{codice}", generaCodice())
					.replace("{cab}", cliente.getCab())
					.replace("{id_filiale}", cliente.getFiliali().getId().toString());

			//System.out.println(sql);

			out.write(sql+"\n");
		}

	}

	public static String generaCodice() {

		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);	 
		return generatedString.toUpperCase();
	}

	public static void caricaFiliali(File excel, PrintWriter out) throws FileNotFoundException, IOException{

		FileInputStream fis1 = null;
		XSSFWorkbook inWorkbook = null;

		try {
			fis1 = new FileInputStream(excel);
			inWorkbook = new XSSFWorkbook(fis1);
			XSSFSheet sheet = inWorkbook.getSheetAt(0);

			DataFormatter objDefaultFormat = new DataFormatter();
			FormulaEvaluator objFormulaEvaluator = inWorkbook.getCreationHelper().createFormulaEvaluator();

			Iterator<Row> rowIterator = sheet.iterator();    
			// Estraggo la riga di intestazione
			rowIterator.next();   

			List<Filiale> listaFiliali = new ArrayList<Filiale>();

			int emptyCount = 0;
			while(rowIterator.hasNext() && emptyCount < 2) {
				// Parto dalla riga 2 con i dati
				Row row = rowIterator.next();

				//				0	N.
				//				1	FILIALE DI
				//				2	CAB
				//				3	INDIRIZZO
				//				4	COMUNE

				Cell cellValue = row.getCell(2);
				objFormulaEvaluator.evaluate(cellValue); // This will evaluate the cell, And any type of cell will return string value

				String cab = objDefaultFormat.formatCellValue(cellValue,objFormulaEvaluator);	

				if(StringUtils.isEmpty(cab)) {
					emptyCount++;
					continue;
				}	

				Integer num = (int)row.getCell(0).getNumericCellValue();		
				String nome = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

				Filiale filiale = new Filiale(nome, cab, num);				
				filiale.setId(idFiliale++);

				listaFiliali.add(filiale);
			}

			writeSqlFiliali(listaFiliali, out);

		}finally {
			inWorkbook.close();
			fis1.close();
		}

	}

	private static void writeSqlFiliali( List<Filiale> listaFiliali, PrintWriter out) throws IOException {

		String query = "INSERT INTO filiali (id,nome,codice,cab) VALUES " + 
				"({id},'{nome}',{codice},'{cab}');";

		for (Filiale filiale : listaFiliali) {

			filiali.put(filiale.getCodice(), filiale);			

			String sql = query.replace("{nome}", filiale.getNome().replace("'", "\\'")).replace("{codice}", filiale.getCodice().toString()).replace("{cab}", filiale.getCab())
					.replace("{id}", filiale.getId().toString());
			//System.out.println(sql);	
			out.write(sql+"\n");			
		}

	}

//	public static void main(String[] args) throws Exception{
//
//		filiali = new HashMap<Integer, Filiale>();		
//
//		File sql = new File("C:\\Users\\ut1051\\Desktop\\BCC\\query.sql");
//
//
//		FileWriter fw = new FileWriter(sql, true);
//		BufferedWriter bw = new BufferedWriter(fw);
//		PrintWriter out = new PrintWriter(bw); 
//		try {
//
//			out.write("-- FILIALI \n");
//			File elencoFiliali = new File("C:\\Users\\ut1051\\Desktop\\BCC\\Filiali.xlsx");
//			caricaFiliali(elencoFiliali, out);
//
//			out.println();
//			out.write("-- CLIENTI \n");
//
//			File clienti = new File("C:\\Users\\ut1051\\Desktop\\BCC\\Clienti.xlsx");
//			caricaClienti(clienti, out);
//
//			out.println();
//		} finally {
//			out.close();
//		} 
//	}

}
