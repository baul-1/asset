var catnamemodify = 0;
var subMoveSelectVaule;
var catnamecheck = 0;

function fn_handleOnChange(e){
    subMoveSelectVaule = e.value;
}
function blockRightClick(){
    return false;
}
							 
function fn_modify(f){
    if(catnamemodify==1){
        alert('중복된 이름입니다.');
        f.newname.focus();
        return;
    }

    f.action='fsubmodify';
    f.submit();
}


function fn_submove(f){

    f.subname.value = $('.namemodify').attr('id');
    
    var yesNo = confirm('이동 하시겠습니까?');
    if(yesNo == true){
        if(subMoveSelectVaule == undefined){
            f.mcatname.value = $('.default').val();
        }else {
            f.mcatname.value = subMoveSelectVaule;
        }
    }else {
        return;
    }

    f.action = 'fsubmove';
    f.submit();
}
						
function fn_ADD(f){
    if(f.insertsubname.value.trim() == ''){
       alert('내용을 입력하세요.');
       f.insertsubname.focus();
       return;
   }
   if(catnamecheck == 1){
       alert('중복된 이름입니다.');
       f.insertsubname.focus();
       return;
   }
   f.action = 'fsubinsert';
   f.submit();
}

function fn_modifyclose(){
    $(".modifycommand").hide();
}

$(function(){
						
    $('.move').click(function(){
        $('.movecommand').css({"left": "770px" ,"top": "360px"}).show(150);
        $(".insertCommand").hide();
        $(".modifycommand").hide();
        $('input[type=text][name=insertsubname]').val('');
    });
    
    $('.moveClose').click(function(){
        $('.movecommand').hide();
    });

    $('.plus').click(function(){
        $('.insertCommand').css({"left": "770px" ,"top": "360px"}).show(150);
        $(".modifycommand").hide();
        $(".movecommand").hide();
    });
    
    $('.insertClose').click(function(){
        $('input[type=text][name="insertsubname"]').val('');
        $(".insertCommand").hide();
    });
    
 
     $('.insertsubname').keyup(function(){
         var name = $('.insertsubname').val(); 
         $.ajax({
             url: 'catnamecheck',
             type: 'post',
             data: {catname: name},
             dataType : 'json',
             success: function(response){
                 if(response.result == 0){
                     $('.insertsubname').css('outline', '2px solid rgba(0,0,225,0.7)');
                     $('.insertsubname').css('border', 'none');
                     catnamecheck = 0;
                 }else{
                     $('.insertsubname').css('outline', '2px solid red');
                     $('.insertsubname').css('border', 'none');
                     catnamecheck = 1;
                 }
             },
             error : function(){
                 alert('관리자에게 문의하세요.');
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
                   $('.catnamemodify').css('border', 'none');
                    catnamemodify = 0;
               }else{
                   $('.catnamemodify').css('outline', '2px solid red');
                   $('.catnamemodify').css('border', 'none');
                    catnamemodify = 1;
               }
           },
           error : function(){
               alert('ajax통신오류');
           }
       });
    });

    $('.delete').click(function(){
        var yesNo = confirm('삭제 하시겠습니까?');
        if(yesNo == true){
            location.href='fsubdelete?sub='+$(this).attr('id');
        }
    });

    
    $('.namemodify').click(function(){
        
        var winWidth = $(document).width();
        var winHeight = $(document).height();

        posLeft = winWidth / 2 + 'px';
        posTop = winHeight / 2 + 'px';

        $('input[type=text][name=newname]').val('');
        $('input[type=text][name=newname]').val($('.namemodify').attr('id'));
        $('input[type=text][name=subname]').val('');
        $('input[type=text][name=subname]').val($('.namemodify').attr('id'));
        
        $(".insertCommand").hide();
        $(".movecommand").hide();
        $('input[type=text][name=insertsubname]').val('');
        $(".modifycommand").css({"left": "770px" ,"top": "360px"}).show(150);
        
    });

    $('.modifyClose').click(function(){
        $('.inputname').attr('value','');
        $(".modifycommand").hide();
    });
    
        
        
        
    $('.fname').dblclick(function(){
        location.href='fimg?sub='+$(this).attr('id');
        
    });
    
    
    $(document).click(function(){
        $(".contextmenu").hide();
    });
    
    
    $('.fname').click(function(){
        $('.fname').css('background','white');	
        $('.fname').css('color','black');		
        $(this).css('background','rgba(65, 105, 225, 0.1)');			
        $(this).css('color','blue');			
    });
    

    $('.transform').click(function(){
        location.href='usub?cat='+$('.catname').val();
    });


    
    $('.fname').mousedown(function(e){
    
        
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
             $(this).css('background','rgba(65, 105, 225, 0.1)');
             $(this).css('color','blue');
             $(".contextmenu").hide();
             $(".contextmenu").css({"left": posLeft,"top": posTop}).show(300);
             $('.namemodify').attr('id',$(this).attr('id'));
             $(".modifycommand").hide();
             $(".movecommand").hide();
             $(".insertCommand").hide();
             $('.delete').attr('id',$(this).attr('id'));
        }
    });
});

 