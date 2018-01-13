package kr.ac.sungshin.parcelbox.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import kr.ac.sungshin.parcelbox.R;
import kr.ac.sungshin.parcelbox.model.request.Join;
import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.response.Result;
import kr.ac.sungshin.parcelbox.model.response.User;
import kr.ac.sungshin.parcelbox.model.response.VerificationCodeResult;
import kr.ac.sungshin.parcelbox.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    @BindView(R.id.join_button_back)
    Button buttonBack;
    @BindView(R.id.join_edittext_id)
    EditText editTextId;
    @BindView(R.id.join_button_duplication)
    Button buttonDuplication;
    @BindView(R.id.join_button_requestcode)
    Button buttonRequestCode;
    @BindView(R.id.join_edittext_checkcode)
    EditText editTextcheckCode;
    @BindView(R.id.join_button_checkcode)
    Button buttonCheckCode;
    @BindView(R.id.join_edittext_password)
    EditText editTextPassword;
    @BindView(R.id.join_edittext_repassword)
    EditText editTextRepassword;
    @BindView(R.id.join_radiogroup_type)
    RadioGroup radioGroupType;
    @BindView(R.id.join_edittext_name)
    EditText editTextName;
    @BindView(R.id.join_edittext_phone)
    EditText editTextPhone;
    @BindView(R.id.join_edittext_address)
    EditText eidtTextAddress;
    @BindView(R.id.join_button_join)
    Button buttonJoin;

    private NetworkService service;
    private final String TAG = "JoinActivity";

    private boolean isDuplicate = false;
    private boolean isCheckEmail = false;
    private  String verificationCode = "";

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);

        bindClickListener();

    }

    //클릭 이벤트 바인딩
    public void bindClickListener() {
        buttonDuplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Result> checkId = service.getIdCheck(editTextId.getText().toString());
                    checkId.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()) {
                                Log.d(TAG, response.body().toString());
                                if (response.body().getMessage().equals("no duplication")) {
                                    Toast.makeText(getApplicationContext(), "사용가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                    isDuplicate = true;
                                }
                                if (response.body().getMessage().equals("duplicated")) {
                                    Toast.makeText(getApplicationContext(), "이미 사용중인 이메일이 존재합니다. 다른 이메일로 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                    isDuplicate = false;
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });

            }
        });

        buttonRequestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final Call<VerificationCodeResult> checkNumber = service.getChecknumber(editTextId.getText().toString());
               checkNumber.enqueue(new Callback<VerificationCodeResult>() {
                   @Override
                   public void onResponse(Call<VerificationCodeResult> call, Response<VerificationCodeResult> response) {
                       if(response.isSuccessful()){
                           Log.d(TAG, response.body().toString());
                           if (response.body().getMessage().equals("duplicated")){
                               Toast.makeText(getApplicationContext(), "이미 사용중인 이메일이 존재합니다. 다른 이메일로 시도해 주세요.", Toast.LENGTH_SHORT).show();
                           }
                           if(response.body().getMessage().equals("failuire")){
                               Toast.makeText(getApplicationContext(), "알수 없는 오류 입니다.", Toast.LENGTH_SHORT).show();
                           }
                           if(response.body().getMessage().equals("email success")){
                               verificationCode = response.body().getVerificationCode().toString();
                               Toast.makeText(getApplicationContext(), "인증번호가 메일로 발송되었습니다. 확인후 입력해 주세요.", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }
                   @Override
                   public void onFailure(Call<VerificationCodeResult> call, Throwable t) {

                   }
               });
            }
        });
        buttonCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editTextcheckCode.getText().toString();
                if(code.equals(verificationCode)){
                    isCheckEmail = true;
                    Toast.makeText(getApplicationContext(), "인증 되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();
                String password = editTextPassword.getText().toString();
                String repassword = editTextRepassword.getText().toString();
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String address = eidtTextAddress.getText().toString();
                int typeId = radioGroupType.getCheckedRadioButtonId();
                RadioButton radionbuttonType = (RadioButton) findViewById(typeId);
                String type = radionbuttonType.getText().toString();

                if (!checkValid(id, password, repassword, type, name, phone, address )) return;

                Toast.makeText(getBaseContext(), "확인완료 입력해주세요."+type, Toast.LENGTH_SHORT).show();

            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    //유효성 체크
    public boolean checkValid(String id, String password, String repassword ,String type, String name, String phone, String address) {
        // 빈칸 체크
        if (id.equals("") ) {
            Toast.makeText(getBaseContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ( password.equals("")|| repassword.equals("")){
            Toast.makeText(getBaseContext(), "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(type.equals("")){
            Toast.makeText(getBaseContext(), "타입을 선택해 주세요." ,Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.equals("") || name.length() >10){
            Toast.makeText(getBaseContext(), "이름을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(phone.equals("")){
            Toast.makeText(getBaseContext(), "전화번호를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(address.equals("")) {
            Toast.makeText(getBaseContext(), "주소를 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        //비밀번호 일치 체크
        if(!password.equals(repassword)) {
            Toast.makeText(getBaseContext(), "비밀번호와 비밀번호확인이 일치하지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        //이메일 유효성 체크
        if (!id.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$")) {
            Toast.makeText(getBaseContext(), "이메일 형식이 맞지 않습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        //비밀번호 체크
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")){
            Toast.makeText(getBaseContext(), "비밀번호는 8자리이상 영문 숫자 조합입니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isCheckEmail){
            Toast.makeText(getBaseContext(), "이메일 인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isDuplicate){
            Toast.makeText(getBaseContext(), "이메일 중복 체크를 하지 않으셨습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
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
