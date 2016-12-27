var qualifyURL = function(pathOrURL) {
   if (!(new RegExp('^(http(s)?[:]//)','i')).test(pathOrURL)) {
     return $(document.body).data('base') + pathOrURL;
   }

   return pathOrURL;
 };

$(function () {
    var eb = new EventBus("/eventbus/");
    eb.onopen = function () {
        eb.registerHandler("to-client", function (err, msg) {
            var item = eval("(function(){return " + msg.body + ";})()");
            var dt = new Date(item.date);
            var mouth = dt.getMonth() + 1;
            var fdt = dt.getFullYear() + "-" + mouth + "-" + dt.getDate() + "  " + dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
            $('#list-message')
                .append('<li>' +
                    '<div class="message-data">' +
                    '<span class="message-data-name"><i class="fa fa-circle online"></i> Me</span>' +
                    '<span class="message-data-time"> - ' + fdt + '</span>' +
                    '</div>' +
                    '<div class="message my-message">' +
                    item.message +
                    '</div>' +
                    '</li>\n');

        });
    };
    $('#form1')
        .submit(function (e) {
            e.preventDefault();
            var message = $('#msg')
                .val();
            if (message.length > 0) {
                eb.publish("to-server", message);
                $('#msg')
                    .val("");
            }
        });


});

function getallch() {
    var t = '<ul style="margin-left: -15%;">';
    var url = qualifyURL("/allChannel");
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        var status = xhr.status;
        if (status == 200) {
            var data = xhr.response;
            $.each(data, function (i, item) {
                var channels = item.NAME.replace('channel_', '');
                t += '<li><a href=# id="' + channels + '" onClick="change_channel(this.id)" ><i class="entypo-qq"></i><span class="title">' + item.NAME.replace('channel_', '') + '</span></a></li>';
            });
            t += '</ul>';

            $('#chanel')
                .append(t);
        } else {
            callback(status);
        }
    };
    xhr.send();
};

function getallMessage() {
    var t = '<ul style="margin-left: -3%;" id="list-message">';
    var url = qualifyURL("/all");
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        var status = xhr.status;
        if (status == 200) {
            var data = xhr.response;
            $.each(data, function (i, item) {
                t += '<li>' +
                    '<div class="message-data">' +
                    '<span class="message-data-name"><i class="fa fa-circle online"></i> ' + item.NAME + '</span>' +
                    '<span class="message-data-time"> - ' + item.DATE + '</span>' +
                    '</div>' +
                    '<div class="message my-message">' +
                    item.MESSAGE +
                    '</div>' +
                    '</li>';

            });
            t += '</ul>';
            $('#clientMessage')
                .append(t);
        } else {
            callback(status);
        }
    };
    xhr.send();
};

function change_channel(chan) {
    var url = qualifyURL("getMessages/" + chan+"");
    $('#list-message')
        .empty();
    window.location.reload();
    var t = '<ul style="margin-left: -3%;" id="list-message">';
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        var status = xhr.status;
        if (status == 200) {
            var data = xhr.response;
            $.each(data, function (i, item) {
                t += '<li>user  <span style="float:right">' + item.DATE + '</span> : ' + item.MESSAGE + '</li>';

            });
            t += '</ul>';
            $('#clientMessage')
                .append(t);
        } else {
            callback(status);
        }
    };
    xhr.send();

};
function current_channel() {
    var url = qualifyURL("/current-channel");
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        var status = xhr.status;
        if (status == 200) {
            var data = xhr.response;
            $.each(data, function (i, item) {
                $('#current_channel').append(item.current);
            });
            
        } else {
            callback(status);
        }
    };
    xhr.send();

};

function add_channel() {
    var urls = "/createChannel/" + document.getElementById('channel-name')
        .value;
    var url = qualifyURL(urls);
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function () {
        var status = xhr.status;
        if (status == 200) {
            var data = xhr.response;
            $.each(data, function (i, item) {

            });
        } else {
            callback(status);
        }
    };
    xhr.send();
    $('#GSCCModal')
        .modal('hide');
    window.location.reload();
};