<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="false" %>
<c:set var="id" value="${pageContext.request.getSession(false).getAttribute('id')}"/>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>fastcampus</title>
  <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
  <style>
    article{
      margin : 5px 20% 20px 20%
    }

    section{
      padding: 5% 10% 3% 10%;
      border : 1px solid #cccbcb;
      border-radius: 10px;
    }

    textarea{
      outline: none;
    }

    input[type=text]{
      outline: none;
      width: 100%;
      padding: 5px;
      margin-bottom: 5px;
      border:0px;
      font-size : 20px
    }

    a{
      text-decoration : none;
    }

    ul{
      list-style: none;
    }

    #content > textarea{
      margin-top: 10px;
      width: 100%;
      height: 500px;
      border-color: #cccbcb;
      border-left: 0;
      border-right: 0;
      padding: 8px;
      resize: none;
    }

    #writer{
      text-align: right;
    }

    #form > div{
      padding-bottom: 15px;
    }

    .button{
      text-align: right;
    }

    .button > button{
      margin-right: 10px;
      background-color: #30426E;
      color:white;
      border : 0;
      padding: 10px;
      border-radius: 5px;
    }

    .modal {
      display: none; /* Hidden by default */
      position: fixed; /* Stay in place */
      z-index: 1; /* Sit on top */
      padding-top: 100px; /* Location of the box */
      left: 0;
      top: 0;
      width: 100%; /* Full width */
      height: 100%; /* Full height */
      overflow: auto; /* Enable scroll if needed */
      background-color: rgb(0,0,0); /* Fallback color */
      background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }

    .close{
      cursor: pointer;
    }

    /* comment */
    .meta{
      padding-top : 10px;
      padding-left : 10px
    }

    .comment-content{
      min-height: 50px;
      padding: 5px;
    }

    .comment-bottom{
      font-size : small;
      text-align : right;
      padding-bottom: 7px;
    }

    .comment-bottom > a{
      padding-right: 5px;
    }

    .comment-item{
      border-bottom: 1px solid #cccbcb;
    }

    /* paging */
    .paging-container{
      padding: 20px 0 20px 0;
      text-align: center;
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

    /* Comment writeBox Content */
    #comment-writebox{
      padding: 10px;
      border-top : 1px solid #cccbcb;
      border-radius: 4px;
    }

    .comment-writebox-content{
      padding : 10px 0 5px 0;
    }

    .comment-writebox-content > textarea{
      width: 100%;
      min-height: 100px;
      resize : none;
      padding: 5px;
      border-color: #cccbcb;
    }

    .register-box{
      text-align: right;
      padding: 10px 0 6px 0;
    }

    .register-box > a{
      background-color: #30426E;
      color:white;
      border : 0;
      padding: 5px;
      border-radius: 5px;
    }

    /* reply writeBox Content */
    #reply-writebox{
      display: none;
    }

    #reply-content{
      padding: 10px;
    }

    .reply-writebox-content{
      padding-top: 8px;
      padding-left : 10px
    }

    .reply-writebox-content > textarea{
      width: 100%;
      min-height: 100px;
      resize : none;
      padding: 5px;
      border-color: #cccbcb;
    }


    /* Modal Content */
    .modal-content {
      background-color: #fefefe;
      margin: auto;
      padding: 20px;
      border: 1px solid #888;
      width: 50%;
    }

    #comment{
      display: none;
    }

    .reply{
      padding-left: 20px;
    }

  </style>
