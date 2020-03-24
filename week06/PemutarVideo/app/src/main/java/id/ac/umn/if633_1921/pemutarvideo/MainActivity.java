package id.ac.umn.if633_1921.pemutarvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video = findViewById(R.id.videoView);
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(video);
        video.setMediaController(controller);
        Uri videoUri = Uri.parse("android.resource://" +
                getPackageName() + "/"+ R.raw.profileumn);
        video.setVideoURI(videoUri);

    }
}
