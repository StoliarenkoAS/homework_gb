package gb.stoliarenkoas.ru.lesson8;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout root;

    private ConstraintSet mainSet = new ConstraintSet();
    private ConstraintSet alternativeSet = new ConstraintSet();
    private boolean original = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        root = findViewById(R.id.root);
        mainSet.clone(root);
        alternativeSet.clone(this, R.layout.content_alternative);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionSet set = new TransitionSet();

                set.addTransition(new ChangeBounds());
                set.setDuration(1000);
                set.setInterpolator(new DecelerateInterpolator());
                set.setOrdering(TransitionSet.ORDERING_TOGETHER);

                TransitionManager.beginDelayedTransition(root, set);
                if (original) {
                    alternativeSet.applyTo(root);
                    original = false;
                } else {
                    mainSet.applyTo(root);
                    original = true;
                }

            }
        });
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
}
