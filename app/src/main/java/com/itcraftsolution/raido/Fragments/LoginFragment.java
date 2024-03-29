package com.itcraftsolution.raido.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.itcraftsolution.raido.R;
import com.itcraftsolution.raido.databinding.FragmentLoginBinding;
import com.itcraftsolution.raido.spf.SpfUserData;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private GoogleSignInClient googleSignInClient;
    private ActivityResultLauncher<Intent> signInActivityLauncher;
    private FirebaseAuth auth;
    private String userNumber, verifyId, userNumberWithoutCode;
    private SpfUserData spfUserData;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(getLayoutInflater());

        createGoogleRequest();
        spfUserData = new SpfUserData(requireContext());
        auth = FirebaseAuth.getInstance();

        binding.btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edLoginPhoneNumber.getText().toString().length() != 10) {
                    binding.edLoginPhoneNumber.setError("Plz Enter Valid Number!!");
                } else {
                    userNumber = "+91 " + binding.edLoginPhoneNumber.getText().toString();
                    userNumberWithoutCode = binding.edLoginPhoneNumber.getText().toString();
                    binding.llVerifyOtp.setVisibility(View.VISIBLE);
                    binding.llGetOpt.setVisibility(View.GONE);
                    sendVerificationCode(userNumber);
                }
            }
        });

        binding.btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkOtp()) {
                    verifyCode(binding.otpView.getOTP());
                }

            }
        });

        binding.btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

        signInActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        signInWithGoogleAccount(account);
                    } catch (ApiException a) {

                    }
                }
            }
        });
        return binding.getRoot();
    }

    private void createGoogleRequest() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.firebaseClientId))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);
    }

    private void signInWithGoogle() {
        Intent intent = googleSignInClient.getSignInIntent();
        signInActivityLauncher.launch(intent);
    }

    private void signInWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    getParentFragmentManager().beginTransaction().replace(R.id.frLoginContainer,
                            new LoginProfileFragment()).addToBackStack(null).commit();
                    Toast.makeText(requireContext(), "Email: " + account.getEmail(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Sign In with Phone Number
    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).
                setActivity(requireActivity())
                .setPhoneNumber(phoneNumber)
                .setCallbacks(callbacks)
                .setTimeout(30L, TimeUnit.SECONDS)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                binding.otpView.setOTP(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(requireContext(), "" + e.toString(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verifyId = s;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    spfUserData.setSpfUserLoginDetails(null, null, null, userNumberWithoutCode, null, null);
                    getParentFragmentManager().beginTransaction().replace(R.id.frLoginContainer,
                            new LoginProfileFragment()).addToBackStack(null).commit();
                } else {
                    Toast.makeText(requireContext(), "" + Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkOtp() {
        boolean condition = true;
        if (binding.otpView.getOTP().length() != 6) {
            Toast.makeText(getContext(), "Fill The OTP", Toast.LENGTH_SHORT).show();
            condition = false;
        }
        return condition;
    }


}