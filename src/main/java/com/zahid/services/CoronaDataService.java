package com.zahid.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zahid.models.LocationData;

@Service
public class CoronaDataService {
    private static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationData> allData = new ArrayList<>();
    private long totalReportedCases = 0;
    
    @PostConstruct
    @Scheduled(cron = "* * * * * *")
    // @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {

        List<LocationData> newData = new ArrayList<>(); // concurrency issue resolving

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DATA_URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // System.out.println(response.body());

        // StringReader csvBodyReader = new StringReader(response.body());
        // CSVFormat format = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).build();
        // CSVParser csvParser = new CSVParser(csvBodyReader, format);
        // List<CSVRecord> records = csvParser.getRecords();

        StringReader csvBodyReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        
        for (CSVRecord record : records) {
            // Province/State,Country/Region,Lat,Long
            // String state = record.get("Province/State");
            // String country = record.get("Country/Region");
            // String lat = record.get("Lat");
            // String lon = record.get("Long");
            // System.out.println(country);

            // int latestTotalCases = Integer.parseInt(record.get(record.size() - 1));
            // System.out.println(latestTotalCases);
            int latestTotalCases = Integer.parseInt(record.get(record.size() - 1));
            totalReportedCases += latestTotalCases;

            LocationData locationData = new LocationData();
            locationData.setState(record.get("Province/State"));
            locationData.setCountry(record.get("Country/Region"));
            locationData.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
            
            // System.out.println(locationData);
            newData.add(locationData);
        }
        this.allData = newData;
    }

	public List<LocationData> getAllData() {
		return allData;
	}

	public long getTotalReportedCases() {
		return totalReportedCases;
	}

    
}
