package gb.stoliarenkoas.ru.lesson7;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.RotateDownTransformer;
import com.google.android.material.tabs.TabLayout;

public class FragmentOne extends Fragment {

    ViewPager pager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        final TextView textView = view.findViewById(R.id.text_view);
        textView.setOnClickListener((v) -> {
            final FragmentTwo fragmentTwo = new FragmentTwo();

            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setPathMotion(new ArcMotion());
            changeBounds.setInterpolator(new BounceInterpolator());

            fragmentTwo.setSharedElementEnterTransition(changeBounds);
            getFragmentManager().beginTransaction().replace(R.id.container, fragmentTwo)
                    .addSharedElement(textView, getString(R.string.text_view_transition))
                    .addToBackStack(FragmentOne.class.getName())
                    .commit();
        });

        pager = view.findViewById(R.id.pager);
        pager.setPageTransformer(true, new RotateDownTransformer());
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.RED), "RED");
        pagerAdapter.addFragment(PagerFragment.createInstance(0xFFFF8800), "ORANGE");
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.YELLOW), "YELLOW");
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.GREEN), "GREEN");
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.CYAN), "CYAN");
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.BLUE), "BLUE");
        pagerAdapter.addFragment(PagerFragment.createInstance(Color.MAGENTA), "MAGENTA");
        pager.setAdapter(pagerAdapter);

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);


        return view;
    }

}
