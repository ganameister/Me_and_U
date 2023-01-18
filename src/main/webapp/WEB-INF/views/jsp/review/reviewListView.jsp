<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>review</title>
		<!-- Header -->
		<link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Dancing+Script&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/review/review.css' />">
		<c:import url="/WEB-INF/views/layout/head.jsp" />
		<script src="<c:url value='/js/review/review.js' />"></script> 	
		<!--//Header  -->
	</head>	
	<body>
		<!-- wrap -->
		<div id="wrap">
			<!-- Top -->			
			<c:import url="/WEB-INF/views/layout/top.jsp" /> 
			<!--// Top -->
			
			<!-- review_warp -->
			<div id="review_warp">	
			
				<!-- 제목 -->
				<div class="review-top">
					<div class="subTitle">BUCKETLIST</div>
					<div class="toptitle">Review</div>
					<div ><p class="underTitle">후기</p></div>
					<div class="newPost"><button id="newPost">New Post</button></div>
				</div>

				<!-- 카테고리 -->
				<div class ="review_board_top">
					<div class="select_categ" align="left">Select Category</div>
					<div class="categoryBtn" id="categ">
						<input type="button" class="cateBtn" id="categ1" value="전체">
						<input type="button" class="cateBtn" id="categ2" value="여행">
						<input type="button" class="cateBtn" id="categ3" value="운동">
						<input type="button" class="cateBtn" id="categ4" value="게임">
						<input type="button" class="cateBtn" id="categ5" value="문화">
						<input type="button" class="cateBtn" id="categ6" value="음악">
						<input type="button" class="cateBtn" id="categ7" value="기타">								
					</div>
				</div>						
				<!-- 리뷰 리스트  -->			
				<div class="reviewBox">	
																		
					<ol class="image-list grid-view">
					
			 	<c:forEach var="rev" items="${reviewList}"> 		
			
						<li class="review_container" value="${rev.reviewCtg}"> 
							<!-- id 중앙정렬 -->
							<div class="mem_InfoBox"><span><img src="<c:url value='/images/${rev.memImg }'/>" id="memImg"></span>	
							<span id="reviewmemId">${rev.memId }</span></div>							
														
							<div class ="review-Info" >
								<img  class="review-img" src="${rev.reviewImg }" alt="">																
								<div class="review_underBox">									
									<!-- 하트하트 -->
									<div class="heartbox">							
									<button class="heartBtn" ><img src='<c:url value="/images/heart.png"/>' id="heartbtnImg" ></button>
									
									<span class="heartNo">${rev.reviewHart }</span>
									</div>
									<div class="review-title"><span class="title">${rev.reviewTitle }</span></div>			
									<div class="comment"><span>${rev.reviewWrite }</span></div>
									<div align="right"><p id="reviewDate"><fmt:formatDate value='${rev.reviewDate }' pattern="yyyy-MM-dd  hh:mm"/></p></div>							
								</div>																
							</div>
						</li>
						
					 	</c:forEach> 
																									
					</ol>												
				</div>
				<!-- /div reviewBox -->						
																
			</div>
			<!-- /review_warp -->	

			<!-- Bottom -->
			<!-- footer -->
			<c:import url="/WEB-INF/views/layout/bottom.jsp" /> 
			<!-- //footer -->
			<!-- //Bottom -->
		</div>
		<!-- //wrap -->
	</body>
</html>