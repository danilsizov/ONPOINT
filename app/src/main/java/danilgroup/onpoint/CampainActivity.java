package danilgroup.onpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Danil on 13.04.2016.
 */
public class CampainActivity extends Activity {

    public LinearLayout companyLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.campain);
        companyLayout = (LinearLayout)findViewById(R.id.company);
    }

    public void goToProfile(View view) {
        Intent intent = new Intent(CampainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void goToCompain(View view) {
        Intent intent = new Intent(CampainActivity.this, CampainActivity.class);
        startActivity(intent);
    }

    public void goToQuest(View view) {
        Intent intent = new Intent(CampainActivity.this, QuestActivity.class);
        startActivity(intent);
    }

    public void goToOptions(View view) {
        Intent intent = new Intent(CampainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }

    public void getAllInformation(View view) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height = 200;
        view.setLayoutParams(params);
    }
}
