package com.tcm.glossoscopy.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final String phoneNumber;
    private final String phoneCode;

    public SmsCodeAuthenticationToken(String phoneNumber, String phoneCode) {
        super(null);
        this.phoneNumber = phoneNumber;
        this.phoneCode = phoneCode;
        setAuthenticated(false);
    }

    @Override
    public Object getPrincipal() {
        return phoneNumber;
    }

    @Override
    public Object getCredentials() {
        return phoneCode;
    }
}