package fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import in.aptamitra.R;

/**
 * Created by Hemorvi Champaneria on 11-07-2015.
 */
public class Fragmentthree extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.thirdscreen, container, false);
    }
}
