// package br.com.fiap.cgenius.view;
// import org.springframework.beans.factory.annotation.Autowired;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.notification.Notification;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.component.textfield.TextField;
// import com.vaadin.flow.router.Route;

// import br.com.fiap.cgenius.domain.dto.AtendenteRequest;
// import br.com.fiap.cgenius.domain.service.AtendenteService;

// @Route("finalizar")
// public class ConcluiCadastroView extends VerticalLayout {

//     @Autowired
//     private AtendenteService atendenteService;

//     public ConcluiCadastroView() {
//         TextField nomeField = new TextField("Nome");
//         TextField cpfField = new TextField("CPF");
//         TextField setorField = new TextField("Setor");
//         TextField senhaField = new TextField("Senha");
//         TextField perfilField = new TextField("Perfil");
//         TextField emailField = new TextField("Email");
//         Button finalizarButton = new Button("Finalizar Cadastro");

//         finalizarButton.addClickListener(event -> {
//             try {
//                 AtendenteRequest atendenteRequest = new AtendenteRequest(
//                     nomeField.getValue(),
//                     emailField.getValue(),
//                     cpfField.getValue(),
//                     setorField.getValue(),
//                     senhaField.getValue(),
//                     perfilField.getValue()
//                 );
//                 atendenteService.update(atendenteRequest.toModel());
//                 Notification.show("Cadastro finalizado com sucesso");
//             } catch (Exception e) {
//                 Notification.show("Falha ao finalizar cadastro: " + e.getMessage());
//             }
//         });

//         add(nomeField, cpfField, setorField, senhaField, perfilField, emailField, finalizarButton);
//     }
// }