package com.example.instagramclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;


public class EditProfileAdapter extends RecyclerView.Adapter<EditProfileAdapter.MyViewHolder>  {
        private List<ProfileModel> profileItems;

        private final MyViewHolder.OnFocusChangeListener focusChangeListener;
        private final MyViewHolder.OnItemClickListener clickListener;



    public EditProfileAdapter(List<ProfileModel> profileItems, MyViewHolder.OnFocusChangeListener focusListener,MyViewHolder.OnItemClickListener clickListener) {
            this.profileItems = profileItems;
        this.focusChangeListener = focusListener;
        this.clickListener = clickListener;

    }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_profile_adapterview, parent, false);
            return new MyViewHolder(view);


        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.setUserTextField(profileItems.get(position));

            holder.backBtn.setOnClickListener(v -> clickListener.onItemClick(v, position));
            holder.imgViewShare.setOnClickListener(v -> clickListener.onItemClick(v, position));
            holder.imgViewShare1.setOnClickListener(v -> clickListener.onItemClick(v, position));
            holder.imgViewShare2.setOnClickListener(v -> clickListener.onItemClick(v, position));
            holder.imgViewShare.setOnClickListener(v -> clickListener.onItemClick(v, position));
           // holder.chosencountiesLayout.setOnClickListener(v -> clickListener.onItemClick(v, position));
            //holder.choseninterestsLayout.setOnClickListener(v -> clickListener.onItemClick(v, position));
            holder.edtProfession.setOnFocusChangeListener((v, hasFocus) -> focusChangeListener.onFocusChange(v, false));
            holder.edtBio.setOnFocusChangeListener((v, hasFocus) -> focusChangeListener.onFocusChange(v, false));
            holder.edtUsername.setOnFocusChangeListener((v, hasFocus) -> focusChangeListener.onFocusChange(v, false));
            holder.edtAge.setOnFocusChangeListener((v, hasFocus) -> focusChangeListener.onFocusChange(v, false));
            holder.edtName.setOnFocusChangeListener((v, hasFocus) -> focusChangeListener.onFocusChange(v, false));

        }

        @Override
        public int getItemCount() {
            return profileItems.size();
        }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imgViewShare, imgViewShare1, imgViewShare2;

            public EditText edtName, edtBio, edtProfession, edtAge, edtUsername;
        ImageButton backBtn;


        ButtonCreator buttonCreator;

            private TableLayout countyLayout, profileHobbiesLayout, sportsLayout, chosencountiesLayout, choseninterestsLayout;


            public MyViewHolder(View itemView) {
                super(itemView);
                buttonCreator = new ButtonCreator(itemView.getContext(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo when clicked get pop up for match choices for that row
                    }
                });

                imgViewShare = itemView.findViewById(R.id.imgViewShare);
                imgViewShare1 = itemView.findViewById(R.id.imgViewShare1);
                imgViewShare2 = itemView.findViewById(R.id.imgViewShare2);





                backBtn = itemView.findViewById(R.id.backbtn);

                edtName = itemView.findViewById(R.id.edtName);
                edtAge = itemView.findViewById(R.id.edtAge);
                countyLayout = itemView.findViewById(R.id.countyLayout);
                edtUsername = itemView.findViewById(R.id.edtUsername);
                edtBio = itemView.findViewById(R.id.edtBio);
                profileHobbiesLayout = itemView.findViewById(R.id.profileHobbiesLayout);
                edtProfession = itemView.findViewById(R.id.edtProfession);
                sportsLayout = itemView.findViewById(R.id.sportsLayout);
                chosencountiesLayout = itemView.findViewById(R.id.chosencountiesLayout);
                choseninterestsLayout = itemView.findViewById(R.id.choseninterestsLayout);
            }

            public interface OnItemClickListener {
                void onItemClick(View view, int position);
            }

            public interface OnFocusChangeListener {
                void onFocusChange(View view, boolean hasFocus);
            }

            void setUserTextField(ProfileModel data) {

                edtName.setText(data.getProfileName());
                edtAge.setText(data.getAge());
                edtUsername.setText(data.getUserName());
                edtBio.setText(data.getProfileBio());
                edtProfession.setText(data.getProfileProfession());
                if(data.getProfileCounty() != null )
                    buttonCreator.buttonCreator(countyLayout, data.getProfileCounty());
                if (data.getProfileHobbies() != null)
                    buttonCreator.buttonCreator(profileHobbiesLayout, data.getProfileHobbies());
                if (data.getSports() != null)
                    buttonCreator.buttonCreator(sportsLayout, data.getSports());
                if (data.getChosencounties() != null)
                    buttonCreator.buttonCreator(chosencountiesLayout, data.getChosencounties());
                if (data.getChoseninterests() != null)
                    buttonCreator.buttonCreator(choseninterestsLayout, data.getChoseninterests());
                if(data.getImage1() != null) {

                    Picasso.get()
                            .load(new File(data.getImage1()))
                            .placeholder(R.drawable.baseline_add_a_photo_24)
                            //.rotate(90)
                            // .fit()
                            //.centerCrop()
                            .into(imgViewShare);
                }
                if(data.getImage2() != null) {

                    Picasso.get()
                            .load(new File(data.getImage2()))
                            .placeholder(R.drawable.baseline_add_a_photo_24)
                            //.rotate(90)
                            // .fit()
                            //.centerCrop()
                            .into(imgViewShare1);
                }
                if(data.getImage3() != null) {
                    Picasso.get()
                            .load(new File(data.getImage3()))
                            .placeholder(R.drawable.baseline_add_a_photo_24)
                            //.rotate(90)
                            // .fit()
                            //.centerCrop()
                            .into(imgViewShare2);
                }

            }



    }
    public void setItems(List<ProfileModel> profileItems) {
        this.profileItems = profileItems;
        notifyDataSetChanged(); // notify the adapter that the data set has changed

    }


}


