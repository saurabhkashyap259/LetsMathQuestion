package com.mate.letsmathquestion;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.mate.letsmathquestion.fragments.QuestionFragment;
import com.mate.letsmathquestion.helper.DatabaseHandler;
import com.mate.letsmathquestion.models.Question;
import com.mate.letsmathquestion.utils.AppData;
import com.mate.letsmathquestion.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    private static Fragment previousFragment;
    private static ArrayList<Fragment> fragmentsOnStack = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set attribute for app name in toolbar
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));

        //status bar background color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        //Fragment Manager
        fragmentManager = getSupportFragmentManager();

        //AppData.addAppData();

        openFragment(Constants.QuestionFragmentCount);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void openFragment(int fragmentCount) {

        switch (fragmentCount) {

            case Constants.QuestionFragmentCount:
                openFragment(new QuestionFragment());
                break;

            default:
                break;
        }
    }

    public static void openFragment(Fragment frag) {

        // frame.removeAllViews();
        if (previousFragment != null) {

            //Log.e(TAG, "Previous Fragment: " + previousFragment);
            destroyFragment(previousFragment);

        }

        if (fragmentsOnStack.size() > 0) {
            for (int i = 0; i < fragmentsOnStack.size(); i++) {
                destroyFragment(fragmentsOnStack.get(i));
            }

            fragmentsOnStack = new ArrayList<>();
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        // /ft.setCustomAnimations(R.anim.slide_in_right,
        // R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.container_body, frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        fragmentTransaction.commit();
        previousFragment = frag;

    }

    public static void destroyFragment(Fragment frag) {
        // frame.removeAllViews();

        //Log.e(TAG, "Destroy Fragment: " + frag);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(frag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        fragmentTransaction.commit();
    }
}
