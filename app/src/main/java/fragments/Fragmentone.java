package fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.aptamitra.R;

/**
 * Created by Hemorvi Champaneria on 11-07-2015.
 */
public class Fragmentone extends Fragment {

    TextView text1, titletext;
    ImageView logo;
    Typeface face;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.firstscreen, container, false);

        text1 = (TextView) rootView.findViewById(R.id.text1);
        titletext = (TextView) rootView.findViewById(R.id.titletext);
        logo = (ImageView) rootView.findViewById(R.id.logo);
        face = Typeface.createFromAsset(getActivity().getAssets(), "TrajanPro_Regular.otf");
        text1.setTypeface(face);
        titletext.setTypeface(face);

        return rootView;

        // Inflate the layout for this fragment
        /*return inflater.inflate(
                R.layout.firstscreen, container, false);*/
    }
}
