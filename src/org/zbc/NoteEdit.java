/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.zbc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEdit extends Activity {

	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private String fileName;
	private String filePath;

	private final int ACTIVITY_SMS_CREATE = 1;
	private final int ACTIVITY_EDIT_CREATE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_edit);
		setTitle(R.string.update);

		mTitleText = (EditText) findViewById(R.id.title);
		mBodyText = (EditText) findViewById(R.id.body);

		Bundle extras = getIntent().getExtras();
		fileName = extras.getString("FileName");
		filePath = extras.getString("FilePath");
		try {
			String fileContent = new FileService(this).read(filePath);
			mTitleText.setText(fileName);
			mBodyText.setText(fileContent);

		} catch (Exception e) {
			Toast.makeText(NoteEdit.this, "Fail to Read File",
					Toast.LENGTH_SHORT).show();
		}

		Button sendbutton = (Button) findViewById(R.id.sendbutton);
		Button backButton = (Button) findViewById(R.id.backbutton);
		ButtonClickListener bListener = new ButtonClickListener();
		backButton.setOnClickListener(bListener);
		sendbutton.setOnClickListener(bListener);

	}

	private final class ButtonClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.backbutton) {
				NoteEdit.this.finish();
			}

			if (v.getId() == R.id.sendbutton) {

				Intent i = new Intent(NoteEdit.this, SMSActivity.class);
				i.putExtra("FileName", fileName);
				i.putExtra("FilePath", filePath);
				NoteEdit.this.startActivityForResult(i, ACTIVITY_SMS_CREATE);

			}

		}

	}
}
