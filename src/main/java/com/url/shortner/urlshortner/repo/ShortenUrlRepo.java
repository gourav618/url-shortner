package com.url.shortner.urlshortner.repo;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.url.shortner.urlshortner.models.ShortenUrlBean;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ShortenUrlRepo {

    private static final String CSV_FILE_PATH = "src/main/resources/data.csv";
    private static final Integer defaultDay = 1;

    public boolean writeToCsv(String shortenUrl, String destinationUrl) {
        ShortenUrlBean shortenUrlBean = new ShortenUrlBean();
        shortenUrlBean.setDestinationUrl(destinationUrl);
        shortenUrlBean.setShortenUrl(shortenUrl);
        shortenUrlBean.setDaysToValidity(defaultDay);
        shortenUrlBean.setValidSince(new Date());

        try {
            List<ShortenUrlBean> csvData = getCsvData();
            csvData.add(shortenUrlBean);
            updateCsvData(csvData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean updateDestinationUrl(String shortenUrl, String destinationUrl) {
        try {
            List<ShortenUrlBean> beans = getCsvData();

            List<ShortenUrlBean> updatedRows = beans.stream().map(row -> {
                if (row.getShortenUrl().equals(shortenUrl)) {
                    row.setDestinationUrl(destinationUrl);
                }
                return row;
            }).collect(Collectors.toList());

            updateCsvData(updatedRows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean updateExpiryDate(String shortenUrl, Integer daysToAdd) {
        try {
            List<ShortenUrlBean> csvData = getCsvData();

            List<ShortenUrlBean> updatedRows = csvData.stream().map(row -> {
                if (row.getShortenUrl().equals(shortenUrl)) {
                    row.setDaysToValidity(row.getDaysToValidity() + daysToAdd);
                }
                return row;
            }).collect(Collectors.toList());

            updateCsvData(updatedRows);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void updateCsvData(List<ShortenUrlBean> updatedRows) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE_PATH));
        StatefulBeanToCsv<ShortenUrlBean> beanToCsv = new StatefulBeanToCsvBuilder<ShortenUrlBean>(writer).build();
        beanToCsv.write(updatedRows);
        writer.close();
    }

    public List<ShortenUrlBean> getCsvData() throws FileNotFoundException {
        return new CsvToBeanBuilder<ShortenUrlBean>(new FileReader(CSV_FILE_PATH))
                .withType(ShortenUrlBean.class)
                .build()
                .parse();
    }
}
