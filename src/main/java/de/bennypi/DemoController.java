package de.bennypi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoController {

  @GetMapping("/hello")
  public String getIndexPage(Model model) {
    model.addAttribute("greeting", "Hello from the otter side");
    model.addAttribute("message", new Message());
    return "hello";
  }

  @PostMapping("/hello")
  public String showMessage(Model model, @ModelAttribute Message message) {
    model.addAttribute("greeting", "Hello from the otter side");
    model.addAttribute("returnedMessage", message.getMessage());
    model.addAttribute("message", new Message());
    return "hello";
  }
}
