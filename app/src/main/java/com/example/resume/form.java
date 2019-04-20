package com.example.resume;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class form extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name,age,email,phone,quali,we,proj,hob,lang;
    TextView nametext,agetext,emailtext,phonetext,qualitext,wetext,projtext,hobtext,langtext;
    private RelativeLayout llPdf;
    Button create;
    private Bitmap bitmap;
    static Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        spinner = (Spinner) findViewById(R.id.quali);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quali_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        we = findViewById(R.id.we);
        proj = findViewById(R.id.proj);
        hob = findViewById(R.id.hob);
        lang = findViewById(R.id.lang);

        nametext = findViewById(R.id.nametext);
        agetext = findViewById(R.id.agetext);
        emailtext = findViewById(R.id.emailtext);
        phonetext = findViewById(R.id.phonetext);
        qualitext = findViewById(R.id.qualitext);
        wetext = findViewById(R.id.wetext);
        projtext = findViewById(R.id.projtext);
        hobtext = findViewById(R.id.hobtext);
        langtext = findViewById(R.id.langtext);

        llPdf = findViewById(R.id.llPdf);
        create = findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nametext.setText(name.getText().toString());
                nametext.setVisibility(View.VISIBLE);
                name.setVisibility(View.INVISIBLE);

                agetext.setText(age.getText().toString());
                agetext.setVisibility(View.VISIBLE);
                age.setVisibility(View.INVISIBLE);

                emailtext.setText(email.getText().toString());
                emailtext.setVisibility(View.VISIBLE);
                email.setVisibility(View.INVISIBLE);

                phonetext.setText(phone.getText().toString());
                phonetext.setVisibility(View.VISIBLE);
                phone.setVisibility(View.INVISIBLE);

                //qualitext.setText(quali.getText().toString());


                wetext.setText(we.getText().toString());
                wetext.setVisibility(View.VISIBLE);
                we.setVisibility(View.INVISIBLE);

                projtext.setText(proj.getText().toString());
                projtext.setVisibility(View.VISIBLE);
                proj.setVisibility(View.INVISIBLE);

                hobtext.setText(hob.getText().toString());
                hobtext.setVisibility(View.VISIBLE);
                hob.setVisibility(View.INVISIBLE);

                langtext.setText(lang.getText().toString());
                langtext.setVisibility(View.VISIBLE);
                lang.setVisibility(View.INVISIBLE);

                create.setVisibility(View.INVISIBLE);

                Log.i("size"," "+llPdf.getWidth() +"  "+llPdf.getHeight());
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());

                createPdf();
            }
        });
    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

//    public void create(View v) {
//        Log.i("size"," "+llPdf.getWidth() +"  "+llPdf.getWidth());
//        createPdf();
//    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHeight = (int) height, convertWidth = (int) width;
        Log.i("height and width",convertHeight+" "+convertWidth);

        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/Your ResuME.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){
        File file = new File("/sdcard/Your ResuME.pdf");
        if (file.exists()) {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            }
            catch(ActivityNotFoundException e) {
                Toast.makeText(this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        qualitext.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        qualitext.setText(parent.getItemAtPosition(pos).toString());
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please select a qualification!", Toast.LENGTH_SHORT).show();
    }
}
