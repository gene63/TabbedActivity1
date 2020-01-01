package com.example.tabbedactivity1.ui.main.bookkeeper_fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.tabbedactivity1.R;
import com.example.tabbedactivity1.data.bookDAO;
import com.example.tabbedactivity1.data.bookDatabase;
import com.example.tabbedactivity1.data.bookEntity;
import com.example.tabbedactivity1.data.dbClient;
import com.example.tabbedactivity1.ui.main.phoneNumber_fragment.PageViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class bookkeeperFragment extends DialogFragment {

    RecyclerView bookkeeperRecycler = null;
    bookkeeperAdapter bookkeeperAdapter = null;
    bookDatabase bookDB = null;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;

    public static bookkeeperFragment newInstance(int index) {
        bookkeeperFragment fragment = new bookkeeperFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 3;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bookkeeper, container, false);
        Button add = root.findViewById(R.id.addbutton);



        //오늘날짜 가져오기
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        final String year = yearFormat.format(currentTime);
        final String month = monthFormat.format(currentTime);
        final String day = dayFormat.format(currentTime);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(year, month, day, true);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        // database instance
        bookDB = Room.databaseBuilder(this.getContext(),
                bookDatabase.class,
                "book_database").build();
        bookkeeperRecycler = (RecyclerView) getView().findViewById(R.id.bookkeeperRecycler);
        bookkeeperRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        getBEs();
    }

    void show(final String year, final String month, final String day, final boolean addsub){

        final Context mContext = getActivity().getApplicationContext();
        final LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_dialog, (ViewGroup)getActivity().findViewById( R.id.customdialog));
        final bookEntity bookEntity = new bookEntity();

        //실행코드
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        bookEntity.setType("exp");

        //각 view 별 정의
        Button inc = (Button) view.findViewById(R.id.incButton);
        Button exp = (Button) view.findViewById(R.id.expButton);

        String repDate = "\n날짜 : " + year + "년 " + month + "월 " + day + "일\n"; // 날짜 만들기
        final String strDate = year + "." + month + "." + day;

        TextView date = (TextView)view.findViewById(R.id.date);
        date.setText(repDate);
        final TextView as = (TextView)view.findViewById(R.id.price);
        if(addsub){
            as.setText("수입");
            as.setTextColor(Color.parseColor("#0000FF"));
        }
        else{
            as.setText("지출");
            as.setTextColor(Color.parseColor("#FF0000"));
        }
        final EditText inputPrice = (EditText)view.findViewById(R.id.inputPrice);
        inputPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        Button changeDate = (Button)view.findViewById(R.id.datebutton);
        Button yes = (Button)view.findViewById(R.id.yesbutton);
        Button no = (Button)view.findViewById(R.id.nobutton);

        //클릭했을 때 실행되는 매서드

        inc.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        as.setText("수입");
                        as.setTextColor(Color.BLUE);
                        bookEntity.setType("inc");
                    }
                });

        exp.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        as.setText("지출");
                        as.setTextColor(Color.RED);
                        bookEntity.setType("exp");
                    }
                });

        changeDate.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        DateOnClickHandler(getView(),year,month,day,addsub);
                        dialog.cancel();
                    }
                }
        );

        yes.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        String price = inputPrice.getText().toString();
                        bookEntity.setValue(price);
                        bookEntity.setDate(strDate);

                        // adding to DB
                        dbClient.getInstance(getActivity().getApplicationContext())
                                .getBookDB()
                                .bookDAO()
                                .insertData(bookEntity);

                        Toast.makeText(getActivity().getApplicationContext(),"저장완료",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                }
        );

        no.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(getActivity().getApplicationContext(),"입력취소",Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                }
        );
    }

    public void DateOnClickHandler(View view, String year, String month, String day, final boolean as)
    {
        DatePickerDialog.OnDateSetListener callbackMethod;
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String newYear =String.valueOf(year);
                String newMonth =String.valueOf(month+1);
                String newDay =String.valueOf(dayOfMonth);
                show(newYear, newMonth, newDay, as);
            }
        },  Integer.parseInt(year),  Integer.parseInt(month)-1,  Integer.parseInt(day));

        dialog.show();
    }

    private void getBEs() {
        class GetBEs extends AsyncTask<Void, Void, List<bookEntity>> {

            @Override
            protected List<bookEntity> doInBackground(Void... voids) {
                List<bookEntity> bookList = dbClient
                        .getInstance(getActivity().getApplicationContext())
                        .getBookDB()
                        .bookDAO()
                        .getAllBookEntity();
                return bookList;
            }

            @Override
            protected void onPostExecute(List<bookEntity> bookList) {
                bookkeeperAdapter = new bookkeeperAdapter(bookList, getActivity());
                bookkeeperRecycler.setAdapter(bookkeeperAdapter);
                super.onPostExecute(bookList);
                Log.v("attach executed", "");
            }
        }

        GetBEs gb = new GetBEs();
        gb.execute();
    }
}



