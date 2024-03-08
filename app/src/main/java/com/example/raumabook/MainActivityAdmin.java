package com.example.raumabook;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm392.entity.History;
import com.example.project_prm392.entity.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityAdmin extends AppCompatActivity {


    FloatingActionButton addNew;
    AdminAdapter adminAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    ArrayList<User> userList;
    RecyclerView rc_user;
    String searchName = "";

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        addNew = findViewById(R.id.btn_add);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchName = newText;
                userData();
                return false;
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityAdmin.this,UserCreateActivity.class));
            }
        });

        rc_user = findViewById(R.id.rc_user);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rc_user.setLayoutManager(mLayout);
        rc_user.setItemAnimator(new DefaultItemAnimator());

        userData();
    }

    private void fileList(String newText) {
        List<User> filteredList = new ArrayList<>();
        for (User user : userList){
            if(user.getUser().toLowerCase().trim().contains(newText.toLowerCase())){
                filteredList.add(user);
            }
        }
        if(filteredList.isEmpty()){
            adminAdapter.setFilteredList(filteredList);
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else {
            adminAdapter.setFilteredList(filteredList);
        }
    }

    private void userData() {
        database.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList = new ArrayList<>();
                for(DataSnapshot item : snapshot.getChildren()){

                    User user = item.getValue(User.class);
                    user.setId(item.getKey());
                    if(searchName.trim().isEmpty()
                        || user.getUser().toLowerCase().contains(searchName.trim().toLowerCase())){
                        userList.add(user);
                    }
                }
                adminAdapter = new AdminAdapter(userList, MainActivityAdmin.this);
                rc_user.setAdapter(new AdminAdapter(userList, new AdminAdapter.OnClickDetails() {
                    @Override
                    public void onClickDetails(User user) {
                        openDialogDetail(user);
                    }
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openDialogDetail(User user) {
        Dialog dialog1= new Dialog(this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.user_details_dialog);
        Window window= dialog1.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog1.setCancelable(false);

        TextView username = dialog1.findViewById(R.id.tv_user_details);
        username.setText(user.getUser());
        EditText address = dialog1.findViewById(R.id.edt_address_details);
        if (user.getAddress() == null) {
            address.setHint("none");
        } else {
            address.setText(user.getAddress());
        }
        EditText phone = dialog1.findViewById(R.id.edt_phone_details);
        if (user.getPhone() == null) {
            phone.setHint("none");
        } else {
            phone.setText(user.getPhone());
        }
        EditText email = dialog1.findViewById(R.id.edt_gmail_details);
        if (user.getEmail() == null) {
            email.setHint("none");
        } else {
            email.setText(user.getEmail());
        }
        if (user.getEmail() == null) {
            email.setHint("none");
        } else {
            email.setText(user.getEmail());
        }
        CircleImageView image = dialog1.findViewById(R.id.imageDetails);
        EditText imageUrl = dialog1.findViewById(R.id.edt_imageurl_details);
        if (user.getImage() == null || user.getImage().isEmpty()) {
            imageUrl.setHint("none");
            Glide.with(this).load("https://png.pngtree.com/element_our/20200610/ourlarge/pngtree-default-avatar-image_2237213.jpg").into(image);
        } else {
            imageUrl.setText(user.getImage());
            Glide.with(this).load(user.getImage()).into(image);
        }

        Spinner spinnerRole = dialog1.findViewById(R.id.sp_role_details);
        Spinner spinnerGender = dialog1.findViewById(R.id.sp_gender_details);

        int userRoleInt = user.getRole();
        String userRole = String.valueOf(userRoleInt);
        String userGender = user.getGender();

        String[] gendersArray = getResources().getStringArray(R.array.genders_array);

        String[] rolesArray = getResources().getStringArray(R.array.roles_array);
        if (userRoleInt >= 1 && userRoleInt <= rolesArray.length) {
            userRole = rolesArray[userRoleInt - 1];
        }

        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rolesArray);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gendersArray);

        spinnerRole.setAdapter(roleAdapter);
        spinnerGender.setAdapter(genderAdapter);

        if (userRole != null) {
            int rolePosition = roleAdapter.getPosition(userRole);
            if (rolePosition >= 0) {
                spinnerRole.setSelection(rolePosition);
            }
        }

        if (userGender != null) {
            int genderPosition = genderAdapter.getPosition(userGender);
            if (genderPosition >= 0) {
                spinnerGender.setSelection(genderPosition);
            }
        }

        Button btn_update_details = dialog1.findViewById(R.id.btn_update_details);
        btn_update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> updateData = new HashMap<>();
                user.setAddress(address.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setImage(imageUrl.getText().toString());
                user.setGender(spinnerGender.getSelectedItem().toString());
                user.setRole((int) spinnerRole.getSelectedItemId());

                updateData.put("address", user.getAddress());
                updateData.put("email", user.getEmail());
                updateData.put("gender", user.getGender());
                updateData.put("phone", user.getPhone());
                updateData.put("role", user.getRole());
                updateData.put("image", user.getImage());
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                db.child(user.getUser()+"").updateChildren(updateData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(MainActivityAdmin.this,"Update successfull",Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }
                });
            }
        });


        Button btn_deleted_details = dialog1.findViewById(R.id.btn_delete_details);
        btn_deleted_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Confirm")
                        .setMessage("Do you want to delete")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.child(user.getUser()+"").removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(MainActivityAdmin.this,"Delete successfull",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                DatabaseReference  newdb = FirebaseDatabase.getInstance().getReference("history");
                                newdb.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                                            History history= dataSnapshot.getValue(History.class);
                                            if(history.getUser().getUser().equals(user.getUser())){
                                                newdb.child(history.getId()).removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                dialog.dismiss();
                                dialog1.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
            }
        });

        Button btn_cancel_details = dialog1.findViewById(R.id.btn_cancel_details);
        btn_cancel_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        dialog1.show();

    }
}