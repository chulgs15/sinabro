
# JPA-SCOTT Example

# Hello World!

- JDK : jdk11
- IDEA : Intellij
- JPA : 5.3.10

## Purpose

JPA로 SCOTT Schema를 연결해서 여러 예제 데이터를 만들 본다.
(Connect SCOTT Schema with JPA to create several example data.)


### Recursive Query Example

Self-reference를 사용해서 Recursive Call 구현. 자세한 내용은 [Link](https://thisandthatit.blogspot.com/2021/03/scott-recursive-query.html)를 참조. 
(Implement Recursive Call using Self-reference. See [Link](https://thisandthatit.blogspot.com/2021/03/scott-recursive-query.html) for more information) 

1. ScottTest.getEmployeeUpstreamHierarchy() : Manager to Member.
2. ScottTest.getEmployeeDownStreamHierarchy() : Member to Manager.

### Inner/Outer/Cross Join Example
Database 의 Inner/Outer/Cross Join 과 집계함수를 Java 로  구현. 
(Implementation of the Inner/Outer/Cross Join and aggregation functions in Database in Java.)

1. ScottTest.innerJoin_Stream() : Inner Join with Stream.
2. ScottTest.innerJoin_JPA_Association_Stream() : Inner Join with JPA
3. ScottTest.outerJoin_Stream() : Outer Join with stream.
4. ScottTest.crossJoin_Stream() : Cross and Outer Join with stream.

자세한 내용은 아래 Link 를 참조. (See Link for more information)
- [Inner join Link](https://thisandthatit.blogspot.com/2021/03/scott-inner-join-java.html)
- Outer join Link
- Cross join Link
