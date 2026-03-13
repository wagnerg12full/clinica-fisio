# Especificações Técnicas do Projeto

## 1. Stack Tecnológica
- **Linguagem**: Java 11+ (compatível com Java 17).
- **Framework Web**: JSF (JavaServer Faces) 2.3 / Jakarta EE.
- **Componentes UI**: PrimeFaces 12.0+ (ou versão atual estável).
- **Servidor de Aplicação**: JBoss WildFly 27+ (Jakarta EE 10).
- **ORM/JPA**: Hibernate (implementação do WildFly).
- **Banco de Dados**: MySQL 8.0 / PostgreSQL (Configurado via Datasource no WildFly).

## 2. Build e Deploy
- **Build Tool**: Maven (`mvn clean package`).
- **Artefato**: `.war`.
- **Fluxo de Deploy**: Após o build, o `.war` deve ser movido para a pasta `standalone/deployments` do WildFly.

## 3. Padrões de Código
- **Encoding**: UTF-8 em todos os arquivos e na comunicação HTTP.
- **Navegação**: JSF Faces Flow ou navegação explícita com `ajax="false"` para evitar quebras de redirecionamento no WildFly.