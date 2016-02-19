import java.util.HashMap;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap model = new HashMap();
      model.put("definitions", request.session().attribute("definitions"));
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //<a href="/words">View Word List</a>
    //<a href="/words/new">Add a New Word</a>


//START OF WORDS PAGES

    get("/words", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("words", Word.all());
      model.put("template", "templates/words.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //<a href="/words/new">Enter a new Word</a>
    //<a href="/words">Return to Word List</a>

    get("words/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/word-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //<a href="/words">Return to Word List</a>

    post("/words", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String word = request.queryParams("word");
      Word newWord = new Word(word);
      model.put("word", newWord);
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    //<a href="/words/new">Add a new word</a>
    //<a href="/words">Return to Word List</a>

    get("/words/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("word", Word.find(Integer.parseInt(request.params(":id"))));
      model.put("template", "templates/word.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // <a href="/words/$word.getId()/definitions/new">Add a New Definition</a>
    // <a href="/words">Return to Word List</a>

    get("words/:id/definitions/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Word word = Word.find(Integer.parseInt(request.params(":id")));
      ArrayList<Definition> definitions = word.getDefinitions();
      model.put("word", word);
      model.put("definitions", definitions);
      model.put("template", "templates/word-definition-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


// START OF DEFINITION PAGES

    // get("/definitions", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("definitions", Definition.all());
    //   model.put("template", "templates/success.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // // get("definitions/new", (request, response) -> {
    // //   HashMap<String, Object> model = new HashMap<String, Object>();
    // //   model.put("template", "templates/definition-form.vtl");
    // //   return new ModelAndView(model, layout);
    // // }, new VelocityTemplateEngine());
    //
    // post("/definitions", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Word word = Word.find(Integer.parseInt(request.queryParams("wordId")));
    //
    //   ArrayList<Definition> definitions = word.getDefinitions();
    //
    //   if (definitions == null) {
    //     definitions = new ArrayList<Definition>();
    //     request.session().attribute("definitions", definitions);
    //   }
    //
    //   String description = request.queryParams("description");
    //   Definition newDefinition = new Definition(description);
    //   word.addDefinition(newDefinition);
    //   // definitions.add(newDefinition);
    //
    //   model.put("definitions", definitions);
    //   model.put("word", word);
    //   model.put("template", "templates/word.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/definitions/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Definition definition = Definition.find(Integer.parseInt(request.params(":id")));
    //   model.put("definition", definition);
    //   model.put("template", "templates/definition.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
  }

}