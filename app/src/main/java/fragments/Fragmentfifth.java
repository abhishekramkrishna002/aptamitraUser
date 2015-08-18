package fragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.aptamitra.R;


/**
 * Created by Hemorvi Champaneria on 11-07-2015.
 */
public class Fragmentfifth extends Fragment {

    TextView text1;
    ImageView icon1;
    Typeface face;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fifthscreen, container, false);

        text1 = (TextView) rootView.findViewById(R.id.text1);
        icon1 = (ImageView) rootView.findViewById(R.id.icon1);
        face = Typeface.createFromAsset(getActivity().getAssets(), "TrajanPro_Regular.otf");
        text1.setTypeface(face);

        return rootView;
    }
}
