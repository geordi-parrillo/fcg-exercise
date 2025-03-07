package it.geordi.fcg_exercise.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class UserCSVHelper {
    public static final String TYPE = "text/csv";
    private static final String[] HEADERS = { "Name", "Surname", "Email", "Address" };

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static boolean hasNotCSVFormat(MultipartFile file) {
        return !hasCSVFormat(file);
    }

    public static List<User> getUsers(MultipartFile file) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(HEADERS)
                    .setSkipHeaderRecord(true)
                    .setIgnoreSurroundingSpaces(true)
                    .setNullString("")
                    .get();

            Iterable<CSVRecord> csvRecords = csvFormat.parse(fileReader);

            List<User> result = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                User user = User.builder()
                        .name(csvRecord.get("Name"))
                        .surname(csvRecord.get("Surname"))
                        .email(csvRecord.get("Email"))
                        .address(csvRecord.get("Address"))
                        .build();

                result.add(user);
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}
