package com.adi.SchoolKioskApp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.*;
import java.io.*;

import org.json.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.adi.SchoolKioskApp.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

private FragmentFirstBinding binding;
private int textLen = 0;
private String curText = "Enter ID";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
      return binding.getRoot();

    }

    public void resetDisplay(){
        textLen = 0;
        curText = "Enter ID";
    }
    public String addText(String text){
        if (textLen <= 4 && !(curText.equals("Authorized") || curText.equals("No Senior Priv") || curText.equals("Invalid ID"))) {
            if (textLen == 0) {
                curText = text;
            } else {
                curText = curText + text;
            }
            textLen += 1;
        }
        return curText;
    }
    public String invalidStudent(){
        curText = "Invalid ID";
        return curText;
    }
    public String noPriv(){
        curText = "No Senior Priv";
        return curText;
    }
    public String success() {
        curText = "Authorized";
        return curText;
    }
    public String deleteText(){
        if (!(curText.equals("Authorized") || curText.equals("No Senior Priv") || curText.equals("Invalid ID")))
        {
            if (textLen > 0) {
                textLen -= 1;
                curText = curText.substring(0, textLen);
            }
            if (textLen == 0) {
                curText = "Enter ID";
            }
        }
        return curText;
    }
    public void clickButton(String output){
        TextView tv1 = (TextView) getView().findViewById(R.id.idtext);
        tv1.setText(addText(output));
        ((MainActivity) getActivity()).vibrateButton();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("1");}
        });
        binding.buttontwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("2");}
        });
        binding.buttonthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("3");}
        });
        binding.buttonfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("4");}
        });
        binding.buttonfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("5");}
        });
        binding.buttonsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("6");}
        });
        binding.buttonseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("7");}
        });
        binding.buttoneight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("8");}
        });
        binding.buttonnine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("9");}
        });
        binding.buttonzero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {clickButton("0");}
        });
        binding.buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).vibrateButton();
                TextView tv1 = (TextView) getView().findViewById(R.id.idtext);
                tv1.setText(deleteText());
            }
        });
        binding.buttondelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                resetDisplay();
                return false;
            }
        });
        binding.buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).vibrateButton();
                int privStatus = 0;
                String nameStatus = null;
                try {
                    URL homeURL = new URL("http://192.168.1.218:8000/kiosk/login" + "?id=" + curText + "&kiosk=2");
//                    URL url = new URL("https://api.github.com/users/google");
                    HttpURLConnection con = (HttpURLConnection) homeURL.openConnection();
                    con.setRequestMethod("GET");
//                    con.setDoOutput(true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //hi
                    System.out.println("Hi");
                    String inputLine = in.readLine();
                    inputLine = inputLine.concat(in.readLine().concat(in.readLine().concat(in.readLine().concat(in.readLine().concat(in.readLine().concat(in.readLine()))))));
                    in.close();
                    JSONObject jObject = new JSONObject(inputLine);
                    nameStatus = (String) jObject.get("name");
                    privStatus = (int) jObject.get("seniorPriv");
                } catch (ProtocolException e) {
                    System.out.println(e);
                } catch (IOException e) {
                    System.out.println(e);
                } catch (JSONException e) {
                    System.out.println(e);
                }
                boolean status = false;
                TextView tv1 = (TextView) getView().findViewById(R.id.idtext);
                if (tv1.getText().equals("99999")){
                    getActivity().recreate();
                }
                if (nameStatus.equals("Invalid ID")){
                    tv1.setText(invalidStudent());
                }
                else if (privStatus == 0){
                    tv1.setText(noPriv());
                }
                else{
                    tv1.setText(success());
                    status = true;
                }
                if (status == true){
                    tv1.setBackgroundColor(Color.parseColor("#551FFF00"));
                    getView().findViewById(R.id.viewabove).setBackgroundColor(Color.parseColor("#551FFF00"));
                    getView().findViewById(R.id.viewbelow).setBackgroundColor(Color.parseColor("#551FFF00"));
                }
                else{
                    tv1.setBackgroundColor(Color.parseColor("#55FF1F00"));
                    getView().findViewById(R.id.viewabove).setBackgroundColor(Color.parseColor("#55FF1F00"));
                    getView().findViewById(R.id.viewbelow).setBackgroundColor(Color.parseColor("#55FF1F00"));
                }
//                try {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                } catch (InterruptedException e) {
//                    System.out.println("yo");
//                    // Does nothing, because the display will be reset
//                }
//                resetDisplay();
                tv1.setText(curText);
                TextView tv2 = (TextView) getView().findViewById(R.id.idtext);
                String tv2text = (String) tv2.getText();
                tv1.setText(curText);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetDisplay();
                        tv1.setText(curText);
                        tv1.setBackgroundColor(Color.parseColor("#55EFE5E5"));
                        getView().findViewById(R.id.viewabove).setBackgroundColor(Color.parseColor("#55EFE5E5"));
                        getView().findViewById(R.id.viewbelow).setBackgroundColor(Color.parseColor("#55EFE5E5"));
                    }
                }, 750); //3000 is time in ms.


            }
        });
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}