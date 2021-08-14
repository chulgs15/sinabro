# Two Sum

* nums : 데이터 제공
* 두개의 인덱스를 Return
    * return indices of the two numbers such that they add up to target.
    * 목표값을 함산할 수 있도록 두 숫자의 인덱스를 반환합니다.
* 무조건 값이 있다. 
* 동일한 요소를 2번 사용할 수 없다. 

## 제약조건

* 2 <= nums.length <= 10^4
  * 데이커 길이는 2개 이상이다.
* -10^9 <= nums[i] <= 10^9
  * nums[i]가 target보다 크면 Scan 할 필요가 없다.
* -10^9 <= target <= 10^9
* Only one valid answer exists.

## 생각해보니...

* 원하는 결과는 2개의 배열이니,,,값을 하나만 찾으면 다른 하나를 찾기 위해 모든 값을 알아볼 필요가 없네.
* 값을 hashmap으로 저정하고 ㄱ밧을 찾으면 된다.