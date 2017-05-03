package cn.czfy.zsdx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.czfy.zsdx.R;


public class LibraryNowListFragment extends Fragment {

   private View view;
    private ListView lv_nowlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_library_now_list, container, false);
        lv_nowlist= (ListView) view.findViewById(R.id.lv_nowlist);
        return view;
    }


}
