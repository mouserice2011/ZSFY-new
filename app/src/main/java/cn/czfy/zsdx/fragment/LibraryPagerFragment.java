package cn.czfy.zsdx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.ui.tabstrip.PagerSlidingTabStrip;

/**
 * Created by sinyu on 2017/5/1.
 */


public class LibraryPagerFragment extends Fragment {

    private static String[] TITLES;
    private static String[] URLS = new String[]{"", "", ""};//, ""

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pager, container, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TITLES = getResources().getStringArray(R.array.library_titles);

        FragmentPagerAdapter adapter = new NewsAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
    }

    class NewsAdapter extends FragmentPagerAdapter {
        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new LibraryFragment();
            } else if (position == 1) {
                return new LibraryNowListFragment();
            } else if (position == 2) {
                return new LibraryHisListFragment();
            } else if (position == 3) {
                return new LibraryPersonalFragment();
            }
            return null;
            //return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position % TITLES.length];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}
