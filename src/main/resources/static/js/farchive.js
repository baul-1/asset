var catnamecheck = 0;
var catnamemodify = 0;
var checkCount = 2;
function blockRightClick(){
    return false;
}
function fn_imgSelectMove(f) {

    var yesNo = confirm('선택된 이미지를 이동 하시겠습니까?');
    if (yesNo == true) {
        f.catname.value = catMoveSelectVaule;
        f.subname.value = subMoveSelectVaule;

        alert(f.catname.value);
        alert(f.subname.value);

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

    f.action = 'farchiveselectdelete';
    f.submit();
}

function fn_selectRestore(f) {

    var yesNo = confirm('선택된 목록을 복원 하시겠습니까?');

    if (yesNo == true) {
        
        if ($('input[name="checkbox"]:checked').length < 1) {
            alert('최소 1개 이상 선택을 해야됩니다.');
            return;
        }
    } else {
        return;
    }

    f.action = 'farchiverestoreselect';
    f.submit();
}

function fn_modifyclose(){
    $(".modifycommand").hide();
}

function fn_ADD(f){
    if(f.addName.value.trim() == ''){
        alert('내용을 입력하세요.');
        f.addName.focus();
        return;
    }
    if(catnamecheck == 1){
        alert('중복된 이름입니다.');
        f.addName.focus();
        return;
    }
    f.action = 'fcatinsert';
    f.submit();

}
function fn_modify(f){
    if(catnamemodify==1){
        alert('중복된 이름입니다.');
        f.newname.focus();
        return;
    }

    f.action='fcatmodify';
    f.submit();
}

$(function(){
						
    var barCnt = 2;
    
    $('.bar').click(function(){
        var message = '';
        
        if(barCnt % 2 == 0){
            $("#barspan").remove();
            message += '<span id="barspan">';
            message += '<i class="fas fa-times fa-2x"></i>';
            message += '</span>';
            $('.bar').html(message);	
            $('.midnav').show(100);
            barCnt++
        }else {
            $("#barspan").remove();
            message += '<span id="barspan">';
            message += '<i class="fas fa-bars fa-2x"></i>';
            message += '</span>';
            $('.bar').html(message);
            $('.midnav').hide(100);
            barCnt++
        }
    });
    
    $('.varClose').click(function(){
        $('.nav').hide(200);
    });
    $('.delete').click(function(){
        var id = $(this).attr('id');
        if(confirm('삭제 하시겠습니까?')){
            location.href='farchivedelete?delete='+id;
        }
    });
    
    $('.restore').click(function(){
         var id = $(this).attr('id');
    	 if(confirm('복원 하시겠습니까?')){
        	 $('.checkbox').checked = true;
         	location.href='farchiverestore?restore='+id;
         }
    });

        
    
    $(document).click(function(){
        $(".contextmenu").hide();
    });
    
    
    $('.im').click(function(){
        $('.fname').css('background','white');	
        $('.fname').css('color','black');		
        $('.iname').css('background','white');	
        $('.iname').css('color','black');		
        $(this).css('background','rgba(65, 105, 225, 0.1)');			
        $(this).css('color','blue');			
    });
    
    $('.transform').click(function(){
        location.href='uindex';
    });


    
    $('.im').mousedown(function(e){
    
        
        var winWidth = $(document).width();
        var winHeight = $(document).height();
        
        var posX = e.pageX;
        var posY = e.pageY;
        
        var menuWidth = $(".contextmenu").width();
          var menuHeight = $(".contextmenu").height();
          
        var secMargin = 10;
        
        if(posX + menuWidth + secMargin >= winWidth && posY + menuHeight + secMargin >= winHeight){
          posLeft = posX - menuWidth - secMargin + "px";
          posTop = posY - menuHeight - secMargin + "px";
        }
        else if(posX + menuWidth + secMargin >= winWidth){
          posLeft = posX - menuWidth - secMargin + "px";
          posTop = posY + secMargin + "px";
        }
        else if(posY + menuHeight + secMargin >= winHeight){
          posLeft = posX + secMargin + "px";
          posTop = posY - menuHeight - secMargin + "px";
        }
        else {
          posLeft = posX + secMargin + "px";
          posTop = posY + secMargin + "px";
        };
        
        if(e.which == 3){
             $('.fname').css('background','white');
             $('.fname').css('color','black');			
             $('.iname').css('background','white');
             $('.iname').css('color','black');			
             $(this).css('background','rgba(65, 105, 225, 0.1)');
             $(this).css('color','blue');
             $(".contextmenu").hide();
             $(".contextmenu").css({"left": posLeft,"top": posTop}).show(300);
            $('.restore').attr('id',$(this).attr('id'));
            $(".modifycommand").hide();
            $('.delete').attr('id',$(this).attr('id'));
            $('.deleteA').attr('id',$(this).find('.mainimgdiv').attr('id'));
        }
    });

    $('.add').keyup(function(){
        var name = $('.add').val(); 
        $.ajax({
            url: 'catnamecheck',
            type: 'post',
            data: {catname: name},
            dataType : 'json',
            success: function(response){
                if(response.result == 0){
                    $('.add').css('outline', '2px solid rgba(0,0,225,0.7)');
                        catnamecheck = 0;
                }else{
                    $('.add').css('outline', '2px solid red');
                        catnamecheck = 1;
                }
            },
            error : function(){
                alert('ajax통신오류');
            }
        });
    });

    $('.catnamemodify').keyup(function(){
        var name = $('.catnamemodify').val(); 
        $.ajax({
            url: 'catnamecheck',
            type: 'post',
            data: {catname: name},
            dataType : 'json',
            success: function(response){
                if(response.result == 0){
                    $('.catnamemodify').css('outline', '2px solid rgba(0,0,225,0.7)');
                        catnamemodify = 0;
                }else{
                    $('.catnamemodify').css('outline', '2px solid red');
                        catnamemodify = 1;
                }
            },
            error : function(){
                alert('ajax통신오류');
            }
        });
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
});

