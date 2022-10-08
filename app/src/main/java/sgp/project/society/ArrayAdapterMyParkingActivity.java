package sgp.project.society;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterMyParkingActivity extends ArrayAdapter<DummyClassParkingActivity> {

    private Activity context;
    private List<DummyClassParkingActivity> parkingList;

    public ArrayAdapterMyParkingActivity(Activity context, List<DummyClassParkingActivity> parkingList)
    {
        super(context, R.layout.list_layout_parkingactivity, parkingList);
        this.context = context;
        this.parkingList = parkingList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem = inflater.inflate(R.layout.list_layout_parkingactivity, null, true);

        TextView tv_name = listviewitem.findViewById(R.id.tv_name);
        TextView tv_vehicle_number = listviewitem.findViewById(R.id.tv_vehicle_number);
        TextView tv_vehicle_type = listviewitem.findViewById(R.id.tv_vehicle_type);

        DummyClassParkingActivity dummyClassParkingActivity = parkingList.get(position);

        tv_name.setText(dummyClassParkingActivity.getName());
        tv_vehicle_number.setText(dummyClassParkingActivity.getVehicle_number());
        tv_vehicle_type.setText(dummyClassParkingActivity.getVehicle_type());

        return listviewitem;
    }
}
