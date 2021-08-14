public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee(String firstName, String lastName, String country, int age) {

    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }
    @Override
    public String toString() {
        return "id " + id +
                "/ имя " + firstName +
                "/ фамилия " + lastName +
                "/ страна " + country +
                "/ age " + age;
    }

    public void addEmployee(Long id, String firstName, String lastName, String country, int age) {
        Employee employee = new Employee(id, firstName, lastName, country, age);
    }
}