</head>
<body>
  <jsp:include page="module/menu.jsp"/>
  <article>
    <section id="board">
    <div class="board">
      <form id="form" method="post" action="">
      <div id="bno"><input name="bno" type="text" value="${boardDto.bno}" hidden></div>
      <div id="title"><input name="title" type="text" value="${boardDto.title}" placeholder="제목을 입력해 주세요" spellcheck="false" readonly></div>
      <div id="writer"><input name="writer" value="${boardDto.writer}" hidden><span>작성자 : ${boardDto.writer}</span></div>
      <div id="content"><textarea name="content" placeholder="내용을 입력해 주세요" spellcheck="false" readonly>${boardDto.content}</textarea></div><!-- board 클래스의 끝 -->
      </form>
      <div class="button">
        <button id="board-write">새글쓰기</button>
        <button id="board-send">등록</button>
        <button id="board-modify">수정</button>
        <button id="board-remove">삭제</button>
        <button id="board-cancel">취소</button>
        <button id="board-list" onclick="listBtn()">목록</button>
      </div>
    </div>
    </section>
    <section id="comment">
      <div id="commentList">
        <ul id="commentUl">
        </ul>
        <div class="paging-container">
        </div>
      </div>
      <div id="comment-writebox">
      </div>
      <div id="modalWin" class="modal">
      </div>
      <div id="reply-writebox">
      </div>
    </section>
  </article>
  <script>
    let msg = "${msg}"
    let mode = "${mode}"
    let bno = "${boardDto.bno}"
    let cmtPage;
    let cmtPageSize;
    let count;

    let writeMode = function(){
      let cancelBtn = document.querySelector("#board-cancel");

      document.querySelector("#title > input").readOnly=false;
      document.querySelector("#content > textarea").readOnly=false;

      document.querySelector("#board-remove").hidden=true;
      document.querySelector("#board-write").hidden=true;
      document.querySelector("#board-modify").hidden=true;

      cancelBtn.addEventListener("click", function(){
        window.history.back();
      })
    }

    let newMode = function(){
      // let form = document.querySelector("#form");
      let sendBtn = document.querySelector("#board-send");

      writeMode();
      document.querySelector("#writer> span").innerHTML = "작성자 : "+'${id}';
      document.querySelector("#writer> input").setAttribute("value",'${id}');
      // document.querySelector("textarea").hidden=true;

      sendBtn.addEventListener("click",write);

    }

    let readMode = function(){
      let modBtn = document.querySelector("#board-modify");
      let writeBtn = document.querySelector("#board-write");
      let removeBtn = document.querySelector("#board-remove");

      document.querySelector("#board-send").hidden=true;
      document.querySelector("#board-cancel").hidden=true;
      if("${id}"!="${boardDto.writer}"){
        modBtn.hidden=true;
        removeBtn.hidden=true;
      }

      writeBtn.addEventListener("click",function(){
        location.href="<c:url value='/board/write${sc.queryString}'/>";
      })

      removeBtn.addEventListener("click",()=> ajaxData('DELETE','<c:url value='/board/${boardDto.bno}'/>',{},"삭제"))

      modBtn.addEventListener("click",function(){
        location.href="<c:url value='/board/${bno}${sc.queryString}&mode=mod'/>";
      })

      document.querySelector("#comment").style.display = "block";
      showListPaging(0);
      commentWriteBox();
      modalContent();
      reply_writeBox();
      document.querySelector("#btn-write-comment").addEventListener("click",commentWrite);
      document.querySelector("#commentUl").addEventListener("click",commentOption);
      document.querySelector(".modal > div > span").addEventListener("click", closeModal);
      document.querySelector(".paging-container").addEventListener("click", function(e){
        if(e.target.getAttribute("class").includes("page")){
          showListPaging(e.target.getAttribute("value"))
        }
      })
    }

    let modMode = function(){
      let form = document.querySelector("#form");
      let sendBtn = document.querySelector("#board-send");
      let cancelBtn = document.querySelector("#board-cancel");
      writeMode();
      document.querySelector("#board-send").hidden=false;
      document.querySelector("#board-cancel").hidden=false;

      sendBtn.addEventListener("click",()=>{
        let title = document.querySelector("#title > input").value;
        let writer = document.querySelector("#writer > input").value;
        let content = document.querySelector("#content > textarea").value;
        ajaxData('PUT','<c:url value='/board/${boardDto.bno}'/>',{title:title, content:content, writer:writer},"수정")
      })

      cancelBtn.addEventListener("click",function(){
        location.href="<c:url value='/board/'/>${boardDto.bno}${sc.queryString}"
      })

    }

    if(msg != ""){
      if(msg=="WRT_ERR")
        alert("게시글 작성에 실패했습니다.");
      else if(msg=="DEL_ERR")
        alert("삭제에 실패했습니다.");
      else if(msg=="MOD_OK")
        alert("수정에 성공했습니다.");
    }

    window.onload = function()
    {
      if (mode == "new") {
        newMode();
      } else if (mode == "mod") {
        modMode();
      } else {
        readMode()
      }
    }

    function closeModal(){
      document.querySelector(".modal").style.display = "none";
    }

    function listBtn(){
      location.href = '<c:url value="/board${sc.queryString}"/>';
    }

    function showListPaging(commentPage){
      showList(commentPage)
      showPaging(commentPage);
      reply_writeBox();
    }

    function showPaging(commentPage){
      let xhr = new XMLHttpRequest();
      commentPage= (commentPage==null ? 0:commentPage);
      xhr.open('GET', 'http://localhost/MyWeb1/comments/paging/'+commentPage+'?bno='+bno);

      xhr.send();

      xhr.onload = function(){
        if(xhr.status==200){
          const res = JSON.parse(xhr.responseText);
          cmtPage = res.sc.page;
          cmtPageSize = res.sc.pageSize;
          count = res.totalCnt;
          totalPage = res.totalPage;
          document.querySelector(".paging-container").innerHTML = paging(res);
        }
      }
    }

    function paging(result){
      let tmp = '<div class="paging">';
      if(`${'${result.showPrev}'}`=='true'){
        tmp+= `<a href="#" class="page" value="${'${result.beginPage-1}'}" onclick="return false">&lt;</a>`
      }

      let step;
      for(step = `${'${result.beginPage}'}`; step<=`${'${result.endPage}'}`; step++){
        if(`${'${result.sc.page}'}` == step){
          tmp+= `<a href="#" class="page paging-active" value="${'${step}'}" onclick="return false">${'${step}'}</a>`
        } else{
          tmp+= `<a href="#" class="page" value="${'${step}'}" onclick="return false">${'${step}'}</a>`
        }
      }

      if(`${'${result.showNext}'}`=='true'){
        tmp+= `<a class="page" value="${'${result.endPage+1}'}" onclick="return false">&gt;</a>`
      }

      return tmp+'</div>'
    }

    function commentWriteBox(){
      let tmp =
              `<i class="fa fa-user-circle" aria-hidden="true"></i>
              <span class="commenter commenter-writebox">${id}</span>
        <div class="comment-writebox-content">
            <textarea name="" id="comment-textarea" class="textarea" cols="30" rows="3" placeholder="댓글을 남겨보세요" spellcheck="false"></textarea>
        </div>
        <div id="comment-writebox-bottom">
            <div class="register-box">
                <a href="#" class="btn" id="btn-write-comment" onclick="return false">등록</a>
            </div>
        </div>`
      document.querySelector("#comment-writebox").innerHTML = tmp;
    }

    function showList(commentPage){
      let xhr = new XMLHttpRequest();

      xhr.open('GET', 'http://localhost/MyWeb1/comments/'+commentPage+'?bno=${boardDto.bno}')

      xhr.send();

      xhr.onload = function(){
        const res = JSON.parse(xhr.responseText);
        let tmp='';
        if(res){
          res.forEach((comment)=>{
            let date = comment.reg_date;
            tmp += `<li class="comment-item" data-cno=${'${comment.cno}'}>`

            tmp += `
            <div class="meta">`
            if(comment.pcno != comment.cno){
              tmp += `ㄴ`
            }
             tmp +=
               `<span class="comment-img">
                <i class="fa fa-user-circle" aria-hidden="true"></i>
              </span>
              <span class="commenter">${'${comment.commenter}'}</span>
              <span class="up_date">`+dateFormat(date)+`</span>
            </div>`
            if(comment.pcno != comment.cno){
              tmp += `<div class="comment-area reply">`
            } else{
              tmp += `<div class="comment-area">`
            }
            tmp +=
             `<div class="comment-content">${'${comment.comment}'}</div>
              <div class="comment-bottom">
                <a href="#" class="btn-write"  data-cno=${'${comment.cno}'} data-bno=${'${comment.bno}'} data-pcno=${'${comment.pcno}'} onclick="return false">답글쓰기</a>`
            if(`${'${comment.commenter}'}`=='${id}') {
              tmp+=
               `<a href="#" class="btn-modify" data-cno=${'${comment.cno}'} data-bno=${'${comment.bno}'} data-pcno=${'${comment.pcno}'} onclick="return false">수정</a>`
              tmp+=
               `<a href="#" class="btn-delete" data-cno=${'${comment.cno}'} data-bno=${'${comment.bno}'} data-pcno=${'${comment.pcno}'} onclick="return false">삭제</a>`
            }
            tmp+=
             `</div>
            </div>
          </li>`
          })
          document.querySelector("#commentUl").innerHTML = tmp;
        }
      }

      xhr.onerror = function(){
        alert("write error");
      }
    }

    function write(){
      let title = document.querySelector("#title > input").value;
      let writer = document.querySelector("#writer > input").value;
      let content = document.querySelector("#content > textarea").value;
      let data = {title : title, writer: writer, content : content }
      let url = '<c:url value="/board/write"/>'
      ajaxData('POST',url, data, "작성");
    }

    function commentWrite(){
      let xhr = new XMLHttpRequest();
      let comment = document.querySelector("#comment-textarea").value;
      xhr.open('POST', 'http://localhost/MyWeb1/comments');

      // 포스트 내용에 어떻게 댓글작성내용을 담을것인지지
      let data = {
          bno:${boardDto.bno!=null ? boardDto.bno:0},
          comment:comment
      };

      xhr.setRequestHeader("content-type", "application/json");


     xhr.send(JSON.stringify(data));

      xhr.onload = function(){
        if (xhr.status == 200) {
          alert("댓글 등록에 성공했습니다.");
          document.querySelector("#comment-textarea").value = "";
          showListPaging(0);
        } else {
          alert("댓글 등록에 실패했습니다.");
        }

      }
    }

    function reply_writeBox(){
      let tmp =
      `<div id="reply-content">
        <div class="meta">ㄴ
          <i class="fa fa-user-circle" aria-hidden="true"></i>
          <span class="commenter commenter-writebox">${id}</span>
        </div>
          <div class="reply-writebox-content">
            <textarea id="" class="textarea" cols="30" rows="3" placeholder="답글을 남겨보세요" spellcheck="false"></textarea>
          </div>
          <div id="reply-writebox-bottom">
            <div class="register-box">
              <a href="#" class="btn" id="btn-write-reply" onclick="return false">등록</a>
              <a href="#" class="btn" id="btn-cancel-reply" onclick="return false">취소</a>
          </div>
        </div>
       </div>`
      document.querySelector("#reply-writebox").innerHTML = tmp;
    }

    function modalContent(){
      let tmp =
       `<div class="modal-content">
          <span class="close">&times;</span>
          <p>
          <h2> | 댓글 수정</h2>
          <div id="modify-writebox">
            <div class="commenter commenter-writebox"></div>
            <div class="modify-writebox-content">
              <textarea name="" id="" class="textarea" cols="30" rows="5" placeholder="댓글을 남겨보세요"></textarea>
            </div>
            <div id="modify-writebox-bottom">
              <div class="register-box">
                <a href="#" class="btn" id="btn-write-modify" onclick="return false">등록</a>
              </div>
            </div>
          </div>
          </p>
        </div>`
      document.querySelector("#modalWin").innerHTML = tmp;
    }

    function commentOption(e){ // 옵션별로 만들기 답글쓰기 수정 삭제
      let command = e.target.getAttribute("class");
      let cno = e.target.getAttribute("data-cno");
      let pcno = e.target.getAttribute("data-pcno");

      if(command=="btn-delete")
        remove(e);
      if(command=="btn-modify")
        update(e);
      if(command=="btn-write")
        reply(e)

      function reply(e){
        let reply_box = document.querySelector("#reply-writebox");
        let reply_content = document.querySelector("#reply-content");
        let comment = document.querySelector("li[data-cno='"+cno+"']");
        comment.append(reply_content);

        reply_content.addEventListener("click", function(event){
          if(event.target.id=="btn-write-reply") {
            let xhr = new XMLHttpRequest();
            let comment = reply_content.querySelector("textarea").value;
            xhr.open('POST', 'http://localhost/MyWeb1/comments');

            // 포스트 내용에 어떻게 댓글작성내용을 담을것인지지
            let data = {
              bno:${boardDto.bno!=null ? boardDto.bno:0},
              pcno:pcno,
              comment:comment
            };

            xhr.setRequestHeader("content-type", "application/json");


            xhr.send(JSON.stringify(data));

            xhr.onload = function(){
              if (xhr.status == 200) {
                alert("댓글 등록에 성공했습니다.");
                reply_writeBox();
                showListPaging(cmtPage);
              } else {
                alert("댓글 등록에 실패했습니다.");
              }

            }
          }
        })

        reply_content.addEventListener("click", function(event){
          if(event.target.id=="btn-cancel-reply")
            reply_box.append(reply_content);
        })

      }

      function update(e){
        let modal = document.querySelector(".modal");
        let textarea = modal.querySelector("textarea");
        let content = e.target.parentElement.previousElementSibling;

        modal.style.display = "block";
        textarea.innerHTML = content.innerHTML;

        modal.querySelector("#btn-write-modify").addEventListener("click", function(){
          let xhr = new XMLHttpRequest();
          xhr.open('put', 'http://localhost/MyWeb1/comments/'+cno);

          let data = {
            comment:textarea.value
          };

          xhr.setRequestHeader("content-type", "application/json");

          xhr.send(JSON.stringify(data));

          xhr.onload = function(){
            if (xhr.status == 200) {
              alert("수정에 성공했습니다.")
              closeModal();
              showListPaging(cmtPage);
            } else {
              alert("수정에 실패했습니다.")
            }
          }
        })
      }

      async function remove(e){
        let cno = e.target.getAttribute("data-cno");
        let data = {
          bno:${boardDto.bno!=null ? boardDto.bno:0}
        };

        fetch('http://localhost/MyWeb1/comments/'+cno,{
          method: 'DELETE',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(data),
        })
                .then(res => {
                  if(res.status==200){
                    alert("삭제에 성공했습니다.");
                    if(Math.ceil((count-1)/cmtPageSize)!=totalPage)
                      showListPaging(cmtPage-1);
                    else
                      showListPaging(cmtPage);
                  } else{
                    alert("삭제에 실패했습니다.");
                  }
                })
                .catch((error)=>{
                  alert("에 실패하셨습니다.");
                })
      }
    }



    function dateFormat(date) {
      let now = new Date()
      let nowMilli = now.getTime();
      let timeDiff = nowMilli-date;

      if(timeDiff>3600000 && timeDiff<86400000){
        return Math.floor(timeDiff/3600000)+"시간전";
      } else if(timeDiff<3600000){
        return Math.floor(timeDiff/60000)+"분전";
      }


      let reg_date = new Date(date);

      let month = reg_date.getMonth() + 1;
      let day = reg_date.getDate();
      // let hour = reg_date.getHours();
      // let minute = reg_date.getMinutes();
      // let second = reg_date.getSeconds();

      month = month >= 10 ? month : '0' + month;
      day = day >= 10 ? day : '0' + day;
      // hour = hour >= 10 ? hour : '0' + hour;
      // minute = minute >= 10 ? minute : '0' + minute;
      // second = second >= 10 ? second : '0' + second;

      // return reg_date.getFullYear() + '.' + month + '.' + day + ' ' + hour + ':' + minute + ':' + second;
      return reg_date.getFullYear() + '.' + month + '.' + day;
    }

    async function ajaxData(method ='', url = '', data = [], message=''){
      fetch(url , {
        method: method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      })
              .then(res => res.json())
              .then(res => {
                if(res.result =="OK"){
                  alert(message+"에 성공하셨습니다.");
                  listBtn();
                }
                else
                  alert(message+"에 실패하셨습니다.\n원인 : " + res.result);
              })
              .catch((error)=>{
                alert(message+"에 실패하셨습니다.");
              })
    }


  </script>
</body>
</html>