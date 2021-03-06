// Generated by view binder compiler. Do not edit!
package com.example.yabichat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import com.example.yabichat.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final FragmentContainerView mainFragmentContainer;

  @NonNull
  public final TabItem tabChats;

  @NonNull
  public final TabItem tabContacts;

  @NonNull
  public final TabLayout tabLayoutMain;

  private ActivityMainBinding(@NonNull LinearLayout rootView,
      @NonNull FragmentContainerView mainFragmentContainer, @NonNull TabItem tabChats,
      @NonNull TabItem tabContacts, @NonNull TabLayout tabLayoutMain) {
    this.rootView = rootView;
    this.mainFragmentContainer = mainFragmentContainer;
    this.tabChats = tabChats;
    this.tabContacts = tabContacts;
    this.tabLayoutMain = tabLayoutMain;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.main_fragment_container;
      FragmentContainerView mainFragmentContainer = rootView.findViewById(id);
      if (mainFragmentContainer == null) {
        break missingId;
      }

      id = R.id.tab_chats;
      TabItem tabChats = rootView.findViewById(id);
      if (tabChats == null) {
        break missingId;
      }

      id = R.id.tab_contacts;
      TabItem tabContacts = rootView.findViewById(id);
      if (tabContacts == null) {
        break missingId;
      }

      id = R.id.tabLayoutMain;
      TabLayout tabLayoutMain = rootView.findViewById(id);
      if (tabLayoutMain == null) {
        break missingId;
      }

      return new ActivityMainBinding((LinearLayout) rootView, mainFragmentContainer, tabChats,
          tabContacts, tabLayoutMain);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
