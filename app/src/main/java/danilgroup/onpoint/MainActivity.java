package danilgroup.onpoint;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";
    public TextView myName;
    public TextView myXP;
    public TextView myLevelView;
    public ProgressBar myProgressBar;
    public int delta = 10;// Вынести куда-то

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();new ParseTask().execute();
    }

    int level_xp (int level) {
        return level >= 2 ? 5 * (level + 2) * (level - 1) : 0;
    }

    int get_level(int exp) {
        int i = 2;
        int level = 0;
        while(exp >= 0) {
            exp -= (i++)*delta;
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

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
            Log.d(LOG_TAG, strJson);
            myName = (TextView) findViewById(R.id.myName);
            myXP = (TextView) findViewById(R.id.myXP);
            myLevelView = (TextView) findViewById(R.id.myLevel);
            myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);

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


                int experience = Integer.parseInt(reader.getString("experience"));
                Log.d(LOG_TAG, "XP " + experience);

                int myLevel = get_level(experience);
//                String myLevel = get_level(experience) + "";
//                String myExperience = experience + "";
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



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void goToProfile(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToCompain(View view) {
        Intent intent = new Intent(MainActivity.this, CampainActivity.class);
        startActivity(intent);
    }

    public void goToQuest(View view) {
        Intent intent = new Intent(MainActivity.this, QuestActivity.class);
        startActivity(intent);
    }

    public void goToOptions(View view) {
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }
}
