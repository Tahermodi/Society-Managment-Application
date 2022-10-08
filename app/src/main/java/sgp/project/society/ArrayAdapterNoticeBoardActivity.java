package sgp.project.society;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterNoticeBoardActivity extends ArrayAdapter<DummyClassNoticeBoardActivity> {

    private Activity context;
    private List<DummyClassNoticeBoardActivity> noticeList;

    public ArrayAdapterNoticeBoardActivity(Activity context, List<DummyClassNoticeBoardActivity> noticeList)
    {
        super(context, R.layout.list_layout_noticeboardactivity, noticeList);
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.list_layout_noticeboardactivity, null, true);

        TextView tv_notice = listviewitem.findViewById(R.id.tv_notice);

        DummyClassNoticeBoardActivity dummyClassNoticeBoardActivity = noticeList.get(position);

        tv_notice.setText(dummyClassNoticeBoardActivity.getNotice());

        return listviewitem;
    }
}
