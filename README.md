# NF-e Emissão MVP

API REST para geração, assinatura digital e envio de Notas Fiscais Eletrônicas (NF-e) para a SEFAZ, seguindo o layout 4.00.

## Tecnologias

- Java 17
- Spring Boot 3.3.7
- Spring Data JPA
- JAXB (geração de XML)
- Lombok
- H2 Database (desenvolvimento)
- Maven

## Funcionalidades

- Geração de XML da NF-e no padrão SEFAZ 4.00
- Assinatura digital com certificado A1 (.pfx)
- Geração automática da chave de acesso (44 dígitos)
- Comunicação SOAP com webservices da SEFAZ
- Persistência das notas fiscais no banco de dados
- Armazenamento local dos XMLs assinados
- Health check via Spring Actuator
- Hot reload com DevTools

## Estrutura do Projeto

```
src/main/java/com/nfemissor/nf_emissao_mvp/
├── config/              # Configurações (certificado, SEFAZ)
├── controller/          # Endpoints da API REST
├── dto/                 # Objetos de entrada/saída (request/response)
├── exception/           # Tratamento centralizado de erros
├── model/               # Entidades do banco de dados
├── repository/          # Acesso ao banco de dados
├── service/             # Regras de negócio
│   ├── AssinaturaDigitalService   # Assinatura XML com certificado A1
│   ├── NfeMontadorService         # Montagem do objeto NFe
│   ├── NfeService                 # Orquestração do fluxo
│   ├── NfeXmlService              # Conversão objeto → XML
│   ├── SefazService               # Comunicação SOAP com SEFAZ
│   └── XmlStorageService          # Armazenamento local dos XMLs
└── xml/                 # Classes de mapeamento XML (JAXB)
    ├── endereco/
    ├── imposto/
    └── nfe/
```

## Pré-requisitos

- JDK 17 (recomendado: [Amazon Corretto 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html))
- Maven 3.8+
- Certificado digital A1 (.pfx) para envio à SEFAZ

## Configuração

### Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto com as seguintes variáveis:

```env
# Certificado Digital A1
CERTIFICADO_CAMINHO=<caminho_do_certificado.pfx>
CERTIFICADO_SENHA=<senha_do_certificado>

# SEFAZ
SEFAZ_AMBIENTE=<1_producao_ou_2_homologacao>
SEFAZ_UF=<codigo_uf>
SEFAZ_URL_AUTORIZACAO=<url_webservice_autorizacao>
SEFAZ_URL_RETORNO=<url_webservice_retorno>
SEFAZ_URL_CONSULTA=<url_webservice_consulta>
SEFAZ_URL_INUTILIZACAO=<url_webservice_inutilizacao>
SEFAZ_URL_CANCELAMENTO=<url_webservice_cancelamento>

# Banco de Dados
DATABASE_URL=<jdbc_url_do_banco>
DATABASE_USERNAME=<usuario_banco>
DATABASE_PASSWORD=<senha_banco>
JPA_DDL_AUTO=<update_ou_validate>

# Armazenamento de XMLs
XML_DIRETORIO=<caminho_diretorio_xmls>
```

### Valores padrão (desenvolvimento)

Caso as variáveis de ambiente não sejam definidas, a aplicação usa os seguintes valores padrão:

| Variável | Valor Padrão |
|----------|-------------|
| `SEFAZ_AMBIENTE` | `2` (homologação) |
| `SEFAZ_UF` | `35` (SP) |
| `DATABASE_URL` | `jdbc:h2:mem:nfedb` |
| `DATABASE_USERNAME` | `sa` |
| `DATABASE_PASSWORD` | (vazio) |
| `JPA_DDL_AUTO` | `update` |

> **Importante:** Nunca commite o arquivo `.env` ou certificados `.pfx` no repositório.

### URLs dos Webservices SEFAZ (SVRS - Homologação)

| Serviço | URL |
|---------|-----|
| Autorização | `https://nfe-homologacao.svrs.rs.gov.br/ws/NfeAutorizacao4/NFeAutorizacao4.asmx` |
| Retorno | `https://nfe-homologacao.svrs.rs.gov.br/ws/NfeRetAutorizacao4/NFeRetAutorizacao4.asmx` |
| Consulta | `https://nfe-homologacao.svrs.rs.gov.br/ws/NfeConsulta4/NfeConsulta4.asmx` |
| Inutilização | `https://nfe-homologacao.svrs.rs.gov.br/ws/NfeInutilizacao4/NfeInutilizacao4.asmx` |
| Cancelamento | `https://nfe-homologacao.svrs.rs.gov.br/ws/RecepcaoEvento/RecepcaoEvento4.asmx` |

