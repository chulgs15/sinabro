
# PV And Lease Accounting

# Hello World!

- JDK : jdk11
- JPA : 5.3.10

## Purpose
Java로 현재가치와 리스부채 등록을 구현했다.  
(It implements present value and lease liability registration with Java.)

### PV(Present Value)
현재가치 계산을 Java로 구현했다. 자세한 내용은 [Link](https://thisandthatit.blogspot.com/2021/03/fi3nance-java-pv-calculattion.html) 를 참조.  
(We implemented present value calculations in Java. [Link](https://thisandthatit.blogspot.com/2021/03/finance-java.html) for more information)

1. PVTest.calculatePV_just_function() : Calculate PV Example.

### Lease Liabilities Accounting.
'K-IFRS 1116'가 도입으로 기존 사용권 자산(Righ-of-use assets, 이하 리스자산)은 부채로 등록하며, 현재가치 금액으로 분개를 기재한다.  
이번 예제는 리스부채 현재가치 계산하는 예제를 만들었다. 자세한 내용은 [Link](https://thisandthatit.blogspot.com/2021/03/finance-java.html)
를 참조.  
(With the introduction of "K-IFRS 1116", Righ-of-use sets(=Lease Liability) are registered as liabilities and are listed as current value amounts.
This example creates an example of calculating the present value of a lease liability. 
[Link](https://thisandthatit.blogspot.com/2021/03/finance-java.html) for more information)

1. PVTest.calculateLeasePV_print_sameAmount() : Calculate the present value with the same amount
2. PVTest.calculateLeasePV_print_differentAmount() : Calculate the present value with the different amount
