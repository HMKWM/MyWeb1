<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false"%>
<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
  <style>
    * { box-sizing:border-box; }

    form {
      width:400px;
      height:600px;
      display : flex;
      flex-direction: column;
      align-items:center;
      position : absolute;
      top:50%;
      left:50%;
      transform: translate(-50%, -50%) ;
      border: 1px solid rgb(89,117,196);
      border-radius: 10px;
      padding: 10px;
    }

    .input-field {
      width: 300px;
      height: 40px;
      border : 1px solid rgb(89,117,196);
      border-radius:5px;
      padding: 0 10px;
      margin-bottom: 10px;
    }
    label {
      width:300px;
      height:30px;
      margin-top :4px;
      display: block;
    }

    button {
      background-color: rgb(89,117,196);
      color : white;
      width:300px;
      height:50px;
      font-size: 17px;
      border : none;
      border-radius: 5px;
      margin : 20px 0 30px 0;
    }

    .title {
      font-size : 40px;
      margin: 40px 0 0px 0;
    }

    .msg {
      height: 20px;
      text-align:center;
      font-size:16px;
      color:red;
      margin-bottom: 10px;
    }
  </style>
  <title>Register</title>
</head>
<body>
<c:url value="/register" var="sendURI"/>

<%--@elvariable id="userDto" type="com.fastcampus.MyWeb1.Domain.UserDto"--%>
<form>
  <div class="title">가입 정보</div>
  <div id="id">
    <label for="">아이디</label>
    <input class="input-field" type="text" name="id" value="${userDto.id}" readonly>
  </div>
  <div id="pwd">
    <label for="">비밀번호</label>
    <input class="input-field" type="password" name="pwd" value="${userDto.pwd}" readonly>
  </div>
  <div id="name">
    <label for="">이름</label>
    <input class="input-field" type="text" name="name" value="${userDto.name}" readonly>
  </div>
  <div id="email">
    <label for="">이메일</label>
    <input class="input-field" type="text" name="email" value="${userDto.email}" readonly>
  </div>
  <div id="birth">
    <label for="">생일</label>
    <input class="input-field" type="date" name="birth" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${userDto.birth}"/>' readonly>
  </div>

  <a href="<c:url value="/"/>">홈으로 돌아가기</a>
</form>
<script>
  let msg = '${msg}';
  if(msg != null || msg!=''){
    alert(msg);
  }
</script>
</body>
</html>