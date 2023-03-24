package com.tp3.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tp3.AkkaService.AkkaService;

@Controller
public class MRController {

	@GetMapping("/test")
	public String mapreduce() {
		return "test";
	}

	@PostMapping("/init")
	public String initialisation() {
		AkkaService.AKKASERVICE.initialisation();
		return "redirect:/test";
	}

	@PostMapping("/analyse")
	public String analyse(@RequestParam("test") String test, Model model) {
		AkkaService.AKKASERVICE.analyse(test);
		model.addAttribute("test", test);
		return "test";

	}

	@PostMapping("/result")
	public String resultat(@RequestParam("mot") String mot, Model model) {
		int nombre = AkkaService.AKKASERVICE.getWordOccurrences(mot);
		model.addAttribute("mot", mot);
		model.addAttribute("nombre", nombre);
		System.out.println("le mot " + mot + " apparait " + nombre + " fois");
		return "test";

	}

}
