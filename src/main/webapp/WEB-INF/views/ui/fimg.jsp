<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>MAP</title>
			<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
			<link rel="preconnect" href="https://fonts.gstatic.com">
			<link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
			<link rel="stylesheet" href="css/fimg.css"> 
			<script src="js/fimg.js"></script>
		</head>

		<body oncontextmenu="return blockRightClick()" class="body" onload="catSelect()">



			<div class="main" >
				<div class="top">
					<div class="logo">
						<div>
							<a href="map"><img class="logoimg" alt="copin" src="http://10.10.0.20:9999/img/copin.png" style="width: 120px;"></a>
						</div>
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
							<li style="display: flex;" class="pointer"><i class="far fa-trash-alt" style="margin-top: 3px; margin-right: 15px;"></i><a href="farchive">휴지통</a></li>
						</ul>
					</div>

					<div class="midBody">


						<div class="midBodyTop">

							<div class="categoryWrap">

								<div class="category">
									<span id="CATEGORY"><a href="/" style="color: black;">CATEGORY</a></span>
									<i class="fas fa-chevron-right"></i>
								</div>

								<div class="category">
									<span id="CATEGORY"><a href="fsub?cat=${catname }" style="color: black;">${catname}</a></span>
									<i class="fas fa-chevron-right"></i>
								</div>

								<div class="category">
									${subname }
									<input type="hidden" class="subname" value="${subname }">
									<input type="hidden" class="catname" value="${catname }">
									<i class="fas fa-chevron-down"></i>
								</div>
							</div>
						</div>

						<div class="midBodyMain">

							<div class="" style="width: 99.5%; height: 50px; margin-top: 10px; display: flex;">
								<div style=" line-height: 43px; margin: 0px 10px 0px 13px;">
									<input class="pointer" type="checkbox" id="checkboxAll">
								</div>
								<div>
									<label class="pointer stop-dragging" id="checkboxAll" for="checkboxAll" style="font-size: 14px; line-height: 45px;">모두 선택</label>
								</div>
								<div style=" line-height: 45px; margin-left: 80px;">
									<button class="pointer outline deleteMoveButton stop-dragging" style=" margin-right: 20px;"><label for="selectDelete" class="pointer">선택 삭제</label></button>
									<button class="pointer outline deleteMoveButton stop-dragging moveSelect">선택 이동</button>
								</div>
								<div style=" line-height: 45px; font-size: 14px; margin-left: auto; margin-right: 15px;">
									<span class="pointer stop-dragging" id="latest">최신순</span> |
									<span class="pointer stop-dragging" id="productname">이름순</span>
								</div>
							</div>
						

							<form id="uploadForm" name="uploadForm" method="post" enctype="multipart/form-data">
								<div style="width: 97%; min-width: 1570px; height: 120px; border:solid rgba(192, 192, 192) 1px; margin-left: 15px; display: flex;">

									<div id="fileDrop" class="fileDrop scrollnone" style=" width: 95%; height:110px;  margin-right:15px; overflow: auto; margin-top: 5px; ">
										<div class="fileNotice stop-dragging" style="width: 500px; height: 100px;margin: auto; margin-top: 10px; text-align: center; line-height: 100px; font-weight: 100; color: rgb(169, 169, 169); ">
											파일을 드레그 해주세요.
										</div>
									</div>

									<button type="button" class="pointer upload stop-dragging uploadbutton" style=" width: 120px; height: 80px; margin-left: auto; margin-top: 20px; margin-right: 30px; text-align:  center; line-height: 80px; border:none; background: rgb(169, 169, 169); color: white; ">업로드</button>
								</div>
							</form>

							<form>
								<div class="movecommand">
									<div style=" height: 50px; display: flex; ">
										<div style=" margin-left: 30px; padding-top: 10px; font-size: 25px;">이동</div>
										<div style=" padding-top: 10px; margin-left: auto; margin-right: 30px" class="moveClose pointer"><i class="fas fa-times"></i></div>
									</div>

									<div style="display: flex;">

										<div style=" height: 50px; margin-top: 10px; ">
											<input type="text" class="catname" name="catname" style="display: none;">
											<input type="text" name="imgname" style="display: none;">
											<input type="text" name="rsubname" value="${subname}" style="display: none;">
											<select onchange="fn_catHandleOnChange(this)" id="catSelect" class="catSelect" style="width: 150px; height: 30px; margin-left: 30px; padding-left: 10px;">
												<c:forEach items="${catnamelist }" var="catname">
													<option value="${catname }">${catname }</option>
												</c:forEach>
											</select>
										</div>

										<div style=" height: 50px; margin-top: 10px; ">
											<input type="text" class="subname" name="subname" style="display: none;">
											<select onchange="fn_subHandleOnChange(this)" class="subSelect" style="width: 150px; height: 30px; margin-left: 30px; padding-left: 10px;">
											</select>
										</div>
									</div>

									<div style="border: height: 50px; margin-top: 5px; text-align: right; padding-top: 5px;">
										<input type="button" class="exit pointer moveClose" value="취소" onclick="fn_modifyclose()" style="background: white; width: 80px; height: 45px; border: none ">
										<input type="button" class="pointer" value="확인" onclick="fn_imgmove(this.form)" style="margin-right: 30px; width: 80px; height: 45px; background: rgb(0, 0, 255, 0.7); color: white; border: none; border-radius:5px;">
									</div>
								</div>
							</form>

							<form>
								<div class="moveSelectcommand">
									<div style=" height: 50px; display: flex; ">
										<div style=" margin-left: 30px; padding-top: 10px; font-size: 25px;">선택 이동</div>
										<div style=" padding-top: 10px; margin-left: auto; margin-right: 30px" class="moveClose pointer"><i class="fas fa-times"></i></div>
									</div>

									<div style="display: flex;">

										<div style=" height: 50px; margin-top: 10px; ">
											<input type="text" class="catname" name="catname" style="display: none;">
											<input type="text" name="imgname" style="display: none;">
											<input type="checkbox" name="select" style="display: none">
											<input type="text" name="rsubname" value="${subname}" style="display: none;">
											<select onchange="fn_catHandleOnChange(this)" id="catSelect" class="catSelect2" style="width: 150px; height: 30px; margin-left: 30px; padding-left: 10px;">
												<c:forEach items="${catnamelist }" var="catname">
													<option value="${catname }">${catname }</option>
												</c:forEach>
											</select>
										</div>

										<div style=" height: 50px; margin-top: 10px; ">
											<input type="text" class="subname" name="subname" style="display: none;">
											<select onchange="fn_subHandleOnChange(this)" class="subSelect" id="subSelect" style="width: 150px; height: 30px; margin-left: 30px; padding-left: 10px;">
											</select>
										</div>
									</div>

									<div style="border: height: 50px; margin-top: 5px; text-align: right; padding-top: 5px;">
										<input type="button" class="exit pointer moveClose" value="취소" onclick="fn_modifyclose()" style="background: white; width: 80px; height: 45px; border: none ">
										<input type="button" class="pointer" value="확인" onclick="fn_imgSelectMove(this.form)" style="margin-right: 30px; width: 80px; height: 45px; background: rgb(0, 0, 255, 0.7); color:  white; border: none; border-radius:5px;">
										<input type="button" id="selectDelete" onclick="fn_selectDelete(this.form)" style="display: none;">
									</div>
								</div>

								<div class="flex-container">
									<c:choose>
										<c:when test="${not empty imgname }">
											<c:forEach var="name" items="${imgname }">
												<div class="fname pointer" id="${name.name }">
													<div class="mainimgdiv" id="${name.name }">
														<input type="checkbox" class="checkbox pointer" value="${name.name}" name="checkbox" style="margin-left: 10px; margin: 5px 0px 0px 10px; width: 20px; height: 20px;">
														<img class="mainimg" alt="${name.name }" src="http://10.10.0.20:9999/img/${name.name }">
														
													</div>
													<div class="fnameText">
														<div>${name.name }</div>
														<div>${name.createdate }</div>
													</div>
												</div>
											</c:forEach>

										</c:when>

										<c:otherwise>
											<div style="  width: 450px; box-shadow:10px; height: 450px; margin: auto;  margin-top:  5%; border-radius: 50%; background: rgba(220, 220, 220, 0.3); " class='newfileupload'>
												<img class="mainimg" src="http://10.10.0.20:9999/img/file.png" style="margin-left: 150px; margin-top:130px;  height: 150px; width: 150px;">
												<div style="text-align: center;">
													<div style="margin-top: 5px; font-size: 20px; color: rgb(128, 128, 128);"> '파일 업로드' 버튼을 사용하세요.</div>
												</div>
											</div>
										</c:otherwise>

									</c:choose>

								</div>

							</form>

							<ul class="contextmenu">
								<li class="namemodify" style="display: flex;"><i class="fas fa-pencil-alt" style="margin-top: 7px; margin-right: 10px;"></i><a href="#" class="modifyA">이름바꾸기</a></li>
								<li class="move" style="display: flex;"><i class="fas fa-arrows-alt-h" style="margin-top: 7px; margin-right: 10px;"></i><a href="#">이동</a></li>
								<li class="delete" style="display: flex;"><i class="far fa-trash-alt" style="margin-top: 7px; margin-right: 10px;"></i><a href="#">삭제</a></li>
							</ul>

							<form>
								<div class="modifycommand">
									<div style=" height: 50px; display: flex; ">
										<div style=" margin-left: 30px; padding-top: 10px; font-size: 25px;">이름 바꾸기</div>
										<div style=" padding-top: 10px; margin-left: auto; margin-right: 30px"class="modifyClose  "><i class="fas fa-times pointer "></i></div>
									</div>

									<div style=" height: 50px; margin-top: 10px; ">
										<input type="text" class="catnamemodify inputname inputpadding" name="newname" style="width: 330px; height: 30px; margin-left: 30px;">
										<input type="text" name="imgname" style="display: none ;">
										<input type="text" name="subname" value="${subname }" style="display: none ;">
										<input type="text" name="filepath" style="display: none;">
									</div>

									<div style="border: height: 50px; margin-top: 5px; text-align: right; padding-top: 5px;">
										<input type="button" class="exit pointer" value="취소" onclick="fn_modifyclose()" style="background: white; width: 80px; height: 45px; border: none ">
										<input type="button" class="pointer" value="확인" onclick="fn_modify(this.form)"  style="margin-right: 30px; width: 80px; height: 45px; background: rgb(0, 0, 255, 0.7); color: white; border: none; border-radius:5px;">
									</div>
								</div>
							</form>

						</div>

					</div>

				</div>

			</div>

		</body>

		</html>