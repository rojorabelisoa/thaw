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
     var url = "http://localhost:8080/allChannel";
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function() {
      var status = xhr.status;
      if (status == 200) {
      	for(i=0;i<xhr.response.length;i++){
        	$('#channel').append(xhr.response[i].NAME +    " - ");

        }
      } else {
        callback(status);
      }
    };
    xhr.send();
  };
  function getallMessage() {
     var url = "http://localhost:8080/all";
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function() {
      var status = xhr.status;
      if (status == 200) {
        $('#chat').append(xhr.response[0].MESSAGE);
      } else {
        callback(status);
      }
    };
    xhr.send();
  };