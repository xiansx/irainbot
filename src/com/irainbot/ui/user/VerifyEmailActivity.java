package com.irainbot.ui.user;

import com.irainbot.R;
import com.irainbot.ui.BaseActivity;
import com.irainbot.utils.ToolUtils;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 发送链接到邮箱
 */
public class VerifyEmailActivity extends BaseActivity implements
		OnClickListener {
	private ImageView iv_logo; // 应用logo
	private TextView tv_ok; // ok返回键
	private TextView tv_verifyemail_lable1; // 提示信息
	private TextView tv_verifyemail_lable3; // 提示信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_verify_email);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setBackVisibility(View.GONE);
		setTitle(getString(R.string.user_verifyemail_title));

		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		tv_verifyemail_lable1 = (TextView) findViewById(R.id.tv_verifyemail_lable1);
		tv_verifyemail_lable3 = (TextView) findViewById(R.id.tv_verifyemail_lable3);

		tv_ok.setOnClickListener(this);

		String frompage = getIntent().getStringExtra("frompage");
		if (!ToolUtils.isEmpty(frompage)) {
			if (frompage.equals(SignUpActivity.class.getName())) { // 注册提示信息
				tv_verifyemail_lable1.setVisibility(View.VISIBLE);
				tv_verifyemail_lable3
						.setText(R.string.user_verifyemail_lable_four);
			} else { // 忘记密码提示信息
				tv_verifyemail_lable1.setVisibility(View.GONE);
				tv_verifyemail_lable3
						.setText(R.string.user_verifyemail_lable_three);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ok: // 确定返回
			gotoAct(LoginActivity.class);
			finish();
			break;
		}
	}

}
