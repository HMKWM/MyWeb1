<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>fastcampus</title>
  <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
  <style>
    section{
      margin : 20px 15% 20px 15%
    }

    a{
      text-decoration: none;
      color: black;
    }

    .paging{
      /*width: 100%;*/
      margin-top : 20px;
      text-align: center;
    }
    table{
      width : 100%;
      text-align: center;
    }
    tr{
      height: 40px;
    }

    table{
      width: 100%;
      border-top: 1px solid #ccc;
      border-collapse: collapse;
    }

    th, td {
      border-bottom: 1px solid #ccc;
    }

    .bno{
      width: 60px;
    }
    td[class=title]{
      /*width: 500px;*/
      text-align: left;
      padding-left: 10px;
    }

    .writer{
      width: 100px;
    }

    .date{
      width: 100px;
    }

    .view-count{
      width: 80px;
    }

    .paging > a{
      cursor: pointer;
      padding: 5px 8px 5px 8px;
      background-color: #cccbcb;
      color : black;
      margin-left: 4px;
      margin-right: 4px;
      border-radius: 4px;
    }

    .search-container {
      background-color: cccbcb;
      width: 100%;
      height: 110px;
      border: 1px solid #cccbcb;
      border-right-style: none;
      border-left-style: none;
      margin-top : 10px;
      margin-bottom: 10px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .search-form {
      height: 37px;
      display: flex;
    }
    .search-option {
      width: 100px;
      height: 100%;
      outline: none;
      margin-right: 5px;
      border: 1px solid #ccc;
      color: gray;
    }
    .search-option > option {
      text-align: center;
    }
    .search-input {
      color: gray;
      background-color: white;
      border: 1px solid #ccc;
      height: 100%;
      width: 300px;
      font-size: 15px;
      padding: 5px 7px;
    }
    .search-input::placeholder {
      color: gray;
    }
    .search-button {
      /* 메뉴바의 검색 버튼 아이콘  */
      width: 20%;
      height: 100%;
      background-color: white;
      color: black;
      border : 1px solid #ccc;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 15px;
    }
    .search-button:hover {
      color: rgb(165, 165, 165);
    }

    .btn-container{
      margin-bottom: 20px;
    }

    .btn-write{
      background-color : white;
      padding: 8px;
      border : 1px solid #ccc;
      float: right;
      border-radius: 5px;
      margin-bottom: 10px;
    }

  </style>

</head>
<body>
  <jsp:include page="module/menu.jsp"/>
  <section>
    <div class="search-container">
      <form action="<c:url value="/board"/>" class="search-form" method="get">
        <select class="search-option" name="option">
          <option value="A" ${param.option=='A' ? "selected" : ""}>제목+내용</option>
          <option value="T" ${param.option=='T' ? "selected" : ""}>제목만</option>
          <option value="W" ${param.option=='W' ? "selected" : ""}>작성자</option>
        </select>

        <input type="text" name="keyword" class="search-input" type="text" value="${param.keyword}" placeholder="검색어를 입력해주세요">
        <input type="submit" class="search-button" value="검색">
      </form>
    </div>
    <div class="btn-container">
      <button id="writeBtn" class="btn-write" onclick="location.href='<c:url value="/board/write${ph.sc.queryString}"/>'">글쓰기</button>
    </div>

    <div id="list">
      <table>
        <tr>
          <th class="bno">번호</th>
          <th class="title">제목</th>
          <th class="writer">작성자</th>
          <th class="date">날짜</th>
          <th class="view-count">조회수</th>
        </tr>
        <c:forEach var="BoardDto" items="${list}">
          <tr>
            <td class="bno"><c:out value="${BoardDto.bno}"/></td>
            <td class="title"><a href="<c:url value='/board/${BoardDto.bno}${ph.sc.queryString}'/>">${BoardDto.title}</a><span><strong> [${BoardDto.comment_cnt}]</strong></span></td>
            <td class="writer"><c:out value="${BoardDto.writer}"/></td>
            <td class="date">
              <c:choose>
                <c:when test="${BoardDto.reg_date.time >= today}">
                  <fmt:formatDate value="${BoardDto.reg_date}" pattern="HH:mm"/>
                </c:when>
                <c:otherwise>
                  <fmt:formatDate value="${BoardDto.reg_date}" pattern="yyyy.MM.dd"/>
                </c:otherwise>
              </c:choose>
            </td>
            <td class="view-count"><c:out value="${BoardDto.view_cnt}"/></td>
          </tr>
        </c:forEach>
      </table>
    </div>
    <div class="paging-container">
      <div class="paging">
        <c:if test="${ph.totalCnt==null || ph.totalCnt==0}">
          <div>게시물이 없습니다.</div>
        </c:if>
        <c:if test="${ph.totalCnt!=null && ph.totalCnt!=0}">
          <c:if test="${ph.showPrev}">
            <a href="<c:url value='/board${ph.sc.getQueryString(ph.beginPage-1)}'/>">&lt;</a>
          </c:if>
          <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
            <a href="<c:url value='/board${ph.sc.getQueryString(i)}'/>">${i}</a>
          </c:forEach>
          <c:if test="${ph.showNext}">
            <a href="<c:url value='/board${ph.sc.getQueryString(ph.endPage+1)}'/>">&gt;</a>
          </c:if>
        </c:if>
      </div><%--paging--%>
    </div><%--page-container--%>
  </section>
<script>
  let msg = "${msg}";

  if(msg != ""){
    if(msg=="WRT_OK")
      alert("게시글 작성에 성공했습니다.");
    else if(msg=="DEL_OK")
      alert("삭제에 성공했습니다.");
  }

</script>
</body>
</html>