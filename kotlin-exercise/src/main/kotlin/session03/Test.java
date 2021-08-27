package session03;

public class Test {
    public static void main(String[] args) {
        System.out.println("hello world");

        Person shin1 = new Person(1L, "shin");
        Person shin2 = new Person(1L, "shin");

        System.out.println("shin1.hashcode = " + shin1.hashCode() );
        System.out.println("shin2.hashcode = " + shin2.hashCode() );

        System.out.println(shin1.hashCode() == shin2.hashCode());
    }

    static class Person {
        private Long id;
        private String Name;

        public Person(Long id, String name) {
            this.id = id;
            Name = name;
        }
    }
}
