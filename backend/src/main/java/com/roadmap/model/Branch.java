package com.roadmap.model;

public class Branch {

    private int branchId;
    private String branchName;
    private String branchCode;

    public Branch() {
    }

    public Branch(int branchId, String branchName, String branchCode) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchCode = branchCode;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}