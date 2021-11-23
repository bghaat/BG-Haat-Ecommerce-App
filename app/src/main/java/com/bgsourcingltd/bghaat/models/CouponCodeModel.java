package com.bgsourcingltd.bghaat.models;

public class CouponCodeModel {

    private String couponCode;
    private String promoTitle;
    private String expiredDate;
    private String amount;


    public CouponCodeModel() {
    }

    public CouponCodeModel(String couponCode, String promoTitle, String expiredDate, String amount) {
        this.couponCode = couponCode;
        this.promoTitle = promoTitle;
        this.expiredDate = expiredDate;
        this.amount = amount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
