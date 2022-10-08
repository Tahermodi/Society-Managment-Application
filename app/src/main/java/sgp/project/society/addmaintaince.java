package sgp.project.society;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addmaintaince extends AppCompatActivity {
    Button CreateButton, back;
    EditText name, municipaltext, flat_no, g_s_charge, water_charge, car_p_charge, g_m_t_m, interest;
    int PageWidth = 1200;
    Date dateobj;
    DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmaintaince);

        CreateButton = findViewById(R.id.generate);
        name = findViewById(R.id.Name);
        municipaltext = findViewById(R.id.M_text);
        flat_no = findViewById(R.id.flat_number);
        g_s_charge = findViewById(R.id.G_S_charge);
        water_charge = findViewById(R.id.W_charge);
        car_p_charge = findViewById(R.id.Car_charge);
        g_m_t_m = findViewById(R.id.G_M_T_M);
        interest = findViewById(R.id.interest);
        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addmaintaince.this, MainActivity.class);
                startActivity(intent);

            }
        });


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createPDF();


    }


    private void createPDF() {
        CreateButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                dateobj = new Date();

                if (name.getText().toString().length() == 0 || municipaltext.getText().toString().length() == 0 ||
                        flat_no.getText().toString().length() == 0 || g_s_charge.getText().toString().length() == 0 ||
                        water_charge.getText().toString().length() == 0 || car_p_charge.getText().toString().length() == 0 ||
                        g_m_t_m.getText().toString().length() == 0 || interest.getText().toString().length() == 0) {
                    Toast.makeText(addmaintaince.this, "Some Fields Empty", Toast.LENGTH_LONG).show();
                } else {
                    PdfDocument myPdfDocument = new PdfDocument();
                    Paint myPaint = new Paint();
                    Paint titlePaint = new Paint();
                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);
                    Canvas canvas = myPage1.getCanvas();

                    titlePaint.setTextAlign(Paint.Align.CENTER);
                    titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.ITALIC));
                    titlePaint.setTextSize(70);
                    canvas.drawText("MAINTENANCE BILL", PageWidth / 2, 150, titlePaint);

                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setTextSize(50);
                    canvas.drawLine(70, 200, 1050, 200, myPaint);
                    canvas.drawText("NAME:-" + name.getText().toString(), (PageWidth / 2) - 300, 250, myPaint);
                    canvas.drawLine(70, 300, 1050, 300, myPaint);
                    canvas.drawText("FLAT.NO:-" + flat_no.getText().toString(), (PageWidth / 2) - 300, 350, myPaint);

                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    myPaint.setTextSize(40);
                    dateFormat = new SimpleDateFormat("dd/MM/yy");
                    canvas.drawText("Date: " + dateFormat.format(dateobj), PageWidth - 20, 40, myPaint);

                    dateFormat = new SimpleDateFormat("hh:mm:ss");
                    canvas.drawText("Time: " + dateFormat.format(dateobj), PageWidth - 20, 90, myPaint);

                    // myPaint.setStyle(Paint.Style.STROKE);
                    //myPaint.setStrokeWidth(2);
                    //canvas.drawRect(20,200,PageWidth-20,280,myPaint);
                    //myPaint.setTextAlign(Paint.Align.LEFT);
                    //myPaint.setStyle(Paint.Style.FILL);

                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setTextSize(50);
                    canvas.drawLine(70, 400, 1050, 400, myPaint);
                    canvas.drawText("Particulars", (PageWidth / 2) - 230, 500, myPaint);
                    canvas.drawText("Amount", (PageWidth / 2) + 350, 500, myPaint);
                    canvas.drawLine(70, 550, 1050, 550, myPaint);


                    // canvas.drawLine(500,210,500,270,myPaint);
                    //canvas.drawLine(680,210,680,270,myPaint);

                    float t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0;
                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setTextSize(45);
                    canvas.drawText("Cleaning & Gardening Charges", (PageWidth / 2) - 210, 640, myPaint);
                    canvas.drawText(municipaltext.getText().toString(), (PageWidth / 2) + 350, 640, myPaint);
                    t1 = Float.parseFloat(municipaltext.getText().toString());
                    canvas.drawLine(70, 680, 1050, 680, myPaint);

                    canvas.drawText("Administrative expenses  Charges", (PageWidth / 2) - 200, 740, myPaint);
                    canvas.drawText(g_s_charge.getText().toString(), (PageWidth / 2) + 350, 740, myPaint);
                    t2 = Float.parseFloat(g_s_charge.getText().toString());
                    canvas.drawLine(70, 780, 1050, 780, myPaint);

                    canvas.drawText("Water Charges", (PageWidth / 2) - 230, 840, myPaint);
                    canvas.drawText(water_charge.getText().toString(), (PageWidth / 2) + 350, 840, myPaint);
                    t3 = Float.parseFloat(water_charge.getText().toString());
                    canvas.drawLine(70, 880, 1050, 880, myPaint);

                    canvas.drawText("Car Parking charges", (PageWidth / 2) - 230, 940, myPaint);
                    canvas.drawText(car_p_charge.getText().toString(), (PageWidth / 2) + 350, 940, myPaint);
                    t4 = Float.parseFloat(car_p_charge.getText().toString());
                    canvas.drawLine(70, 980, 1050, 980, myPaint);

                    canvas.drawText("Event Fund", (PageWidth / 2) - 230, 1040, myPaint);
                    canvas.drawText(g_m_t_m.getText().toString(), (PageWidth / 2) + 350, 1040, myPaint);
                    t5 = Float.parseFloat(g_m_t_m.getText().toString());
                    canvas.drawLine(70, 1080, 1050, 1080, myPaint);
                    float Total = t1 + t2 + t3 + t4 + t5;
                    canvas.drawText("Interest", (PageWidth / 2) - 230, 1140, myPaint);
                    canvas.drawText(interest.getText().toString(), (PageWidth / 2) + 350, 1140, myPaint);
                    t6 = Float.parseFloat(interest.getText().toString());
                    t6 = (Total * t6) / 100;
                    Total = Total + t6;
                    canvas.drawLine(70, 1180, 1050, 1180, myPaint);
                    canvas.drawLine((PageWidth / 2) - 545, 200, (PageWidth / 2) - 545, 1280, myPaint);
                    canvas.drawLine((PageWidth / 2) + 450, 200, (PageWidth / 2) + 450, 1280, myPaint);

                    canvas.drawLine((PageWidth / 2) + 200, 400, (PageWidth / 2) + 200, 1280, myPaint);

                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setColor(Color.BLACK);
                    myPaint.setTextSize(45);
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("Total", (PageWidth / 2) - 250, 1240, myPaint);
                    myPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(Total), (PageWidth / 2) + 400, 1240, myPaint);
                    canvas.drawLine(70, 1280, 1050, 1280, myPaint);
                    myPdfDocument.finishPage(myPage1);

                    File file = new File(Environment.getExternalStorageDirectory(), "/" + flat_no.getText().toString() + "_Maintenance.pdf");

                    try {
                        myPdfDocument.writeTo(new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.close();
                }
            }

        });

    }
}
