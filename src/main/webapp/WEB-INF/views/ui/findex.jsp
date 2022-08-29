<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>MAP</title>
			<link rel="stylesheet" href="css/findex.css">
			<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
			<link href="https://fonts.gstatic.com" rel="preconnect">
			<link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
			<script src="js/findex.js"></script>
		</head>

		<body oncontextmenu="return blockRightClick()" class="body">

			<div class="main" style="s">
				<div class="top">
					<div class="logo">
						<div><a href="map"><img alt="copin" src="http://10.10.0.20:9999/img/copin.png"
									style="width: 120px;"></a></div>
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
							<li style="display: flex;" class="pointer"><i class="fab fa-google-drive" style="margin-top: 3px; margin-right: 11px;"></i> <a href="map" style="color: blue;">에셋 관리</a></li>
							<li style="display: flex;" class="pointer"><i class="far fa-trash-alt"
									style="margin-top: 3px; margin-right: 15px;"></i><a href="farchive">휴지통</a></li>
						</ul>
					</div>

					<div class="midBody">


						<div class="midBodyTop">

							<div class="categoryWrap">

								<div class="category">
									CATEGORY
									<i class="fas fa-chevron-down"></i>
								</div>

							</div>
						</div>

						<div class="midBodyMain">

							<div class="pointer plus" style="margin: 10px 0px 0px 10px; width: 28px;  height: 29px;  ">
								<i class="far fa-plus-square fa-2x" style="color: blue"></i>
							</div>

							<c:choose>
								<c:when test="${not empty catname }">
									<div style="width: 100%;">
										<div class="flex-container">
											<c:forEach var="name" items="${catname }">
												<div class="fname pointer" id="${name }" style="display: flex;">
													<div style="margin-left: 15px;">
														<i class="fas fa-folder"></i>
													</div>
													<div class="text-color" style="margin-left: 12px;">
														${name }
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
								</c:when>

								<c:otherwise>
									<div
										style="  width: 450px; box-shadow:10px; height: 450px; margin: auto;  margin-top:  5%; border-radius: 50%; background: rgba(220, 220, 220, 0.3); ">
										<img class="mainimg" alt="" src="http://10.10.0.20:9999/img/file.png"
											style="margin-left: 150px; margin-top:130px;  height: 150px; width: 150px;">
										<div style="text-align: center;">
											<div style="margin-top: 5px; font-size: 20px; color: rgb(128, 128, 128);">
												'새로 만들기' 버튼을 사용하세요.</div>
										</div>
									</div>
								</c:otherwise>
							</c:choose>


							<ul class="contextmenu">
								<li class="namemodify" style="display: flex;"><i class="fas fa-pencil-alt"
										style="margin-top: 7px; margin-right: 10px;"></i><a href="#">이름 바꾸기</a></li>
								<li class="delete" style="display: flex;"><i class="far fa-trash-alt"
										style="margin-top: 7px; margin-right: 10px;"></i><a href="#">삭제</a></li>
							</ul>

							<form>
								<div class="modifycommand">
									<div style=" height: 50px; display: flex; ">
										<div style=" margin-left: 30px; padding-top: 10px; font-size: 25px;">이름 바꾸기
										</div>
										<div style=" padding-top: 10px; margin-left: auto; margin-right: 30px"
											class="modifyClose pointer"><i class="fas fa-times"></i></div>
									</div>

									<div style=" height: 50px; margin-top: 10px; ">
										<input type="text" class="catnamemodify   inputname   inputpadding"
											name="newname" style="width: 330px; height: 30px; margin-left: 30px;"
											autocomplete="off">
										<input type="text" name="catname" style="display: none;">
									</div>

									<div
										style="border: height: 50px; margin-top: 5px; text-align: right; padding-top: 5px;">
										<input type="button" class="exit pointer" value="취소" onclick="fn_modifyclose()"
											style="background: white; width: 80px; height: 45px; border: none ">
										<input type="button" class="pointer" value="확인" onclick="fn_modify(this.form)"
											style="margin-right: 30px; width: 80px; height: 45px; background: rgb(0, 0, 255, 0.7); color: white; border: none; border-radius:5px;">
									</div>
								</div>
							</form>

							<form>
								<div class="insertCommand">
									<div style=" height: 50px; display: flex; ">
										<div style=" margin-left: 30px; padding-top: 10px; font-size: 25px;">만들기</div>
										<div style="padding-top: 10px; margin-left: auto; margin-right: 30px"
											class="insertClose"><i class="fas fa-times pointer"></i></div>
									</div>

									<div style=" height: 50px; margin-top: 10px; ">
										<input type="text" class="inputpadding insertname" name="insertname"
											style="width: 330px; height: 30px; margin-left: 30px;" autocomplete="off">
									</div>

									<div
										style="border: height: 50px; margin-top: 5px; text-align: right; padding-top: 5px;">
										<input type="button" class="exit pointer insertClose" value="취소"
											style="background: white; width: 80px; height: 45px; border: none ">
										<input type="button" class="pointer" value="확인" onclick="fn_ADD(this.form)"
											style="margin-right: 30px; width: 80px; height: 45px; background: rgb(0, 0, 255, 0.7); color: white; border: none; border-radius:5px;">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</body>

		</html>