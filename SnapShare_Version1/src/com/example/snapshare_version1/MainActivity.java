package com.example.snapshare_version1;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;







import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected static final int MEDIA_TYPE_IMAGE = 0; 
	private static String TAG = "Camera";
	Uri fileUri = null;
	//ImageView photoImage = null;
	ImageView imageView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button cameraButton = (Button)findViewById(R.id.button1);
		imageView = (ImageView)findViewById(R.id.image_view_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		        fileUri = Uri.fromFile(getOutputPhotoFile());
		        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		        startActivityForResult(i, MEDIA_TYPE_IMAGE );
		        
			}
		});
        //Button stepsButton = (Button)findViewById(R.id.button2);
        //stepsButton.setOnClickListener(stepListener);
	}

private File getOutputPhotoFile() {
		
		  File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"SnapShare");
		  
		  if (!directory.exists()) {
		    if (!directory.mkdirs()) {
		      Log.e(TAG, "Failed to create storage directory.");
		      return null;
		    }
		  }
		  
		  String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());
		  
		  return new File(directory.getPath() + File.separator + "IMG_"  
		                    + timeStamp + ".jpg");
}
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == MEDIA_TYPE_IMAGE) {
		    if (resultCode == RESULT_OK) {
		      Uri photoUri = null;
		      if (data == null) {
		        // A known bug here! The image should have saved in fileUri
		        Toast.makeText(this, "Image saved successfully", 
		                       Toast.LENGTH_LONG).show();
		        photoUri = fileUri;
		      } else {
		        photoUri = data.getData();
		        Toast.makeText(this, "Image saved successfully in: " + data.getData(), 
		                       Toast.LENGTH_LONG).show();
		      }
		      showPhoto(photoUri.getPath());
		    } else if (resultCode == RESULT_CANCELED) {
		      Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
		    } else {
		      Toast.makeText(this, "Callout for image capture failed!", 
		                     Toast.LENGTH_LONG).show();
		    }
		  }
		}
	
	private void showPhoto(String photoUri) {
		  File imageFile = new File (photoUri);
		  if (imageFile.exists()){
		     Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		     BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
		     imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		     imageView.setImageDrawable(drawable);
		  }       
	}
	
	
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	
	/* using camera API
	private void takePhoto() {
		camera1 = Camera.open();
		PictureCallback callback = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				
				File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
				if(file == null)
				{
					
				}
				try
				{
					FileOutputStream stream = new FileOutputStream(file);
					stream.write(data);
					stream.close();
				}
				catch (FileNotFoundException e)
				{
					Log.e(TAG, "File not found: " + e.getMessage());
			        e.getStackTrace();
				}
				catch (IOException e)
				{
					Log.e(TAG, "I/O error writing file: " + e.getMessage());
			        e.getStackTrace();
				}
			}
		};
		camera1.takePicture(null, null, callback);
		
	}
	protected File getOutputMediaFile(int mediaTypeImage) {
		File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());
  if (!dir.exists()) {
    if (!dir.mkdirs()) {
      Log.e(TAG, "Failed to create storage directory.");
      return null;
    }
  }
  String timeStamp = 
      new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
  if (mediaTypeImage == MEDIA_TYPE_IMAGE) {
    return new File(dir.getPath() + File.separator + "IMG_"  
                    + timeStamp + ".jpg");
  } else {
    return null;
  }
	}
*/

}
