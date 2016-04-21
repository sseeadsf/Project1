package com.example.admin.mousemove;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private XmlPullParserFactory fc;
    private XmlPullParser parser;

    public void btn_Login(View v) throws XmlPullParserException, IOException {
        int status=0;
        EditText user = (EditText) findViewById(R.id.user);
        EditText pass = (EditText) findViewById(R.id.pass);
        ArrayList<User> users = Info();
        for (int i = 0; i < users.size(); i++) {
            if (user.getText().toString().equals(users.get(i).getName()) && pass.getText().toString().equals(users.get(i).getPasscode())) {
                intent();
                status = 1;
            }
        }
        if(status==0) {
            AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
            b.setTitle("Alert");
            b.setMessage("Some thing went wrong");
            b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try {
            readXml();

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void intent(){
        Intent i = new Intent(getApplicationContext(), MainView.class);
        startActivity(i);
    }
    private void readXml() throws XmlPullParserException, FileNotFoundException {
        fc = XmlPullParserFactory.newInstance();
        parser = fc.newPullParser();
        String xmlfile = "/data/USERSXML.xml";
        FileInputStream fIn = new FileInputStream(xmlfile);
        parser.setInput(fIn, null);
    }
    private ArrayList<User> Info() throws IOException, XmlPullParserException {
        ArrayList<User> users = new ArrayList<User>();
        User user = null;
        int eventType = -1;
        String nodeName = null;
        String name = null;
        String passcode = null;
        int id = 0;
        String fullname = null;

        while(eventType!= XmlPullParser.END_DOCUMENT){
            eventType = parser.next();
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    nodeName = parser.getName();
                    if (nodeName.equals("id")){
                        id = Integer.parseInt(parser.nextText());
                    }
                    if (nodeName.equals("name")){
                        name = parser.nextText();
                    }
                    if(nodeName.equals("fullname")){
                        fullname = parser.nextText();
                    }
                    if(nodeName.equals("passcode")){
                        passcode = parser.nextText();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    nodeName = parser.getName();
                    if(nodeName.equals("users")){
                        user = new User(id, name, fullname, passcode);
                        users.add(user);
                    }
            }
        }
        return users;
    }
}


