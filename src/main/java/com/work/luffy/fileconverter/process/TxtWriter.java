package com.work.luffy.fileconverter.process;

import com.work.luffy.fileconverter.tasks.SensorConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class TxtWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensorConverter.class);

    public void writeToFile(List<String> inputData, String url) {
        LOGGER.info("writing to out put file :  " + url);
        Path file = Paths.get(url);
        try {
            Files.write(file, inputData,StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
