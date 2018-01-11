package kr.ac.sungshin.parcelbox.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.ac.sungshin.parcelbox.R;
import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.response.User;
import kr.ac.sungshin.parcelbox.network.ApplicationController;
import kr.ac.sungshin.parcelbox.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_edittext_email)
    EditText editTextEmail;
    @BindView(R.id.login_edittext_password)
    EditText editTextPassword;
    @BindView(R.id.login_checkbox_autoLogin)
    CheckBox checkBoxAutoLogin;
    @BindView(R.id.login_checkbox_savingId)
    CheckBox checkBoxSavingId;
    @BindView(R.id.login_button_login)
    Button buttonLogin;
    @BindView(R.id.login_button_signUp)
    Button buttonSignUp;

    private NetworkService service;
    private final String TAG = "LoginActivity";

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        service = ApplicationController.getInstance().getNetworkService();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bindClickListener();
        setUserEmail();
    }

    // 클릭 이벤트 바인딩
    public void bindClickListener() {
        checkBoxAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { checkBoxAutoLogin.setBackgroundResource(R.drawable.check_on); }
                else { checkBoxAutoLogin.setBackgroundResource(R.drawable.check_off); }
            }
        });

        checkBoxSavingId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { checkBoxAutoLogin.setBackgroundResource(R.drawable.check_on); }
                else { checkBoxAutoLogin.setBackgroundResource(R.drawable.check_off); }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                boolean isCheckedForAutoLogin = checkBoxAutoLogin.isChecked();
                boolean isCheckedForSavingId = checkBoxSavingId.isChecked();

                if (!checkValid(email, password)) return;

                final SharedPreferences userInfo = getSharedPreferences("user", MODE_PRIVATE);
                final SharedPreferences.Editor editor = userInfo.edit();

                editor.putBoolean("isCheckedForAutoLogin", isCheckedForAutoLogin);
                editor.putBoolean("isCheckedForSavingId", isCheckedForSavingId);
                editor.apply();

                Login info = new Login(email, password);
                Call<User> checkLogin = service.getLoginResult(info);
                checkLogin.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, response.body().toString());

                            editor.putInt("type", response.body().getType());
                            editor.putString("email", response.body().getEmail());
                            editor.putString("phone", response.body().getPhone());
                            editor.putString("address", response.body().getAddress());
                            editor.putString("company", response.body().getCompany());
                            editor.apply();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setUserEmail () {
        SharedPreferences userInfo = getSharedPreferences("user", MODE_PRIVATE);
        String remainEmail = userInfo.getString("email", "");

        editTextEmail.setText(remainEmail);
    }

    public boolean checkValid(String email, String password) {
        // 빈칸 체크
        if (email.equals("") || password.equals("")) {
            Toast.makeText(getBaseContext(), "이메일과 패스워드를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 이메일 체크
        if (!email.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$")) {
            Toast.makeText(getBaseContext(), "이메일 형식이 맞지 않습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //뒤로가기 버튼 클릭
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            this.backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 가기 키를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }
}
