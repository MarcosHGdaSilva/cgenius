package br.com.fiap.cgenius.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import br.com.fiap.cgenius.auth.AuthService;
import br.com.fiap.cgenius.auth.Credentials;
import br.com.fiap.cgenius.auth.Token;

import java.util.Locale;
import java.util.ResourceBundle;

@Route("login")
public class LoginView extends VerticalLayout {

    @Autowired
    private AuthService authService;

    private ResourceBundle messages;

    public LoginView() {
        Locale locale = new Locale("pt", "BR");
        messages = ResourceBundle.getBundle("messages", locale);

        TextField emailField = new TextField(messages.getString("email"));
        PasswordField passwordField = new PasswordField(messages.getString("password"));
        Button loginButton = new Button(messages.getString("login"));
        Button googleLoginButton = new Button(messages.getString("login.google"));

        loginButton.addClickListener(event -> {
            try {
                Credentials credentials = new Credentials(emailField.getValue(), passwordField.getValue());
                Token token = authService.login(credentials);
                Notification.show(messages.getString("login.success") + ": " + token);
                getUI().ifPresent(ui -> ui.navigate("ChatView"));
            } catch (Exception e) {
                System.out.println(e);
                Notification.show(messages.getString("login.failure") + ": " + e.getMessage());
            }
        });

        googleLoginButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.getPage().setLocation("/oauth2/authorization/google"));
        });

        add(emailField, passwordField, loginButton, googleLoginButton);
    }
}