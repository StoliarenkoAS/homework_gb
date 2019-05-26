package gb.stoliarenkoas.ru.material03;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.main_frame);
        final BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        buttonOne = findViewById(R.id.button_one);
        buttonTwo = findViewById(R.id.button_two);
        buttonThree = findViewById(R.id.button_three);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(fabOnClickListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.main_frame, new Fragment1()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.main_frame, new Fragment2()).commit();
                    return true;
                case R.id.navigation_notifications:
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.main_frame, new Fragment3()).commit();
                    return true;
            }
            return false;
        }
    };

    private View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final boolean open = buttonOne.getVisibility() == View.VISIBLE;
            Animator.AnimatorListener animationListener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (!open) {
                        buttonOne.setVisibility(View.VISIBLE);
                        buttonTwo.setVisibility(View.VISIBLE);
                        buttonThree.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (open) {
                        buttonOne.setVisibility(View.GONE);
                        buttonTwo.setVisibility(View.GONE);
                        buttonThree.setVisibility(View.GONE);
                    }
                }
            };
            buttonOne.animate().translationYBy(open ? 200 : -200).alpha(open ? 0f : 1f).setDuration(500).setListener(animationListener).start();
            buttonTwo.animate().translationYBy(open ? 400 : -400).alpha(open ? 0f : 1f).setDuration(500).setListener(animationListener).start();
            buttonThree.animate().translationYBy(open ? 600 : -600).alpha(open ? 0f :1f).setDuration(500).setListener(animationListener).start();
        }
    };

}
