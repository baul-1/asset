<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>MAP</title>
			<style type="text/css">
			</style>
			<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
			<link rel="preconnect" href="https://fonts.gstatic.com">
			<link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
			<link rel="stylesheet" href="css/farchive.css">
			<script src="js/farchive.js"></script>
		</head>

		<body oncontextmenu="return blockRightClick()" class="body">

			<div class="main" style="s">
				<div class="top">
					<div class="logo">
						<div><a href="map"><img alt="copin" src="http://10.10.0.20:9999/img/copin.png" style="width: 120px;"></a></div>
					</div>

					<div class="login">
						<div class="loginbutton">
							<a href="signout">로그아웃</a>
						</div>
					</div>

				</div>



				<div class="mid">

					<div class="midSide">
						<ul class="midSideNav">
							<li class="pointer midSideContents"><i class="fab fa-google-drive" style="margin-right: 7px;"></i> <a href="map">에셋 관리</a></li>
							<li class="pointer midSideContents"><i class="far fa-trash-alt" style="margin-right: 15px;"></i><a href="farchive" style="color: blue;">휴지통</a></li>
						</ul>
					</div>

					<div class="midBody">

						<div class="midBodyTop">
							<div class="categoryWrap">
								<div class="category">ARCHIVE</div>
							</div>
						</div>

						<div class="midBodyMain">
							<form id="midForm">
								<div style="width: 99.5%; height: 50px; margin-top: 10px; display: flex;">
									<div style=" line-height: 43px; margin: 0px 10px 0px 13px;">
										<input class="pointer" type="checkbox" id="checkboxAll">
									</div>
									<div>
										<label class="pointer stop-dragging" id="checkboxAll" for="checkboxAll" style="font-size: 14px; line-height: 45px;">모두 선택</label>
									</div>

									<div style=" line-height: 45px; margin-left: 80px;">
										<button class="pointer outline deleteMoveButton stop-dragging" style=" margin-right: 20px;"><label for="selectDelete" class="pointer">선택삭제</label></button>
										<button class="pointer outline deleteMoveButton stop-dragging moveSelect"><label for="selectRestore" class="pointer">선택 복원</label></button>
										<input type="button" id="selectDelete"  onclick="fn_selectDelete(this.form)" style="display: none;">
										<input type="button" id="selectRestore" onclick="fn_selectRestore(this.form)" style="display: none;">
									</div>
								</div>
										
							
											
								<div class="flex-wrap">
									<c:if test="${not empty catname or not empty subname}">
										<div style="margin-left: 14px; margin-bottom: 10px; margin-top: 10px;">폴더</div>
									</c:if>
									<div class="flex-container">
										<c:if test="${not empty catname }">
											<c:forEach var="name" items="${catname }">
												<div class="fname im pointer" id="${name }" style="display: flex;">
													<input type="checkbox" class="checkbox pointer" value="${name}" name="checkbox" style="margin-left: 10px; margin: 12px 0px 0px 10px; width: 20px; height: 20px;">
													<div style="margin-left: 15px;">
														<i class="fas fa-folder"></i>
													</div>
													<div class="text-color" style="margin-left: 12px;">
														${name }
													</div>
												</div>
											</c:forEach>
										</c:if>
										
										<c:if test="${not empty subname }">
											<c:forEach var="name" items="${subname }">
												<div class="fname im pointer" id="${name }" style="display: flex;">
													<div style="margin-left: 15px;">
													<i class="fas fa-folder"></i>
												</div>
												<div class="text-color" style="margin-left: 12px;">
													${name }
												</div>
											</div>
										</c:forEach>
									</c:if>
									
									
								</div>
								
								<c:if test="${not empty imgname }">
									<div style="margin-left: 14px; margin-bottom: 10px; margin-top: 10px;">파일</div>
								</c:if>
								
								<div style="display: flex; flex-wrap: wrap; ">
									<c:if test="${not empty imgname }">
										<c:forEach var="name" items="${imgname }">
											<div class="iname im pointer" id="${name }">
												<div class="mainimgdiv " id="${name}">
													<input type="checkbox" class="checkbox pointer" value="${name}" name="checkbox" style="margin-left: 10px; margin: 5px 0px 0px 10px; width: 20px; height: 20px;">
													<img class="mainimg" alt="${name }" src="http://10.10.0.20:9999/img/${name }">
												</div>
												<div class="fnameText">${name }</div>
											</div>
										</c:forEach>
									</c:if>
								</div>
								
								<c:if test="${empty catname and empty subname and empty imgname }">
									<div style="  width: 450px; box-shadow:10px; height: 450px; margin: auto;  margin-top:  5%; border-radius: 50%;  ">
									<img class="mainimg" src="http://10.10.0.20:9999/img/휴지통.png" style="margin-left: 150px; margin-top:130px;  height: 150px; width: 150px;">
									<div style="text-align: center;">
										<div style="margin-top: 5px; font-size: 20px; color: rgb(128, 128, 128);">항목 없음</div>
											<div style="margin-top: 5px; font-size: 13px; color: rgb(128, 128, 128);">
												휴지통으로 이동한 항목이 여기에 표시됩니다.</div>
											</div>
										</div>
									</c:if>
								</div>
								
							</form>
							<ul class="contextmenu">
								<li class="restore pointer" style="display: flex;"><i class="far fa-arrow-alt-circle-up" style="margin-top: 7px; margin-right: 10px;"></i><a>복원</a></li>
								<li class="delete pointer" style="display: flex;"><i class="far fa-trash-alt" style="margin-top: 7px; margin-right: 10px;"></i><a >삭제</a></li>
							</ul>

						</div>

					</div>

				</div>

			</div>


		</body>

		</html>