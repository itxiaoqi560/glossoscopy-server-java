package com.tcm.glossoscopy.provider;


import com.tcm.glossoscopy.service.impl.UserDetailServiceImpl;
import com.tcm.glossoscopy.token.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private UserDetailServiceImpl userDetailServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String phoneNumber = (String) authentication.getPrincipal();
        String phoneCode = (String) authentication.getCredentials();
        UserDetails userDetails = userDetailServiceImpl.loadUserPhoneNumber(phoneNumber, phoneCode);
        //第一个参数是LoginUser用户信息，第二个参数是凭证，第三个参数是权限信息
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}