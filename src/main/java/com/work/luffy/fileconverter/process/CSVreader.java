package com.work.luffy.fileconverter.process;

import com.work.luffy.fileconverter.tasks.SensorConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVreader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVreader.class);

    public List<String> read(String url) {
        File csvFile = new File(url);
        List<String> result = new ArrayList<>();

        if (csvFile.isFile()) {
            LOGGER.info("reading file " + url);
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
                csvReader.readLine();

                String row;
                while ((row = csvReader.readLine()) != null) {
                    result.add(row);
                }
                csvReader.close();
            } catch (IOException e) {
                LOGGER.error("file not found");
                e.printStackTrace();
            }
        }
        return result;
    }
}
