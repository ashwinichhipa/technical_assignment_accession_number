package com.ebi.Accession.Controller;

import com.ebi.Accession.Interface.AccessionInterface;
import com.ebi.Accession.Model.AccessionNumbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessionController {
	@Autowired
    AccessionInterface accessionInt;
	
	@GetMapping("/home")
    public String accessionForm(Model model) {
        model.addAttribute("accessionNumbers", new AccessionNumbers());
        return "home";
    }

    @PostMapping("/home")
    public String groupSubmit(@ModelAttribute AccessionNumbers accessionNumbers, Model model) {
        model.addAttribute("aNum", accessionInt.bulkAccessionNumbers(accessionNumbers.getInput()));
        return "result";
    }
    
}
