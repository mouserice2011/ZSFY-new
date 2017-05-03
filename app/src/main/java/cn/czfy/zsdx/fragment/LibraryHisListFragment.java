package cn.czfy.zsdx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.czfy.zsdx.R;

/**
 * Created by sinyu on 2017/5/1.
 */

public class LibraryHisListFragment extends Fragment {

    private View view;
    private ListView lv_nowlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_library_his_list, container, false);
        lv_nowlist= (ListView) view.findViewById(R.id.lv_nowlist);
        return view;
    }


}
