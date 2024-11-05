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
                        Cliente: <nome>, Perfil: <perfil> de acordo com o plano indicado e seus detalhamentos,
                        crie o script para convencer o cliente a adquirir o plano.

                        Plano  Serenidade((30 a 50 anos):

                        a) Proteção Financeira:
                        Seguro contra perda de renda por 3 meses
                        Cobertura para compras de alto valor
                        Alertas de gastos excessivos em tempo real
                        b) Benefícios Profissionais:
                        Acesso a espaços de coworking premium
                        Cursos online de gestão financeira e liderança
                        Networking events trimestrais
                        c) Vantagens Família:
                        Cashback em contas de serviços essenciais (luz, água, internet)
                        Descontos em planos de saúde e educação
                        Cobertura estendida para compras familiares

                        Plano Equilíbrio(acima de 50 anos):
                        a) Proteção Financeira:
                        Seguro funeral e assistência familiar
                        Cobertura para tratamentos médicos não cobertos pelo plano de saúde
                        Consultoria de planejamento de aposentadoria
                        b) Benefícios Saúde e Bem-estar:
                        Telemedicina gratuita
                        Descontos em farmácias e exames laboratoriais
                        Programa de atividades físicas para terceira idade
                        c) Vantagens Lifestyle:
                        Descontos em pacotes de viagem
                        Acesso VIP a museus e teatros
                        Serviço de concierge 24/7

                        Plano Conexão (18 a 29 anos):
                        a) Proteção Financeira:
                        Seguro para dispositivos eletrônicos
                        Proteção contra fraudes em compras online
                        Consultoria para planejamento financeiro pessoal
                        b) Benefícios Educação e Carreira:
                        Cursos online gratuitos de habilidades profissionais
                        Mentoria profissional trimestral
                        Descontos em programas de intercâmbio e pós-graduação
                        c) Vantagens Lifestyle:
                        Acesso VIP a festivais de música e cultura
                        Programa de milhas com foco em viagens internacionais
                        Cashback em assinaturas de streaming e apps
                    
                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                ).build();
    }

    public String sentToAi(Cliente cliente) {
        String prompt = String.format("Cliente: %s, Perfil: %s",
        cliente.getNome(),cliente.getPerfil());
        String script = chatClient.prompt().user(prompt).call().content();
        return script;
    }

}
