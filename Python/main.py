# 출력
print("Hello World!")
print(1)
print([1, 2, 3])

# 입력
input("Enter the name")  # 값 입력 받기

# List  // 값 수정 가능 var
myList = [1, 2, 3, 4, 5]  # 리스트 생성
myList.append(6)  # 데이터 추가

# Tuple // 값 수정이 불가능 val
myTuple = (1, 2, 3, 4, 5)  # 튜플 생성

# Dictionary //{key1: value1, key2: value2, key3: value3}
myDictionary = {"key1": 1, "key2": 2, "key3": 3}
myDictionary["key1"] = 100
for key in myDictionary.keys():
    print(key)  # key1, key2, key3 출력
for value in myDictionary.values():
    print(value)  # value1, value2, value3 출력
for key, value in myDictionary.items():
    print(key, value)  # key1, value1, key2, value2, key3, value3 출력

# Type 변환하기
myType = 1  # 자료형은 int
str(myType)  # 자료형은 str (= '1')
type(myType)  # 'type(변수)'로 타입 확인 가능 (= str)

# String
myString = '일반 스트링'
myStringEnter = '''이
사이에
엔터도
적용'''  # '이\n사이에\n엔터도\n적용'이 출력

# Formatting
myFormatting = 'My name is %s' % 'Luke'  # 'My name is Luke'이 출력

# format()
myFormat = 'My name is {}'.format('Luke')  # 'My nmae is Luke'이 출력

# Index
myIndex = "Luke"
print(myIndex[0])  # 'L'이 출력
print(myIndex[-1])  # 'e'이 출력

# Slice
mySlice = "Luke"
print(mySlice[:3])  # 'Luk'가 출력 (맨 앞, 맨 뒤는 생략 가능)
print(mySlice[1:3])  # 'uke'가 출력

# split()
mySplit = "My name is Luke"
print(mySplit.split())  # ['My', 'name', 'is', 'Luke'] 출력 (default는 공백)

# List (mutable)
myList = []
myStudent = ['Luke', 'Skywalker', 'Han']
myList.append(1)  # 값 추가하기
myList.append(2)
myList.append(3)
myList.append(myStudent)
del myList[1]  # 값 지우기
print(myList[0:2])  # Slice 가능
myList.sort()  # 정렬
myList.count(2)  # 값 개수 찾기
len(myList)  # List 안의 요소 개수

# Tuple (immutable)
myTuple1 = (1, 2, 3, 4, 5)  # tuple의 선언방식1
myTuple2 = 1, 2, 3, 4, 5  # tuple의 선언방식2

# Packing / UnPacking
myPacking = (1, 2, 3, 4, 5)
num1, num2, num3, num4, num5 = myPacking  # 각 변수에 myPacking을 Unpacking하여 넣음
num1, num2 = num2, num1  # num1 -> num2 / num2 -> num1

# for
for element in myList:
    print(element)
for num in [1, 2, 3]:
    print(num)
for myString in "Have a nice day!":
    print(myString)
for num in range(10):  # 0부터 9까지
    print(num)
for num in range(10, 20):  # 10부터 20까지
    print(num)

# while
while True:
    print("Infinite Loop")
while False:
    print("False Loop")

# Comprehension
for number in numbers:
    if number % 2 == 0:
        print(number)  # 이와 같은 코드를 아래와 같이 바꿀 수 있음

print([number for number in numbers if number % 2 == 0])

# membership
memberList = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
trueValue1 = 'A' in memberList  # True
falseValue1 = 'Z' in memberList  # False
trueValue2 = 'Y' not in memberList  # True
falseValue2 = 'F' not in memberList  # False
