package dnit.commons.snv;

import dnit.commons.exception.CommonException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


final class ClientSnvImplementation {

    private ClientSnvImplementation() { }

    private static final int MAX_NUM_RETRYS = 3;
    private static final int RETRY_DELAY_MS = 5000;

    private static final String ENDPOINT =
            "https://servicos.dnit.gov.br/sgplan/homol/sge-arvore-decisao/api/v1/snv";


    /**
     * Implementação temporária do cliente para obter os SNVs disponíveis.
     * Futuramente, essa implementação irá utilizar outro endpoint
     */
    public static List<SNVResponse> obtemSNVs(
            String uf,
            String br,
            Double latitude,
            Double longitude
    ) {
        int attempts = 0;

        while (true) {
            try {
                return obtainSNV(uf, br, latitude, longitude);

            } catch (Exception e) {
                attempts++;

                if (attempts >= MAX_NUM_RETRYS) {
                    throw new CommonException("Erro ao obter SNVs: " + e.getMessage(), e);
                }

                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new CommonException("Erro ao obter SNVs: " + ie.getMessage(), ie);
                }
            }
        }
    }



    private static List<SNVResponse> obtainSNV(
            String uf,
            String br,
            Double latitude,
            Double longitude
    ) {
        try {
            URL url = new URL(ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create valid JSON string with proper decimal format
            String jsonInput =
                    " { " +
                        "\"uf\": " + uf + "," +
                        "\"br\": " + br + "," +
                        "\"latitude\": " + latitude + "," +
                        "\"longitude\": " + longitude +
                    " } ";

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            InputStream inputStream = (responseCode < HttpURLConnection.HTTP_BAD_REQUEST)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,
                                                                         StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder responseJson = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseJson.append(inputLine);
            }
            in.close();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new CommonException("Erro ao obter SNVs: " + responseCode);
            }

            if (responseJson.length() == 0) {
                throw new CommonException("Erro ao obter SNVs: resposta vazia");
            }

            return parseSNVResponse(responseJson.toString());

        } catch (Exception e) {
            throw new CommonException("Erro ao obter SNVs: " + e.getMessage(), e);
        }
    }



    private static List<SNVResponse> parseSNVResponse(String json) {
        List<SNVResponse> list = new ArrayList<>();

        // Remove surrounding brackets and split objects
        json = json.trim();
        if (json.startsWith("[")) json = json.substring(1);
        if (json.endsWith("]")) json = json.substring(0, json.length() - 1);

        // Only one object in your example, but split in case of many
        String[] objects = json.split("\\},\\{");

        for (String obj : objects) {
            obj = obj.trim();
            if (!obj.startsWith("{")) obj = "{" + obj;
            if (!obj.endsWith("}")) obj = obj + "}";

            String snv = extractValue(obj, "snv");
            String versao = extractValue(obj, "versao");

            SNVResponse response = new SNVResponse(snv, versao);
            list.add(response);
        }

        return list;
    }



    private static String extractValue(String jsonObject, String key) {
        String pattern = "\"" + key + "\":";
        int index = jsonObject.indexOf(pattern);
        if (index == -1) return null;

        index += pattern.length();
        char firstChar = jsonObject.charAt(index);

        if (firstChar == '"') {
            int end = jsonObject.indexOf('"', index + 1);
            return jsonObject.substring(index + 1, end);
        } else if (jsonObject.startsWith("null", index)) {
            return null;
        } else {
            int end = jsonObject.indexOf(',', index);
            if (end == -1) end = jsonObject.indexOf('}', index);
            return jsonObject.substring(index, end).trim();
        }
    }

}
