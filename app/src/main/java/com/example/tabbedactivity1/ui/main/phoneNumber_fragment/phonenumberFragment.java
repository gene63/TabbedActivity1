package com.example.tabbedactivity1.ui.main.phoneNumber_fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tabbedactivity1.MainActivity;
import com.example.tabbedactivity1.R;
import com.example.tabbedactivity1.data.bookEntity;
import com.example.tabbedactivity1.data.dbClient;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class phonenumberFragment extends ListFragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    ListView list1;
    LinearLayout ll;
    Button addBtn;

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

//     class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
//         ProgressDialog pd;
//
//         @Override
//         protected void onPreExecute() {
//             // TODO Auto-generated method stub
//             super.onPreExecute();
//
//             pd = ProgressDialog.show(getActivity(), "Loading Contacts",
//                     "Please Wait");
//         }
//
//         @Override
//         protected ArrayList<String> doInBackground(Void... params) {
//             // TODO Auto-generated method stub
//             ArrayList<String> contacts = new ArrayList<String>();
//             String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " asc";
//             Context applicationContext = MainActivity.getContextOfApplication();
//             Cursor c = applicationContext.getContentResolver().query(
//                     ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                     null, null, sortOrder);
//             while (c.moveToNext()) {
//
//                 String contactName = c
//                         .getString(c
//                                 .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                 String phNumber = c
//                         .getString(c
//                                 .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                 contacts.add("이       름 : "+ contactName + "\n"+ "전화번호 : " + phNumber);
//
//             }
//
//             c.close();
//
//             return contacts;
//         }
//
//         @Override
//         protected void onPostExecute(ArrayList<String> contacts) {
//             // TODO Auto-generated method stub
//             super.onPostExecute(contacts);
//
//             pd.cancel();
//             ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                     getActivity().getApplicationContext(), R.layout.text, contacts);
//             adapter.notifyDataSetChanged();
//             list1.setAdapter(adapter);
//         }
//
//     }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phonenumber,container,false);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        ll = (LinearLayout) view.findViewById(R.id.LinearLayout1);
        list1 = (ListView) view.findViewById(android.R.id.list);
        addBtn = (Button) view.findViewById(R.id.addperson);

        //화면에 전화번호 가져오기
//        LoadContactsAyscn lca = new LoadContactsAyscn();
//        lca.execute();


                //전화번호부에서 번호 가져오기
                ArrayList<String> contacts = new ArrayList<String>();
                call(contacts);

                //추가 버튼
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        addpersonshow(contacts);
            }
        });


        return view;
 }

    void addpersonshow(ArrayList<String> contacts){

        final Context mContext = getActivity().getApplicationContext();
        final LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.addperson_dialog, (ViewGroup)getActivity().findViewById( R.id.addpersondialog));
        //final bookEntity bookEntity = new bookEntity();

        //실행코드
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        //bookEntity.setType("exp");

        //각 view 별 정의
        final EditText inputname = (EditText)view.findViewById(R.id.inputName);
        final EditText inputphonenum = (EditText)view.findViewById(R.id.inputPhonenumber);
        inputphonenum.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

        Button cancel = (Button) view.findViewById(R.id.cancelbutton);
        Button save = (Button) view.findViewById(R.id.savebutton);


        //클릭했을 때 실행되는 매서드

        cancel.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        dialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(),"취소",Toast.LENGTH_LONG).show();
                    }
                });

        save.setOnClickListener(
                new Button.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            // 정보 저장
                            String name = inputname.getText().toString();
                            String phonenumber = inputphonenum.getText().toString();
                            ContactAdd(name, phonenumber);
                            contacts.add("이       름 : "+ name + "\n"+ "전화번호 : " + phonenumber);
                            call(contacts);
                            Toast.makeText(getActivity().getApplicationContext(),"저장완료",Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                });

    }

    public void call(ArrayList<String> contacts){
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), R.layout.text, contacts);
        adapter.notifyDataSetChanged();
        list1.setAdapter(adapter);
    }

    public void ContactAdd(String name, String phonenum){
                ArrayList<ContentProviderOperation> list = new ArrayList<>();
                try{
                    list.add(
                            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                                    .build()
                    );

                    list.add(
                            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)

                                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)   //이름
                                    .build()

                    );

                    list.add(
                            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)

                                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phonenum)           //전화번호
                                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE  , ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)   //번호타입(Type_Mobile : 모바일)

                                    .build()
                    );
                    getActivity().getApplicationContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, list);  //주소록추가
                    list.clear();   //리스트 초기화
                }catch(RemoteException e){
                    e.printStackTrace();
                }catch(OperationApplicationException e){
                    e.printStackTrace();
                }
    }

}