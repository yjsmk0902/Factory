# 출력
print("Hello World!")
print(1)
print([1, 2, 3])

# 입력
input("Enter the name") # 값 입력 받기

# List  // 값 수정 가능 var
myList = [1, 2, 3, 4, 5]    # 리스트 생성
myList.append(6)    # 데이터 추가

# Tuple // 값 수정이 불가능 val
myTuple = (1, 2, 3, 4, 5)   # 튜플 생성

# Dictionary //{key1: value1, key2: value2, key3: value3}
myDictionary = {"key1": 1, "key2": 2, "key3": 3}
myDictionary["key1"] = 100

# Type 변환하기
myType = 1      # 자료형은 int
str(myType)     # 자료형은 str (= '1')
type(myType)    # 'type(변수)'로 타입 확인 가능 (= str)

# String
myString ='일반 스트링'
myStringEnter = '''이
사이에
엔터도
적용'''       # '이\n사이에\n엔터도\n적용'이 출력

# Formatting
myFormatting = 'My name is %s' % 'Luke'     # 'My name is Luke'이 출력

# format()
myFormat = 'My name is {}'.format('Luke')     # 'My nmae is Luke'이 출력

# Index
myIndex = "Luke"
print(myIndex[0])   # 'L'이 출력
print(myIndex[-1])  # 'e'이 출력

# Slice
mySlice = "Luke"
print(mySlice[:3])   # 'Luk'가 출력 (맨 앞, 맨 뒤는 생략 가능)
print(mySlice[1:3])   # 'uke'가 출력

# split()
mySplit = "My name is Luke"
print(mySplit.split())   # ['My', 'name', 'is', 'Luke'] 출력 (default는 공백)




