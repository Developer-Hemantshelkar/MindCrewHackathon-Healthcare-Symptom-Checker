import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

public class SymptomApi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject outputJson = new JSONObject();
        try {
            JSONObject inputJson = new JSONObject(sb.toString());
            String symptoms = inputJson.optString("symptoms", "").toLowerCase();

            JSONArray conditions = new JSONArray();
            if (symptoms.contains("fever") && symptoms.contains("cough")) conditions.put("Flu / Viral Infection");
            if (symptoms.contains("headache") && symptoms.contains("nausea")) conditions.put("Migraine");
            if (symptoms.contains("chest pain")) conditions.put("Heart Issue");
            if (symptoms.contains("sore throat")) conditions.put("Throat Infection");
            if (conditions.length() == 0) conditions.put("Consult a doctor for proper diagnosis");

            outputJson.put("conditions", conditions);

        } catch (JSONException e) {
            outputJson.put("error", "Invalid JSON input");
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(outputJson.toString());
        out.flush();
    }
}
