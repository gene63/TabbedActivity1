package com.example.tabbedactivity1.ui.main.bookkeeper_fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.slice.Slice;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.tabbedactivity1.MainActivity;
import com.example.tabbedactivity1.R;
import com.example.tabbedactivity1.data.bookDatabase;
import com.example.tabbedactivity1.data.bookEntity;
import com.example.tabbedactivity1.data.dbClient;
import com.example.tabbedactivity1.ui.main.phoneNumber_fragment.PageViewModel;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class bookkeeperFragment extends DialogFragment {

    RecyclerView bookkeeperRecycler = null;
    bookkeeperAdapter bookkeeperAdapter = null;
    bookDatabase bookDB = null;

    private PieChartView chart;
    private PieChartData data;


    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;

    public static bookkeeperFragment newInstance(int index) {
        bookkeeperFragment fragment = new bookkeeperFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    int[] colors = {Color.parseColor("#E74C3C")
            , Color.parseColor("#E67E22")
            , Color.parseColor("#F1C40F")
            , Color.parseColor("#2ECC71")
            , Color.parseColor("#1ABC9C")
            , Color.parseColor("#3498D8")
            , Color.parseColor("#9B59B6")};

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

        View root = inflater.inflate(R.layout.bookkeeper_fragment, container, false);
        Button add = root.findViewById(R.id.addbutton);

        setHasOptionsMenu(true);

        chart = (PieChartView) root.findViewById(R.id.pieChart);

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
        final bookEntity bookentity = new bookEntity();
        List<bookEntity> beList;

        //실행코드
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        //각 view 별 정의
        Button inc = (Button) view.findViewById(R.id.incButton);
        Button exp = (Button) view.findViewById(R.id.expButton);

        String repDate = "\n날짜 : " + year + "년 " + month + "월 " + day + "일\n"; // 날짜 만들기
        final int Date = Integer.valueOf(year)*10000 +
                Integer.valueOf(month)*100 +
                Integer.valueOf(day);

        TextView date = (TextView)view.findViewById(R.id.date);
        date.setText(repDate);
        bookentity.setType("exp");

        final EditText inputPrice = (EditText)view.findViewById(R.id.inputPrice);
        inputPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        Button changeDate = (Button)view.findViewById(R.id.datebutton);
        Button yes = (Button)view.findViewById(R.id.yesbutton);
        Button no = (Button)view.findViewById(R.id.nobutton);

        final Spinner spinner = (Spinner)view.findViewById((R.id.category));

        //클릭했을 때 실행되는 매서드

        inc.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        bookentity.setType("inc");
                    }
                });

        exp.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        bookentity.setType("exp");
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
                        String category = spinner.getSelectedItem().toString();
                        bookentity.setValue(Integer.valueOf(price));
                        bookentity.setDate(Date);
                        bookentity.setCategory(category);
                        // adding to DB
                        Log.d(bookentity.getType(),"1");

                        dbClient.getInstance(getActivity().getApplicationContext())
                                .getBookDB()
                                .bookDAO()
                                .insertData(bookentity);

                        Log.d(bookentity.getType(),"2");

                        getBEs();

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
                bookkeeperAdapter.setItemClick(new bookkeeperAdapter.ItemClick() {
                    @Override
                    public void onClick(View view, int position) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("수입/지츨 내역 삭제");
                        builder.setMessage("정말 삭제하시겠습니까?");
                        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbClient.getInstance(getActivity().getApplicationContext())
                                        .getBookDB()
                                        .bookDAO()
                                        .deleteData(bookkeeperAdapter.getBookList().get(position));
                                getBEs();
                                Toast.makeText(getActivity().getApplicationContext(),"삭제완료",Toast.LENGTH_LONG).show();
                            }
                        });

                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity().getApplicationContext(),"취소됨",Toast.LENGTH_LONG).show();
                            }
                        });

                        builder.show();
                    }
                });

                bookkeeperRecycler.setAdapter(bookkeeperAdapter);

                int[] balanceList = getBalance(bookList);
                int sumBalance = getSum(balanceList);
                List<SliceValue> SVList = getSVList(balanceList);
                TextView balanceView = getActivity().findViewById(R.id.balance);
                balanceView.setText(Integer.toString(sumBalance));
                data = new PieChartData(SVList);
                data.setHasCenterCircle(true);
                data.setHasLabels(true);
                chart.setPieChartData(data);

                super.onPostExecute(bookList);
            }
        }

        GetBEs gb = new GetBEs();
        gb.execute();
    }

    int[] getBalance(List<bookEntity> bookList) {
        int len = bookList.size();
        int[] sum = {0, 0, 0, 0, 0, 0, 0, 0};
        for(int i=0; i<len; i++){
            bookEntity be = bookList.get(i);
            if(be.getType().equals("inc")){
                sum[7] += be.getValue();
            }
            else{
                switch(be.getCategory()){
                    case "식비":
                        sum[0] += be.getValue();
                        break;
                    case "교통":
                        sum[1] += be.getValue();
                        break;
                    case "문화":
                        sum[2] += be.getValue();
                        break;
                    case "주거/통신":
                        sum[3] += be.getValue();
                        break;
                    case "의료":
                        sum[4] += be.getValue();
                        break;
                    case "경조/선물":
                        sum[5] += be.getValue();
                        break;
                    case "기타":
                        sum[6] += be.getValue();
                        break;
                }
            }
        }
        return sum;
    }

    int getSum(int[] arr){
        int len = arr.length;
        int sum = 0;
        for(int i=0; i<(len-1); i++){
            sum -= arr[i];
        }
        sum += arr[len-1];
        return sum;
    }

    List<SliceValue> getSVList(int[] arr){
        List<SliceValue> SVList = new ArrayList<SliceValue>();
        for(int i=0; i<7; i++){
            SliceValue sv = new SliceValue((float)arr[i], colors[i]);
            SVList.add(sv);
        }
        return SVList;
    }
}



