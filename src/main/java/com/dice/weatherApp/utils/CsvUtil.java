package com.dice.weatherApp.utils;

import com.dice.weatherApp.model.Credential_ele;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class CsvUtil {
    private final String CSV_FILE;

    public CsvUtil() {
        Properties properties = Util.getApplicationProperties();
        CSV_FILE = properties.getProperty("csv.file.path");
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Credential_ele> readClients() {
        List<Credential_ele> credentialResponses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    Credential_ele credentialResponse = new Credential_ele();
                    credentialResponse.setClientIdHash(values[0]);
                    credentialResponse.setClientSecretHash(values[1]);
                    credentialResponses.add(credentialResponse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentialResponses;
    }

    public void writeClient(Credential_ele credentialResponse) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            writer.write(credentialResponse.getClientIdHash() + "," + credentialResponse.getClientSecretHash());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
