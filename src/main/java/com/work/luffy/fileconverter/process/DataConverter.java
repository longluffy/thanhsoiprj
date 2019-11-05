package com.work.luffy.fileconverter.process;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataConverter {
    public List<String> dataConvert(List<String> inputData, String timeString){
        List<String> output = new ArrayList<>();

        for (String line : inputData){
            String[] params = line.split(";");
            output.add(params[2] + "\t" + params[6] + "\t" + params[4] + "\t" + timeString + "\t" + "00" );
        }
        return output;
    }
}
