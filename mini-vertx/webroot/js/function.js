$(function()    
{    
   var eb = new    EventBus("/eventbus/");
   eb.onopen = function () { 
      eb.registerHandler("to-client",    function (err, msg) {
         $('#chat').append(msg.body +    "\n");        
         });
      };
      $('#form1').submit(function (e)    {
         e.preventDefault();        
         var message = $('#msg').val();
         if (message.length > 0) {    
            eb.publish("to-server",    message);
            $('#msg').val("");
         }
   });
   
	
});
function getallch() {
  var t='<ul>';
     var url = "http://localhost:8080/allChannel";
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function() {
      var status = xhr.status;
      if (status == 200) {
      	var data = xhr.response;
        $.each(data, function(i, item) {
          t +='<li><a href=#><span class="title">'+item.NAME.replace('channel_','')+'</span></a></li>';
        });
        t +='</ul>';
        
        $('#chanel').append(t);
      } else {
        callback(status);
      }
    };
    xhr.send();
  };
  function getallMessage() {
    var t='<ul>';
     var url = "http://localhost:8080/all";
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function() {
      var status = xhr.status;
      if (status == 200) {
         var data = xhr.response;
      	$.each(data, function(i, item) {
          t +='<li>'+item.MESSAGE+'</li>';
        	
        });
        t +='</ul>';
        $('#chat').append(t);
      } else {
        callback(status);
      }
    };
    xhr.send();
  };