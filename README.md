# DNIT - commons-lib

Biblioteca comum (commons-lib) utilizada em projetos do DNIT, com foco em funcionalidades compartilhadas, validações de domínio e utilitários diversos. 

Desenvolvida em Java/Kotlin.

## ✨ Funcionalidades Principais

- 📐 **Cálculos padrões utilizados no módulo SGP (IGG, IRI, ICS, SCI...)**


- ✅ **Validações e Models para entidades do DNIT**, como:
    - `BR` (Rodovias Federais)
    - `UF` (Unidades Federativas)
    - `Região` (Geográficas)
    - `SNV` 
  

- 🔍 **Validações via Anotações**
    - Anotações customizadas para validação automática de campos como `@ValidUF`, `@ValidBR`, `@ValidBRs`, `@ValidUFs`, `@ValidRegiao`, `@ValidRegioes`, `@ValidSNV`, `@ValidSNVs` `@ValidLatitude`, `@ValidLongitude` e `@ValidAltimetria`
  

- 🌍 **Validação Geográfica**
    - Validação de coordenadas válidas (Latitude/Longitude/Altimetria)
    - Cálculo de distância entre dois pontos geográficos (Latitude/Longitude)
    - Verificação se uma coordenada está dentro do território brasileiro
  

- 🔄 **Integração com o SNV**
    - Rotina automatizada para obtenção e atualização de dados do Sistema Nacional de Viação (SNV)


- 🧰 **Coleção de Utils**
    - Manipulação de `String`, `Collections`, `NanoID`, `Time` e mais

## 🚀 Instalação

Adicione a dependência no seu projeto Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.dev-dnit</groupId>
    <artifactId>commons-lib</artifactId>
    <version>1.2.8</version>
</dependency>
```