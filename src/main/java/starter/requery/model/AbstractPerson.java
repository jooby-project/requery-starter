package starter.requery.model;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;

@Entity
public abstract class AbstractPerson {

  @Key
  @Generated
  int id;

  String name;

  String email;

  int age;
}
