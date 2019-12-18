//The skeleton of this package was learned on YouTube through videos by SimCoder
package com.example.yswipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.example.yswipe.Cards.arrayAdapter;
import com.example.yswipe.Cards.cards;
import com.example.yswipe.Matches.MatchesActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity  {

    private cards cardsData[];
    private com.example.yswipe.Cards.arrayAdapter arrayAdapter;
    private int i;
    private FirebaseAuth mFirebaseAuth;
    private String currentUId;
    private DatabaseReference usersDb;

    ListView listView;
    List<cards> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUId = mFirebaseAuth.getCurrentUser().getUid();
        rowItems = new ArrayList<cards>();
        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);
        checkUserPreferences();

//      The swipecards feature is thanks to Dionysus on GitHub:  https://github.com/Diolor/Swipecards
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                cards object = (cards) dataObject;
                String userId = object.getUserId();
                usersDb.child(userId).child("swiped").child("nah").child(currentUId).setValue(true);
                Toast.makeText(MainActivity.this, "Nah", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards object = (cards) dataObject;
                String userId = object.getUserId();
                usersDb.child(userId).child("swiped").child("yes").child(currentUId).setValue(true);
                Toast.makeText(MainActivity.this, "Yes!", Toast.LENGTH_SHORT).show();
                isSwipedMatch(userId);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void isSwipedMatch(String userId) {
        DatabaseReference currentUserSwipedDb = usersDb.child(currentUId).child("swiped").child("yes").child(userId);
        currentUserSwipedDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(MainActivity.this, "It's a Match!", Toast.LENGTH_LONG).show();
                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    usersDb.child(dataSnapshot.getKey()).child("swiped").child("matches").child(currentUId).child("ChatId").setValue(key);
                    usersDb.child(currentUId).child("swiped").child("matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private String userPreferences;
    private String userGender;
    private String userUid;

    public void checkUserPreferences() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("gender").getValue() != null && dataSnapshot.child("preferences").getValue() != null) {
                        userGender = dataSnapshot.child("gender").getValue().toString();
                        Log.d("usergen", userGender);
                        userPreferences = dataSnapshot.child("preferences").getValue().toString();
                        userUid = user.getUid().toString();
                    }
                    getUserPreferences();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getUserPreferences() {
        usersDb.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              if (dataSnapshot.exists()
                  && !dataSnapshot.getKey().equals(userUid)
                  && dataSnapshot.child("gender").getValue() != null
                  && dataSnapshot.child("preferences").getValue() != null) {
                  if (!dataSnapshot.child("swiped").child("nah").hasChild(currentUId)
                      && !dataSnapshot.child("swiped").child("yes").hasChild(currentUId))
                          if(dataSnapshot.child("gender").getValue().toString().equals(userPreferences)
                            && dataSnapshot.child("preferences").getValue().toString().equals(userGender)
                          || userPreferences.equals("2") && dataSnapshot.child("preferences").getValue().toString().equals("2")
                          || userPreferences.equals("2") && dataSnapshot.child("preferences").getValue().toString().equals(userGender)) {
                          String profilePicUrl = "default";
                          if (!dataSnapshot.child("profilePicUrl").getValue().equals("default")) {
                              profilePicUrl = dataSnapshot.child("profilePicUrl").getValue().toString();
                          }
                          cards item = new cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), profilePicUrl, dataSnapshot.child("resCollege").getValue().toString(), dataSnapshot.child("year").getValue().toString());
                          rowItems.add(item);
                          arrayAdapter.notifyDataSetChanged();
                  }
              }
      }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void logoutUser(View view) {
        mFirebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;

    }

    public void settings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        return;

    }

    public void matches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }

}
