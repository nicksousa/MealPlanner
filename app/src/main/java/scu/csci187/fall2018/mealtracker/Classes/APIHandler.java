package scu.csci187.fall2018.mealtracker.Classes;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.json.JSONTokener;
        import org.json.JSONArray;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.concurrent.ExecutionException;

        import android.os.AsyncTask;



public class APIHandler extends AsyncTask<String, Void, JSONObject> {

    public Query search(QueryParam qp) {


        // TODO For reference. Please remove later. String test = "https://api.edamam.com/search?q=chicken&app_id=b957081d&app_key=889e79d32df59ed1621b6247b075e26a&from=0&to=3&calories=591-722&health=alcohol-free";

        // Declare necessary variables for getting JSON from API based on search

        JSONObject json = null;


        String assembledQuery = qp.assembleSearchURL();

        try {
            json = this.execute(assembledQuery).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get values. Currently a test to get recipe data:

        String resultsKeyword = "hits";
        JSONArray searchResults;
        try {
            searchResults = json.getJSONArray(resultsKeyword);
        } catch (JSONException e) {
            return null;
        }

        Query query = new Query(searchResults);
        return query;

    }


    @Override
    protected JSONObject doInBackground(String... assembledQuery) {

        StringBuilder stringBuilder = new StringBuilder();

        // Get JSON from API
        try {

            // Create the URL
            URL url = new URL(assembledQuery[0]);

            // Access URL and save output to a buffer
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {

                // Read every line of the buffer and put it into the string builder
                for (String line; (line = buffer.readLine()) != null;) {
                    stringBuilder.append(line);
                }

                try {
                    JSONArray jsonArray = ( (JSONArray) new JSONTokener(stringBuilder.toString()).nextValue() );
                    return jsonArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Error = " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Error with IO in URL accession");
            return null;
        }
    }

}