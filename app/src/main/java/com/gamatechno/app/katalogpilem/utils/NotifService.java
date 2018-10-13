package com.gamatechno.app.katalogpilem.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.model.MovieCatalogueData;
import com.gamatechno.app.katalogpilem.model.OtherData;
import com.gamatechno.app.katalogpilem.network.MovieClient;
import com.gamatechno.app.katalogpilem.view.DetailActivity;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifService extends GcmTaskService {

    public static String TAG_TASK_UPCOMING = "upcoming movies";

    private MovieClient client = ApiUtils.getAPIService();

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {
            loadData();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }

        return result;
    }

    private void loadData() {

        String api_key = "6cbbb575d03419c61482de70c8706aae";
        String language = "en-US";

        client.getUpcoming(api_key, language).enqueue(new Callback<OtherData>() {
            @Override
            public void onResponse(Call<OtherData> call, Response<OtherData> response) {
                if (response.isSuccessful()){
                    List<MovieCatalogueData> data = response.body().getMovies();
                    int position = new Random().nextInt(data.size());
                    
                    MovieCatalogueData catalogueData = data.get(position);
                    String title = catalogueData.getTitle();
                    String msg = catalogueData.getOverview();
                    int notifId = 200;

                    showNotification(getApplicationContext(), title, msg, notifId, catalogueData);
                } else loadFailed();
            }

            @Override
            public void onFailure(Call<OtherData> call, Throwable t) {

            }
        });
    }

    private void loadFailed() {
        Toast.makeText(this, "Error get data", Toast.LENGTH_SHORT).show();
    }

    private void showNotification(Context context, String title, String message, int notifId, MovieCatalogueData data) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("backdrop", data.getBackdropPath());
        intent.putExtra("overview", data.getOverview());
        intent.putExtra("release_date", StringUtils.dateConverter(data.getReleaseDate()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
