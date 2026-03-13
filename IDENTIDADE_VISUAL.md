# Especificações Visuais e UI

## 1. Escalas Visuais (EVA e Preditores)
Todas as escalas de deslizamento (sliders) devem seguir rigorosamente o padrão:
- **Componente**: `p:slider` vinculado a `h:inputHidden`.
- **Altura**: `15px`.
- **Background**: `linear-gradient(to right, #90EE90 0%, #FFFF00 50%, #FF0000 100%)`.
- **Bordas**: `border-radius: 5px`.
- **Tipografia das Réguas**: Negrito, tamanho `0.9em`.

## 2. Paleta de Cores (Botões PrimeFaces)
- **Ações Primárias (Salvar)**: `ui-button-primary`.
- **Ações Secundárias (Cancelar)**: `ui-button-secondary`.
- **Ações de Alerta (Excluir/Sair)**: `ui-button-danger`.
- **Botões de Início (Nova Avaliação)**: `ui-button-warning rounded-button`.

## 3. Organização de Layout
- Uso de `p:tabView` para separar grandes blocos de informação.
- Uso de `p:panelGrid columns="1" layout="grid"` para responsividade.