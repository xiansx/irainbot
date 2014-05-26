package com.irainbot.ui.user;

import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONObject;

import com.irainbot.R;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
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
 * 忘记密码
 */
public class ForgotPwdActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_logo; // 应用logo
	private EditText et_email; // email
	private TextView tv_forgot_pwd_submit; // 提交

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_forgot_pwd);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.user_forget_pwd_title));

		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		et_email = (EditText) findViewById(R.id.et_email);
		tv_forgot_pwd_submit = (TextView) findViewById(R.id.tv_forgot_pwd_submit);

		tv_forgot_pwd_submit.setOnClickListener(this);
	}

	/**
	 * 发送修改密码链接到邮箱
	 */
	private void doSubmit() {
		String email = et_email.getText().toString();
		int resid = CheckInput.checkEmail(email);

		if (resid <= -1) {
			showDialog("", getString(R.string.user_login_doing), true);
			DataInterface.findPwd(email, callback);
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
			String findpwd = "";
			try {
				JSONObject jsonObj = new JSONObject(t);
				findpwd = jsonObj.optString("findpwd");
				if (!ToolUtils.isEmpty(findpwd)) {
					result = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 登录失败
			if (result == -1) {
				showToast("", R.string.user_login_failure);
			} else {
				String email = et_email.getText().toString();
				ToolUtils.sendEmail(email, "Modify password", findpwd);
				Intent intent = new Intent(ForgotPwdActivity.this,
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
		case R.id.tv_forgot_pwd_submit: // 提交
			doSubmit();
			break;
		}
	}

}
