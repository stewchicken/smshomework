package org.zbc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileExplorerActivity extends ListActivity {

	// private final int

	private final int ACTIVITY_SMS_CREATE = 1;
	private final int ACTIVITY_EDIT_CREATE = 2;

	private List<String> item = null;
	private List<String> path = null;
	private String root = "/";
	private TextView myPath;
	private String TAG = "FileExplorerActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		myPath = (TextView) findViewById(R.id.path);
		getDir(root);
	}

	private void getDir(String dirPath) {
		myPath.setText("Location: " + dirPath);

		item = new ArrayList<String>();
		path = new ArrayList<String>();

		File f = new File(dirPath);
		File[] files = f.listFiles();

		if (!dirPath.equals(root)) {

			item.add(root);
			path.add(root);

			item.add("../");
			path.add(f.getParent());

		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			path.add(file.getPath());
			if (file.isDirectory())
				item.add(file.getName() + "/");
			else
				item.add(file.getName());
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				R.layout.row, item);
		setListAdapter(fileList);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		final File file = new File(path.get(position));

		if (file.isDirectory()) {
			if (file.canRead())
				getDir(path.get(position));
			else {
				new AlertDialog.Builder(this)
						.setIcon(R.drawable.icon)
						.setTitle(
								"[" + file.getName()
										+ "] folder can't be read!")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
									}
								}).show();
			}
		} else {

			if (file.getName().contains("Homework")
					&& file.getName().contains("txt")) {

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setIcon(R.drawable.icon);
				builder.setTitle("[" + file.getName() + "]");
				builder.setPositiveButton("Edit",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								Intent i = new Intent(
										FileExplorerActivity.this,
										NoteEdit.class);
								i.putExtra("FileName", file.getName());
								i.putExtra("FilePath", file.getAbsolutePath());
								FileExplorerActivity.this
										.startActivityForResult(i,
												ACTIVITY_EDIT_CREATE);
							}
						});

				builder.setNeutralButton("SendSMS",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								Intent i = new Intent(
										FileExplorerActivity.this,
										SMSActivity.class);
								i.putExtra("FileName", file.getName());
								i.putExtra("FilePath", file.getAbsolutePath());
								FileExplorerActivity.this
										.startActivityForResult(i,
												ACTIVITY_SMS_CREATE);
							}
						});

				builder.show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
		case ACTIVITY_SMS_CREATE:
			break;
		case ACTIVITY_EDIT_CREATE:
			break;
		}
	}

	public static class SMSReceiver extends BroadcastReceiver {

		private String TAG = "FileExplorerActivity.SMSReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {

			Object[] pdus = (Object[]) intent.getExtras().get("pdus");
			for (Object pdu : pdus) {
				SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
				String sender = message.getOriginatingAddress();
				// String header= message.get
				String content = message.getMessageBody();

				if (content.contains("Homework") && content.contains(".txt")) {
					abortBroadcast();// it is sms belongs to this project, other
										// apps should not be able to see it
				}
				Toast.makeText(context,
						"Sms From Sender  " + sender + " : " + content,
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}