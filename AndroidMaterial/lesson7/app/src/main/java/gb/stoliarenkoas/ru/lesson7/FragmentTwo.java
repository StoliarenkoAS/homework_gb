package gb.stoliarenkoas.ru.lesson7;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.PatternPathMotion;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;


public class FragmentTwo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        final TextView textView = view.findViewById(R.id.text_view);
        textView.setOnClickListener((v) -> {
            final FragmentOne fragmentOne = new FragmentOne();

            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setPathMotion(new PatternPathMotion());
            changeBounds.setInterpolator(new BounceInterpolator());

            fragmentOne.setSharedElementEnterTransition(changeBounds);
            getFragmentManager().beginTransaction().replace(R.id.container, fragmentOne)
                    .addSharedElement(textView, getString(R.string.text_view_transition))
                    .addToBackStack(FragmentOne.class.getName())
                    .commit();
        });

        return view;
    }

}
