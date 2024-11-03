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

@Route("login")
public class LoginView extends VerticalLayout {

    @Autowired
    private AuthService authService;

    public LoginView() {
        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Senha");
        Button loginButton = new Button("Login");

        loginButton.addClickListener(event -> {
            try {
                Credentials credentials = new Credentials(emailField.getValue(), passwordField.getValue());
                Token token = authService.login(credentials);
                Notification.show("Login bem-sucedido: " + token);
            } catch (Exception e) {
                Notification.show("Falha no login: " + e.getMessage());
            }
        });

        add(emailField, passwordField, loginButton);
    }
}