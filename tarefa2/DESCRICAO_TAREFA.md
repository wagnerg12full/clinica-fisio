## Alterações na Avaliação do Paciente

1. Nova Aba: Diagostico e Ações

A nova aba deve conter dois campos descritivos ("Diagnóstico Cinésio Funcional" e "Conduta Fisioterapêutica"), permitindo informar até 1000 carecteres. Campos de preenchimento opcional. 


2. Novo Cadastro: Evolução do Paciente

Esse cadastro faz parte do acompanhamento do paciente a partir da Avaliação. A cada sessão o profissional registra os procedimentos realizados e atual situação do paciente. É um registro de histórico de atendimento fisioterapêutico.

2.1. Detalhes técnicos

* A tabela deve ter os campos id, avaliacao, descricao e data. Todos campos obrigatórios. Gravar data e hora como padrão a data e hora atuais (new Date);

* Seguir as orientações do arquivo ARQUITETURA_BACKEND.md para o desenvolvimento da camada de negócio; 

* Seguir as orientações dos arquivos ESPECIFICACOES_TECNICAS.md e IDENTIDADE_VISUAL.md para o desenvolvimento da camada view;

2.2. Histórias de Usuário

a. Consultar Evolução do Paciente

O usuário logado ao sistema: 
- Clica no menu Avaliação;
- Clica no botão Pesquisar (action avaliacaoBean.pesquisar).

Se o sistema retornar resultados, o usuário logado seleciona um dos registros retornados. Nesse caso, deve ser incluído um novo botão na coluna de Ações (sugestão: um ícone de relógio), para o registro de evolução do paciente.

O usuário logado ao sistema:

- Clica no botão Registrar Evolução (sugestão de action avaliacaoBean.registrarEvolucao());

b. Registrar Evolução do Paciente

O sistema exibe nova tela contendo: 

Registro de Evolução Paciente:
(Nome do paciente)

Descreva abaixo
(Aqui, uma caixa de texto para informar até 1000 caracteres, obrigatório)

Dois botões (Confirmar / Cancelar)

Uma tabela mostrando os registros de evolução associados àquela avaliação de paciente, ordenados de forma decrescente por data.

Se o usuário clicar em Confirmar, o sistema salva o registro e volta para o fluxo principal da avaliação; senão, apenas volta para o fluxo principal sem fazer nada. Para as duas situações solicitar a confirmação do usuário.

O sistema salva o registro associado à avaliação na data e hora atuais. 

Um registro de evolução não será excluído (é um histórico); O usuário poderá registrar quantos registros quiser no mesmo dia para a mesma avaliação; o que importa é que tenha uma exibição cronológica decrescente.


IMPORTANTE:

O layout do projeto já está aprovado, não precisa fazer alterações no layout. Isso quer dizer que você deve somente criar a nova aba e campos para essa aba, conforme descrito na tarefa.
Deve seguir também À risca a implementação do formulário de evolução do paciente da mesma forma de como os outros formulários são implementados.