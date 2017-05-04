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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.czfy.zsdx.R;
import cn.czfy.zsdx.db.dao.LibraryDao;
import cn.czfy.zsdx.domain.LibraryLoginBean;
import cn.czfy.zsdx.tool.Utility;
import cn.czfy.zsdx.view.RoundProgressbarWithProgress;


public class LibraryNowListFragment extends Fragment {

   private View view;
    private ListView lv_nowlist;
    private LibraryDao dao;
    private List<LibraryLoginBean.NowListBean> nowListBeen;
    MyNowAdapter myNowAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_library_now_list, container, false);
        lv_nowlist= (ListView) view.findViewById(R.id.lv_nowlist);
        dao=new LibraryDao(LibraryNowListFragment.this.getActivity());
        nowListBeen=dao.findAll_Now();

        myNowAdapter=new MyNowAdapter();
        lv_nowlist.setAdapter(myNowAdapter);
        lv_nowlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LibraryLoginBean.NowListBean item=nowListBeen.get(position);
                Utility tool = new Utility();
                String seName="";
                try {
                    seName= item.getBookName().toString().substring(0,10);

                }catch (Exception e){
                    seName=item.getBookName().toString();
                }
                tool.searchBook(LibraryNowListFragment.this.getActivity(),seName);
            }
        });

        return view;
    }
    class MyNowAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return nowListBeen.size();
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
            convertView= View.inflate(LibraryNowListFragment.this.getActivity(),R.layout.list_lib_now,null);
            LibraryLoginBean.NowListBean item=nowListBeen.get(position);
            RoundProgressbarWithProgress roundProgressbarWithProgress= (RoundProgressbarWithProgress) convertView.findViewById(R.id.round_progressBar);
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            String time = formatter.format(curDate);
            long days=0;
            try {
                Date date=formatter.parse(item.getEndTime());
                long between = (date.getTime() - curDate.getTime());
                 days = between / (1000 * 60 * 60 * 24);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            roundProgressbarWithProgress.text="剩\n"+days+"天";
            int progress= (int) ((days/45.0)*100);
            roundProgressbarWithProgress.setProgress(100-progress);
            TextView tv1= (TextView) convertView.findViewById(R.id.name);
            //TextView tv2= (TextView) convertView.findViewById(R.id.auther);
           // TextView tv3= (TextView) convertView.findViewById(R.id.address);
            TextView tv4= (TextView) convertView.findViewById(R.id.stime);
            TextView tv5= (TextView) convertView.findViewById(R.id.etime);
            tv1.setText(item.getBookName());
           // tv2.setText("作者："+item.getAuthor());
            //tv3.setText("馆藏地："+item.getAddress());
//            TasksCompletedView mTasksView = (TasksCompletedView)  convertView.findViewById(R.id.tasks_view);
//            mTasksView.setProgress(20);

            tv4.setText("借阅日期："+item.getStartTime());
            tv5.setText("应还日期："+item.getEndTime());


            return convertView;
        }
    }


}
