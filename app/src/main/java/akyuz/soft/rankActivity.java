package akyuz.soft;




import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.akyzsoft.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class rankActivity extends Activity {


   private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
   private DatabaseReference databaseReference = firebaseDatabase.getReference("users");
    private ArrayList<String> list = new ArrayList<>();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);


        databaseReference.orderByChild("record").limitToFirst(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
              count++;
                User user = dataSnapshot.getValue(User.class);
               list.add(String.valueOf(count)+". "+ String.valueOf(user.username) +" "+String.valueOf(user.record));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
      });



        final ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),
                R.layout.myrow,
                list);

        ListView lv = findViewById(R.id.listview1);
        lv.setAdapter(adapter);

        Handler mh = new Handler();
        mh.postDelayed(new Runnable() {
        @Override
            public void run() {

               adapter.notifyDataSetChanged();
            }
        }, 2000);
       }}