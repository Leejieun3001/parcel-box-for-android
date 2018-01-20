package kr.ac.sungshin.parcelbox.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.ac.sungshin.parcelbox.MainActivity;
import kr.ac.sungshin.parcelbox.R;
import kr.ac.sungshin.parcelbox.delivery.DeliveryActivity;
import kr.ac.sungshin.parcelbox.home.UserHomeActivity;
import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.response.LoginResult;
import kr.ac.sungshin.parcelbox.model.response.User;
import kr.ac.sungshin.parcelbox.network.ApplicationController;
import kr.ac.sungshin.parcelbox.network.NetworkService;
import kr.ac.sungshin.parcelbox.network.UserInfo;
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
    @BindView(R.id.login_textview_findInfo)
    TextView textViewFindInfo;

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

        checkAutoLogin();
        bindClickListener();
        setUserEmail();
    }

    public void checkAutoLogin () {
        final SharedPreferences userInfo = getSharedPreferences("user", MODE_PRIVATE);
        boolean isAutoLogin = userInfo.getBoolean("isCheckedForAutoLogin", false);
        boolean isSavingId = userInfo.getBoolean("isCheckedForSavingId", false);
        String token = userInfo.getString("token", "");
        if (isAutoLogin && !token.equals("")) {
            ApplicationController.getInstance().setTokenOnHeader(token);

            Intent intent;
            int type = userInfo.getInt("type", 0);
            if (type == 0) { intent = new Intent(getBaseContext(), UserHomeActivity.class); } // 택배기사
            else { intent = new Intent(getBaseContext(), DeliveryActivity.class); }           // 일반 사용자

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            if (isAutoLogin) checkBoxAutoLogin.setChecked(true);
            if (isSavingId) {
                checkBoxSavingId.setChecked(true);
                editTextEmail.setText(userInfo.getString("id", ""));
            }
        }
    }

    // 클릭 이벤트 바인딩
    public void bindClickListener() {
        checkBoxAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { checkBoxAutoLogin.setButtonDrawable(R.drawable.check_on); }
                else { checkBoxAutoLogin.setButtonDrawable(R.drawable.check_off); }
            }
        });

        checkBoxSavingId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { checkBoxSavingId.setButtonDrawable(R.drawable.check_on); }
                else { checkBoxSavingId.setButtonDrawable(R.drawable.check_off); }
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
                Call<LoginResult> checkLogin = service.getLoginResult(info);
                checkLogin.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            if (message.equals("NO_USER")) {
                                Toast.makeText(getBaseContext(), "입력하신 회원정보는 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            } else if (message.equals("INCORRECT")) {
                                Toast.makeText(getBaseContext(), "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            } else if (message.equals("SUCCESS")) {
                                User user = response.body().getUser();
                                int type = user.getType();
                                String token = response.body().getToken();

                                editor.putInt("type", type);
                                editor.putString("id", user.getId());
                                editor.putString("phone", user.getPhone());
                                editor.putString("address", user.getAddress());
                                editor.putString("company", user.getCompany());
                                editor.putString("token", token);
                                editor.apply();

                                ApplicationController.getInstance().setTokenOnHeader(token);

                                Intent intent;
                                if (type == 0) { intent = new Intent(getBaseContext(), UserHomeActivity.class); } // 택배기사
                                else { intent = new Intent(getBaseContext(), DeliveryActivity.class); }           // 일반 사용자

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {

                    }
                });
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
        textViewFindInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), FindInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setUserEmail() {
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
