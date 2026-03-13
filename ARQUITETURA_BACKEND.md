# Arquitetura do Sistema

## 1. Camada de Entidade (`com.clinica.model`)
- **FichaAvaliacao**: Deve usar `Integer` (Wrapper) para campos numéricos nulos.
- **Relacionamentos**: Usar `Set` (HashSet) para `@ElementCollection` (ex: patologias e tratamentos) para evitar `MultipleBagFetchException`.
- **Enums**: Localizados no mesmo pacote, mapeados com `@Enumerated(EnumType.STRING)`.

## 2. Camada de Serviço (`com.clinica.service`)
- **EJB**: Usar `@Stateless`.
- **Consultas (JPQL)**: Sempre usar `LEFT JOIN FETCH` para coleções e relacionamentos para evitar `LazyInitializationException` e otimizar a performance da busca.

## 3. Camada de Controle (`com.clinica.controller`)
- **Escopo**: `@FlowScoped` ou `@ViewScoped`.
- **Inicialização**: Uso obrigatório de `@PostConstruct` para inicializar listas e valores padrão (0) dos sliders.
- **Tratamento de Dados**: Métodos de carregamento devem prever dados nulos de registros antigos e normalizá-los.