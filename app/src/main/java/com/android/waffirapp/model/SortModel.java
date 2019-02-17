package com.android.waffirapp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SortModel {
    private String id;
    private String title;

    public SortModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

}
