package org.zbc;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSActivity extends Activity {
	private EditText numberText;
	private EditText contentText;
	private String TAG = "SMSActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);

		Bundle extras = getIntent().getExtras();
		String fileName = extras.getString("FileName");
		String filePath = extras.getString("FilePath");
		numberText = (EditText) this.findViewById(R.id.number);
		contentText = (EditText) this.findViewById(R.id.content);
		contentText.setText("Send Comments For " + fileName + ": " + "\n");
		Button sendbutton = (Button) this.findViewById(R.id.sendbutton);
		Button backbutton = (Button) this.findViewById(R.id.backbutton);

		ButtonClickListener bListener = new ButtonClickListener();
		sendbutton.setOnClickListener(bListener);
		backbutton.setOnClickListener(bListener);
		Log.i(TAG, "onCreate");
	}

	private final class ButtonClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.sendbutton) {
				String number = numberText.getText().toString();
				String content = contentText.getText().toString();
				SmsManager manager = SmsManager.getDefault();
				ArrayList<String> texts = manager.divideMessage(content);

				for (String text : texts) {
					manager.sendTextMessage(number, null, text, null, null);
				}

				Toast.makeText(SMSActivity.this, R.string.success,
						Toast.LENGTH_SHORT).show();
			}
			if (v.getId() == R.id.backbutton) {
				SMSActivity.this.finish();
			}

		}

	}

}