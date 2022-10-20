package uz.mh.click.domains;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class GenericParam extends Auditable{
    private String name;
    private String value;
    private InputType inputType;

    private enum InputType {
        FLOAT,
        VARCHAR

    }
}
