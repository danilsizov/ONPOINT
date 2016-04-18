package danilgroup.onpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
    }

    User user = new User();

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
