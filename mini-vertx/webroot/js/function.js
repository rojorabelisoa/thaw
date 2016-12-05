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