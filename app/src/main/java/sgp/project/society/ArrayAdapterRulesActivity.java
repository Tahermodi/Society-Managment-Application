package sgp.project.society;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterRulesActivity extends ArrayAdapter<DummyClassRulesActivity> {

    private Activity context;
    private List<DummyClassRulesActivity> ruleList;

    public ArrayAdapterRulesActivity(Activity context, List<DummyClassRulesActivity> ruleList)
    {
        super(context, R.layout.list_layout_rulesactivity, ruleList);
        this.context = context;
        this.ruleList = ruleList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.list_layout_rulesactivity, null, true);

        TextView tv_rule = listviewitem.findViewById(R.id.tv_rule);

        DummyClassRulesActivity dummyClassRulesActivity = ruleList.get(position);

        tv_rule.setText(dummyClassRulesActivity.getRule());

        return listviewitem;
    }
}
