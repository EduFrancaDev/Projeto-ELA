E.L.A. - Emergência, Localização e Apoio

📱 Sobre o Projeto

Arquitetura Atual



┌────────────────────┐    ┌──────────────────┐    ┌─────────────────────┐
│  E.L.A. (Frontend) │◄──►│  Servidor Nuvem  │◄──►│ E.L.A. Background    │
│  Android (Kotlin)  │    │  Flask/PostgreSQL│    │ NFC + Rastreamento  │
└────────────────────┘    └──────────────────┘    └─────────────────────┘
│                      │                        │
▼                      ▼                        ▼
Tela Principal         API REST Segura        Leitura NFC Silenciosa
Cadastro/Login         Histórico Global       Localização Contínua
Perfil e Contatos      Split Pagamento        Notificação Interativa



O **E.L.A.** é um aplicativo nativo Android (Kotlin + Material Design) desenvolvido para oferecer suporte rápido e discreto em situações de risco. Com foco em usabilidade, o app permite criar alertas de emergência, cadastrar contatos, rastrear a localização da usuária e gerenciar o histórico dos chamados.

> 💡 O projeto foi idealizado para ser um único app unificado com o `E.L.A. Background`, sendo que ambos estão atualmente em desenvolvimento como componentes separados, mas com integração via backend RESTful.

---

## 🎯 Principais Características

- Chamado de Emergência Ativo: Geração manual e automática via integração NFC.
- Notificações Interativas: Confirmação ou cancelamento com timeout de 60 segundos.
- Perfil da Usuária: Dados básicos e foto.
- Contatos de Emergência: Cadastro de até 5 pessoas confiáveis.
- Histórico de Chamados: Acompanhamento de ações passadas.
- Push Notification e Status em Tempo Real.
- Modo discreto com visual semelhante a apps comuns.

---

🚀 Funcionalidades

🆘 Tela Principal
- Exibição de status do chamado (ativo/inativo).
- Botões: Confirmar, Cancelar, Finalizar Chamado.
- Modo discreto para notificações.

📇 Cadastro e Login
- Formulário com nome, e-mail, senha, CPF, e contato de emergência.
- Upload de foto de perfil.
- Redirecionamento entre cadastro e login.

👥 Contatos de Segurança
- Lista visual com nome, e-mail, telefone e foto.
- Edição e exclusão de contatos.
- Limite: até 5 contatos por perfil.

📚 Histórico
- Cards com data, horário e tipo do chamado (acidental ou intencional).
- Visual moderno e responsivo.

🔔 Notificações
- Envio programado com contagem regressiva.
- Ações confirmadas via BroadcastReceiver.
- Notificações permanentes após confirmação.

---

🏗️ Estrutura do Projeto



app/src/
├── main/
│   ├── java/com/example/appandroid/
│   │   ├── LoginActivity.kt
│   │   ├── MainActivity.kt
│   │   ├── TelaPrincipal.kt
│   │   ├── PerfilActivity.kt
│   │   ├── HistoricoActivity.kt
│   │   ├── ContatosSegurancaActivity.kt
│   │   ├── NotificationHelper.kt
│   │   └── Receivers.kt (Cancel, Confirm, AutoConfirm)
│   └── res/
│       ├── layout/ → Telas XML
│       ├── drawable/ → Estilos visuais
│       ├── font/ → Tipografia personalizada
│       └── values/ → Cores, strings e temas



---

⚙️ Instalação e Execução

Pré-requisitos

- Android Studio Flamingo+
- Kotlin 2.0+
- Gradle 8.11+
- API Mínima: 24 (Android 7.0)
- Permissões**: NFC, Localização, Notificações, Internet

Clonagem e Execução

git clone https://github.com/EduFrancaDev/Projeto-ELA.git
cd Projeto-ELA


1. Abra o projeto no Android Studio
2. Aguarde a indexação e sincronização do Gradle
3. Execute o app com um emulador físico que suporte NFC e Android 10+

---

🔐 Permissões Utilizadas

| Permissão             | Finalidade                  |
| --------------------- | --------------------------- |
| `POST_NOTIFICATIONS`  | Exibir alertas visuais      |
| `CAMERA`              | Captura de foto no cadastro |
| `READ/WRITE_EXTERNAL` | Upload de imagens           |
| `INTERNET`            | Comunicação com backend     |

---

## 🔄 Fluxo do Chamado

1. Usuária inicia um chamado
2. Notificação interativa é exibida
3. Confirmação ou timeout (60s)
4. Chamado é registrado + notificação permanente
5. Usuária finaliza e informa se foi acidental ou intencional
6. Dados são salvos no histórico e enviados ao backend

---

## 🧪 Testes

Instrumented**: `ExampleInstrumentedTest.kt`
Unitários**: `ExampleUnitTest.kt`
Testes manuais com Broadcast ADB para simular NFC:

---

🌐 Integração com Backend


API REST com rotas para:

Criação e encerramento de chamados
Atualização de localização
Consulta de histórico
Token NFC padrão para testes**: `tokendouser123`
Servidor na nuvem** já funcional (ver E.L.A. Background)

---

📚 Documentação Complementar

* `Docs/fluxo_frontend.md`: Explica o ciclo completo da usuária
* `Docs/ux_ui.md`: Justificativa visual das telas
* `Docs/notificacoes.md`: Padrões de uso e usabilidade

---

🗺️ Roadmap Futuro

* [ ] Unificação com E.L.A. Background
* [ ] Autenticação JWT e banco persistente de usuários
* [ ] Suporte a múltiplos perfis de risco
* [ ] Integração com sistema de denúncias e delegacias
* [ ] Dashboard com estatísticas de segurança

---

Desenvolvido com empatia e propósito durante o Hackathon CPBR25 - Mulheres Seguras
Por Douglas Almeida, Eduardo França, Matheus Bueno e João Victor 💜


