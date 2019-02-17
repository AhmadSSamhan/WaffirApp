package com.android.waffirapp.model;

import com.android.waffirapp.help.WrapperProfile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileModel {

    private String id;
    private String rowName;
    private int drawable;
    private WrapperProfile.ProfileTag tagName;

    public ProfileModel(String id, String name,WrapperProfile.ProfileTag tagName,int drawable) {
        this.id = id;
        this.rowName = name;
        this.tagName = tagName;
        this.drawable = drawable;
    }

}
