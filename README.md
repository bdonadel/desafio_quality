# Desafio_Quality
API REST desenvolvida pelo grupo Beta Campers para o Desafio Quality focado em testes durante o IT Bootcamp Backend Java (wave 6). 

## Autores
<a href="https://github.com/vfreitasmeli">
  <img src="https://avatars.githubusercontent.com/u/107959338?s=50&v=4" style="width: 50px">
</a>
<a href="https://github.com/brunavottri">
  <img src="https://avatars.githubusercontent.com/u/108009877?s=120&v=4" style="width: 50px">
</a>
<a href="https://github.com/pealmeida-meli">
  <img src="https://avatars.githubusercontent.com/u/108008922?s=120&v=4" style="width: 50px">
</a>
<a href="https://github.com/thiagosordiMELI">
  <img src="https://avatars.githubusercontent.com/u/108008559?s=120&v=4" style="width: 50px">
</a>
<a href="https://github.com/bdonadel">
  <img src="https://avatars.githubusercontent.com/u/108012641?s=120&v=4" style="width: 50px">
</a>
<a href="https://github.com/felipeticiani-meli">
  <img src="https://avatars.githubusercontent.com/u/108010964?s=120&v=4" style="width: 50px">
</a>

# Sumário

- [Observações](#observações)
- [Funcionalidades](#funcionalidades)
- <a href="https://drive.google.com/file/d/1gCBrdi8smZKJKubESFuziZZ9NbGer9XZ/view?usp=sharing">Diagrama UML </a>
- [Imóveis](#imóveis)
  - [Post - Cadastra um novo imóvel](#addProperty)
  - [Get - Retorna a área de um imóvel](#propertyArea)
  - [Get - Retorna o valor de um imóvel](#propertyValue)
  - [Get - Retorna o maior cômodo do imóvel](#biggestRoom)
  - [Get - Retorna informações do cômodo](#roomInfo)
- [Bairros](#bairros)
  - [Post - Cadastra um novo bairro](#addDistrict)
  - [Get - Retorna o bairro de acordo com o Id passado](#getDistrict)
- [Testes](#testes)
  - [Testes de Integração District]()
  - [Testes de Integração Property]()
  - [Testes Unitários Repositório District]()
  - [Testes Unitários Repositório Property](#TURProperty) <br>
    ⋆ [getById_returnDistrict_whenDistrictExists](#getByIdDistrict1) <br>
    ⋆ [getById_throwException_whenDistrictNotExist](#getByIdDistrict2) <br>
    ⋆ [save_returnDistrict_whenNewDistrict](#saveDistrict1) <br>
    ⋆ [save_updateDistrict_whenDistrictWithId](#saveDistrict2) <br>
    ⋆ [save_throwException_whenDistrictIdExistsAndDistrictNotExist](#saveDistrict3) <br>
  - [Testes Unitários Service District]()
  - [Testes Unitários Service Property]()
# Observações

# Funcionalidades

## Imóveis

`POST /api/v1/property` <br name="addProperty">
Cadastra um novo imóvel.
<pre><code><b>Payload Example:</b>
{
        "propName": "Casa A",
        "districtId": 1,
        "propRooms": [
            {
                "roomName": "Quarto de solteiro",
                "roomWidth": 2.5,
                "roomLength": 4.2
            },
            {
                "roomName": "Quarto de casal",
                "roomWidth": 3.5,
                "roomLength": 4.6
            },
            {
                "roomName": "Cozinha",
                "roomWidth": 3.6,
                "roomLength": 4.8
            },
            {
                "roomName": "Banheiro",
                "roomWidth": 1.8,
                "roomLength": 2.4
            }
        ]
 }
    
<b>Response:</b>

{
    "propId": 1,
    "propName": "Casa A",
    "propDistrict": {
        "districtId": 1,
        "districtName": "lalala",
        "valueDistrictM2": 12
    },
    "propRooms": [
        {
            "roomName": "Quarto de solteiro",
            "roomWidth": 2.5,
            "roomLength": 4.2
        },
        {
            "roomName": "Quarto de casal",
            "roomWidth": 3.5,
            "roomLength": 4.6
        },
        {
            "roomName": "Cozinha",
            "roomWidth": 3.6,
            "roomLength": 4.8
        },
        {
            "roomName": "Banheiro",
            "roomWidth": 1.8,
            "roomLength": 2.4
        }
    ]
}
</code></pre>
- Será validado se:<br>
  - O nome do imóvel não está vazio<br>
  - O nome do imóvel começa com letra maiúscula<br>
  - O nome do imóvel não excede o limite de 30 caracteres<br>
  - O nome do bairro não está vazio<br>
  - O imóvel tem pelo menos um cômodo<br>
  - O nome do cômodo não pode estar vazio<br>
  - O nome do cômodo começa com letra maiúscula<br>
  - O nome do cômodo não excede o limite de 30 caracteres<br>
  - A largura do cômodo não está vazia<br>
  - A largura do cômodo não excede o limite de 25 metros<br>
  - O comprimento do cômodo não excede 33 metros<br>

`GET /api/v1/1/area` <br name="propertyArea">
Retorna a área de um imóvel.
<pre><code><b>Response:</b>
48.2
</code></pre>

`GET /api/v1/1/value` <br name="propertyValue">
Retorna o valor de um imóvel.
<pre><code><b>Response:</b>
482.00
</code></pre>

`GET /api/v1/1/largest-room` <br name="biggestRoom">
Retorna o maior cômodo do imóvel.
<pre><code><b>Response:</b>
{
    "roomName": "Cozinha",
    "roomWidth": 3.6,
    "roomLength": 4.8,
    "roomArea": 17.28
}
</code></pre>

`GET /api/v1/1/roomsArea` <br name="roomInfo">
Retorna nome, largura, comprimento e área de um cômodo.
<pre><code><b>Response:</b>
[
    {
        "roomName": "Quarto de solteiro",
        "roomWidth": 2.5,
        "roomLength": 4.2,
        "roomArea": 10.5
    },
    {
        "roomName": "Quarto de casal",
        "roomWidth": 3.5,
        "roomLength": 4.6,
        "roomArea": 16.099999999999998
    },
    {
        "roomName": "Cozinha",
        "roomWidth": 3.6,
        "roomLength": 4.8,
        "roomArea": 17.28
    },
    {
        "roomName": "Banheiro",
        "roomWidth": 1.8,
        "roomLength": 2.4,
        "roomArea": 4.32
    }
]
</code></pre>

## Bairros

`POST /api/v1/1/district` <br name="addDistrict">
Cadastra um novo bairro.
<pre><code><b>Payload example:</b>
    {
    "districtName" : "Centro",
    "valueDistrictM2" : "12"
    }
    
<b>Response:</b>
    {
    "districtId": 1,
    "districtName": "Centro",
    "valueDistrictM2": 70
    }
    </code></pre>
    
- Será validado se:<br>
  - O nome do bairro não está vazio<br>
  - O nome do bairro começa com letra maiúscula
  - O nome do imóvel não excede o limite de 45 caracteres
  - O valor do m2 do bairro não está vazio
  - O valor do m2 não excede 13 dígitos e 2 casas decimais

`GET /api/v1/1/district/1` <br name="getDistrict">
Retorna o bairro de acordo com o Id passado.
<pre><code><b>Response:</b>
{
    "districtId": 1,
    "districtName": "Centro",
    "valueDistrictM2": 70
}
</code></pre>

## Testes


### Testes Unitários Repositório Property <br name="TURProperty">

`getById_returnDistrict_whenDistrictExists`<br name="getByIdDistrict1">
Testa o caso do método getById, quando o bairro existe e deve retornar o bairro. 
É gerado um bairro como exemplo e ele é salvo.
Em seguida é aplicado o método getById.<br>

Por fim é analisado se:
  - O resultado retornado do getById não é null
  - Se o id retornado é igual ao requisitado
  - Se o nome retornado é igual ao requisitado

`getById_throwException_whenDistrictNotExist`<br name="getByIdDistrict2">
Testa o caso do método getById, de quando o bairro não existe e lança uma exceção.
É gerado um bairro sem Id e é esperado que quando for usado o método getById retorne uma exceção.<br>

Por fim é analisado se:
  - Se contém o Id do bairro na mensagem de erro
  - Se o Status recebido é NOT FOUND

`save_returnDistrict_whenNewDistrict`<br name="saveDistrict1">
Testa o caso do método save, quando é adicionado um novo bairro e o mesmo é retornado.
É gerado um novo bairro e ele é aplicado o método save.<br>

Por fim é analisado se:
  - O resultado retornado do save não é null
  - Se o id retornado é positivo
  - Se o nome do bairro retornado é o mesmo do bairro criado.

`save_updateDistrict_whenDistrictWithId`<br name="saveDistrict2">
Testa o caso do método save, quando é o bairro existe e é atualizado.
É gerado um novo bairro com Id e ele é salvo.
É modificado o nome e o valor é salvo.<br>

Por fim é analisado se:
  - O resultado retornado do save não é null
  - Se o Id retornado continua sendo o mesmo
  - Se o nome do bairro foi atualizado

`save_throwException_whenDistrictIdExistsAndDistrictNotExist`<br name="saveDistrict3">
Testa o caso do método save, de quando o Id já existe e o bairro não existe e lança uma exceção.
É gerado um bairro com Id e é esperado que quando for usado o método save retorne uma exceção.<br>

Por fim é analisado se:
  - Se contém o Id do bairro na mensagem de erro
  - Se o Status recebido é NOT FOUND
