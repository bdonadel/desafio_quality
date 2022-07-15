# Desafio_Quality
API REST desenvolvida pelo grupo Beta Campers para o Desafio Spring durante o IT Bootcamp Backend Java (wave 6). 

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
  - [Get - Retorna todos os imóveis](#allProperties)
- [Bairros](#bairros)
  - [Post - Cadastra um novo bairro](#addDistrict)
  - [Get - Retorna o bairro de acordo com o Id passado](#getDistrict)
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

`GET /api/v1/properties` <br name="allProperties">
Retorna todos os imóveis.
<pre><code><b>Response:</b>
[
    {
        "propId": 1,
        "propName": "Casa A",
        "propDistrict": {
            "districtId": 1,
            "districtName": "Centro",
            "valueDistrictM2": 10
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
    },
    {
        "propId": 2,
        "propName": "Apartamento 12",
        "propDistrict": {
            "districtId": 2,
            "districtName": "Sossego",
            "valueDistrictM2": 10
        },
        "propRooms": [
            {
                "roomName": "Quarto",
                "roomWidth": 2.5,
                "roomLength": 4.2
            },
            {
                "roomName": "Cozinha",
                "roomWidth": 2.5,
                "roomLength": 3.0
            },
            {
                "roomName": "Banheiro",
                "roomWidth": 1.5,
                "roomLength": 2.2
            }
        ]
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
