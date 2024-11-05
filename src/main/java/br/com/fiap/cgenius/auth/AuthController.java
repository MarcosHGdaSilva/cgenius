package br.com.fiap.cgenius.auth;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        return authService.login(credentials);
    }

    // @PostMapping("/login")
    // public String handleLogin(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
    //     try {
    //         Credentials credentials = new Credentials(email, password);
    //         Token token = authService.login(credentials);
    //         redirectAttributes.addFlashAttribute("message", "Login bem-sucedido: " + token);
    //         return "redirect:/chat";
    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("error", "Falha no login: " + e.getMessage());
    //         return "redirect:/login";
    //     }
    // }
}
