package com.example.servingwebcontent;
import java.util.Currency;
import java.util.logging.Logger;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class LandingController {


    @GetMapping("/")
	public RedirectView landing( Model model) {
	  return new RedirectView("/getlisting");
	}

    @GetMapping("/getlisting")
	public String getListing(Model model) {
        String urlString = "https://api.exchangerate.host/latest";
		RestTemplate rest=new RestTemplate();
        String res=rest.getForObject(urlString,String.class);
       
    
       model.addAttribute("rates", new Gson().fromJson(res, JsonObject.class).get("rates").getAsJsonObject());
      //  res.ge
		return "listing";
	}

    @GetMapping("/ratecalculate")
	public String calculateRate(@RequestParam(name="amount", required=true, defaultValue="0") String amount,@RequestParam(name="currency", required=false, defaultValue="1") String currency, Model model) {
        String urlString = "https://api.exchangerate.host/latest";
		RestTemplate rest=new RestTemplate();
        String res=rest.getForObject(urlString,String.class);
             //compute  dollaers
    double dollarValue= Double.parseDouble(amount)/Double.parseDouble(currency);
      model.addAttribute("dolalrValue", dollarValue);
       model.addAttribute("rates", new Gson().fromJson(res, JsonObject.class).get("rates").getAsJsonObject());
  
		return "calculate";
	}


}