## Como Executar

```bash
# Compilar
./mvnw clean compile

# Executar
./mvnw spring-boot:run
```

A aplicação inicia em `http://localhost:8080`.

## Endpoints

### Emitir NF-e

```
POST /api/nfe/emitir
Content-Type: application/json
```

Exemplo de request body:

```json
{
    "identificacao": {
        "codigoUf": "35",
        "naturezaOperacao": "VENDA DE MERCADORIA",
        "serie": "1",
        "numeroNf": "1",
        "tipoOperacao": "1",
        "destinoOperacao": "1",
        "codigoMunicipioFatoGerador": "3550308"
    },
    "emitente": {
        "cnpj": "<cnpj_emitente>",
        "razaoSocial": "<razao_social>",
        "nomeFantasia": "<nome_fantasia>",
        "inscricaoEstadual": "<inscricao_estadual>",
        "codigoRegimeTributario": "1",
        "endereco": {
            "logradouro": "<logradouro>",
            "numero": "<numero>",
            "bairro": "<bairro>",
            "codigoMunicipio": "3550308",
            "nomeMunicipio": "SAO PAULO",
            "uf": "SP",
            "cep": "<cep>"
        }
    },
    "destinatario": {
        "cnpj": "<cnpj_destinatario>",
        "nome": "<nome_destinatario>",
        "endereco": {
            "logradouro": "<logradouro>",
            "numero": "<numero>",
            "bairro": "<bairro>",
            "codigoMunicipio": "3550308",
            "nomeMunicipio": "SAO PAULO",
            "uf": "SP",
            "cep": "<cep>"
        }
    },
    "itens": [
        {
            "codigo": "001",
            "descricao": "PRODUTO EXEMPLO",
            "ncm": "94013000",
            "cfop": "5102",
            "unidade": "UN",
            "quantidade": "1.0000",
            "valorUnitario": "100.00",
            "valorTotal": "100.00",
            "imposto": {
                "icms": {
                    "origem": "0",
                    "csosn": "102"
                },
                "pis": {
                    "cst": "49",
                    "baseCalculo": "100.00",
                    "aliquota": "0.00",
                    "valor": "0.00"
                },
                "cofins": {
                    "cst": "49",
                    "baseCalculo": "100.00",
                    "aliquota": "0.00",
                    "valor": "0.00"
                }
            }
        }
    ],
    "pagamento": {
        "detalhes": [
            {
                "tipo": "01",
                "valor": "100.00"
            }
        ]
    }
}
```

Tipos de pagamento:

| Código | Descrição |
|--------|-----------|
| 01 | Dinheiro |
| 02 | Cheque |
| 03 | Cartão de Crédito |
| 04 | Cartão de Débito |
| 05 | Crédito Loja |
| 15 | Boleto Bancário |
| 17 | Pix |
| 90 | Sem Pagamento |

### Health Check

```
GET /actuator/health
```

### Console H2 (apenas desenvolvimento)

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:nfedb
User: sa
Password: (vazio)
```

## Armazenamento de XMLs

Os XMLs assinados são salvos localmente organizados por ano/mês:

```
xmls/
├── 2603/
│   ├── enviados/
│   │   └── <chave_acesso>.xml
│   └── retornos/
│       └── <chave_acesso>.xml
```

## Deploy (Kubernetes)

Exemplo de configuração de variáveis de ambiente:

```yaml
env:
  - name: CERTIFICADO_CAMINHO
    value: "/app/certificados/certificado.pfx"
  - name: CERTIFICADO_SENHA
    valueFrom:
      secretKeyRef:
        name: nfe-secrets
        key: certificado-senha
  - name: SEFAZ_AMBIENTE
    value: "1"
  - name: DATABASE_URL
    value: "jdbc:postgresql://<host>:5432/nfedb"
  - name: DATABASE_USERNAME
    valueFrom:
      secretKeyRef:
        name: nfe-secrets
        key: db-username
  - name: DATABASE_PASSWORD
    valueFrom:
      secretKeyRef:
        name: nfe-secrets
        key: db-password
```

## .gitignore

Certifique-se de que os seguintes arquivos estão no `.gitignore`:

```
.env
src/main/resources/certificados/*.pfx
xmls/
```
