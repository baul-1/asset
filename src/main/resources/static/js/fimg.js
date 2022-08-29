var checkCount = 2;

var fileList = new Array();
var fileCount = 0;
var fileSizeTotal = 0;


var catMoveSelectVaule;
var subMoveSelectVaule;
var checkArr = [];

var catnamecheck = 0;
var catnamemodify = 0;

function fn_selectDelete(f) {
    var yesNo = confirm('선택된 목록을 삭제 하시겠습니까?');
    if (yesNo == true) {
        if ($('input[name="checkbox"]:checked').length < 1) {
            alert('최소 1개 이상 선택을 해야됩니다.');
            return;
        }
    } else {
        return;
    }
    f.action = 'fimgselectdelete';
    f.submit();
}

function fn_modifyclose() {
    $(".modifycommand").hide();
}

function fn_modify(f) {
    if (catnamemodify == 1) {
        alert('중복된 이름입니다.');
        f.newname.focus();
        return;
    }
    f.action = 'fimgmodify';
    f.submit();
}

function fimgArray(type, subname){

    $.ajax({
        url:"fimgarray",
        type: "get",
        data: {type : type, subname : subname},
        dataType : 'json',
        success: function(res){

           let tag = "";
           for (i = 0; i < res.imgList.length; i++) {
               tag +=
                  '<div class="fname pointer" id="' + res.imgList[i].name + '" >' +
                       '<div class="mainimgdiv" id="' + res.imgList[i].name + '" >' +
                           '<input type="checkbox" class="checkbox pointer" value="' + res.imgList[i].name + ' "  name="checkbox" style="margin-left: 10px; margin: 5px 0px 0px 10px; width: 20px; height: 20px;" >' +
                           '<img class="mainimg" alt="' + res.imgList[i].name + '" src="http://10.10.0.20:9999/img/' + res.imgList[i].name + '">' +
                       '</div>' +
	                   '<div class="fnameText">' +
	                        '<div>' + res.imgList[i].name + '</div>' +
	                        '<div>' + res.imgList[i].createdate  + '</div>'+
	                   '</div>' +
				 '</div>';
           }


           $('.fname').remove();
           $('.flex-container').append(tag);
       },
      error: function (request, status, error) {
           alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
           alert("관리자에게 문의해주세요.");
       }
   });

}
function blockRightClick() {
    return false;
}

function catSelect() {
    catMoveSelectVaule = $('#catSelect option:eq(0)').val();
}

function fn_subHandleOnChange(e) {
    subMoveSelectVaule = e.value;
}

function fn_catHandleOnChange(e) {

    catMoveSelectVaule = e.value;

    $('.subSelect').children('option').remove();

    $.ajax({
        url: 'fimgSubSelect',
        type: 'GET',
        data: { cat: catMoveSelectVaule },
        dataType: 'json',
        success: function (response) {
            if (response.result == 0) {

            } else if (response.result == 1) {
                var sub = response.sub;
                var tag = "";
                if (sub[0] != undefined) {
                    for (i = 0; i < sub.length; i++) {
                        tag += "<option value=" + sub[i] + ">" + sub[i] + "</option>"
                    }
                } else {
                    tag += "<option >해당 하는 폴더가 없습니다.</option>";
                }

                subMoveSelectVaule = sub[0];
                $('.subSelect').append(tag);
            }
        },
        error: function () {
            alert('ajax통신오류');
        }
    });

}

function fn_imgmove(f) {

    if (confirm('이동 하시겠습니까?') == true) {
        if (subMoveSelectVaule != undefined) {
            f.catname.value = catMoveSelectVaule;
            f.subname.value = subMoveSelectVaule;
        } else {
            alert('선택이 옳바르지 않습니다.');
            return;
        }
    } else {
        return;
    }

    f.action = 'fimgmove';
    f.submit();
}

function fn_imgSelectMove(f) {

    var yesNo = confirm('선택된 이미지를 이동 하시겠습니까?');
    if (yesNo == true) {
        f.catname.value = catMoveSelectVaule;
        f.subname.value = subMoveSelectVaule;

        if ($('input[name="checkbox"]:checked').length < 1) {
            alert('최소 1개 이상 선택을 해야됩니다.');
            return;
        }

    } else {
        return;
    }

    f.action = 'fimgselectmove';
    f.submit();
}


