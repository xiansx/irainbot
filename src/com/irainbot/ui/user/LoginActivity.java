package com.irainbot.ui.user;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.irainbot.R;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.ui.main.MainActivity;
import com.irainbot.utils.ToolUtils;

/**
 * 用户登陆
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_logo; // 应用logo
	private EditText et_email; // email
	private EditText et_pwd; // password;
	private TextView tv_login; // 登录
	private TextView tv_forget_pwd; // 忘记密码
	private TextView tv_register; // 注册

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setBackVisibility(View.GONE);
		setTitle(getString(R.string.user_login_title));

		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		et_email = (EditText) findViewById(R.id.et_email);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		tv_login = (TextView) findViewById(R.id.tv_login);
		tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
		tv_register = (TextView) findViewById(R.id.tv_register);

		tv_login.setOnClickListener(this);
		tv_forget_pwd.setOnClickListener(this);
		tv_register.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

	}

	/**
	 * 登录
	 */
	private void doLogin() {
		String email = et_email.getText().toString();
		String password = et_pwd.getText().toString();
		int resid = CheckInput.checkLogin(email, password);

		if (resid <= -1) {
			showDialog("", getString(R.string.user_login_doing), true);
			DataInterface.login(email, password, callback);
		} else {
			showToast("", resid);
		}

	}

	/**
	 * 验证回调 登录
	 */
	private AjaxCallBack<String> callback = new AjaxCallBack<String>() {

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			// TODO Auto-generated method stub
			super.onFailure(t, errorNo, strMsg);
			showToast("", R.string.app_network_error);
			hideDialog();
		}

		@Override
		public void onSuccess(String t) {
			// TODO Auto-generated method stub
			super.onSuccess(t);
			int result = -1;
			hideDialog();
			UserInfo userInfo = null;
			try {
				JSONObject jsonObj = new JSONObject(t);
				String userId = jsonObj.optString("id");
				String email = jsonObj.optString("email");
				String name = jsonObj.optString("name");
				String tempUnit = jsonObj.optString("tempUnit");
				if (!ToolUtils.isEmpty(userId)) {
					result = 0;
					userInfo = new UserInfo(userId, email, et_pwd.getText()
							.toString(), name, "", tempUnit);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 登录失败
			if (result == -1) {
				showToast("", R.string.user_login_failure);
			} else {
				showToast("", R.string.user_login_success);
				UserManagers.getInstance().login(userInfo);
				sendFinishBroadcast(); // 发送广播关闭之前的页面
				gotoAct(MainActivity.class);
				finish();
			}
			
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login: // 登录
			doLogin();
			break;
		case R.id.tv_forget_pwd: // 忘记密码
			gotoAct(ForgotPwdActivity.class);
			break;
		case R.id.tv_register: // 注册
			gotoAct(SignUpActivity.class);
			break;
		}
	}

}
