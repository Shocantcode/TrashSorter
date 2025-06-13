package com.example.apkapplication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore firestore;

    private TextView tvDateTime;
    private FirebaseAnalytics firebaseAnalytics;
    private final Handler handler = new Handler();
    private DatabaseReference databaseRef;

    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            String currentDateTime = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss", new Locale("id", "ID"))
                    .format(new Date());
            tvDateTime.setText(currentDateTime);

            // ✅ Simpan timestamp terbaru ke Firebase Realtime DB
            databaseRef.setValue(currentDateTime);

            handler.postDelayed(this, 1000); // Update every second

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        firestore =FirebaseFirestore.getInstance();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvDateTime = findViewById(R.id.tvDateTime);

        // ✅ Inisialisasi Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // ✅ Inisialisasi Realtime Database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseRef = db.getReference("waktu_realtime");

        // 🟢 Dengarkan perubahan dari Realtime DB
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d("FIREBASE_DB", "Data berubah: " + value);
                tvDateTime.setText(value); // Update tampilan dari data Firebase
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("FIREBASE_DB", "Gagal baca data", error.toException());
            }
        });

        updateTimeRunnable.run(); // Start update jam
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimeRunnable);
    }
}
