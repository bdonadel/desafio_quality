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
- [Imóveis](#imoveis)
  - [Get - Retorna a área de um imóvel](#propertyArea)
  - [Get - Retorna o valor de um imóvel](#propertyValue)
  - [Get - Retorna o maior cômodo do imóvel](#biggestRoom)
  - [Get - Retorna informações do cômodo](#roomInfo)
  - [Get - Retorna todos os imóveis](#allProperties)
# Observações

# Funcionalidades

## Imóveis

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
    },
    {
        "propId": 4,
        "propName": "Apartamento 08",
        "propDistrict": {
            "districtId": 3,
            "districtName": "Passo D'areia",
            "valueDistrictM2": 10
        },
        "propRooms": []
    }
]
</code></pre>

