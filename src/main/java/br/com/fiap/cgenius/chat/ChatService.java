package br.com.fiap.cgenius.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

import br.com.fiap.cgenius.domain.dto.ClienteResponse;
import br.com.fiap.cgenius.domain.model.Cliente;

@Service
public class ChatService {
    final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        você ira criar um script de venda para os clientes informados
                        formate o texto usando a seguinte estrutura:
                        Cliente: <nome>, Perfil: <perfil>, Mensagem: <mensagem>
                        de acordo com o plano indicado, crie o script para convencer o cliente a adquirir o plano

                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                ).build();
    }

    public String sentToAi(Cliente cliente) {
        String prompt = String.format("Cliente: %s, CPF: %s, Email: %s, Mensagem: %s",
        cliente.getNome(),cliente.getPerfil());
        return chatClient.prompt().user(prompt).call().content();
    }

}
