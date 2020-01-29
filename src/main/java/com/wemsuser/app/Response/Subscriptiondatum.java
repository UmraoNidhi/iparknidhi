package com.wemsuser.app.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscriptiondatum {

    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("package_slug")
    @Expose
    private String packageSlug;
    @SerializedName("package_price")
    @Expose
    private String packagePrice;
    @SerializedName("search_limit")
    @Expose
    private String searchLimit;
    @SerializedName("package_pack")
    @Expose
    private String packagePack;
    @SerializedName("display_text")
    @Expose
    private String displayText;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("renueve_days")
    @Expose
    private String renueveDays;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageSlug() {
        return packageSlug;
    }

    public void setPackageSlug(String packageSlug) {
        this.packageSlug = packageSlug;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getSearchLimit() {
        return searchLimit;
    }

    public void setSearchLimit(String searchLimit) {
        this.searchLimit = searchLimit;
    }

    public String getPackagePack() {
        return packagePack;
    }

    public void setPackagePack(String packagePack) {
        this.packagePack = packagePack;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRenueveDays() {
        return renueveDays;
    }

    public void setRenueveDays(String renueveDays) {
        this.renueveDays = renueveDays;
    }
}
