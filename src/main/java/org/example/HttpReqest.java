package org.example;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;


@Data
public class HttpReqest {

    private Method method;

    private String path;

    private String protocol;

    private Map<String, String> headers = new LinkedHashMap<>(); // тут будуть ніби хедери

    private String body;


    public enum Method {
        GET,
        POST
    }

    public static HttpReqest of(String text) {

        HttpReqest reqest = new HttpReqest();


        // чистемо рядки від невидимих символів + розбиваємо по переносу

        String[] lines = text.split("\n");

        for(int i = 0; i < lines.length; i++){
            lines[i] = lines[i].replace("\r", "");
        }

        // далі отримуємо перший рядок і отримуємо з нього данні

        String startLine = lines[0];

        String[] startLineParts = startLine.split(" "); // тепер є частини стартового рядка

        // парсимо стартовий рядок

        reqest.setMethod(Method.valueOf(startLineParts[0])); // ліва частина рядка це метод

        reqest.setPath(startLineParts[1]); // друга частина це пробіл

        reqest.setProtocol(startLineParts[2]); // третя частина це протокол

        // parse headers and body
        // хедери це всі не пусті рядки

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            System.out.println("line = " + line);

            if (line.equals("")) { // якщо рядок пустий то хедери закінчилися we need read body
                StringJoiner body = new StringJoiner("\n");
                for (int j = i ; j < lines.length; j++) {
                    body.add(lines[j]);
                }
                reqest.setBody(body.toString());

            } else { // read header
                String[] keyValue = line.split(": ");

                reqest.getHeaders().put(
                        keyValue[0].strip(),
                        keyValue[1].strip()
                );

            }
        }

        return reqest;

    }
}
