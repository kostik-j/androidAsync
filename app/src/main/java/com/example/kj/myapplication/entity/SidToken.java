package com.example.kj.myapplication.entity;

final public class SidToken {

    private String mSid;

    public SidToken(String sid) throws Exception {
        if (sid.isEmpty()) {
            throw new Exception("Empty secret");
        }
        mSid = sid;
    }

    public String getSid() {
        return mSid;
    }

    @Override
    public String toString() {
        return getSid();
    }

    public boolean equals(SidToken mSid) {
        return getSid().equals(mSid.getSid());
    }
}
