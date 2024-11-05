package br.com.fiap.cgenius.view;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import br.com.fiap.cgenius.chat.ChatService;
import br.com.fiap.cgenius.domain.model.Cliente;
import br.com.fiap.cgenius.domain.service.ClienteService;
import br.com.fiap.cgenius.mail.EmailService;

@Route("chat")
public class ChatView extends VerticalLayout {

    private final EmailService emailService;
    private final ChatService chatService;
    private final ClienteService clienteService;
    private final TextField textField = new TextField();
    private final ComboBox<Cliente> clienteComboBox = new ComboBox<>();
    private final ResourceBundle messages;

    @Autowired
    public ChatView(ChatService chatService, ClienteService clienteService, EmailService emailService) {
        this.chatService = chatService;
        this.clienteService = clienteService;
        this.emailService = emailService;

        Locale locale = new Locale("pt", "BR");
        this.messages = ResourceBundle.getBundle("messages", locale);

        clienteComboBox.setLabel(messages.getString("select.client"));

        List<Cliente> clientes = clienteService.findAll();
        clienteComboBox.setItems(clientes);
        clienteComboBox.setItemLabelGenerator(Cliente::getNome);

        Button button = new Button("Enviar");
        button.addClickListener(clickEvent -> send());

        HorizontalLayout messageInputBar = new HorizontalLayout(clienteComboBox, button);
        messageInputBar.setWidthFull();

        add(messageInputBar);
    }

    private void send() {
        Cliente selectedCliente = clienteComboBox.getValue();
        if (selectedCliente == null) {
            Notification.show(messages.getString("select.client"));
            return;
        }

        String chatResponse = chatService.sentToAi(selectedCliente);
        emailService.sendEmailScript(chatResponse);
        add(messages.getString("ai.response").replace("{0}", chatResponse));

        textField.clear();
    }
}