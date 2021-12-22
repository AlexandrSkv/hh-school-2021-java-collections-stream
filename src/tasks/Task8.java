package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    //Использую skip(). Так не изменится исходный List<Person> persons, вдруг наша фальшивая персона еще понадобиться
    return persons.stream()
    .map(Person::getFirstName)
    .skip(1)
    .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    //Удалил .distinct() и заодно вообще stream()
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    //Воспользуемся Collectors.joining
    return Stream.of(person.getFirstName(), person.getMiddleName(), person.getSecondName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Воспользуемся стримом и добавим фунцию мержа в toMap!
    return persons.stream()
    .collect(Collectors.toMap(Person::getId, person -> convertPersonToString(person), (a,b) -> a));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
   /* В исходном случае сложность гарантирована была O(n*m),
   а в случае anyMatch() - это "short-circuiting terminal operation", и результат может быть лучше.
   Однако в самом плохом случае - все равно O(n*m).

   По сути тоже самое без стрима:

   for (Person person1 : persons1) {
      for (Person person2 : persons2) {
        if (person1.equals(person2)) {
          return true;
        }
      }
    }
    return false; */

   return persons1.stream().anyMatch(persons2::contains);
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    //Считаем четные числа без дополнительной переменной с помощью count()
    //«Не следует привлекать новые сущности без крайней на то необходимости»
    return numbers
    .filter(num -> num % 2 == 0)
    .count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true; //Нужно что то одно поменять...
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
