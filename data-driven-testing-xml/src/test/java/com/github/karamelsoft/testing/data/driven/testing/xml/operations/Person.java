package com.github.karamelsoft.testing.data.driven.testing.xml.operations;

/**
 * Created by jschoreels on 05.04.16.
 */
class Person {

    private String firstName;
    private String lastName;

    public Person(){}//MANDATORY FOR DETECTION

    private Person(Builder builder) {
        firstName = builder.firstName;
        lastName = builder.lastName;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(Person copy) {
        Builder builder = new Builder();
        builder.firstName = copy.firstName;
        builder.lastName = copy.lastName;
        return builder;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public static final class Builder {
        private String firstName;
        private String lastName;

        private Builder() {
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
