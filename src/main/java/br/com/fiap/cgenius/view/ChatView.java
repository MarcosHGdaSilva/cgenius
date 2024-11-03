package br.com.fiap.cgenius.view;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import br.com.fiap.cgenius.chat.ChatService;
import br.com.fiap.cgenius.domain.model.Cliente;
import br.com.fiap.cgenius.domain.service.ClienteService;

@Route("chat")
public class ChatView extends VerticalLayout {

    private final ChatService chatService;
    private final ClienteService clienteService;
    private final MessageList messageList = new MessageList();
    private final TextField textField = new TextField();
    private final ComboBox<Cliente> clienteComboBox = new ComboBox<>("Selecione um Cliente");

    public ChatView(ChatService chatService, ClienteService clienteService) {
        this.chatService = chatService;
        this.clienteService = clienteService;

        List<Cliente> clientes = clienteService.findAll();
        clienteComboBox.setItems(clientes);
        clienteComboBox.setItemLabelGenerator(Cliente::getNome);

        Scroller scroller = new Scroller(messageList);
        messageList.getStyle().setBackground("#3a4b61");
        messageList.setHeightFull();
        scroller.setHeightFull();
        scroller.setWidthFull();

        setHeightFull();

        textField.setPlaceholder("Digite uma mensagem");
        textField.setClearButtonVisible(true);
        textField.setPrefixComponent(VaadinIcon.CHAT.create());
        textField.setWidthFull();
        textField.addKeyDownListener(Key.ENTER, event -> {
           send();
        });

        Button button = new Button("Enviar");
        button.setIcon(VaadinIcon.PAPERPLANE.create());
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickListener(clickEvent -> {
            send();
        });

        HorizontalLayout messageInputBar = new HorizontalLayout(textField, button);
        messageInputBar.setWidthFull();

        add(new H1("Chat com IA"));
        add(messageInputBar);
        add(scroller);
    }

    private void send(){
        String userMessage = textField.getValue();
        addMessage(userMessage, "Usuário", 1);
        Cliente cliente = clienteComboBox.getValue();
        if (cliente == null) {
            Notification.show("Por favor, selecione um cliente.");
            return;
        }
        String chatResponse = chatService.sentToAi(cliente);
        addMessage(chatResponse, "IA", 2);

        textField.clear();
    }

    private void addMessage(String message, String user, int color) {
        MessageListItem messageListItem = new MessageListItem(message, Instant.now(), user);
        messageListItem.setUserColorIndex(color);
        var currentMessages = new ArrayList<>(messageList.getItems());
        currentMessages.add(messageListItem);
        messageList.setItems(currentMessages);
    }
}