package starter.requery;

import org.jooby.Jooby;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;
import org.jooby.requery.Requery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.requery.EntityStore;
import io.requery.Persistable;
import io.requery.sql.TableCreationMode;
import starter.requery.model.Models;
import starter.requery.model.Person;

@SuppressWarnings("unchecked")
public class App extends Jooby {

  /** The logging system. */
  private final Logger log = LoggerFactory.getLogger(getClass());

  {
    /** Connection pool: */
    use(new Jdbc());

    /** JSON: */
    use(new Jackson());

    /** Requery: */
    use(new Requery(Models.DEFAULT)
        .schema(TableCreationMode.DROP_CREATE));

    /** Save a new person on startup: */
    onStart(registry -> {
      Person person = new Person();
      person.setName("Pedro Picapiedra");
      person.setAge(42);
      person.setEmail("pedrookok@picapiedra.com");

      log.info("Saving {}", person);

      EntityStore<Persistable, Person> store = require(EntityStore.class);
      store.insert(person);
    });

    /**
     * List persons:
     */
    get("/", req -> {
      EntityStore<Persistable, Person> store = require(EntityStore.class);
      return store.select(Person.class)
          .get()
          .toList();
    });

    /**
     * Get a person by ID:
     */
    get("/:id", req -> {
      EntityStore<Persistable, Person> data = require(EntityStore.class);
      return data.select(Person.class)
          .where(Person.ID.eq(req.param("id").intValue()))
          .get()
          .first();
    });
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }
}
