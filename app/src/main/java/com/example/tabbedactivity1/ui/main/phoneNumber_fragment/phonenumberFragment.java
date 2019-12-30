package com.example.tabbedactivity1.ui.main.phoneNumber_fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.example.tabbedactivity1.MainActivity;
import com.example.tabbedactivity1.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class phonenumberFragment extends ListFragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    ListView list1;
    LinearLayout ll;
    Button loadBtn;

    public static phonenumberFragment newInstance(int index) {
        phonenumberFragment fragment = new phonenumberFragment();
        Bundle bundle = new Bundle();
        //bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

     class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
         ProgressDialog pd;

         @Override
         protected void onPreExecute() {
             // TODO Auto-generated method stub
             super.onPreExecute();

             pd = ProgressDialog.show(getActivity(), "Loading Contacts",
                     "Please Wait");
         }

         @Override
         protected ArrayList<String> doInBackground(Void... params) {
             // TODO Auto-generated method stub
             ArrayList<String> contacts = new ArrayList<String>();
             String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " asc";
             Context applicationContext = MainActivity.getContextOfApplication();
             Cursor c = applicationContext.getContentResolver().query(
                     ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                     null, null, sortOrder);
             while (c.moveToNext()) {

                 String contactName = c
                         .getString(c
                                 .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                 String phNumber = c
                         .getString(c
                                 .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                 contacts.add("이       름 : "+ contactName + "\n"+ "전화번호 : " + phNumber);

             }
             c.close();

             return contacts;
         }

         @Override
         protected void onPostExecute(ArrayList<String> contacts) {
             // TODO Auto-generated method stub
             super.onPostExecute(contacts);

             pd.cancel();

             ll.removeView(loadBtn);

             ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                     getActivity().getApplicationContext(), R.layout.text, contacts);

             list1.setAdapter(adapter);

         }

     }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phonenumber,container,false);
        ll = (LinearLayout) view.findViewById(R.id.LinearLayout1);
        list1 = (ListView) view.findViewById(android.R.id.list);
        loadBtn = (Button) view.findViewById(R.id.button1);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LoadContactsAyscn lca = new LoadContactsAyscn();
                lca.execute();
            }
        });
        //새로운 코드
        //jsonParsing(getJsonString());
        return view;
 }

    /*
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

     */
}