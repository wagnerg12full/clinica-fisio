# Arquitetura do Sistema - Backend

## 1. Camada de Entidade (`com.clinica.model`)
- **FichaAvaliacao**: Deve usar `Integer` (Wrapper) para campos numéricos nulos (Preditores de Dor).
- **Relacionamentos**: Usar `Set` (HashSet) para `@ElementCollection` (ex: `patologiasAssociadas` e `tratamentosPrevios`) para evitar `MultipleBagFetchException`.
- **Enums**: Localizados no mesmo pacote, mapeados com `@Enumerated(EnumType.STRING)`.

## 2. Camada de Serviço (`com.clinica.service`)
- **EJB**: Usar `@Stateless`.
- **Prevenção de LazyInitializationException**: 
    - Toda consulta que recupere uma Ficha de Avaliação para edição **DEVE** utilizar `LEFT JOIN FETCH` para as coleções `@ElementCollection`.
    - Exemplo: `SELECT DISTINCT f FROM FichaAvaliacao f LEFT JOIN FETCH f.patologiasAssociadas...`
- **Consultas (JPQL)**: Otimizar para trazer o objeto completo dentro da transação do EJB, garantindo que os dados cheguem ao Bean prontos para manipulação.

## 3. Camada de Controle (`com.clinica.controller`)
- **Escopo**: `@FlowScoped` ou `@ViewScoped`.
- **Inicialização**: Uso obrigatório de `@PostConstruct` para inicializar listas vazias e valores padrão (0) dos sliders.
- **Normalização de Dados**: 
    - Ao carregar uma entidade para edição, envolver as coleções carregadas em um novo `HashSet`: 
      `this.avaliacao.setPatologias(new HashSet<>(this.avaliacao.getPatologias()));`
    - Isso "descola" a coleção do proxy do Hibernate e evita erros de sessão fechada quando o JSF tenta converter os dados dos checkboxes.
- **Segurança**: Métodos de carregamento devem prever dados nulos de registros antigos e normalizá-los antes da renderização.