## Tarefa 1 - Ajustes para a tela de avaliação do paciente: adicionar novas abas com campos descritivos e opções de escolha.

1. As imagens dos arquivos descricao_dores_queixas.jpeg , 
escala_funcional_especifica.jpeg e inclinometria_dinamometria.jpeg servirão de base para o entendimento da execução da tarefa;

2. A ideia é adicionar no mínimo três abas à ficha de avaliação do paciente; veja a melhor forma de organizar isso;

3. Nas imagens onde houver campos descritivos, a coluna da tabela deve permitir até 1000 caracteres. 
Deve haver essa mesma validação em tela;

4. Escala Funcional Específica do Paciente
4.1. Os textos "Eu vou pedir para você idenficar...." e "Hoje, há alguma atividade..." devem ser exibidos de forma destacada;
4.2. Os campos que exigem Nota , veja se o primefaces permite o uso de um spinner (veja o mais adequado...pode ser basic, minMax etc).
Os valores de nota válidos são entre 0 e 10;
4.3. Achados clinicos: faça da mesma forma como foi implementado o TratamentoPrevio na ficha de avaliação. Ou seja, permite mais de um item,
o atributo na ficha é do tipo java.util.Set e deve ser anotado como @ElementCollection (não esquecer de JOIN FETCH).

5. Nenhum dos novos atributos da ficha de avaliação que estão sendo passados nessa atividades são de preenchimento obrigatório.

6. O layout do projeto já está aprovado, não precisa fazer alterações no layout. Isso quer dizer que vocÊ deve somente criar novas abas e campos para essas abas, conforme descrito na tarefa.
