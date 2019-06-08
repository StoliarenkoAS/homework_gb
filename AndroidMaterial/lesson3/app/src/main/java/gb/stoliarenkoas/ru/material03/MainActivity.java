package gb.stoliarenkoas.ru.material03;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import gb.stoliarenkoas.ru.material03.touch.ItemTouchCallback;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;

    private Adapter adapter;

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
                    initFragmentTwo();
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
            adapter.onItemDelete(0);
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


    private void initFragmentTwo() {
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.main_frame, new Fragment2()).commitNow();
        List<Message> messages = initList();

        RecyclerView recyclerView = findViewById(R.id.chat_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(messages);
        recyclerView.setAdapter(adapter);

        SnapHelper startSnapHelper = new GravitySnapHelper(Gravity.TOP);
        startSnapHelper.attachToRecyclerView(recyclerView);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchCallback(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        FlipInTopXAnimator animator = new FlipInTopXAnimator();
        animator.setRemoveDuration(500);
        recyclerView.setItemAnimator(animator);
    }

    private List<Message> initList() {
        final List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            messageList.add(new Message("NAME#" + i, "Message text N" + i, Message.Type.FRIEND));
            messageList.add(new Message("YOU", "Your text N" + i, Message.Type.SELF));
        }
        return messageList;
    }

}
