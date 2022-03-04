# demoSecurity

시큐리티를 사용해 보았습니다. db는 mysql

진행을 하며 오류를 2번 발생하였는데

---(오류)

'user/login?error' is not a valid redirect URL

---(해결)

SecurityConfig 클래스에 .loginPage("/user/login") 이부분에 "user/login"으로 적어놔서 오류발생...

---(오류)

java.sql.SQLException: The server time zone value '���ѹα� ǥ�ؽ�' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver 
(via the 'serverTimezone' configuration property) to use a more specifc time zone value if you want to utilize time zone support.

---(해결)

SELECT @@global.time_zone, @@session.time_zone;

쿼리를 입력하면 timezone가 system으로 나온다면  표준시간 설정이 안 되어있는것...

yml 파일에 db이름 + useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC 

해주면 해결 시간 차가 나서 연결에서 오류가 나는가 보다...

---(결과)

![image](https://user-images.githubusercontent.com/71180644/156738724-105dd85f-fbed-41ea-acae-61a3fd1ebd55.png)



![image](https://user-images.githubusercontent.com/71180644/156737252-00e4bb7a-20ca-4205-b73f-528aeac3ea8e.png)
