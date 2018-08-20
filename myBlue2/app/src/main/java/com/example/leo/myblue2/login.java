package com.example.leo.myblue2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leo.myblue2.database.DB;
import com.example.leo.myblue2.database.dbitem;
import com.example.leo.myblue2.database.user;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by leo on 2017/6/18.
 */

public class login extends AppCompatActivity {
    EditText account;
    EditText password;
    TextView showerror;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        enter=(Button)findViewById(R.id.enter);
        enter.setOnClickListener(enterListener);
        account=(EditText)findViewById(R.id.account);
        password=(EditText)findViewById(R.id.password);
        showerror=(TextView)findViewById(R.id.showerror);
    }
    View.OnClickListener enterListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DB db=new DB(login.this);
            String accountText=account.getText().toString();
            String passwordText=password.getText().toString();
            db.loginQuery(accountText,passwordText,new DB.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<dbitem> result) {
                    if(result.size()>0) {
                        user u = (user) result.get(0);
                        Log.e("LEOgo", u.getAccount() + "/" + u.getPassword());
                        if (accountText.equals(u.getAccount()) && passwordText.equals(u.getPassword())) {
                            //Navigator.INSTANCE.navigateToSecond(login.this,"hello");
                            Navigator.INSTANCE.navigateToPermission(login.this, u.getUserid());
                            showerror.setVisibility(View.GONE);
                        }
                        else {
                            Log.e("LEOgo", "帳號密碼錯誤");
                        }

                    }else{
                        showerror.setVisibility(View.VISIBLE);
                        //db.saveTemperature("1","37.5");
                    }
                }
            });
        }
    };
}
