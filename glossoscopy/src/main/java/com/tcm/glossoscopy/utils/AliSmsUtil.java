package com.tcm.glossoscopy.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送工具类
 */

@Slf4j
@Data
@AllArgsConstructor
public class AliSmsUtil {

	private String regionId;
	private String accessKeyId;
	private String secret;
	private String templateCode;
	private String signName;
 
	/**
	 * 发送短信
	 * @param phoneNumbers 手机号
	 * @param code 参数
	 */
	public void sendMessage(String phoneNumbers,String code){
		DefaultProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,secret);
		IAcsClient client = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();

		request.setSysRegionId(regionId);
//	    要发送给那个人的电话号码
		request.setPhoneNumbers(phoneNumbers);
//      我们在阿里云设置的签名
		request.setSignName(signName);
//	    我们在阿里云设置的模板
		request.setTemplateCode(templateCode);
//	    在设置模板的时候有一个占位符
		request.setTemplateParam("{\"code\":\""+ code +"\"}");
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			String result=response.getMessage();
			log.info("手机号{}动态码发送结果：{}",phoneNumbers,result);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
 
}