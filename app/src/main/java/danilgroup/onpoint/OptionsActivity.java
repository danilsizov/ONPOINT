package danilgroup.onpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Danil on 13.04.2016.
 */
public class OptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.options);
    }

    public void goToProfile(View view) {
        Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToCompain(View view) {
        Intent intent = new Intent(OptionsActivity.this, CampainActivity.class);
        startActivity(intent);
    }

    public void goToQuest(View view) {
        Intent intent = new Intent(OptionsActivity.this, QuestActivity.class);
        startActivity(intent);
    }

    public void goToOptions(View view) {
        Intent intent = new Intent(OptionsActivity.this, OptionsActivity.class);
        startActivity(intent);
    }
}
