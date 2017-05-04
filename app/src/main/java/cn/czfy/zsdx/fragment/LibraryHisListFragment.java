package cn.czfy.zsdx.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.db.dao.LibraryDao;
import cn.czfy.zsdx.domain.LibraryLoginBean;
import cn.czfy.zsdx.tool.Utility;

/**
 * Created by sinyu on 2017/5/1.
 */

public class LibraryHisListFragment extends Fragment {

    private View view;
    private ListView lv_nowlist;
    private LibraryDao dao;
    private List<LibraryLoginBean.HisListBean> hisListBean;
    MyHisAdapter myHisAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_library_his_list, container, false);
        dao=new LibraryDao(LibraryHisListFragment.this.getActivity());
        hisListBean=dao.findAll_His();

        lv_nowlist= (ListView) view.findViewById(R.id.lv_nowlist);
        myHisAdapter=new MyHisAdapter();
        lv_nowlist.setAdapter(myHisAdapter);
        lv_nowlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LibraryLoginBean.HisListBean item=hisListBean.get(position);
                Utility tool = new Utility();
                String seName="";
                try {
                    seName= item.getBookName().toString().substring(0,10);

                }catch (Exception e){
                    seName=item.getBookName().toString();
                }
                tool.searchBook(LibraryHisListFragment.this.getActivity(),seName);
            }
        });
        return view;
    }
    class MyHisAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return hisListBean.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= View.inflate(LibraryHisListFragment.this.getActivity(),R.layout.list_lib_his,null);
            LibraryLoginBean.HisListBean item=hisListBean.get(position);
            TextView tv1= (TextView) convertView.findViewById(R.id.name);
            TextView tv2= (TextView) convertView.findViewById(R.id.auther);
            TextView tv3= (TextView) convertView.findViewById(R.id.address);
            TextView tv4= (TextView) convertView.findViewById(R.id.stime);
            TextView tv5= (TextView) convertView.findViewById(R.id.etime);
            tv1.setText(item.getBookName());
            tv2.setText("作者："+item.getAuthor());
            tv3.setText("馆藏地："+item.getAddress());
            tv4.setText("借阅日期："+item.getStartTime());
            tv5.setText("归还日期："+item.getEndTime());


            return convertView;
        }
    }


}
