package pl.codeaddict.rssreaderforreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.codeaddict.rssreaderforreddit.xml.HandleXML;

public class MainActivity extends AppCompatActivity {
    EditText title, link, description;
    Button b1, b2;
    private String finalUrl = "http://tutorialspoint.com/android/sampleXML.xml";
    private HandleXML obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.editText);
        link = (EditText) findViewById(R.id.editText2);
        description = (EditText) findViewById(R.id.editText3);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) ;
                title.setText(obj.getTitle());
                link.setText(obj.getLink());
                description.setText(obj.getDescription());
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(in);
            }
        });
    }
}