package sgp.project.society;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterEventActivity extends ArrayAdapter<DummyClassEventActivity> {

    private Activity context;
    private List<DummyClassEventActivity> eventList;

    public ArrayAdapterEventActivity(Activity context, List<DummyClassEventActivity> eventList)
    {
        super(context, R.layout.list_layout_eventactivity, eventList);
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.list_layout_eventactivity, null, true);

        TextView tv_event_name = listviewitem.findViewById(R.id.tv_event_name);
        TextView tv_event_date = listviewitem.findViewById(R.id.tv_event_date);
        TextView tv_event_day = listviewitem.findViewById(R.id.tv_event_day);
        TextView tv_event_desc = listviewitem.findViewById(R.id.tv_event_desc);

        DummyClassEventActivity dummyClassEventActivity = eventList.get(position);

        tv_event_name.setText(dummyClassEventActivity.getEvent_name());
        tv_event_date.setText(dummyClassEventActivity.getEvent_date());
        tv_event_day.setText(dummyClassEventActivity.getEvent_day());
        tv_event_desc.setText(dummyClassEventActivity.getEvent_desc());

        return listviewitem;
    }
}
