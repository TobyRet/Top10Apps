package com.codurance.top10apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	TextView textView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView1);
        
        new DownloadData().execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=10/xml");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private class DownloadData extends AsyncTask<String, Void, String> {
    	
    	String myXMLData;
    	
		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub
			try {
				myXMLData = downloadXML(urls[0]);
			} catch(IOException e) {
				return "Unable to download XML file";
			}
			return "";
		}
		
		protected void onPostExecute(String result) {
			Log.d("OnPostExecute", myXMLData);
			textView.setText(myXMLData);
		}
    	
		
		private String downloadXML(String theURL) throws IOException {
			int BUFFER_SIZE=2000; //how many characters to be downloaded at a time
			InputStream is = null;
			
			String xmlContents = "";
			
			try {
				URL url = new URL(theURL);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setReadTimeout(10000); // maximum time to wait for input stream before giving up
				connection.setConnectTimeout(15000);
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				int response = connection.getResponseCode();
				Log.d("DownloadXML", "The repsonse returned is " + response);
				is = connection.getInputStream();
				
				InputStreamReader isr = new InputStreamReader(is);
				
				int charRead;
				
				char[] inputBuffer = new char[BUFFER_SIZE]; //an array of 2000
				
				try { 
					while((charRead = isr.read(inputBuffer)) > 0) {
						String readString = String.copyValueOf(inputBuffer, 0, charRead);
						xmlContents += readString;
						inputBuffer = new char[BUFFER_SIZE];
					}
				} catch(IOException e) {
					e.printStackTrace();
					return null;
				}
				
				return xmlContents;
				
			} finally { //whether there is an error or not
				if(is != null) {
					is.close();
				}
			}
		}
		
		
    }
}
