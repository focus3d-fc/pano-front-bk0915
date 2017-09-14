package com.focus3d.pano.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focus3d.pano.common.controller.BaseController;
/**
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/contact")
public class ContactUsController extends BaseController {
	/**
	 * 
	 * *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String contactUs(){
		
		return "/member/contactus";
	}
}
