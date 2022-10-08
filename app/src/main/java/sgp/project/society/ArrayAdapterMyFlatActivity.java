package sgp.project.society;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterMyFlatActivity  extends ArrayAdapter<DummyClassMyFlatActivity> {

    private Activity context;
    private List<DummyClassMyFlatActivity> memberList;

    public ArrayAdapterMyFlatActivity(Activity context, List<DummyClassMyFlatActivity> memberList)
    {
        super(context, R.layout.list_layout_myflatactivity, memberList);
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.list_layout_myflatactivity, null, true);

        TextView tv_name = listviewitem.findViewById(R.id.tv_name);
        TextView tv_mobile_number = listviewitem.findViewById(R.id.tv_mobile_number);

        DummyClassMyFlatActivity dummyClassMyFlatActivity = memberList.get(position);

        tv_name.setText(dummyClassMyFlatActivity.getName());
        tv_mobile_number.setText(dummyClassMyFlatActivity.getMobile_number());

        return listviewitem;
    }
}
