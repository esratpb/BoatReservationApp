package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Model;

import java.io.File;
import java.io.IOException;

public class ReadWriteToModel {
    static ObjectMapper mapper=new ObjectMapper();
    static Model model;

    public static Model readModel() {
        try {
            model = mapper.readValue(new File("src/main/java/model/model.json"), Model.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }
    //
//
    //to Json file writer
    public static void writeModel(Model model) {
        try {
            // Java object to JSON file
            mapper.writeValue(new File("src/main/java/model/model.json"), model);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
