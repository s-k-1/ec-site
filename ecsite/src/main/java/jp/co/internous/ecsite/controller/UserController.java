package jp.co.internous.ecsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.dao.RegisterRepository;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.UserForm;


@Controller 
@RequestMapping("/ecsite")
public class UserController {
	
	
	@Autowired 
	private RegisterRepository registerRepos;
	
	//adminindex.htmlの登録ボタンをするとこのページにいく→OK
	@RequestMapping("/admin/register")
	public String register() {
		return "register";
	}
	
	//duplicatedは重複、二重の意味！
	@RequestMapping("/duplicatedUserName")
	@ResponseBody
	public boolean duplicatedUserName(@RequestBody UserForm f) {
	//findCount・・・見つけて数える？
	//int count・・・個数をカウントする変数を宣言？
		int count = registerRepos.findCountByUserName(f.getUserName());
		return count > 0; //0より多かった時、二重の意味？
	}
	


	@RequestMapping("/registeUser")
	public String registeUserApi(UserForm f) {
		User user = new User();
		user.setUserName(f.getUserName());
		user.setPassword(f.getPassword());
		user.setFullName(f.getFullName());
		registerRepos.saveAndFlush(user);
	
		return "adminindex";
	}
}

