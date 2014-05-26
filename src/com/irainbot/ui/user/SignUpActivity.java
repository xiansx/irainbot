package com.irainbot.ui.user;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONObject;

import com.irainbot.R;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.utils.ToolUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 注册用户
 */
public class SignUpActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_logo; // 应用logo
	private EditText et_name; // name
	private EditText et_email; // email
	private EditText et_pwd; // password;
	private EditText et_pwd_confirm; // password confirm;
	private TextView tv_register; // 注册

	private UserInfo userinfo; // 登录用户

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_reg);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.user_reg));

		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		et_name = (EditText) findViewById(R.id.et_name);
		et_email = (EditText) findViewById(R.id.et_email);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		et_pwd_confirm = (EditText) findViewById(R.id.et_pwd_confirm);
		tv_register = (TextView) findViewById(R.id.tv_register);

		tv_register.setOnClickListener(this);
	}

	/**
	 * 登录
	 */
	private void doReg() {
		String name = et_name.getText().toString();
		String email = et_email.getText().toString();
		String password = et_pwd.getText().toString();
		String pwd_confirm = et_pwd_confirm.getText().toString();
		int resid = CheckInput.checkReg(email, password, pwd_confirm, name);

		if (resid <= -1) {
			userinfo = new UserInfo();
			userinfo.setEmail(email);
			userinfo.setPassword(password);
			userinfo.setName(name);

			showDialog("", getString(R.string.user_reg_doing), true);
			tv_register.setEnabled(false);
			DataInterface.register(userinfo, callback_reg);
		} else {
			showToast("", resid);
		}
	}

	/**
	 * 注册回调
	 */
	private AjaxCallBack<String> callback_reg = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			// TODO Auto-generated method stub
			super.onFailure(t, errorNo, strMsg);
			showToast("", R.string.app_network_error);
			hideDialog();
			tv_register.setEnabled(true);
		}

		@Override
		public void onSuccess(String t) {
			// TODO Auto-generated method stub
			super.onSuccess(t);
			int result = -1;
			String msg = "";
			try {
				JSONObject jsonObj = new JSONObject(t);
				String userId = jsonObj.optString("id");
				if (!ToolUtils.isEmpty(userId)) {
					result = 0;
					userinfo.setUserId(userId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			hideDialog();
			tv_register.setEnabled(true);

			// 注册失败
			if (result == -1) {
				showToast(msg, R.string.user_reg_failure);
			} else {
//				UserManagers.getInstance().login(userinfo);
//				sendFinishBroadcast(); // 发送广播关闭之前的页面

				Intent intent = new Intent(SignUpActivity.this,
						VerifyEmailActivity.class);
				intent.putExtra("frompage", this.getClass().getName());
				startActivity(intent);
				finish();
			}

		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_register: // 注册
			doReg();
			break;
		}
	}

}