$(function () {
    $("#fileDrop").on("dragenter", function (e) { 
        e.preventDefault();
        e.stopPropagation();
    }).on("dragover", function (e) {
        e.preventDefault();
        e.stopPropagation();
    }).on("dragleave", function (e) { 
        e.preventDefault();
        e.stopPropagation();
    }).on("drop", function (e) {  
        e.preventDefault();
        var files = e.originalEvent.dataTransfer.files;

        if (files != null && files != undefined) {

            var tag = "";
            for (i = 0; i < files.length; i++) {
                var f = files[i];

                fileList.push(f);
                var fileName = f.name;
                var fileSize = f.size / 1024 / 1024;
                fileSize = fileSize < 1 ? fileSize.toFixed(3) : fileSize.toFixed(1);
                fileSizeTotal += f.size;
                tag +=
                    "<div class='fileList'>" +
                    "<i class='fas fa-times pointer fileDelete' id ='" + fileName + "'></i>" +
                    "<span class='fileName'>" + fileName + "</span>" +
                    "<span class='fileSize'>" + fileSize + " MB</span>" +
                    "</div>";
            }
            $(this).append(tag);
        }

        $(this).css("background-color", "#FFF");
        $('.fileNotice').css('display', 'none');
        $('.uploadbutton').css('background-color',"#a9a9a9");
        $('.uploadbutton').css('color',"#ffffff");
    });

    $('.upload').click(function (e) {
        e.preventDefault();
        var formData = new FormData($('#uploadForm')[0]);
        formData.append("catname", $('.catname').val());
        formData.append("subname", $('.subname').val());

        if (fileList.length == 0) {
            alert("파일이 없습니다.");
            return;
        }else if(fileSizeTotal / 1024 / 1024 > 20){
             alert("파일 용량이 초과되었습니다. (최대 20MB)");
             return;
        }else if (fileList.length > 0) {
            fileList.forEach(function (f) {
                formData.append("fileList", f);
            });
        }

        $.ajax({
            url: "imgupload",
            data: formData,
            type: 'POST',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            dataType: 'json',
            cache: false,
            timeout:5000,
            success: function (res) {
                if (res.result == 1) {
                    var tag = "";
                    for (i = 0; i < res.imgList.length; i++) {
                        tag +=
                            '<div class="fname pointer" id="' + res.imgList[i].name + '" >' +
                            	'<div class="mainimgdiv" id="' + res.imgList[i].name + '" >' +
                            		'<input type="checkbox" class="checkbox pointer" value="' + res.imgList[i].name + ' "  name="checkbox" style="margin-left: 10px; margin: 5px 0px 0px 10px; width: 20px; height: 20px;" >' +
                            		'<img class="mainimg" alt="' + res.imgList[i].name + '" src="http://10.10.0.20:9999/img/' + res.imgList[i].name + '">' +
                            	'</div>' +
	                            '<div class="fnameText">' +
	                            	'<div>' + res.imgList[i].name + '</div>' +
	                            	'<div>' + res.imgList[i].createdate  + '</div>'+
	                            '</div>' +
							'</div>';
                    }

                    $('.fname').remove();
                    $('.newfileupload').remove();
                    $('.flex-container').append(tag);

                }else if (res.result == 2) {
                    alert("catch");
                }else if (res.result == 3) {
                    alert("중복된 파일명이 존재합니다. ");
                }else if(res.result == 4 ){
                    alert("파일 용량이 초과되었습니다. (최대 20MB)");
                }

                $('.fileList').remove();
                $('.fileNotice').css('display', 'block');
                fileList.splice(0, fileList.length);
            

            }, error: function (request, status, error, timeout) {
                alert("code = " + request.status + " message = " + request.responseText + " error = " + error);
                alert(status + ' + ' + timeout);
                alert("관리자에게 문의해주세요.");
            }
        });

    });

    $(document).on("click", ".fileDelete", function () {
        let index1 = fileList.findIndex(obj => obj.name == $(this).attr('id'));
        let fileSize = fileList[index1].size;
        fileSizeTotal -= fileSize;
        fileList.splice(index1, 1);
        $(this).parents('.fileList').remove();
        if (fileList.length == 0) {
            $('.fileNotice').css('display', 'block');
        }
        
    });

    $('.catnamemodify').keyup(function () {
        var name = $('.catnamemodify').val();
        $.ajax({
            url: 'imgnamecheck',
            type: 'post',
            data: { imgname: name },
            dataType: 'json',
            success: function (response) {
                if (response.result == 0) {
                    $('.catnamemodify').css('outline', '2px solid rgba(0,0,225,0.7)');
                    catnamemodify = 0;
                } else {
                    $('.catnamemodify').css('outline', '2px solid red');
                    catnamemodify = 1;
                }
            },
            error: function () {
                alert('관리자에게 문의해주세요.');
            }
        });
    });

    $('.delete').click(function () {
        var yesNo = confirm('삭제 하시겠습니까?');
        if (yesNo == true) {
            location.href = 'fimgdelete?img=' + $(this).attr('id') + "&sub=" + $('input[type=text][name=rsubname]').val();
        }
    });


    $('.namemodify').click(function () {

        $('input[type=text][name=newname]').val('');
        $('input[type=text][name=newname]').val($('.namemodify').attr('id'));
        $('input[type=text][name=imgname]').val('');
        $('input[type=text][name=imgname]').val($('.namemodify').attr('id'));

        $('input[type=text][name=filepath]').val($(this).find('.modifyA').attr('id'));

        $(".modifycommand").css({ "left": "770px", "top": "360px" }).show(150);

    });

    $('.modifyClose').click(function () {
        $('.inputname').attr('value', '');
        $(".modifycommand").hide();
    });

    $(document).click(function () {
        $(".contextmenu").hide();
    });


    $(document).on("click", ".fname", function (e) {
        $('.fname').css('background', 'white');
        $('.fname').css('color', 'black');
        $(this).css('background', 'rgba(65, 105, 225, 0.1)');
        $(this).css('color', 'blue');
    });


    $(document).on("mousedown", ".fname", function (e) {
        var winWidth = $(document).width();
        var winHeight = $(document).height();

        var posX = e.pageX;
        var posY = e.pageY;

        var menuWidth = $(".contextmenu").width();
        var menuHeight = $(".contextmenu").height();

        var secMargin = 10;

        if (posX + menuWidth + secMargin >= winWidth && posY + menuHeight + secMargin >= winHeight) {
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY - menuHeight - secMargin + "px";
        }
        else if (posX + menuWidth + secMargin >= winWidth) {
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY + secMargin + "px";
        }
        else if (posY + menuHeight + secMargin >= winHeight) {
            posLeft = posX + secMargin + "px";
            posTop = posY - menuHeight - secMargin + "px";
        }
        else {
            posLeft = posX + secMargin + "px";
            posTop = posY + secMargin + "px";
        };

        if (e.which == 3) {
            $('.fname').css('background', 'white');
            $('.fname').css('color', 'black');
            $(this).css('background', 'rgba(65, 105, 225, 0.1)');
            $(this).css('color', 'blue');
            $(".contextmenu").hide();
            $(".contextmenu").css({ "left": posLeft, "top": posTop }).show(300);

            $('.namemodify').attr('id', $(this).attr('id'));
            $('.move').attr('id', $(this).attr('id'));
            $('.delete').attr('id', $(this).attr('id'));

            $(".modifycommand").hide();
            $('.movecommand').hide();
            $('.moveSelectcommand').hide();
            $('.modifyA').attr('id', $(this).find('.mainimgdiv').attr('id'));
        }
    });

    $('#checkboxAll').click(function () {

        if (checkCount % 2 == 0) {
            for (var i = 0; i < $('.checkbox').length; i++) {
                $('.checkbox')[i].checked = true;
            }
            checkCount++;
        } else {
            for (var i = 0; i < $('.checkbox').length; i++) {
                $('.checkbox')[i].checked = false;
            }
            checkCount++;
        }
    });

    $('.move').click(function () {

        $(".catSelect option:eq(0)").prop("selected", true);
        $('.subSelect').children('option').remove();
        $('input[type=text][name=imgname]').val($('.move').attr('id'));
        catMoveSelectVaule = $('#catSelect option:eq(0)').val();
        $.ajax({
            url: 'fimgSubSelect',
            type: 'GET',
            data: { cat: catMoveSelectVaule },
            dataType: 'json',
            success: function (response) {
                if (response.result == 0) {

                } else if (response.result == 1) {
                    var sub = response.sub;
                    var tag = "";
                    for (i = 0; i < sub.length; i++) {
                        tag += "<option class='default' value=" + sub[i] + ">" + sub[i] + "</option>"
                    }
                    subMoveSelectVaule = sub[0];
                    $('.subSelect').append(tag);
                }
            },
            error: function () {
                alert('관리자에게 문의해주세요.');
            }
        });
        $('.movecommand').css({ "left": "770px", "top": "360px" }).show(150);
    });



    $('.moveSelect').click(function () {

        $('.movecommand').hide();
        $('.moveSelectcommand').hide();
        $('.catSelect2 option:eq(0)').prop('selected', true);
        $('.subSelect').children('option').remove();
        $('input[type=text][name=imgname]').val($('.move').attr('id'));
        catMoveSelectVaule = $('#catSelect option:eq(0)').val();

        $.ajax({
            url: 'fimgSubSelect',
            type: 'GET',
            data: { cat: catMoveSelectVaule },
            dataType: 'json',
            success: function (response) {
                if (response.result == 0) {

                } else if (response.result == 1) {
                    var sub = response.sub;
                    var tag = "";
                    for (i = 0; i < sub.length; i++) {
                        tag += "<option class='default' value=" + sub[i] + ">" + sub[i] + "</option>"
                    }
                    subMoveSelectVaule = sub[0];
                    $('.subSelect').append(tag);
                }
            },
            error: function () {
                alert('관리자에게 문의해주세요.');
            }
        });
        $('.moveSelectcommand').css({ "left": "770px", "top": "360px" }).show(150);
    });


    $('.moveClose').click(function () {
        $('.movecommand').hide();
        $('.moveSelectcommand').hide();
    });

    $('#latest').click(function (){
        let type = "latest";
        let subname = $('.subname').val();
        fimgArray(type,subname);
    });
    
    $('#productname').click(function (){
        let type = "productname";
        let subname = $('.subname').val();
        fimgArray(type,subname);
    });

});








