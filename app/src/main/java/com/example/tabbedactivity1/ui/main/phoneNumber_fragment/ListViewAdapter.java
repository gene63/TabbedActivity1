package com.example.tabbedactivity1.ui.main.phoneNumber_fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tabbedactivity1.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Person> PersonList = new ArrayList<Person>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return PersonList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_person" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_person, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView nameView = (TextView) convertView.findViewById(R.id.name) ;
        TextView phoneNumberView = (TextView) convertView.findViewById(R.id.phone_number) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Person person = PersonList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        nameView.setText(person.getName());
        phoneNumberView.setText(person.getPhone_number());
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return PersonList.get(position) ;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String name, String phone_number) {
        Person p = new Person();

        p.setName(name);
        p.setPhone_number(phone_number);

//        Log.d("name", name);
//        Log.d("phone_number", phone_number);
//        Log.d("memo", memo);

        Log.d("@@@@@@@@@@@@", name);
        Log.d("@@@@@@@@@@@@", phone_number);
        PersonList.add(p);
    }



}
