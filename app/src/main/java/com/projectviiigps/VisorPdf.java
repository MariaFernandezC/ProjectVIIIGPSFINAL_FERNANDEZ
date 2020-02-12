package com.projectviiigps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VisorPdf extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_pdf);

        pdfView = findViewById(R.id.pdfView);

        /// leer el pdf
        //  pdfView.fromAsset("ManualGPSKIDS.pdf").load();

        /// leer PDF desde una URL
        //  new RetrievePDFStream().execute("https://appgpsmovil.000webhostapp.com/Pdf/ManualGPSKIDS.pdf");

        ///leer pdf desde bytes
        new RetrievePDFBytes().execute("https://appgpsmovil.000webhostapp.com/Pdf/ManualGPSKIDS.pdf");
    }
    class  RetrievePDFBytes extends AsyncTask<String, Void, byte[]> {

        @Override
        protected byte[] doInBackground(String... strings) {

            InputStream inputStream=null;
            try {
                URL url = new URL (strings [0]);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                if(urlConnection.getResponseCode() ==200){
                    inputStream =new BufferedInputStream(urlConnection.getInputStream());

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                return null;
            }
            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(byte[] bytes){

            pdfView.fromBytes(bytes).load();
        }
    }
}



       /*/ class  RetrievePDFStream extends AsyncTask <String, Void, InputStream> {

            @Override
            protected InputStream doInBackground(String... strings) {

                InputStream inputStream=null;
                try {
                   URL url = new URL (strings [0]);
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                    if(urlConnection.getResponseCode() ==200){
                        inputStream =new BufferedInputStream(urlConnection.getInputStream());

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    return null;
                }
                return inputStream;
            }

            protected void onPostExecute(InputStream inputStream){

                    pdfView.fromStream(inputStream).load();
            }
        }
}/*/

