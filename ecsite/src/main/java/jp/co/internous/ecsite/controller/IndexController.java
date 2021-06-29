package jp.co.internous.ecsite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.PurchaseRepository;
import jp.co.internous.ecsite.model.dao.RegisterRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.dto.LoginDto;
import jp.co.internous.ecsite.model.dto.RegisterDto;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.Purchase;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.form.UserForm;

@Controller
@RequestMapping("/ecsite")
public class IndexController {
	
	@Autowired
	private UserRepository userRepos;
	

	
	@Autowired
	private GoodsRepository goodsRepos;
	
	@Autowired
	private PurchaseRepository purchaseRepos;
	
	private Gson gson = new Gson();
	
	@RequestMapping("/")
	public String index(Model m) {
		List<Goods> goods = goodsRepos.findAll();
		m.addAttribute("goods", goods);
		
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm form) {
			List<User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
			
			LoginDto dto = new LoginDto(0, null, null, "ゲスト");
			
			/* NULLおよび空チェックしてる？
			usersが袋と仮定する。if(users.size()>0)
			⇒usersの袋に、何か入っていればtrue
			 これは袋の中身を覗いて中身が入っていたらtrueという意味。多分。
			 */
			if (users.size() > 0) {
				dto = new LoginDto(users.get(0));
			}
			return gson.toJson(dto);
				}

//ここに書いてた
	
	
@ResponseBody
@PostMapping("/api/purchase")
public String purchaseApi(@RequestBody CartForm f) {
	
	f.getCartList().forEach((c) -> {
		long total = c.getPrice() * c.getCount();
		purchaseRepos.persist(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
	});
	
	return String.valueOf(f.getCartList().size());
	}

@ResponseBody
@PostMapping("/api/history")
public String historyApi(@RequestBody HistoryForm form) {
	String userId = form.getUserId();
	List<Purchase> history = purchaseRepos.findHistory(Long.parseLong(userId));
	/*なお ArrayList は List インターフェースを実装したクラスなので、次のように List オブジェクトとして作成することもできます。
	  List オブジェクトとして作成した場合は、 ArrayList クラスでのみ定義されているメソッドは利用できなくなりますが、
	  同じ List インターフェースを実装したクラスへ変換する場合などに便利です。 
	 */
	List<HistoryDto> historyDtoList = new ArrayList<>();
	history.forEach((v) -> {
		HistoryDto dto = new HistoryDto(v);
		historyDtoList.add(dto);
	});
	return gson.toJson(historyDtoList);
}

}