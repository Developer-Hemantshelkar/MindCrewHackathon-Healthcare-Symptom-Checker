import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

public class HomeServer extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String symptoms = request.getParameter("symptoms");
        String suggestion;

        try {
            String apiResponse = callAPI(symptoms);

            if (apiResponse == null || apiResponse.isEmpty()) {
                suggestion = ruleBasedEngine(symptoms) + " (offline)";
            } else {
                suggestion = parseApiResponse(apiResponse);
            }

        } catch (Exception e) {
            suggestion = ruleBasedEngine(symptoms) + " (offline due to API error)";
        }

        request.setAttribute("symptoms", symptoms);
        request.setAttribute("suggestion", suggestion);

        RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
        rd.forward(request, response);
    }

    private String callAPI(String symptoms) throws Exception {
        String jsonInput = "{ \"symptoms\": \"" + symptoms + "\" }";
        URL url = new URL("http://localhost:8080/Healthcare/SymptomApi");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes("utf-8"));
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder responseBody = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) responseBody.append(responseLine.trim());

        return responseBody.toString();
    }

    private String parseApiResponse(String apiResponse) {
        StringBuilder result = new StringBuilder();
        try {
            JSONObject jsonResponse = new JSONObject(apiResponse);
            if (jsonResponse.has("conditions")) {
                result.append("Possible Conditions:\n");
                JSONArray conditions = jsonResponse.getJSONArray("conditions");
                for (int i = 0; i < conditions.length(); i++) result.append("- ").append(conditions.getString(i)).append("\n");
            } else result.append("No conditions found. Using offline rules.\n").append(ruleBasedEngine(""));
        } catch (JSONException e) {
            result.append("Error parsing API. Using offline rules.\n").append(ruleBasedEngine(""));
        }
        return result.toString();
    }

    private String ruleBasedEngine(String symptoms) {
        String text = symptoms.toLowerCase();
        if (text.contains("fever") && text.contains("cough")) return "Possible flu. Drink fluids and rest.";
        if (text.contains("headache") && text.contains("nausea")) return "Possible migraine. Rest in a quiet room.";
        if (text.contains("chest pain")) return "Possible heart issue. Seek medical help immediately.";
        if (text.contains("sore throat")) return "Possible throat infection. Drink warm fluids.";
        return "Consult a doctor for proper diagnosis.";
    }
}
