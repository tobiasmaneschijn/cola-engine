package com.tobiasmaneschijn.colaengine.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        InputStream resourceAsStream = FileUtils.class.getClassLoader().getResourceAsStream(filename);
        Exception exception = null;
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));

            Exception innerExc= null;
            try {
                String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
                exception = exc;
            }
            finally {
                try {
                    reader.close();
                }
                catch(Exception exc) {
                    if(innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }

            if(innerExc != null)
                throw innerExc;
        }
        catch(Exception exc) {
            exception = exc;
        }
        finally {
            try {
                resourceAsStream.close();
            }
            catch(Exception exc) {
                if(exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }

            if(exception != null)
                throw exception;
        }
        return source.toString();
    }
}
