package kr.ac.sungshin.parcelbox.login;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.ac.sungshin.parcelbox.R;
import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.response.FindingInfo;
import kr.ac.sungshin.parcelbox.network.ApplicationController;
import kr.ac.sungshin.parcelbox.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hyeona on 2018. 1. 13..
 */

public class FindIdFragment extends Fragment {

    @BindView(R.id.findid_button_findid)
    Button buttonFindId;
    @BindView(R.id.findid_edittext_name)
    EditText editTextName;
    @BindView(R.id.findid_edittext_phone)
    EditText editTextPhone;

    private NetworkService service;
    private final String TAG = "FindIdFragment";

    public FindIdFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_id, container, false);
        service = ApplicationController.getInstance().getNetworkService();
        ButterKnife.bind(this, view);

        buttonFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();

                if (!checkValid(name, phone)) return ;

                int phoneNumber = Integer.parseInt(phone);
                Login info = new Login(name, phoneNumber);
                Call<FindingInfo> findId = service.getFindIdResult(info);
                findId.enqueue(new Callback<FindingInfo>() {
                    @Override
                    public void onResponse(Call<FindingInfo> call, Response<FindingInfo> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, response.body().toString());
                            String message = response.body().getMessage();
                            if (message.equals("EXIST_MEMBER")) {
                                Intent intent = new Intent(getContext(), ShowIdActivity.class);
                                intent.putExtra("info", response.body().getId());

                                //activity stack 비우고 새로 시작하기
                                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                                    //안드로이드 버전이 진저브레드가 아니면,
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                } else {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }

                                startActivity(intent);
                            } else if (message.equals("NO_USER")) {
                                Toast.makeText(getContext(), "입력하신 회원정보는 존재하지 않습니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                            } else if (message.equals("FAILURE")) {
                                Toast.makeText(getContext(), "회원정보를 찾을 수 없습니다 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<FindingInfo> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }

    public boolean checkValid(String name, String phone) {
        if (name.matches("/^[0-9]*$/") || name.equals("")) {
            Toast.makeText(getContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.contains("-")) {
            Toast.makeText(getContext(), "핸드폰 번호는 - 없이 숫자만 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.equals("")) {
            Toast.makeText(getContext(), "핸드폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
