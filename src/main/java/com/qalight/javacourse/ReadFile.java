package com.qalight.javacourse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by kpl on 23.07.2014.
 */
public class ReadFile {

    public String ReadFile(String fileName) {
//        InputStream input = null;
        String noTextSourceFile = "No Source File ";

        try {

            byte[] textSources = Files.readAllBytes(Paths.get("./src/main/resources/" + fileName));
            return new String(textSources);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//          finally{
//            if(input!=null){
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return noTextSourceFile;
    }

}
