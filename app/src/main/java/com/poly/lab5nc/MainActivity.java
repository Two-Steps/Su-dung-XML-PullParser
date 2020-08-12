package com.poly.lab5nc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edt_link;
    Button btn_load;
    ListView lv_web;
    AdapterMyWeb adapterMyWeb;
    List<MyItem> myItemList;
    String link = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_link = findViewById(R.id.edt_link);
        btn_load = findViewById(R.id.btn_load);
        lv_web = findViewById(R.id.lv_web);
        myItemList = new ArrayList<>();
        link = edt_link.getText().toString();
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mở luồng mới
                AsyncTask asyncTask = new AsyncTask() {

                    // hàm xử lý luồng
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {

                            URL url = new URL(link);
                            // kết nối url
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            // lấy ra luông dữ liệu
                            InputStream inputStream = httpURLConnection.getInputStream();
                            // khởi tạo xmlParser, trong link không có NameSpace nên setNamespaceAware = false
                            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                            xmlPullParserFactory.setNamespaceAware(false);
                            // khởi tạo và truyền dữ liệu vào Parser
                            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                            xmlPullParser.setInput(inputStream, "utf-8");

                            int eventType = xmlPullParser.getEventType();
                            MyItem myItem = null;
                            String text = "";
                            //
                            while (eventType != xmlPullParser.END_DOCUMENT) {
                                String tag = xmlPullParser.getName();
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        if (tag.equalsIgnoreCase("item")) {
                                            myItem = new MyItem();
                                        }
                                        break;
                                    case XmlPullParser.TEXT:
                                        text = xmlPullParser.getText();
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if (myItem != null) {
                                            if (tag.equalsIgnoreCase("title")) {
                                                myItem.title = text;
                                            } else if (tag.equalsIgnoreCase("description")) {
                                                myItem.description = text;
                                            } else if (tag.equalsIgnoreCase("link")) {
                                                myItem.link = text;
                                            } else if (tag.equalsIgnoreCase("item")) {
                                                myItemList.add(myItem);
                                            }
                                        }
                                        break;
                                }
                                // chuyển tiếp
                                eventType = xmlPullParser.next();
                            }

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    // hàm hiển thi dữ liệu khi kết thúc luồng

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        adapterMyWeb = new AdapterMyWeb(MainActivity.this, myItemList);
                        lv_web.setAdapter(adapterMyWeb);
                        lv_web.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                MyItem item = myItemList.get(position);
                                String link = item.link;
                                Intent intent = new Intent(MainActivity.this, Sub1Activity.class);
                                intent.putExtra("link", link);
                                startActivity(intent);
                            }
                        });
                    }
                };
                // start
                asyncTask.execute();
            }
        });
    }
}
