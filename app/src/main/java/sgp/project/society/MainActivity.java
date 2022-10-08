package sgp.project.society;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{

    CardView card_flat_directory, card_rules, card_parking, card_event,
            card_service_center, card_guest_register, card_notice_board,
            card_my_flat, card_maintenance, card_complain_box,
            card_society_expenses;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card_flat_directory = findViewById(R.id.card_flat_directory);
        card_rules = findViewById(R.id.card_rules);
        card_parking = findViewById(R.id.card_parking);
        card_event = findViewById(R.id.card_event);
        card_service_center = findViewById(R.id.card_service_center);
        card_guest_register = findViewById(R.id.card_guest_register);
        card_notice_board = findViewById(R.id.card_notice_board);
        card_my_flat = findViewById(R.id.card_my_flat);
        card_maintenance = findViewById(R.id.card_maintenance);
        card_complain_box = findViewById(R.id.card_complain_box);
        card_society_expenses = findViewById(R.id.card_society_expenses);

        card_flat_directory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FlatDirectoryActivity.class));
            }
        });

        card_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RulesActivity.class));
            }
        });

        card_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ParkingActivity.class));
            }
        });

        card_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });

        card_service_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ServiceCenterActivity.class));
            }
        });


        card_notice_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoticeBoardActivity.class));
            }
        });

        card_my_flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyFlatActivity.class));
            }
        });

        card_maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MaintenanceActivity.class));
            }
        });











        rfaBtn = findViewById(R.id.activity_main_rfab);
        rfaLayout = findViewById(R.id.activity_main_rfal);

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Logout")
                .setResId(R.drawable.ic_logout)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelColor(0xff283593)
                .setWrapper(0)
        );

        rfaContent.setItems(items)
                .setIconShadowColor(0xff888888);

        rfabHelper = new RapidFloatingActionHelper(
                getApplicationContext(),
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();



    }



    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {

        switch (position)
        {
            case 0:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }

        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

        switch (position)
        {
            case 0:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }

        rfabHelper.toggleContent();
    }








    // added for exit app when user click back button 1 time and give choice yes/no
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Confirm exit..!!");
        alertDialogBuilder.setIcon(R.drawable.ic_exit_app);
        alertDialogBuilder.setMessage("Are you sure want to exit?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
