package org.example;

public class User {
    private long charID;
    private String name;
    private UserPress userPress;
    private PhotoOptions userPhotoOption;

    public User(long charID, String name) {
        this.charID = charID;
        this.name = name;
        this.userPress = UserPress.DEFAULT;
        this.userPhotoOption = PhotoOptions.DEFAULT;
    }

    public PhotoOptions getUserPhotoOption() {
        return userPhotoOption;
    }

    public void setUserPhotoOption(PhotoOptions userPhotoOption) {
        this.userPhotoOption = userPhotoOption;
    }

    public UserPress getUserPress() {
        return userPress;
    }

    public void setUserPress(UserPress userPress) {
        this.userPress = userPress;
    }
}
