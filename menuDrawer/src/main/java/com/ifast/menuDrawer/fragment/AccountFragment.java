package com.ifast.menuDrawer.fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ifast.greenDao.Account;
import com.ifast.greenDao.AccountDao;
import com.ifast.greenDao.DaoMaster;
import com.ifast.greenDao.DaoSession;
import com.ifast.menuDrawer.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class AccountFragment extends Fragment {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AccountDao accountDao;

    private SQLiteDatabase sqLiteDatabase;

    EditText user_name;
    EditText password;
    Button btn_add;
    ListView users;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        final EditText user_name = (EditText) rootView.findViewById(R.id.user_name);
        final EditText password = (EditText) rootView.findViewById(R.id.password);
        Button btn_add = (Button) rootView.findViewById(R.id.btn_add);
        ListView users = (ListView) rootView.findViewById(R.id.users);


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(rootView.getContext(), "account-db", null);
        sqLiteDatabase = helper.getWritableDatabase();//官网说，因为这些操作很耗时获取数据库的方法应该是放在后台进程或异步任务中调用。
        daoMaster = new DaoMaster(sqLiteDatabase);
        daoSession = daoMaster.newSession();
        accountDao = daoSession.getAccountDao();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("user_name", user_name.getText().toString());
                Log.i("password", password.getText().toString());
//                addAccount();
                Account account = new Account();
                account.setName(user_name.getText().toString());
                account.setPassword(password.getText().toString());
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
                Log.i("date",df.format(new Date()));
                account.setData(df.format(new Date()));

                accountDao.insert(account);
                Log.d("account No.", "Acount No." + account.getId());
                user_name.setText("");
                password.setText("");
            }
        });
        users.setAdapter(new MyAdapter(rootView.getContext()));
        return rootView;
    }


    private List<Account> loadAll() {
        return accountDao.loadAll();
    }


    private class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return loadAll().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView account_id, account_name, account_password, account_date;

            convertView = inflater.inflate(R.layout.account_list_item, null);
            account_id = (TextView) convertView.findViewById(R.id.user_detail_id);
            account_id.setText(loadAll().get(position).getId().toString());
            account_name = (TextView) convertView.findViewById(R.id.user_detail_name);
            account_name.setText(loadAll().get(position).getName());
            account_password = (TextView) convertView.findViewById(R.id.user_detail_password);
            account_password.setText(loadAll().get(position).getPassword());
            account_date = (TextView) convertView.findViewById(R.id.user_detail_date);
            account_date.setText(loadAll().get(position).getData().toString());

            return convertView;
        }
    }

}
