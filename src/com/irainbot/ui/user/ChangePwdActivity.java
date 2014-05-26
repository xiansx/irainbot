package com.irainbot.ui.user;

import net.tsz.afinal.http.AjaxCallBack;

import com.irainbot.R;
import com.irainbot.action.iface.DataInterface;
import com.irainbot.action.user.CheckInput;
import com.irainbot.action.user.UserManagers;
import com.irainbot.entity.UserInfo;
import com.irainbot.ui.BaseActivity;
import com.irainbot.utils.ToolUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 修改密码
 */
public class ChangePwdActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_logo; // 应用logo
	private TextView tv_user_email; // 登录邮箱
	private EditText et_old_pwd; // password;
	private EditText et_new_pwd; // password;
	private EditText et_pwd_confirm; // password confirm;
	private TextView tv_change_pwd_submit; // 注册

	private UserInfo userinfo; // 用户信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_change_pwd);
		initView();
		initData();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setTitle(getString(R.string.user_change_pwd_title));

		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		tv_user_email = (TextView) findViewById(R.id.tv_user_email);
		et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
		et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
		et_pwd_confirm = (EditText) findViewById(R.id.et_pwd_confirm);
		tv_change_pwd_submit = (TextView) findViewById(R.id.tv_change_pwd_submit);

		tv_change_pwd_submit.setOnClickListener(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 显示登录邮箱
		userinfo = UserManagers.getInstance().getUserInfo();
		if (userinfo != null) {
			String email = userinfo.getEmail();
			if (!ToolUtils.isEmpty(email))
				tv_user_email.setText(email);
		}
	}

	/**
	 * 修改密码
	 */
	private void doChangePwd() {
		String oldpwd = et_old_pwd.getText().toString();
		String newpwd = et_new_pwd.getText().toString();
		String pwd_confirm = et_pwd_confirm.getText().toString();
		int resid = CheckInput.checkEditPwd(oldpwd, newpwd, pwd_confirm);

		if (resid <= -1) {
			if (userinfo != null) {
				showDialog("", getString(R.string.user_edit_doing), true);
				String userId = userinfo.getUserId();
				DataInterface.updatePwd(userId, oldpwd, newpwd, callback);
			}
		} else {
			showToast("", resid);
		}

	}

	/**
	 * 回调
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
			hideDialog();
			String newpwd = et_new_pwd.getText().toString();
			userinfo.setPassword(newpwd);
			UserManagers.getInstance().login(userinfo);
			showToast("", R.string.user_edit_success);
			finish();

		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_change_pwd_submit: // 修改密码
			doChangePwd();
			break;
		}
	}

}