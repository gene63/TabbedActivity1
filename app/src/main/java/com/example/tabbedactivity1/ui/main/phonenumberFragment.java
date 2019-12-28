package com.example.tabbedactivity1.ui.main;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tabbedactivity1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

 /**
 * A placeholder fragment containing a simple view.
 */
public class phonenumberFragment extends ListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;

    public static phonenumberFragment newInstance(int index) {
        phonenumberFragment fragment = new phonenumberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

         */
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        //새로운 코드
        jsonParsing(getJsonString());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //json 관련 코드

    //json 파일 열어서 string으로 가져오는 함수
    private String getJsonString()
    {
        String json = "";

        try {
            AssetManager am = getResources().getAssets();
            InputStream is = am.open("phonenum.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return json;
    }



    private void jsonParsing(String json)
    {
        ListViewAdapter adapter = new ListViewAdapter() ;
        setListAdapter(adapter);

        try{
            JSONArray phonenumArray = new JSONArray(json);

            Log.d(String.valueOf(phonenumArray.length()),"2");

            for(int i=0; i<phonenumArray.length(); i++)
            {

                JSONObject phonenumObject = phonenumArray.getJSONObject(i);
                String phone_number = phonenumObject.getString("phone_number");
                String name = phonenumObject.getString("name");
                adapter.addItem(name, phone_number);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}