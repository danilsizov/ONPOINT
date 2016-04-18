package danilgroup.onpoint;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Danil on 18.04.2016.
 */
public class User extends MainActivity{

    public static String LOG_TAG = "my_log";
    public TextView myName;
    public TextView myXP;
    public TextView myLevelView;
    public TextView myNameOfQuest;
    public TextView myAboutText;
    public ProgressBar myProgressBar;
    public int delta = 10;// Вынести куда-то


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myName = (TextView) findViewById(R.id.myName);
        myXP = (TextView) findViewById(R.id.myXP);
        myLevelView = (TextView) findViewById(R.id.myLevel);
        myNameOfQuest = (TextView) findViewById(R.id.myNameOfQuest);
        myAboutText = (TextView) findViewById(R.id.myAboutText);
        myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
    }

    int level_xp(int level) {
        return level >= 2 ? 5 * (level + 2) * (level - 1) : 0;

    }

    int get_level(int exp) {
        int i = 2;
        int level = 0;
        while (exp >= 0) {
            exp -= (i++) * delta;
            level += 1;
        }
        return level;
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String in;

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL("http://83.220.175.77:3000/user.json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
            Log.d(LOG_TAG, strJson);

//            JSONObject dataJsonObj = null;
//            String secondName = "";

            try {

                JSONObject reader = new JSONObject(strJson);
//                JSONObject sys  = reader.getJSONObject("name");

                // User name:
                int id = Integer.parseInt(reader.getString("id"));
                Log.d(LOG_TAG, "ID " + id);

                String name = reader.getString("name");
                Log.d(LOG_TAG, "Имя " + name);

                String surname = reader.getString("surname");
                Log.d(LOG_TAG, "surname " + surname);

                myName.setText(name + " " + surname);


//                String myLevel = get_level(experience) + "";
//                String myExperience = experience + "";
                //my level
                int experience = Integer.parseInt(reader.getString("experience"));
                Log.d(LOG_TAG, "XP " + experience);

                int myLevel = get_level(experience);
                int next_xp = level_xp(myLevel + 1);
                int prev_xp = level_xp(myLevel);
                int percent = 100 * (experience - prev_xp) / (next_xp - prev_xp);
                myProgressBar.setProgress(percent);
                myXP.setText(experience + "");
                myLevelView.setText(myLevel + "");

                String role = reader.getString("role");
                Log.d(LOG_TAG, "role " + role);

                JSONArray quests = reader.getJSONArray("quests");
//                for(JSONObject quest in quests) {
//                }
                JSONObject quest = quests.getJSONObject(0);
                // Quest name:
                String q_name = quest.getString("name");
                Log.d(LOG_TAG, "Имя " + q_name);
                String description = quest.getString("description");
                Log.d(LOG_TAG, "Text " + description);
                myNameOfQuest.setText(q_name);
                myAboutText.setText(description);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
