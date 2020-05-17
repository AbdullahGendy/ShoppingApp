
package com.example.shoppingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDetails implements Serializable {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userPhone")
    @Expose
    private String userPhone;

    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("userAddress")
    @Expose
    private String userAddress;
    private final static long serialVersionUID = -8573235671612712718L;

    /**
     * No args constructor for use in serialization
     */
    public UserDetails() {
    }

    /**
     * @param userType
     * @param userAddress
     * @param userPhone
     * @param userName
     */
    public UserDetails(String userName, String userPhone, String userType, String userAddress) {
        super();
        this.userName = userName;
        this.userPhone = userPhone;
        this.userType = userType;
        this.userAddress = userAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userPin) {
        this.userType = userPin;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

}
