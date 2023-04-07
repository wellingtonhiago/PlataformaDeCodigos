# PlataformaDeCodigos
## Controller

`CodigoController`

```java
@RestController
public class CodigoController {

    private final List<ApiCodigos> listaApi = new ArrayList<>();

    @GetMapping("/api/code/{i}")
    public ApiCodigos apiCodigo(@PathVariable int i) {
        return listaApi.get(i-1);
    }

    @GetMapping("/code/{i}")
    public ModelAndView codigo(@PathVariable int i) {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("codigo");
        mv.addObject("codigoData", listaApi.get(i-1).getDate());
        mv.addObject("codigo", listaApi.get(i-1).getCode());
        return mv;
    }

    @GetMapping("/code/new")
    public ModelAndView newCodigo() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("novoCodigo");
        return mv;
    }

    @PostMapping("/api/code/new")
    public String postCodigo(@RequestBody ApiCodigos codigo) {
        ApiCodigos novoCodigo = new ApiCodigos(codigo.getCode());
        listaApi.add(novoCodigo);

        Id id = new Id();
        return id.toString();
    }

    @GetMapping("/api/code/latest")
    public List<ApiCodigos> apiCodigos() {
        List<ApiCodigos> listaApiReversa = new ArrayList<>(listaApi);

        Collections.reverse(listaApiReversa);

        return listaApiReversa.stream().limit(10).collect(Collectors.toList());
    }

    @GetMapping("/code/latest")
    public ModelAndView codigoLista() {
        ModelAndView mv = new ModelAndView();

        List<ApiCodigos> listaApiReversa = new ArrayList<>(listaApi);
        Collections.reverse(listaApiReversa);

        mv.setViewName("codigoLatest");
        mv.addObject("codigos",
                listaApiReversa.stream().limit(10).collect(Collectors.toList()));

        return mv;
    }
}
```

O método `apiCodigo` recupera um único código da lista de códigos armazenados, o método `codigo` renderiza uma página HTML com um único código e sua data de criação, e o método `codigoLista` renderiza uma página com os 10 códigos mais recentes.

O método `newCodigo` renderiza uma página HTML com um formulário que permite ao usuário enviar um novo código para ser armazenado no sistema.

O método `postCodigo` recebe um novo código em formato JSON via requisição POST e o armazena na lista de códigos, retornando um ID único para esse código.

Por fim, o método `apiCodigos` retorna os 10 códigos mais recentes em formato JSON. Todos os códigos armazenados são mantidos em uma lista que é inicializada no construtor da classe e é acessível por todos os métodos do controlador.

## Model

`ApiCodigos`

```java
public class ApiCodigos {

    private String code;
    private String date;

    public ApiCodigos(){

    }
    public ApiCodigos(String code) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String date = now.format(formato);

        this.date = date;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
```

Esse código é uma classe Java que define um modelo de dados para representar um objeto que contém um código e a data em que foi criado.

A classe **`ApiCodigos`** tem dois atributos privados, **`code`** e **`date`**, que são acessíveis através dos métodos públicos **`getCode()`** e **`getDate()`**, respectivamente. O construtor da classe aceita uma string **`code`** como entrada e usa a classe **`LocalDateTime`** do pacote **`java.time`** para obter a data e hora atual e formatá-la em uma string no formato "yyyy/MM/dd HH:mm:ss".

`Id`

```java
public class Id {
    public static int id = 0;

    public Id (){
        id++;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Id.id = id;
    }

    @Override
    public String toString() {
        return "{\"id\":" + "\"" + getId() + "\"" + "}";
    }
}
```

Esse código é uma classe Java que contém um campo estático **`id`** e métodos para manipular esse campo. A classe também possui um método **`toString()`** que retorna uma representação JSON da instância atual da classe.

O campo estático **`id`** é inicializado com o valor 0 e é incrementado pelo construtor da classe sempre que um novo objeto **`Id`** é criado. O construtor da classe não recebe nenhum argumento.

## Templates

`novoCodigo.html`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <style>
    #code_snippet {
      width: 100%;
      height: 300px;
      font-family: "Courier New", Courier, monospace;
      font-size: 16px;
      line-height: 1.5;
      padding: 10px;
      box-sizing: border-box;
      border: 2px solid #ccc;
      border-radius: 4px;
    }

    #send_snippet {
      display: inline-block;
      background-color: #4CAF50;
      color: white;
      text-align: center;
      padding: 14px 20px;
      margin-top: 10px;
      margin-bottom: 10px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 16px;
    }

    #send_snippet:hover {
      background-color: #3e8e41;
    }

    #send_snippet:active {
      background-color: #2f6e2f;
    }
  </style>

</head>
<body>
<form method="post">
    <textarea id="code_snippet">// write your code here </textarea>
    <br> <button id="send_snippet" type="submit" onclick="send()">Submit</button>
</form>
<script>
        function send() {
    let object = {
        "code": document.getElementById("code_snippet").value
    };

    let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/code/new', false)
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);

    if (xhr.status == 200) {
      alert("Success!");
    }
}
    </script>
</body>
</html>
```

Esse código é uma página HTML que contém um formulário com um campo de texto e um botão de envio. Quando o usuário escreve algum código no campo de texto e clica no botão de envio, a função **`send()`**
 é chamada. Essa função cria um objeto JavaScript com uma propriedade "code" que contém o valor do campo de texto. Esse objeto é transformado em um objeto JSON por meio da função **`JSON.stringify()`**
 e, em seguida, é enviado para um endpoint de API em "/api/code/new" usando o método HTTP POST através de um objeto XMLHttpRequest. O cabeçalho HTTP **`Content-type`**
 é definido como "application/json; charset=utf-8" para indicar que o corpo da solicitação contém um objeto JSON. Se a resposta HTTP retornada pelo endpoint da API for 200, um alerta com a mensagem "Success!" é exibido para o usuário.

---

`codigoLatest.html`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
</head>
<body>
<#list codigos as codigo>
<span id="load_date">${codigo.getDate()}</span>
<pre id="code_snippet">${codigo.getCode()}</pre>
</#list>
</body>
</html>
```

A partir da tag **`<#list>`** é possível deduzir que **`codigos`** é uma lista de objetos, onde cada objeto possui as propriedades **`date`** e **`code`**.

Dentro do bloco da tag **`<#list>`**, há um laço que itera sobre cada objeto da lista e gera um elemento **`<span>`** com o valor da propriedade **`date`** e um elemento **`<pre>`** com o valor da propriedade **`code`** do objeto.

O elemento **`<span>`** é usado para exibir a data de cada código, enquanto o elemento **`<pre>`** é usado para exibir o código em uma formatação pré-formatada (ou seja, preservando espaços em branco e quebras de linha).

---

`codigo.html`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
</head>
<body>
<span id="load_date">${codigoData}</span>
<pre id="code_snippet">${codigo}</pre>
</body>
</html>
```

Esse código HTML estar sendo usado para exibir um código específico em uma página da web. O valor da variável **`codigo`** é usado para preencher o conteúdo da tag **`<pre>`**, enquanto o valor da variável **`codigoData`** é usado para preencher o conteúdo da tag **`<span>`**.

---
