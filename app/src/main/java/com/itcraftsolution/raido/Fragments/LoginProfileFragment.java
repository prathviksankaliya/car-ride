package com.itcraftsolution.raido.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.itcraftsolution.raido.Activity.MainActivity;
import com.itcraftsolution.raido.R;
import com.itcraftsolution.raido.databinding.FragmentLoginProfileBinding;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class LoginProfileFragment extends Fragment {

    private FragmentLoginProfileBinding binding;
    private ActivityResultLauncher<String> getImageLauncher;
    private Uri photoUri;
    private static final int PERMISSION_ID = 44;
    private String destPath, encodedImageString;
    private Bitmap bitmap;
    private boolean checkImage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginProfileBinding.inflate(getLayoutInflater());
        binding.btnLoginSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edLoginName.getText().toString().length() <= 2)
                {
                    Snackbar.make(binding.loginProfileMainLayout,"Name must be Minimum 3 characters", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setTextColor(getResources().getColor(R.color.white))
                            .show();
                    binding.edLoginName.requestFocus();
                }else if(binding.igLoginPic.getDrawable() == null)
                {
                    Snackbar.make(binding.loginProfileMainLayout,"Please set your profile picture", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .setTextColor(getResources().getColor(R.color.white))
                            .show();
                    binding.igLoginPic.requestFocus();
                }
//                startActivity(new Intent(requireContext(), MainActivity.class));
            }
        });

        binding.igLoginEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermission()) {
                    getImageLauncher.launch("image/*");
                }else{
                    requestStoragePermission();
                }
            }
        });

        getImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result.getPath() != null) {
                    photoUri = result;
                    destPath = UUID.randomUUID().toString() + ".png";

                    UCrop.Options options = new UCrop.Options();

                    options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                    options.setCircleDimmedLayer(true);
                    options.setCompressionQuality(90);


                    UCrop.of(photoUri, Uri.fromFile(new File(requireContext().getCacheDir(), destPath)))
                            .withOptions(options)
                            .withAspectRatio(0, 0)
                            .useSourceImageAspectRatio()
                            .withMaxResultSize(1080, 720)
                            .start(requireContext(), LoginProfileFragment.this);

                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(photoUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        encodeBitmapImageString(bitmap);
                        checkImage = true;

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return binding.getRoot();
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestStoragePermission()
    {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_ID);
    }

    private void encodeBitmapImageString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        binding.igLoginPic.setImageBitmap(bitmap);
        byte[] bytesOfImage =byteArrayOutputStream.toByteArray();
        encodedImageString = android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }
}