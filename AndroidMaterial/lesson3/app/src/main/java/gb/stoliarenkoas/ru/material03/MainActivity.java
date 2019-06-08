package gb.stoliarenkoas.ru.material03;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.transition.ChangeBounds;
import androidx.transition.PathMotion;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

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
    FloatingActionButton fab;

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
        fab = findViewById(R.id.fab);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        initFragmentOne();
                        return true;
                    case R.id.navigation_dashboard:
                        initFragmentTwo();
                        return true;
                    case R.id.navigation_notifications:
                        initFragmentThree();
                        return true;
                }
                return false;
            };

    private View.OnClickListener fabOnClickListenerFragmentTwo = new View.OnClickListener() {
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

    private List<Message> initList() {
        final List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            messageList.add(new Message("NAME#" + i, "Message text N" + i, Message.Type.FRIEND));
            messageList.add(new Message("YOU", "Your text N" + i, Message.Type.SELF));
        }
        return messageList;
    }

    private void initFragmentTwo() {
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.main_frame, new Fragment2())
                .setCustomAnimations(R.anim.come_from_below, R.anim.come_from_right)
                .commitNow();
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
        fab.setOnClickListener(fabOnClickListenerFragmentTwo);

    }

    private void initFragmentOne() {
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.main_frame, new Fragment1())
                .setCustomAnimations(R.anim.come_from_left, R.anim.come_from_right)
                .commit();

        transaction1.runOnCommit(() -> {
            final TextView textView = findViewById(R.id.fragment_one_text_view);
            final ImageView imageView = findViewById(R.id.fragment_one_image_view);

            fab.setOnClickListener((v) -> {
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.button_animator);
                animator.setTarget(textView);
                animator.start();
                ((AnimatedVectorDrawable)imageView.getDrawable()).start();
            });
        });
    }

    private void initFragmentThree() {
        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
        transaction3.replace(R.id.main_frame, new Fragment3())
                .setCustomAnimations(R.anim.come_from_right, R.anim.come_from_left)
                .commitNow();

        final ViewGroup root = findViewById(R.id.fragment3_root);
        final Scene scene1 = new Scene(root);
        final Scene scene2 = Scene.getSceneForLayout(root, R.layout.fragment_fragment3_scene2, this);

        fab.setOnClickListener((v -> swapScene(scene2)));
    }

    private void swapScene(final Scene to) {
        final TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new Slide());
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        transitionSet.setDuration(2000);
        TransitionManager.go(to, transitionSet);
    }

}
