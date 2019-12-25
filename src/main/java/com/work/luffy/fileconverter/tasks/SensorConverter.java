package com.work.luffy.fileconverter.tasks;

import com.work.luffy.fileconverter.process.CSVreader;
import com.work.luffy.fileconverter.process.DataConverter;
import com.work.luffy.fileconverter.process.TxtWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
@EnableScheduling
public class SensorConverter {

    @Autowired
    CSVreader csVreader;

    @Autowired
    TxtWriter txtWriter;

    @Autowired
    DataConverter dataConverter;

    @Value("${station_code}")
    String station_code;

    @Value("${station_name}")
    String station_name;

    @Value("${input_path}")
    String input_path;

    @Value("${output_path}")
    String output_path;

    final long checkInterval = 300000;

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorConverter.class);


    @Scheduled(fixedDelay = checkInterval, initialDelay =2000 )
    public void SensorConverter() {

        DateTimeFormatter inputFileNameFormat = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
        DateTimeFormatter outputFileNameFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        LOGGER.info("running Checking jobs");

        for (int i=0; i<=checkInterval/1000/60 ;i++) {
            LocalDateTime checkTime = LocalDateTime.now().minus(i, ChronoUnit.MINUTES);
            String dateTimeStr = inputFileNameFormat.format(checkTime);

            String filename = station_code + "-" + dateTimeStr + ".mv1.csv";
            String url = input_path + filename;

            LOGGER.info("checking files from " + dateTimeStr);

            List<String> inputData = csVreader.read(url);

            if (inputData.size() >=1 ){
                String timeString = outputFileNameFormat.format(checkTime);
                timeString += "00";
                System.out.println(timeString);
                String output_url = output_path + filename.substring(0, filename.length() - 4) + ".txt";
                System.out.println(output_url);
                String output_new = output_path + station_name + "_" +  timeString  + ".txt";
                txtWriter.writeToFile(dataConverter.dataConvert(inputData, timeString), output_new);
            }
        }
    }
}